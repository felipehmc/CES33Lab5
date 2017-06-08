package simulador;

public class MetricaQtdProcessos implements IMetrica {
	private final int limite;
	
	MetricaQtdProcessos(int _limite){
		limite = _limite;
	}
	
	public boolean estaSobrecarregado(CPU cpu, int processosEmExecucao) {
		if((double)cpu.quantidadeProcessos() > limite)
			return true;
		return false;
	}
}
