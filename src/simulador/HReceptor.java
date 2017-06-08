package simulador;

import java.util.ArrayList;
import java.util.Random;

public class HReceptor extends Heuristica{
	
	Random random = new Random();
	
	HReceptor(int _limiteClocksOciosos, IMetrica _metrica, int _retry, ArrayList <CPU> _cpus){
		super(_limiteClocksOciosos,_metrica,_retry,_cpus);
	}
	
	public int geraNumeroAleatorio(int num){
		int numGerado = random.nextInt(cpus.size());
		while(numGerado == num){
			numGerado = random.nextInt(cpus.size());
		}
		return numGerado;
	}
	
	public int clock(){
		boolean[] terminouProcesso = new boolean[cpus.size()];
		for(int i = 0; i < cpus.size(); i++){
			terminouProcesso[i] = cpus.get(i).executaClock();
		}
		for(int i = 0; i < cpus.size(); i++){
			if(terminouProcesso[i] || cpus.get(i).clocksOciosos >= limiteClocksOciosos){
				heuristicaDoReceptor(cpus.get(i), metrica);
			}
		}
		return ++currentClock;
	}
		
	public void heuristicaDoReceptor(CPU node, IMetrica metrica){
		if(!metrica.estaSobrecarregado(node,processosEmExecucao)){
			int tentativas = 0;
			while(tentativas < retry){
				int randomCPU = geraNumeroAleatorio(node.getID());
				CPU randomNode = cpus.get(randomCPU);
				if(metrica.estaSobrecarregado(randomNode,processosEmExecucao)&& randomNode.quantidadeProcessos() > 1){
					Processo p = randomNode.ultimoProcesso();
					node.addProcesso(p);
					break;
				}
				tentativas++;
			}
		}
		node.clocksOciosos = 0;
	}
}
