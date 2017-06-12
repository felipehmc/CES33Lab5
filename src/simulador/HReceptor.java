package simulador;

import java.util.ArrayList;
import java.util.Random;

public class HReceptor extends Heuristica{
	
	Random random = new Random();
	
	public HReceptor(int _limiteClocksOciosos, IMetrica _metrica, int _retry, ArrayList <CPU> _cpus){
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
			if(terminouProcesso[i]) processosEmExecucao--;
		}
		for(int i = 0; i < cpus.size(); i++){
			if(terminouProcesso[i] || cpus.get(i).clocksOciosos >= limiteClocksOciosos){
				heuristicaDoReceptor(cpus.get(i), metrica);
			}
		}
		return ++currentClock;
	}
		
	public void heuristicaDoReceptor(CPU cpu, IMetrica metrica){
		if(!metrica.estaSobrecarregado(cpu,processosEmExecucao)){
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
					//randomCPU.sondagensRecebidas++; 
					break;
				}
			}
			cpu.sondagensTransmitidas += tentativas;
		}
		cpu.clocksOciosos = 0;
	}
}
