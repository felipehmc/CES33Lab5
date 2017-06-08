package simulador;

public class MetricaTempoMedio implements IMetrica {
	private final double limite;
	
	MetricaTempoMedio(double _limite){
		limite = _limite;
	}
	
	@Override
	public boolean estaSobrecarregado(CPU cpu, int processosEmExecucao) {
		if((double)cpu.quantidadeProcessos()/processosEmExecucao > limite)
			return true;
		return false;
	}
}
