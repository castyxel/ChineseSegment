import java.io.*;
import java.util.*;
import java.nio.*;

public class DictionaryConstructor {
	public static void main(String[] args) throws IOException{
		BufferedReader in = new BufferedReader( new FileReader("pkedic.txt"));
		String s;
		String[] wset = null;
		StringBuilder sb = new StringBuilder();
		while( (s = in.readLine()) != null ) {
			wset = s.split("  ");
			for(int i = 0; i < wset.length; i++){
				if(wset[i].length() < 3) continue;
				int endPt;
				for(endPt = wset[i].length() - 1; endPt >= 0; endPt --)
					if(wset[i].charAt(endPt) == '/') break;
				sb.append(wset[i].substring(0, endPt));
				sb.append("\n");
			}
		}
		in.close();
		
		String str = sb.toString();
		synchronized(in){
			FileWriter fw = new FileWriter("dic.txt");
			fw.write(str);
			fw.close();
		}
	}
}
