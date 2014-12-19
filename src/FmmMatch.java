import java.nio.ByteBuffer;
import java.util.*;
import java.io.*;


public class FmmMatch {
	MyTrie dictionary;
	String dictionaryFile;
	String essayFile;
	String targetFile;

	/*
	 * 把文件的分割好后放入指定文件中去
	 */

	public static void main(String args[]){
		FmmMatch fmm = new FmmMatch();
		fmm.dictionaryFile = "dic.txt";
		fmm.essayFile = "essay.txt";
		fmm.targetFile = "FmmResult.txt";
		fmm.dictionary = new MyTrie();
		try {
			fmm.dictionary.loadDictionary(fmm.dictionaryFile);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		/*
		try{
			FileInputStream fileIn = new FileInputStream("wordTree.ser");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			fmm.dictionary = (MyTrie) in.readObject();
		}catch(IOException e){
			//消息框显示错误
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		
		File in = new File(fmm.targetFile);
		String essay = null;
		try {
			essay = BufferRead.bufferRead(fmm.essayFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//显示框显示未载入文件
			e.printStackTrace();
		}
		essay = CodeConversion.toUnicode(essay);
		synchronized (in) {
			FileWriter fw = null;
			try {
				fw = new FileWriter(fmm.targetFile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			essay = fmm.dictionary.split(essay);
			try {
				fw.write(CodeConversion.toChinese(essay));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				fw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}