package simulador;

import java.util.ArrayList;
import java.util.Random;

public class HEmissor extends Heuristica{
	
	Random random = new Random();
	
	HEmissor(int _limiteClocksOciosos, IMetrica _metrica, int _retry, ArrayList <CPU> _cpus){
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
		boolean[] novoProcesso = new boolean[cpus.size()];
		for(int i = 0; i < cpus.size(); i++){
			novoProcesso[i] = cpus.get(i).executaClock();
			if(novoProcesso[i]) processosEmExecucao--;
		}
		for(int i = 0; i < cpus.size(); i++){
			if(novoProcesso[i] || cpus.get(i).clocksOciosos >= limiteClocksOciosos){
				heuristicaDoEmissor(cpus.get(i), metrica);
			}
		}
		return ++currentClock;
	}
		
	public void heuristicaDoEmissor(CPU cpu, IMetrica metrica){
		//if(!metrica.estaSobrecarregado(cpu,processosEmExecucao)){
			int tentativas = 0;
			while(tentativas < retry){
				tentativas++;
				int randomCPUindex = geraNumeroAleatorio(cpu.getID());
				CPU randomCPU = cpus.get(randomCPUindex);
				randomCPU.sondagensRecebidas++;
				//if(metrica.estaSobrecarregado(randomCPU,processosEmExecucao)&& randomCPU.quantidadeProcessos() > 1){
				if(randomCPU.quantidadeProcessos()>1){	
					Processo p = randomCPU.ultimoProcesso();
					cpu.addProcesso(p);
					
					break;
				}
			}
			cpu.sondagensTransmitidas += tentativas;
		//}
		cpu.clocksOciosos = 0;
	}
}

