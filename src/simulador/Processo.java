package simulador;

public class Processo {

	int CPUCriadora;
	int horaCriacao;
	int tempoDeCPU;
	int clocksRestantesDeCPU;
	
	Processo(int CPU, int hora, int tempo){
		CPUCriadora = CPU;
		horaCriacao = hora;
		tempoDeCPU = tempo;
		clocksRestantesDeCPU = tempo;
	}
	
	public boolean clock(){
		clocksRestantesDeCPU--;
		if(clocksRestantesDeCPU > 1)
			return false;
		return true; // retorna true quando o processo eh finalizado
	}
	
}