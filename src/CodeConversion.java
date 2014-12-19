import java.io.*;
public class CodeConversion {
	public static String toUnicode(String s) {
		StringBuilder s1 = new StringBuilder();
		for (int i =0; i < s.length(); i++) {
			String v = Integer.toHexString(s.charAt(i) & 0xffff);
			if (v.length() == 1)
				s1.append("000");
			else if (v.length() == 2)
				s1.append("00");
			else if (v.length() == 3)
				s1.append("0");
			s1.append(v);

		}
		return s1.toString();
	}
	
	public static String bmmVertoUnicode(String s) {
		StringBuilder s1 = new StringBuilder();
		for (int i =s.length()-1; i >= 0; i--) {
			String v = Integer.toHexString(s.charAt(i) & 0xffff);
			if (v.length() == 1)
				s1.append("000");
			else if (v.length() == 2)
				s1.append("00");
			else if (v.length() == 3)
				s1.append("0");
			s1.append(v);

		}
		return s1.toString();
	}
	/*
	 * unicode转换为utf-8 4位16进制转换
	 */
	public static String toChinese(String str) {
		StringBuilder s = new StringBuilder();
		int v = 0; // 用来保存16进制转换
		int vcnt = 0; // 用来记录是否已经到达4位16进制的最后一位
		for (int i = 0; i < str.length(); i++) {
			if (Character.isLowerCase(str.charAt(i))) {
				v <<= 4;
				v += (int) str.charAt(i) - 'a' + 10;
				vcnt++;
			} else {
				v <<= 4;
				v += (int) str.charAt(i) - '0';
				vcnt++;
			}
			if (vcnt == 4) {
				vcnt = 0;
				s.append((char) v);
				v = 0;
			}
		}

		return s.toString();
	}
	
	/*
	 * unicode转换为utf-8 4位16进制转换
	 */
	public static String bmmVertoChinese(String str) {
		StringBuilder s = new StringBuilder();
		int v = 0; // 用来保存16进制转换
		for (int i = str.length() - 4; i >= 0; i -= 4) {
			v = 0;
			for (int j = 0; j < 4; j++) {
				if (Character.isLowerCase(str.charAt(i + j))) {
					v <<= 4;
					v +=  str.charAt(i+j) - 'a' + 10;
				} else {
					v <<= 4;
					v +=  str.charAt(i+j) - '0';
				}
			}
			s.append((char)v);
		}

		return s.toString();
	}
}
