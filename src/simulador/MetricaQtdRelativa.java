package simulador;

public class MetricaQtdRelativa implements IMetrica{
	
	private final double limite;
	
	MetricaQtdRelativa(double _limite){
		limite = _limite;
	}
	
	public boolean estaSobrecarregado(CPU cpu, int processosEmExecucao){
		if(cpu.taxaMediaDeUtilizacao() > limite)
			return true;
		return false;
	}

}
