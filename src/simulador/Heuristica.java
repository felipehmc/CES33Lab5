package simulador;

import java.util.ArrayList;

public abstract class Heuristica {
	
	public int processosEmExecucao;
	public int currentClock;
	protected int limiteClocksOciosos;
	protected IMetrica metrica;
	protected int retry;
	public ArrayList <CPU> cpus;
	
	protected Heuristica (int _limiteClocksOciosos, IMetrica _metrica, int _retry, ArrayList <CPU> _cpus){
		limiteClocksOciosos = _limiteClocksOciosos;
		metrica = _metrica;
		retry = _retry;
		cpus = _cpus;
		processosEmExecucao = 0;
		currentClock = 0;
	}
	
	public abstract int clock();
}
