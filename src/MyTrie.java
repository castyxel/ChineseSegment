import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;


public class MyTrie implements Serializable{
	
	int[][] ch = new int[1000010][17];//每层的扩展字母
	int[] val = new int[1000010];//标记是否为词的最后一位，并记录词对应的编号
	int sz;

	public MyTrie() {
		sz = 1; //树的尺寸
		Arrays.fill(ch[0], 0);
		Arrays.fill(val, 0);
	}

	private int idx(char x) {
		if (Character.isLowerCase(x))
			return x - 'a' + 10;
		else
			return x - '0';
	}

	/*
	 * 插入词s，在字典里是第v个词，v从1开始计数
	 */
	public void insert(String s, int v) {
		int u = 0, n = s.length();
		for (int i = 0; i < n; i++) {
			int c = idx(s.charAt(i));
			if (ch[u][c] == 0) {
				Arrays.fill(ch[sz], 0);
				ch[u][c] = sz++;
				val[sz] = 0;
			}
			u = ch[u][c];
		}
		val[u] = v++;
	}
	
	/*
	 * Bmm专用，逆向分割
	 * */
	public String bmmSplit(String T) {
		int u = 0;
		int numOfPrintedOut = 1; // 已输出字符     
		StringBuilder buf = new StringBuilder();
		for (int i = 0; i < T.length();) {
			int c = idx(T.charAt(i));
			// 一个中文编码需要4个byte，4个byte一起检查，只要中途字典树回溯到了根部，即找不到该字
			for (int j = 0; j < 4; j++) {
				c = idx(T.charAt(i + j));
				u = ch[u][c];
				if (u == 0)
					break;
			}
			if (u == 0) {
				if (numOfPrintedOut == 1) {
					for (int j = 0; j < 4; j++) {
						buf.append(T.charAt(i + j)); // 如果当前字为单字且字典没有收录，分割当前字
					}
				} else {
					i -= 4; // 如果当前字不为单字，回溯一个字
				}
				buf.append("00200020"); //用双空格分隔
				numOfPrintedOut = 1;
				i += 4;
				continue;
			}
			for (int j = 0; j < 4; j++)
				buf.append(T.charAt(i + j));
			numOfPrintedOut++;
			i += 4;
		}
		return buf.toString();
	}

	/*
	 * 正向接收一个字符串T，并且用"  "分割，规则是FMM
	 */
	public String split(String T) {
		int u = 0;
		int numOfPrintedOut = 1; // 已输出字符
		StringBuilder buf = new StringBuilder();
		for (int i = 0; i < T.length();) {
			int c = idx(T.charAt(i));
			// 一个中文编码需要4个byte，4个byte一起检查，只要中途字典树回溯到了根部，即找不到该字
			for (int j = 0; j < 4; j++) {
				c = idx(T.charAt(i + j));
				u = ch[u][c];
				if (u == 0)
					break;
			}
			if (u == 0) {
				if (numOfPrintedOut == 1) {
					for (int j = 0; j < 4; j++) {
						buf.append(T.charAt(i + j)); // 如果当前字为单字且字典没有收录，分割当前字
					}
				} else {
					i -= 4; // 如果当前字不为单字，回溯一个字
				}
				buf.append("00200020");
				numOfPrintedOut = 1;
				i += 4;
				continue;
			}
			for (int j = 0; j < 4; j++)
				buf.append(T.charAt(i + j));
			numOfPrintedOut++;
			i += 4;
		}
		return buf.toString();
	}
	
	//把dictionaryFile中的词载入到Trie树中，即载入内容到内存
	void loadDictionary(String dictionaryFile) throws IOException{
		BufferedReader in = new BufferedReader(new FileReader(dictionaryFile));
		String s;
		int v = 1;
		while ((s = in.readLine()) != null && s != null) {
			s = CodeConversion.toUnicode(s);
			insert(s, v++);
		}
	}
	
