package simulador;

import java.util.ArrayList;
import java.util.Random;

public class Simulador {
	int DURATION = 300;
	int TMT;
	int amount;
	int qtdCPUS;
	Heuristica heuristica;
	Random random = new Random();
	
	public int geraNumeroAleatorio(int num){
		int numGerado = random.nextInt(num);
		return numGerado;
	}
	
	public int[] gerarTemposAleatoriosMediaTMT(){
		int tempoTotal = amount*TMT;
		int [] tempos = new int [amount];
		int limite = tempoTotal;
		for(int i = 0; i < amount; i++){
			tempos[i] = 1 + geraNumeroAleatorio(limite - amount + i + 1);
			limite = limite - tempos[i];
		}
		return tempos;
	}
	
	Simulador(int _qtdCPUS ,int _TMT, int _qtdProcessos, Heuristica _heuristica){
		TMT = _TMT;
		amount = _qtdProcessos;
		qtdCPUS = _qtdCPUS; 
		heuristica = _heuristica;
	}
	Simulador(){}
	
	void simuladorIntenso(){
		ArrayList <CPU> cpus = new ArrayList<CPU>(); 
		for(int i = 0; i< qtdCPUS; i++){
			cpus.add(new CPU(i));
		}
		while(heuristica.currentClock < DURATION){
						
		}
	}
	
	void simuladorLeve(){
		
	}
	
	public static void main(String[] args){
		Simulador sim = new Simulador();
		sim.amount = 5;
		sim.TMT = 6;
		int num [] = sim.gerarTemposAleatoriosMediaTMT();
		for(int i = 0; i < num.length;i++){
			System.out.println(num[i] + " ");
		}
	}
}
