package simulador;

import java.util.ArrayList;
import java.util.Random;

public class HEmissor extends Heuristica{

	public int processosEmExecucao;
	int retry;
	final int limiteClocksOciosos;
	IMetrica metrica;
	Random random = new Random();
	
	ArrayList <CPU> cpus = new ArrayList<CPU>();
	
	HEmissor(int _limiteClocksOciosos, IMetrica _metrica, int _retry){
		limiteClocksOciosos = _limiteClocksOciosos;
		metrica = _metrica;
		retry = _retry;
	}
	
	public int geraNumeroAleatorio(int num){
		int numGerado = random.nextInt(cpus.size());
		while(numGerado == num){
			numGerado = random.nextInt(cpus.size());
		}
		return numGerado;
	}
	
	public void clock(){
		boolean[] terminouProcesso = new boolean[cpus.size()];
		for(int i = 0; i < cpus.size(); i++){
			terminouProcesso[i] = cpus.get(i).executaClock();
		}
		for(int i = 0; i < cpus.size(); i++){
			if(terminouProcesso[i] || cpus.get(i).clocksOciosos >= limiteClocksOciosos){
				heuristicaDoReceptor(cpus.get(i), metrica);
			}
		}
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
