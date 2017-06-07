package simulador;

import java.util.ArrayList;
import java.util.Random;

public class HReceptor {

	public int processosEmExecucao;
	int retry;
	int limiteClocksOciosos;
	IMetrica metrica;
	Random random = new Random();
	
	ArrayList <Node> nodes = new ArrayList<Node>();
	
	HReceptor(int _limiteClocksOciosos, IMetrica _metrica, int _retry){
		limiteClocksOciosos = _limiteClocksOciosos;
		metrica = _metrica;
		retry = _retry;
	}
	
	public int geraNumeroAleatorio(int num){
		int numGerado = random.nextInt(nodes.size());
		while(numGerado == num){
			numGerado = random.nextInt(nodes.size());
		}
		return numGerado;
	}
	
	public void clock(){
		boolean[] terminouProcesso = new boolean[nodes.size()];
		for(int i = 0; i < nodes.size(); i++){
			terminouProcesso[i] = nodes.get(i).executaClock();
		}
		for(int i = 0; i < nodes.size(); i++){
			if(terminouProcesso[i] || nodes.get(i).clocksOciosos >= limiteClocksOciosos){
				heuristicaDoReceptor(nodes.get(i), metrica);
			}
		}
	}
		
	public void heuristicaDoReceptor(Node node, IMetrica metrica){
		if(!metrica.estaSobrecarregado(node,processosEmExecucao)){
			int tentativas = 0;
			while(tentativas < retry){
				int randomCPU = geraNumeroAleatorio(node.getID());
				Node randomNode = nodes.get(randomCPU);
				if(metrica.estaSobrecarregado(randomNode,processosEmExecucao)&& randomNode.quantidadeProcessos() > 1){
					Processo p = randomNode.ultimoProcesso();
					node.addProcesso(p);
					break;
				}
				tentativas++;
			}
		}
		node.clocksOciosos = 0;
	}
	
	public static void main(String[] args) {
		
	}
}
