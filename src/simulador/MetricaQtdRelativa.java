package simulador;

public class MetricaQtdRelativa implements IMetrica{
	
	private final double limite;
	
	MetricaQtdRelativa(double _limite){
		limite = _limite;
	}
	
	public boolean estaSobrecarregado(CPU cpu, int processosEmExecucao){
		if((double)cpu.quantidadeProcessos()/processosEmExecucao > limite)
			return true;
		return false;
	}

}
