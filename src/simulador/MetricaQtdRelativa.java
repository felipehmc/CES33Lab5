package simulador;

public class MetricaQtdRelativa implements IMetrica{
	
	private final double limite;
	
	MetricaQtdRelativa(double _limite){
		limite = _limite;
	}
	
	public boolean estaSobrecarregado(Node node, int processosEmExecucao){
		if(node.taxaMediaDeUtilizacao() > limite)
			return true;
		return false;
	}

}
