import java.nio.ByteBuffer;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.*;

/*
 * 功能：反向最大匹配
 * */
public class BmmMatch {
	MyTrie dictionary;
	String dictionaryFile;
	String essayFile;
	String targetFile;

	/*
	 * 把文件的分割好后放入指定文件中去
	 */

	public static void main(String args[]) throws IOException {
		BmmMatch Bmm = new BmmMatch();
		Bmm.dictionaryFile = "dic.txt";
		Bmm.essayFile = "essay.txt";
		Bmm.targetFile = "BmmResult.txt";

		Bmm.dictionary = new MyTrie();
		Bmm.dictionary.bmmVerloadDictionary(Bmm.dictionaryFile);

		try {
			FileOutputStream fileOut = new FileOutputStream("/tmp/employee.ser");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(Bmm.dictionary);
			out.close();
			fileOut.close();
			System.out.printf("Serialized data is saved in /tmp/employee.ser");
		} catch (IOException i) {
			i.printStackTrace();
		}
		
		
		Bmm.dictionary = null;
	      try
	      {
	         FileInputStream fileIn = new FileInputStream("/tmp/employee.ser");
	         ObjectInputStream in = new ObjectInputStream(fileIn);
	         Bmm.dictionary = (MyTrie) in.readObject();
	         in.close();
	         fileIn.close();
	      }catch(IOException i)
	      {
	         i.printStackTrace();
	         return;
	      }catch(ClassNotFoundException c)
	      {
	         System.out.println("MyTrie class not found");
	         c.printStackTrace();
	         return;
	      }
		File in = new File(Bmm.targetFile);
		String essay = BufferRead.bufferRead(Bmm.essayFile);
		essay = CodeConversion.bmmVertoUnicode(essay);
		synchronized (in) {
			FileWriter fw = new FileWriter(Bmm.targetFile);
			essay = Bmm.dictionary.bmmSplit(essay);
			fw.write(CodeConversion.bmmVertoChinese(essay));
			fw.close();
		}
	}
}