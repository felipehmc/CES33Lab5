package Simulador3;

import java.util.ArrayList;
import java.util.Random;

import simulador.CPU;
import simulador.Heuristica;
import simulador.IMetrica;
import simulador.Processo;

public class HHibrido extends Heuristica{
	
	boolean[] novoProcesso = new boolean[cpus.size()];
	Random random = new Random();
	
	HHibrido(int _limiteClocksOciosos, IMetrica _metrica, int _retry, ArrayList <CPU> _cpus){
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
			novoProcesso[i] = false;
		}
		for(int i = 0; i < cpus.size(); i++){
			novoProcesso[i] = cpus.get(i).criouNovoProcesso;
			cpus.get(i).criouNovoProcesso = false;
		}
		
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
		
		for(int i = 0; i < cpus.size(); i++){
			if(novoProcesso[i]){
				heuristicaDoEmissor(cpus.get(i), metrica);
			}
		}
		return ++currentClock;
	}
		
	public void heuristicaDoEmissor(CPU cpu, IMetrica metrica){
		int tentativas = 0;
		while(tentativas < retry){
			tentativas++;
			int randomCPUindex = geraNumeroAleatorio(cpu.getID());
			CPU randomCPU = cpus.get(randomCPUindex);
			randomCPU.sondagensRecebidas++;
			if(!metrica.estaSobrecarregado(randomCPU,processosEmExecucao)&& cpu.quantidadeProcessos()>1){
				System.out.println("saindo");
				Processo p = cpu.ultimoProcesso();
				randomCPU.addProcesso(p);
				break;	
			}
		}
		cpu.sondagensTransmitidas += tentativas;
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
					System.out.println("ENtrando");
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

