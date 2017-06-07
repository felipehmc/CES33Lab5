package simulador;

public class MetricaQtdProcessos implements IMetrica {
	private final int limite;
	
	MetricaQtdProcessos(int _limite){
		limite = _limite;
	}
	
	public boolean estaSobrecarregado(Node node, int processosEmExecucao) {
		if((double)node.quantidadeProcessos() > limite)
			return true;
		return false;
	}
}
