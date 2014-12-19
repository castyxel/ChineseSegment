import java.util.Collections;
import java.util.List;


public class Graph {
	public Node[] maps;

	public Graph() {
		maps = null;
	}

	public Graph(int n) {
		maps = new Node[2*n];
		for (int i = 0; i < n; i++)
			maps[i] = new Node();
	}

	public void addNode(int parent, Integer valueOf) {
		// TODO Auto-generated method stub
		maps[parent].add(valueOf);
	}
	public void sortNodes(int sz) {
		for(int i = 0; i < sz; i++)
			maps[i].sortNode();
	}
	public int getAmountOfDisSonsOf(int id) {
		return maps[id].getDifSon();
	}
	
}