		void bmmVerloadDictionary(String dictionaryFile) throws IOException{
			BufferedReader in = new BufferedReader(new FileReader(dictionaryFile));
			String s;
			int v = 1;
			while ((s = in.readLine()) != null && s != null) {
				s = CodeConversion.bmmVertoUnicode(s);
				insert(s, v++);
			}
		}

	/*
	 * 接收一个字符串T，并在图G上对上面所有出现的词进行连线
	 */
	public void buildWordsEdge(String T, Graph G) {
		int tpt = 0; // T词指针，为4的倍数，记录当前为第几个字
		int parent = 0;
		while (tpt < T.length()) {
			int u = 0; // 树根
			parent = tpt;
			for (int i = tpt; i + 3 < T.length(); i += 4) {
				// 一个中文编码需要4个byte，4个byte一起检查，只要中途字典树回溯到了根部，即找不到该字
				for (int j = 0; j < 4; j++) {
					int c = idx(T.charAt(i + j));
					u = ch[u][c];
					if (u == 0)
						break;
				}
				if (u == 0) {
					break;
				}
				// 如果找到一个单词
				if (val[u] != 0) {
					G.addNode(parent, Integer.valueOf(i+4));
				}
			}
			tpt += 4;
		}
	}
	
	/*
	 * 接收一个字符串T，利用动态规划得到最大概率的分词方案
	 */
	public String maxProbSplit(String T, BiGramModel bigramModel, String startStr) {
		double[] prob = new double[(T.length()/4)+5]; //prob[i]为以位置i为末尾的字符串分割所得最大概率
		String[]  pres = new String[T.length()/4+5]; //pres[i]为到位置i产生最大概率的最后一个词
		int[] pre = new int[T.length() + 5]; //记录前驱，用来输出
		boolean[] isBlock = new boolean[T.length() + 5];
		Arrays.fill(prob, 0.0);
		Arrays.fill(isBlock, false);
		prob[0] = 0;
		pres[0] = startStr;
		int tpt = 0; // T指针，为4的倍数，记录当前为第几个字
		int parent = 0;
		StringBuilder sb = new StringBuilder();
		int id = 1;
		while (tpt < T.length()) {
			int u = 0; // 树根
			boolean isFirstWord = true;; //判断是否为单字用
			sb.delete(0, sb.length());
			for (int i = tpt; i + 3 < T.length(); i += 4) {
				// 一个中文编码需要4个byte，4个byte一起检查，只要中途字典树回溯到了根部，即找不到该字
				int j;
				for (j = 0; j < 4; j++) {
					int c = idx(T.charAt(i + j));
					u = ch[u][c];
					sb.append(T.charAt(i+j));
					if (u == 0)
						break;
				}
				if (u == 0) {
					if(isFirstWord) {
						for(j++; j < 4; j++) {
							sb.append(T.charAt(i+j));
						}
					}
					else break;
				}
				// 如果找到一个单词
				id = (i + 4) / 4;
				if (val[u] != 0 || isFirstWord) {
					isFirstWord = false;
					double vprob = bigramModel.getP(CodeConversion.toChinese(pres[tpt/4]), CodeConversion.toChinese(sb.toString())); //计算到当前位的概率
					vprob = Math.log(vprob);
					if(vprob + prob[tpt/4]> prob[id] || prob[id] == 0.0) {
						prob[id]  = vprob + prob[tpt/4];
						pres[id] = sb.toString();
						pre[i+4] = tpt;
					}
					
				}
			}
			tpt += 4;
			id ++;
		}
		
		sb.delete(0, sb.length());
		int u = T.length();
		while(u != 0) {
			isBlock[u-1] = true;
			u = pre[u];
		}
		for(int i = 0; i < T.length(); i++){
			sb.append(T.charAt(i));
			if(isBlock[i])sb.append("00200020");
		}
		return sb.toString();
	}
}