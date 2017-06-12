package simulador;

import java.util.ArrayList;

public abstract class Heuristica {
	protected int processosEmExecucao;
	protected int currentClock;
	protected int limiteClocksOciosos;
	protected IMetrica metrica;
	protected int retry;
	protected ArrayList <CPU> cpus;
	
	Heuristica (int _limiteClocksOciosos, IMetrica _metrica, int _retry, ArrayList <CPU> _cpus){
		limiteClocksOciosos = _limiteClocksOciosos;
		metrica = _metrica;
		retry = _retry;
		cpus = _cpus;
		processosEmExecucao = 0;
		currentClock = 0;
	}
	
	protected abstract int clock();
}
