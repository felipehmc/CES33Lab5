package simulador;

public class MetricaTempoMedio implements IMetrica {
	private final double limite;
	
	MetricaTempoMedio(double _limite){
		limite = _limite;
	}
	
	@Override
	public boolean estaSobrecarregado(Node node, int processosEmExecucao) {
		if((double)node.quantidadeProcessos()/processosEmExecucao > limite)
			return true;
		return false;
	}
}
