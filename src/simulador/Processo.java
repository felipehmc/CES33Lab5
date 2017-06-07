package simulador;

public class Processo {

	int CPUCriadora;
	int horaCriacao;
	int tempoDeCPU;
	int tempoRestanteDeCPU;
	boolean finalizado;
	
	Processo(int CPU, int hora, int tempo){
		CPUCriadora = CPU;
		horaCriacao = hora;
		tempoDeCPU = tempo;
		tempoRestanteDeCPU = tempo;
		finalizado = false;
	}
	
	public boolean clock(){
		tempoRestanteDeCPU--;
		if(tempoRestanteDeCPU > 1)
			return false;
		return true; // retorna true quando o processo eh finalizado
	}
	
}
