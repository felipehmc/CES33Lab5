package Simulador3;

import java.util.ArrayList;
import java.util.Random;

import simulador.CPU;
import simulador.HReceptor;
import simulador.Heuristica;
import simulador.MetricaQtdProcessos;

public class SimuladorHibrido {
	int DURATION = 100;
	int TMT;
	int amount;
	int qtdCPUS;
	Heuristica heuristica;
	
	Random random = new Random();
	
	public int geraNumeroAleatorio(int num){
		int numGerado = random.nextInt(num);
		return numGerado;
	}
	
	public int[] gerarTemposAleatoriosMediaTMT(int _amount){
		int [] tempos = new int [_amount];
		for(int i = 0; i < _amount; i++){
			tempos[i] = (int) (random.nextGaussian()*TMT/3 + TMT);
		}
		return tempos;
	}
	
	public int[] gerarCPUSAleatorias(int qtd){
		int [] indices_cpus_para_novos_processos = new int [qtd];
		for(int i = 0; i < qtd; i++){
			indices_cpus_para_novos_processos[i] = geraNumeroAleatorio(qtdCPUS);
		}
		return indices_cpus_para_novos_processos;
	}
	
	SimuladorHibrido(int _qtdCPUS ,int _TMT, int _qtdProcessos, Heuristica _heuristica){
		TMT = _TMT;
		amount = _qtdProcessos;
		qtdCPUS = _qtdCPUS; 
		heuristica = _heuristica;
	}
	
	void simuladorLeve(){
		simulador(amount/3);
	}
	
	void simuladorIntenso(){
		simulador(amount);
	}
	
	void simulador(int _amount){
		while(heuristica.currentClock <= DURATION){
			if(heuristica.currentClock % TMT == 0){
				int [] temposAleatorios = gerarTemposAleatoriosMediaTMT(_amount);
				int [] cpuAleatorias = gerarCPUSAleatorias(_amount);
				for(int i = 0; i < _amount; i++){
					heuristica.cpus.get(cpuAleatorias[i]).criaNovoProcesso(heuristica.currentClock, temposAleatorios[i]);
					heuristica.cpus.get(cpuAleatorias[i]).criouNovoProcesso = true;
					//System.out.println("Gerou processo de "+ temposAleatorios[i] + " em CPU " + cpuAleatorias[i]);
					heuristica.processosEmExecucao++;
				}
			}
			heuristica.clock();
		}
	}
	
	public static void main(String[] args){
		int N = 4;
		int TMT = 30;
		int qtdProcessos = 20;
		int LIM_CLOCKS_OCIOSOS = 5;
		int RETRY = 5;
		
		ArrayList <CPU> multiprocessadores = new ArrayList<CPU>();
		for(int i = 0; i < N; i++){
			multiprocessadores.add(i, new CPU(i));
		}
		
		Heuristica heuristica = new HHibrido(LIM_CLOCKS_OCIOSOS, new MetricaQtdProcessos(5), RETRY, multiprocessadores); 
		SimuladorHibrido sim = new SimuladorHibrido(N, TMT, qtdProcessos, heuristica);
		
		//Heuristica heuristica2 = new HReceptor(LIM_CLOCKS_OCIOSOS, new MetricaTempoMedio(60), RETRY, multiprocessadores); 
		//Simulador sim = new Simulador(qtdCPUS, TMT, qtdProcessos, heuristica2);
		
		sim.simuladorIntenso();
		//sim.simuladorLeve();
		
		int sumRec = 0;
		int sumTra = 0;
		for(int i = 0; i < N; i++){
			CPU c = multiprocessadores.get(i);
			System.out.println("CPU: " + i + " Trans: "+ c.sondagensTransmitidas + " / Rece: " + c.sondagensRecebidas);
			sumRec+=c.sondagensRecebidas;
			sumTra+=c.sondagensTransmitidas;
		}
		System.out.println(sumRec+ " " + sumTra);
	}
}
