import java.io.*;
import java.util.*;
import java.nio.*;

// 字到点的映射
class wordToPoint {
	final static int size = 17;
	static Integer[][][][] mp = new Integer[size][size][size][size];
	static int sz = 0;

	public void getId() {
		for (int a = 0; a < 16; a++) {
			for (int b = 0; b < 16; b++) {
				for (int c = 0; c < 16; c++) {
					for (int d = 0; d < 16; d++) {
						mp[a][b][c][d] = sz++;
					}
				}
			}
		}
	}
};



public class ShortestPath {
	Graph graph = null;
	MyTrie dictionary;
	String dictionaryFile;
	String essayFile;
	String targetFile;
	int[] dist = null; // 距离数组
	boolean[] vis = null; // 访问数组
	int size; // 一句话长度
	int[] pre = null; // 记录每个点的后继，用来记录最短路径

	
	public void buildMap(String s) {
		size = s.length() + 5;
		pre = new int[size];
		Arrays.fill(pre, 0);
		graph = new Graph(size);
		// 对整句话连边
		for (int i = 0; i < s.length(); i += 4) {
			graph.addNode(i, Integer.valueOf(i + 4));
			pre[i + 4] = i;
		}
		dictionary.buildWordsEdge(s, graph);
	}

	// 建立s的最短路径
	public void dijkstra(String s) {
		dist = new int[size];
		vis = new boolean[size];
		for (int i = 0; i < size; i++) {
			dist[i] = Integer.MAX_VALUE;
			vis[i] = false;
		}
		for (int i = 0; i < graph.maps[0].sons.size(); i++) {
			int son = graph.maps[0].sons.get(i).intValue();
			dist[son] = 1;
			pre[son] = 0;
		}
		dist[0] = 0;
		vis[0] = true;
		for (int i = 0; i < s.length(); i += 4) {
			int minId = 0, minV = Integer.MAX_VALUE; // 遍历找出最短可达点
			for (int j = s.length()-4; j >= 0; j -= 4) {
				if (vis[j] == false && dist[j] < minV) {
					minV = dist[j];
					minId = j;
				}
			}

			vis[minId] = true;
			for (int j = 0; j < graph.maps[minId].sons.size(); j++) {
				int son = graph.maps[minId].sons.get(j).intValue();
				if (!vis[son] && dist[minId] + 1 < dist[son]) {
					dist[son] = dist[minId] + 1;
					pre[son] = minId;
				}
			}
		}
	}

	public String split(String s) {
		buildMap(s);
		dijkstra(s);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < s.length(); i++)
			vis[i] = false;

		int u = s.length();
		while (u != 0) {
			vis[u] = true;
			u = pre[u];
		}
		//vis[0] = true;
		vis[s.length()-4] = true;
		for (int i = 0; i < s.length(); i++) {
			if (vis[i] == true) {
				sb.append("00200020");
			}
			sb.append(s.charAt(i));
		}

		return sb.toString();
	}

	public static void main(String[] args) throws IOException {
		ShortestPath shr = new ShortestPath();
		shr.dictionaryFile = "dic.txt";
		shr.essayFile = "essay.txt";
		shr.targetFile = "ShortestPathResult.txt";
		
		shr.dictionary = new MyTrie();
		shr.dictionary.loadDictionary(shr.dictionaryFile);
		File in = new File(shr.targetFile);
		/*
		 * if(!in.getParentFile().exists()){ in.getParentFile().mkdirs();
		 * in.createNewFile(); }
		 */
		BufferedReader essaybuf = new BufferedReader(new FileReader(
				shr.essayFile));
		FileWriter fw = new FileWriter(shr.targetFile);
		int v;
		StringBuilder sb = new StringBuilder();
		while ((v = essaybuf.read()) != -1 && v!= -1) {
			char s = (char)v;
			sb.append(s);
			if (s == '。' || s == '，' || s == '！'|| s == '\n') {
				String str = CodeConversion.toUnicode(sb.toString());
				str = shr.split(str);
				fw.write(CodeConversion.toChinese(str));
				sb.delete(0, sb.length());
			} 
		}
		fw.close();
	}

};
