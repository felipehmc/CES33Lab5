package simulador;

import java.util.ArrayList;

public class Node {

	private ArrayList<Processo> processos;
	private final int id;
	private int tempoRestanteTotal;
	public int clocksOciosos = 0; // Mede o tempo de ociosidade
	
	Node(int id){
		this.id = id;
		tempoRestanteTotal = 0;
		processos = new ArrayList <Processo>();
	}
	
	public double taxaMediaDeUtilizacao(){
		return (double) tempoRestanteTotal/processos.size();
	}
	
	public void addProcesso(Processo p){
		processos.add(p);
		tempoRestanteTotal += p.tempoRestanteDeCPU;
	}
	
	public void removeProcesso(Processo p){
		processos.remove(p);
		tempoRestanteTotal -= p.tempoRestanteDeCPU;
	}
	
	public int quantidadeProcessos(){
		return processos.size();
	}
	
	public int getID(){
		return id;
	}
	
	public Processo ultimoProcesso(){
		Processo p = processos.get(processos.size()-1);
		removeProcesso(p);
		return p;
	}
	
	public boolean executaClock(){
		if(quantidadeProcessos() > 0){
			//Se o processo terminou de ser executado,
			//ele eh retirado da lista de processos
			clocksOciosos = 0;
			if(tempoRestanteTotal > 0)
				tempoRestanteTotal--;
			if(processos.get(0).clock()){
				processos.remove(0);
				return true; // retorna-se true quando o processo eh finalizado
			}
		}
		clocksOciosos++;
		return false;
	}
}
