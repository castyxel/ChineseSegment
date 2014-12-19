import java.io.*;

/*
 * 缓冲读入，优化文件读入效率
 */
public class BufferRead {
	public static String bufferRead(String filename) throws IOException {
		BufferedReader in = new BufferedReader(new FileReader(filename));
		String s;
		StringBuilder sb = new StringBuilder();
		while ((s = in.readLine()) != null) {
			sb.append(s);
			sb.append("\n");
		}
		in.close();
		return sb.toString();
	}
}
