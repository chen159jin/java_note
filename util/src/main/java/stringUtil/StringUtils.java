/**
 * 说明：文本及日期处理方法
 * 编写者：谢平
 * 日期：Aug 8, 2007
 * 修改日志：
 *    谢平 Aug 8, 2007 增加所有方法属性的中文注释
 * 湖南强智科技版权所有。
 */
package stringUtil;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.BreakIterator;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Set;
import java.util.SimpleTimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
//import uk.ltd.getahead.dwr.WebContext;
//import uk.ltd.getahead.dwr.WebContextFactory;

/**
 * <p>
 * Title:文本及日期处理方法
 * <p>
 * Description:通用工具类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2007
 * </p>
 * <p>
 * Company: 湖南强智科技发展有限公司
 * </p>
 * 
 */
public class StringUtils {
	/**
	 * "符号
	 */
	private static final char[] QUOTE_ENCODE = "&quot;".toCharArray();

	/**
	 * &符号
	 */
	private static final char[] AMP_ENCODE = "&amp;".toCharArray();

	/**
	 * <符号
	 */
	private static final char[] LT_ENCODE = "&lt;".toCharArray();

	/**
	 * >符号
	 */
	private static final char[] GT_ENCODE = "&gt;".toCharArray();

	/**
	 * 摘要算法
	 */
	private static MessageDigest digest = null;

	/**
	 * 编码序号
	 */
	public static String xh = "";

	/**
	 * 编码字符串
	 */
	public static String code = "";
	/**
	 * 总编码长度
	 */
	public static int len = 80;

	/**
	 * 检查用户名中是否含有非法字符
	 * 
	 * @param username
	 * @return boolean
	 */
	public static boolean validateUserName(String username) {
		Pattern p = Pattern.compile("^\\w+$");
		Matcher m = p.matcher(username);
		if (m.find()) {
			return true;
		}
		return false;
	}

	/**
	 * 将字符数组转换成字符串，以","分隔并且加入字符"'"号
	 * 
	 * @param values
	 * @return String
	 */
	public static String Array3String(String[] values) {
		StringBuffer result = new StringBuffer();
		if (values == null)
			return "";
		for (int i = 0; i < values.length; i++) {
			if (result.length() <= 0)
				result.append("'" + values[i] + "'");
			else
				result.append(",").append("'" + values[i] + "'");
		}
		return result.toString();
	}

	/**
	 * 将字符数组转换成字符串，以","分隔
	 * 
	 * @param values
	 * @return String
	 */
	public static String Array2String(String[] values) {
		StringBuffer result = new StringBuffer();
		if (values == null)
			return "";
		for (int i = 0; i < values.length; i++) {
			if (result.length() <= 0)
				result.append(values[i]);
			else
				result.append(",").append(values[i]);
		}
		return result.toString();
	}

	/**
	 * 将对象数组转换成字符串，以","分隔
	 * 
	 * @param values
	 * @return String
	 */
	public static String Array2String(Object[] values) {
		StringBuffer result = new StringBuffer();
		if (values == null)
			return "";
		for (int i = 0; i < values.length; i++) {
			if (result.length() <= 0)
				result.append(values[i].toString());
			else
				result.append(",").append(values[i].toString());
		}
		return result.toString();
	}

	/**
	 * 将LIST对象转换成字符串
	 * 
	 * @param values
	 * @return String
	 */
	public static String Array2String(List values) {
		StringBuffer result = new StringBuffer();
		if (values == null)
			return "";
		for (int i = 0; i < values.size(); i++) {
			if (result.length() <= 0)
				result.append(values.get(i).toString());
			else
				result.append(",").append(values.get(i).toString());
		}
		return result.toString();
	}

	/**
	 * 将文本转换成64位编码
	 * 
	 * @param txt
	 * @return String
	 */
	public static String base64Encode(String txt) {
		if (txt != null && txt.length() > 0) {
			txt = new BASE64Encoder().encode(txt.getBytes());
		}
		return txt;
	}

	/**
	 * 将BYTE形式的字符转换为64位编码
	 * 
	 * @param txt
	 * @return String
	 */
	public static String base64Encode(byte[] txt) {
		String encodeTxt = "";
		if (txt != null && txt.length > 0) {
			encodeTxt = new BASE64Encoder().encode(txt);
		}
		return encodeTxt;
	}

	/**
	 * 将64位编码方式转换为字符串
	 * 
	 * @param txt
	 * @return String
	 */
	public static String base64decode(String txt) {
		if (txt != null && txt.length() > 0) {
			byte[] buf;
			try {
				buf = new BASE64Decoder().decodeBuffer(txt);
				txt = new String(buf);
			} catch (IOException ex) {
			}
		}
		return txt;
	}

	/**
	 * 将64位编码字符串转换为byte方式
	 * 
	 * @param txt
	 * @return byte[]
	 */
	public static byte[] base64decodebyte(String txt) {
		byte[] buf = null;
		if (txt != null && txt.length() > 0) {
			try {
				buf = new BASE64Decoder().decodeBuffer(txt);
			} catch (IOException ex) {
			}
		}
		return buf;
	}

	/**
	 * 替换字符串
	 * 
	 * @param line
	 *            源字符串
	 * @param oldString
	 *            需要替换的字符串
	 * @param newString
	 *            替换为的字符串
	 * @return String
	 */
	public static final String replace(String line, String oldString,
			String newString) {
		if (line == null) {
			return null;
		}
		int i = 0;
		if ((i = line.indexOf(oldString, i)) >= 0) {
			char[] line2 = line.toCharArray();
			char[] newString2 = newString.toCharArray();
			int oLength = oldString.length();
			StringBuffer buf = new StringBuffer(line2.length);
			buf.append(line2, 0, i).append(newString2);
			i += oLength;
			int j = i;
			while ((i = line.indexOf(oldString, i)) > 0) {
				buf.append(line2, j, i - j).append(newString2);
				i += oLength;
				j = i;
			}
			buf.append(line2, j, line2.length - j);
			return buf.toString();
		}
		return line;
	}

	/**
	 * 替换字符串，忽略大小写
	 * 
	 * @param line
	 *            源字符串
	 * @param oldString
	 *            需要替换的字符串
	 * @param newString
	 *            替换为的字符串
	 * @return String
	 */
	public static final String replaceIgnoreCase(String line, String oldString,
			String newString) {
		if (line == null) {
			return null;
		}
		String lcLine = line.toLowerCase();
		String lcOldString = oldString.toLowerCase();
		int i = 0;
		if ((i = lcLine.indexOf(lcOldString, i)) >= 0) {
			char[] line2 = line.toCharArray();
			char[] newString2 = newString.toCharArray();
			int oLength = oldString.length();
			StringBuffer buf = new StringBuffer(line2.length);
			buf.append(line2, 0, i).append(newString2);
			i += oLength;
			int j = i;
			while ((i = lcLine.indexOf(lcOldString, i)) > 0) {
				buf.append(line2, j, i - j).append(newString2);
				i += oLength;
				j = i;
			}
			buf.append(line2, j, line2.length - j);
			return buf.toString();
		}
		return line;
	}

	/**
	 * 替换字符串，忽略大小写，并记住被替换的位置
	 * 
	 * @param line
	 *            源字符串
	 * @param oldString
	 *            需要替换的字符串
	 * @param newString
	 *            替换为的字符串
	 * @param count
	 *            记录被替换的位置信息
	 * @return String
	 */
	public static final String replaceIgnoreCase(String line, String oldString,
			String newString, int[] count) {
		if (line == null) {
			return null;
		}
		String lcLine = line.toLowerCase();
		String lcOldString = oldString.toLowerCase();
		int i = 0;
		if ((i = lcLine.indexOf(lcOldString, i)) >= 0) {
			int counter = 0;
			char[] line2 = line.toCharArray();
			char[] newString2 = newString.toCharArray();
			int oLength = oldString.length();
			StringBuffer buf = new StringBuffer(line2.length);
			buf.append(line2, 0, i).append(newString2);
			i += oLength;
			int j = i;
			while ((i = lcLine.indexOf(lcOldString, i)) > 0) {
				counter++;
				buf.append(line2, j, i - j).append(newString2);
				i += oLength;
				j = i;
			}
			buf.append(line2, j, line2.length - j);
			count[0] = counter;
			return buf.toString();
		}
		return line;
	}

	/**
	 * 替换字符串，并记住被替换的位置
	 * 
	 * @param line
	 *            源字符串
	 * @param oldString
	 *            需要替换的字符串
	 * @param newString
	 *            替换为的字符串
	 * @param count
	 *            记录被替换的位置信息
	 * @return String
	 */
	public static final String replace(String line, String oldString,
			String newString, int[] count) {
		if (line == null) {
			return null;
		}
		int i = 0;
		if ((i = line.indexOf(oldString, i)) >= 0) {
			int counter = 0;
			counter++;
			char[] line2 = line.toCharArray();
			char[] newString2 = newString.toCharArray();
			int oLength = oldString.length();
			StringBuffer buf = new StringBuffer(line2.length);
			buf.append(line2, 0, i).append(newString2);
			i += oLength;
			int j = i;
			while ((i = line.indexOf(oldString, i)) > 0) {
				counter++;
				buf.append(line2, j, i - j).append(newString2);
				i += oLength;
				j = i;
			}
			buf.append(line2, j, line2.length - j);
			count[0] = counter;
			return buf.toString();
		}
		return line;
	}

	/**
	 * 将所有的HTML标记进行转码 包括(ie, &lt;b&gt;&lt;table&gt;, etc)
	 * 
	 * @param in
	 * @return String
	 */
	public static final String escapeHTMLTags(String in) {
		if (in == null) {
			return null;
		}
		char ch;
		int i = 0;
		int last = 0;
		char[] input = in.toCharArray();
		int len = input.length;
		StringBuffer out = new StringBuffer((int) (len * 1.3));
		for (; i < len; i++) {
			ch = input[i];
			if (ch > '>') {
				continue;
			} else if (ch == '<') {
				if (i > last) {
					out.append(input, last, i - last);
				}
				last = i + 1;
				out.append(LT_ENCODE);
			} else if (ch == '>') {
				if (i > last) {
					out.append(input, last, i - last);
				}
				last = i + 1;
				out.append(GT_ENCODE);
			}
		}
		if (last == 0) {
			return in;
		}
		if (i > last) {
			out.append(input, last, i - last);
		}
		return out.toString();
	}

	/**
	 * 使用MD5算法，返回字符串的HASH字段 Hashes a String using the Md5 algorithm and returns
	 * the result as a String of hexadecimal numbers. This method is
	 * synchronized to avoid excessive MessageDigest object creation. If calling
	 * this method becomes a bottleneck in your code, you may wish to maintain a
	 * pool of MessageDigest objects instead of using this method.
	 * <p>
	 * A hash is a one-way function -- that is, given an input, an output is
	 * easily computed. However, given the output, the input is almost
	 * impossible to compute. This is useful for passwords since we can store
	 * the hash and a hacker will then have a very hard time determining the
	 * original password.
	 * <p>
	 * In Jive, every time a user logs in, we simply take their plain text
	 * password, compute the hash, and compare the generated hash to the stored
	 * hash. Since it is almost impossible that two passwords will generate the
	 * same hash, we know if the user gave us the correct password or not. The
	 * only negative to this system is that password recovery is basically
	 * impossible. Therefore, a reset password method is used instead.
	 * 
	 * @param data
	 *            the String to compute the hash of.
	 * @return a hashed version of the passed-in String
	 */
	public synchronized static final String hash(String data) {
		if (digest == null) {
			try {
				digest = MessageDigest.getInstance("MD5");
			} catch (NoSuchAlgorithmException nsae) {
				System.err.println("Failed to load the MD5 MessageDigest. "
						+ "We will be unable to function normally.");
				nsae.printStackTrace();
			}
		}
		// Now, compute hash.
		digest.update(data.getBytes());
		return encodeHex(digest.digest());
	}

	/**
	 * 将bytes数组转换成16进制编码字符串
	 * 
	 * @param bytes
	 * @return String
	 */
	public static final String encodeHex(byte[] bytes) {
		StringBuffer buf = new StringBuffer(bytes.length * 2);
		int i;

		for (i = 0; i < bytes.length; i++) {
			if (((int) bytes[i] & 0xff) < 0x10) {
				buf.append("0");
			}
			buf.append(Long.toString((int) bytes[i] & 0xff, 16));
		}
		return buf.toString().toUpperCase();
	}

	/**
	 * 将16进制的编码转换回bytes数组
	 * 
	 * @param hex
	 * @return String
	 */
	public static final byte[] decodeHex(String hex) {
		char[] chars = hex.toCharArray();
		byte[] bytes = new byte[chars.length / 2];
		int byteCount = 0;
		for (int i = 0; i < chars.length; i += 2) {
			byte newByte = 0x00;
			newByte |= hexCharToByte(chars[i]);
			newByte <<= 4;
			newByte |= hexCharToByte(chars[i + 1]);
			bytes[byteCount] = newByte;
			byteCount++;
		}
		return bytes;
	}

	/**
	 * 将16进制char转换成字符串
	 * 
	 * @param ch
	 * @return String
	 */
	private static final byte hexCharToByte(char ch) {
		switch (ch) {
		case '0':
			return 0x00;
		case '1':
			return 0x01;
		case '2':
			return 0x02;
		case '3':
			return 0x03;
		case '4':
			return 0x04;
		case '5':
			return 0x05;
		case '6':
			return 0x06;
		case '7':
			return 0x07;
		case '8':
			return 0x08;
		case '9':
			return 0x09;
		case 'a':
			return 0x0A;
		case 'b':
			return 0x0B;
		case 'c':
			return 0x0C;
		case 'd':
			return 0x0D;
		case 'e':
			return 0x0E;
		case 'f':
			return 0x0F;
		}
		return 0x00;
	}

	/**
	 * 使用BreakIterator.wordInstance()进行分词转换 Converts a line of text into an
	 * array of lower case words using a BreakIterator.wordInstance().
	 * <p>
	 * 
	 * This method is under the Jive Open Source Software License and was
	 * written by Mark Imbriaco.
	 * 
	 * @param text
	 *            a String of text to convert into an array of words
	 * @return text broken up into an array of words.
	 */
	public static final String[] toLowerCaseWordArray(String text) {
		if (text == null || text.length() == 0) {
			return new String[0];
		}
		ArrayList wordList = new ArrayList();
		BreakIterator boundary = BreakIterator.getWordInstance();
		boundary.setText(text);
		int start = 0;
		for (int end = boundary.next(); end != BreakIterator.DONE; start = end, end = boundary
				.next()) {
			String tmp = text.substring(start, end).trim();
			tmp = replace(tmp, "+", "");
			tmp = replace(tmp, "/", "");
			tmp = replace(tmp, "\\", "");
			tmp = replace(tmp, "#", "");
			tmp = replace(tmp, "*", "");
			tmp = replace(tmp, ")", "");
			tmp = replace(tmp, "(", "");
			tmp = replace(tmp, "&", "");
			if (tmp.length() > 0) {
				wordList.add(tmp);
			}
		}
		return (String[]) wordList.toArray(new String[wordList.size()]);
	}

	/**
	 * Array of numbers and letters of mixed case. Numbers appear in the list
	 * twice so that there is a more equal chance that a number will be picked.
	 * We can use the array to get a random number or letter by picking a random
	 * array index.
	 */
	/**
	 * 数字和字符char[] random对象来操作
	 */
	private static char[] numbersAndLetters = ("0123456789abcdefghijklmnopqrstuvwxyz"
			+ "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray();

	/**
	 * 使用随机的方式取得数个字符，来自numbersAndLetters中的定义
	 * 
	 * @param length
	 * @return String
	 */
	public static final String randomString(int length) {
		if (length < 1) {
			return null;
		}
		// Create a char buffer to put random letters and numbers in.
		char[] randBuffer = new char[length];
		for (int i = 0; i < randBuffer.length; i++) {
			randBuffer[i] = numbersAndLetters[getRandom(71)];
		}
		return new String(randBuffer);
	}

	/**
	 * 获得一个随机数
	 * 
	 * @param maxRandom
	 *            随机数的取值访问
	 * @return int
	 */
	public static final int getRandom(int maxRandom) {
		return (int) ((1 - Math.random()) * maxRandom);
	}

	/**
	 * Intelligently chops a String at a word boundary (whitespace) that occurs
	 * at the specified index in the argument or before. However, if there is a
	 * newline character before <code>length</code>, the String will be chopped
	 * there. If no newline or whitespace is found in <code>string</code> up to
	 * the index <code>length</code>, the String will chopped at
	 * <code>length</code>.
	 * <p>
	 * For example, chopAtWord("This is a nice String", 10) will return "This is
	 * a" which is the first word boundary less than or equal to 10 characters
	 * into the original String.
	 * 
	 * @param string
	 *            the String to chop.
	 * @param length
	 *            the index in <code>string</code> to start looking for a
	 *            whitespace boundary at.
	 * @return a substring of <code>string</code> whose length is less than or
	 *         equal to <code>length</code>, and that is chopped at whitespace.
	 */
	public static final String chopAtWord(String string, int length) {
		if (string == null) {
			return string;
		}

		char[] charArray = string.toCharArray();
		int sLength = string.length();
		if (length < sLength) {
			sLength = length;
		}

		// First check if there is a newline character before length; if so,
		// chop word there.
		for (int i = 0; i < sLength - 1; i++) {
			// Windows
			if (charArray[i] == '\r' && charArray[i + 1] == '\n') {
				return string.substring(0, i + 1);
			}
			// Unix
			else if (charArray[i] == '\n') {
				return string.substring(0, i);
			}
		}
		// Also check boundary case of Unix newline
		if (charArray[sLength - 1] == '\n') {
			return string.substring(0, sLength - 1);
		}

		// Done checking for newline, now see if the total string is less than
		// the specified chop point.
		if (string.length() < length) {
			return string;
		}

		// No newline, so chop at the first whitespace.
		for (int i = length - 1; i > 0; i--) {
			if (charArray[i] == ' ') {
				return string.substring(0, i).trim();
			}
		}

		// Did not find word boundary so return original String chopped at
		// specified length.
		return string.substring(0, length);
	}

	/**
	 * 将XML标记中敏感的字符换成转义字符
	 * 
	 * @param string
	 * @return String
	 */
	public static final String escapeForXML(String string) {
		if (string == null) {
			return null;
		}
		char ch;
		int i = 0;
		int last = 0;
		char[] input = string.toCharArray();
		int len = input.length;
		StringBuffer out = new StringBuffer((int) (len * 1.3));
		for (; i < len; i++) {
			ch = input[i];
			if (ch > '>') {
				continue;
			} else if (ch == '<') {
				if (i > last) {
					out.append(input, last, i - last);
				}
				last = i + 1;
				out.append(LT_ENCODE);
			} else if (ch == '&') {
				if (i > last) {
					out.append(input, last, i - last);
				}
				last = i + 1;
				out.append(AMP_ENCODE);
			} else if (ch == '"') {
				if (i > last) {
					out.append(input, last, i - last);
				}
				last = i + 1;
				out.append(QUOTE_ENCODE);
			}
		}
		if (last == 0) {
			return string;
		}
		if (i > last) {
			out.append(input, last, i - last);
		}
		return out.toString();
	}

	/**
	 * 将特殊字符转换成转义字符
	 * 
	 * @param string
	 * @return String
	 */
	public static final String escapeForSpecial(String string) {
		if (string == null) {
			return null;
		}
		char ch;
		int i = 0;
		int last = 0;
		char[] input = string.toCharArray();
		int len = input.length;
		StringBuffer out = new StringBuffer((int) (len * 1.3));
		for (; i < len; i++) {
			ch = input[i];
			if (ch > '>') {
				continue;
			} else if (ch == '<') {
				if (i > last) {
					out.append(input, last, i - last);
				}
				last = i + 1;
				out.append(LT_ENCODE);
			} else if (ch == '&') {
				if (i > last) {
					out.append(input, last, i - last);
				}
				last = i + 1;
				out.append(AMP_ENCODE);
			} else if (ch == '"') {
				if (i > last) {
					out.append(input, last, i - last);
				}
				last = i + 1;
				out.append(QUOTE_ENCODE);
			} else if (ch == '>') {
				if (i > last) {
					out.append(input, last, i - last);
				}
				last = i + 1;
				out.append(GT_ENCODE);
			}
		}
		if (last == 0) {
			return string;
		}
		if (i > last) {
			out.append(input, last, i - last);
		}
		return out.toString();
	}

	/**
	 * 将转义字符转换成XML中的标准字符
	 * 
	 * @param string
	 * @return String
	 */
	public static final String unescapeFromXML(String string) {
		string = replace(string, "&lt;", "<");
		string = replace(string, "&gt;", ">");
		string = replace(string, "&quot;", "\"");
		return replace(string, "&amp;", "&");
	}

	private static final char[] zeroArray = "0000000000000000".toCharArray();

	/**
	 * 指定字符串长度，不足的加0
	 * 
	 * @param string
	 * @param length
	 * @return String
	 */
	public static final String zeroPadString(String string, int length) {
		if (string == null || string.length() > length) {
			return string;
		}
		StringBuffer buf = new StringBuffer(length);
		buf.append(zeroArray, 0, length - string.length()).append(string);
		return buf.toString();
	}

	/**
	 * 将日期转换成毫秒，以15位记录，不足的补0
	 * 
	 * @param date
	 * @return String
	 */
	public static final String dateToMillis(Date date) {
		return zeroPadString(Long.toString(date.getTime()), 15);
	}

	/**
	 * 将集合类型转换成字符串，并以指定的分割符分割
	 * 
	 * @param c
	 *            集合
	 * @param spilt
	 *            分隔符
	 * @return String
	 */
	public static final String collectionToString(Collection c, String spilt) {
		if (c == null) {
			return null;
		}
		if (spilt == null) {
			return null;
		}
		String ret = "";
		ArrayList a = new ArrayList(c);
		try {
			for (int i = 0; i < a.size(); i++) {
				String t = (String) a.get(i);
				if (i == a.size() - 1) {
					ret = ret + t;
				} else {
					ret = ret + t + spilt;
				}
			}
			return ret;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 根据指定的长度构建一个密码，密码中没有包含0,o,l和I，以免误解
	 * 
	 * @param length
	 * @return String
	 */
	public static String genPassword(int length) {
		if (length < 1) {
			return null;
		}
		String[] strChars = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "a",
				"b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "m", "n",
				"p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "a" };
		// 没有0,o,l和I，以免误解
		StringBuffer strPassword = new StringBuffer();
		int nRand = (int) java.lang.Math.round(java.lang.Math.random() * 100);
		for (int i = 0; i < length; i++) {
			nRand = (int) java.lang.Math.round(java.lang.Math.random() * 100);
			strPassword.append(strChars[nRand % (strChars.length - 1)]);
		}
		return strPassword.toString();
	}

	/**
	 * 根据指定的长度构建一个密码，以数字组成
	 * 
	 * @param length
	 * @return String
	 */
	public static String genNumPassword(int length) {
		if (length < 1) {
			return null;
		}
		String[] strChars = { "1", "2", "3", "4", "5", "6", "7", "8", "9" };
		StringBuffer strPassword = new StringBuffer();
		int nRand = (int) java.lang.Math.round(java.lang.Math.random() * 100);
		for (int i = 0; i < length; i++) {
			nRand = (int) java.lang.Math.round(java.lang.Math.random() * 100);
			strPassword.append(strChars[nRand % (strChars.length - 1)]);
		}
		return strPassword.toString();
	}

	/**
	 * 根据指定的长度构建一个空格组成字符串
	 * 
	 * @param length
	 * @return String
	 */
	public static String genEmptyString(int length) {
		if (length < 1) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			sb.append(" ");
		}
		return sb.toString();
	}

	/**
	 * 输入URL，得到这个URL中的HTML代码
	 * 
	 * @param str
	 * @return String
	 */
	public static String getHTML(String str) {
		// 判断该页面是否存在
		if (urlExists(str)) {
			StringBuffer sb = new StringBuffer();
			try {
				URL urlObj = new URL(str);
				InputStream streamObj = urlObj.openStream();
				InputStreamReader readerObj = new InputStreamReader(streamObj);
				BufferedReader buffObj = new BufferedReader(readerObj);
				String strLine;
				while ((strLine = buffObj.readLine()) != null) {
					sb.append(strLine);
				}
				buffObj.close();
			} catch (Exception e) {
				return sb.toString();
			}
			return sb.toString();
		} else {
			return null;
		}
	}

	/**
	 * 输入一个数字得到它的ASCII码
	 * 
	 * @param digit
	 * @return String
	 */
	public static String getAsciiString(int digit) {
		byte ret[] = new byte[1];
		ret[0] = (byte) digit;
		return new String(ret);
	}

	/**
	 * 输入一个字符串得到它的ASCII码
	 * 
	 * @param s
	 * @return String
	 */
	public static int getAsciiNum(String s) {
		if (s.length() < 1) {
			return 0;
		}
		byte b = s.getBytes()[0];
		return b;
	}

	/**
	 * 获得当前时间，并转换成yyyyMMddHHmmss格式
	 * 
	 * @return String
	 */
	public static String getCurrTime() {
		return formatDateByFormatStr(new Date(), "yyyyMMddHHmmss");
	}

	/**
	 * 格式化日期yyyy-MM-dd
	 * 
	 * @param date
	 * @return String
	 */
	public static String formatDate(Date date) {
		return formatDateByFormatStr(date, "yyyy-MM-dd");
	}

	/**
	 * 格式化日期时间yyyy-MM-dd HH:mm:ss
	 * 
	 * @param date
	 * @return String
	 */
	public static String formatDateTime(Date date) {
		return formatDateByFormatStr(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 格式化日期yyyy/MM/dd
	 * 
	 * @param myDate
	 * @return String
	 */
	public static String formatDate2(Date myDate) {
		return formatDateByFormatStr(myDate, "yyyy/MM/dd");
	}

	/**
	 * 格式化日期MM-dd HH:mm
	 * 
	 * @param myDate
	 * @return String
	 */
	public static String formatDate3(Date myDate) {
		return formatDateByFormatStr(myDate, "MM-dd HH:mm");
	}

	/**
	 * 格式化日期yyyyMMdd
	 * 
	 * @param myDate
	 * @return String
	 */
	public static String formatDate4(Date myDate) {
		return formatDateByFormatStr(myDate, "yyyyMMdd");
	}

	/**
	 * 格式化日期yyyy-MM-dd，以分解年月日实现
	 * 
	 * @param myDate
	 * @return String
	 */
	public static String formatDate5(Date myDate) {
		return formatDateByFormatStr(myDate, "yyyy-MM-dd");
	}

	/**
	 * 格式化日期时间yyyy-MM-dd HH:mm
	 * 
	 * @param myDate
	 * @return String
	 */
	public static String formatDate6(Date myDate) {
		return formatDateByFormatStr(myDate, "yyyy-MM-dd HH:mm");
	}

	/**
	 * 格式化日期时间yyyy-MM-dd HH:mm:ss
	 * 
	 * @param myDate
	 * @return String
	 */
	public static String formatDate6a(Date myDate) {
		return formatDateByFormatStr(myDate, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 格式化日期yyyy年MM月dd日
	 * 
	 * @param myDate
	 * @return String
	 */
	public static String formatDate7(Date myDate) {
		return formatDateByFormatStr(myDate, "yyyy年MM月dd日");
	}

	/**
	 * 通过格式化字段来格式化日期
	 * 
	 * @param myDate
	 *            输入的日期
	 * @param formatStr
	 *            需要格式化的样式例如yyyy-M-D
	 * @return 格式化后的日期类型
	 */
	public static String formatDateByFormatStr(Date myDate, String formatStr) {
		SimpleDateFormat formatter = new SimpleDateFormat(formatStr);
		return formatter.format(myDate);
	}

	/**
	 * 通过格式化字段来格式化日期
	 * 
	 * @param myDate
	 *            输入的日期
	 * @param formatStr
	 *            需要格式化的样式例如yyyy-M-D
	 * @return 格式化后的日期类型
	 */
	public static String formatDateByFormatStr(Object myDate, String formatStr) {
		if (myDate == null)
			return "";
		SimpleDateFormat formatter = new SimpleDateFormat(formatStr);
		return formatter.format(myDate);
	}

	/**
	 * 通过格式参数格式化float
	 * 
	 * @param f
	 *            输入的值
	 * @param formatStr
	 *            需要格式化的样式例如####.##
	 * @return String
	 */
	public static String formatDouble(float f, String formatStr) {
		DecimalFormat format = new DecimalFormat(formatStr);
		return format.format(f);
	}

	/**
	 * 通过格式参数格式化Object
	 * 
	 * @param f
	 *            输入的值
	 * @param formatStr
	 *            需要格式化的样式例如####.##
	 * @return String
	 */
	public static String formatDouble(Object f, String formatStr) {
		if (f == null)
			return "";
		DecimalFormat format = new DecimalFormat(formatStr);
		return format.format(f);
	}

	/**
	 * 通过格式参数格式化double
	 * 
	 * @param d
	 *            输入的值
	 * @param formatStr
	 *            需要格式化的样式例如####.##
	 * @return String
	 */
	public static String formatDouble(double d, String formatStr) {
		DecimalFormat format = new DecimalFormat(formatStr);
		return format.format(d);
	}

	/**
	 * 格式化为RSS文件需要的rfc822规范格式的时间
	 * 
	 * @param myDate
	 *            Date
	 * @return String
	 */
	public static String formatDateForRss(Date myDate) {
		SimpleDateFormat sdfTemp = new SimpleDateFormat(
				"EEE, d MMM yyyy HH:mm:ss z", Locale.US);
		SimpleTimeZone aZone = new SimpleTimeZone(8, "GMT");
		sdfTemp.setTimeZone(aZone);
		return sdfTemp.format(myDate);
	}

	/**
	 * 将年月日转换成long
	 * 
	 * @param year
	 * @param month
	 * @param date
	 * @return String
	 */
	public static long Date2Long(int year, int month, int date) {
		Calendar cld = Calendar.getInstance();
		month = month - 1;
		cld.set(year, month, date);
		return cld.getTime().getTime();
	}

	/**
	 * 将年月日时分秒转换成long
	 * 
	 * @param year
	 * @param month
	 * @param date
	 * @param hour
	 * @param minute
	 * @param second
	 * @return long
	 */
	public static long Time2Long(int year, int month, int date, int hour,
			int minute, int second) {
		Calendar cld = Calendar.getInstance();
		month = month - 1;
		cld.set(year, month, date, hour, minute, second);
		return cld.getTime().getTime();
	}

	/**
	 * 从一个long型的时间中获得年
	 * 
	 * @param t
	 * @return int
	 */
	public static int getYear(long t) {
		Calendar cld = Calendar.getInstance();
		if (t > 0) {
			cld.setTime(new java.util.Date(t));
		}
		return cld.get(Calendar.YEAR);
	}

	/**
	 * 从long型时间中获得月
	 * 
	 * @param t
	 * @return int
	 */
	public static int getMonth(long t) {
		Calendar cld = Calendar.getInstance();
		if (t > 0) {
			cld.setTime(new java.util.Date(t));
		}
		return cld.get(Calendar.MONTH) + 1;
	}

	/**
	 * 从long型时间中获得日
	 * 
	 * @param t
	 * @return int
	 */
	public static int getDay(long t) {
		Calendar cld = Calendar.getInstance();
		if (t > 0) {
			cld.setTime(new java.util.Date(t));
		}
		return cld.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 从long型时间中获得小时
	 * 
	 * @param t
	 * @return int
	 */
	public static int getHour(long t) {
		Calendar cld = Calendar.getInstance();
		if (t > 0) {
			cld.setTime(new java.util.Date(t));
		}
		return cld.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * 从long型时间中获得分
	 * 
	 * @param t
	 * @return int
	 */
	public static int getMinute(long t) {
		Calendar cld = Calendar.getInstance();
		if (t > 0) {
			cld.setTime(new java.util.Date(t));
		}
		return cld.get(Calendar.MINUTE);
	}

	/**
	 * 从long型时间中获得秒
	 * 
	 * @param t
	 * @return int
	 */
	public static int getSecond(long t) {
		Calendar cld = Calendar.getInstance();
		if (t > 0) {
			cld.setTime(new java.util.Date(t));
		}
		return cld.get(Calendar.SECOND);
	}

	/**
	 * 从Date中获得年
	 * 
	 * @param date
	 * @return int
	 */
	public static int getYear(Date date) {
		Calendar cld = Calendar.getInstance();
		cld.setTime(date);
		return cld.get(Calendar.YEAR);
	}

	/**
	 * 从Date中获得月
	 * 
	 * @param date
	 * @return int
	 */
	public static int getMonth(Date date) {
		Calendar cld = Calendar.getInstance();
		cld.setTime(date);
		return cld.get(Calendar.MONTH) + 1;
	}

	/**
	 * 从Date中获得日
	 * 
	 * @param date
	 * @return int
	 */
	public static int getDay(Date date) {
		Calendar cld = Calendar.getInstance();
		cld.setTime(date);
		return cld.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 从Date中获得小时
	 * 
	 * @param date
	 * @return int
	 */
	public static int getHour(Date date) {
		Calendar cld = Calendar.getInstance();
		cld.setTime(date);
		return cld.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * 从Date中获得分钟
	 * 
	 * @param date
	 * @return int
	 */
	public static int getMinute(Date date) {
		Calendar cld = Calendar.getInstance();
		cld.setTime(date);
		return cld.get(Calendar.MINUTE);
	}

	/**
	 * 从Date中获得秒
	 * 
	 * @param date
	 * @return int
	 */
	public static int getSecond(Date date) {
		Calendar cld = Calendar.getInstance();
		cld.setTime(date);
		return cld.get(Calendar.SECOND);
	}

	/**
	 * 获得当前年份
	 * 
	 * @return int
	 */
	public static int getYear() {
		Calendar cld = Calendar.getInstance();
		cld.setTime(new java.util.Date());
		return cld.get(Calendar.YEAR);
	}

	/**
	 * 获得当前月份
	 * 
	 * @return int
	 */
	public static int getMonth() {
		Calendar cld = Calendar.getInstance();
		cld.setTime(new java.util.Date());
		return cld.get(Calendar.MONTH) + 1;
	}

	/**
	 * 获得当前日期
	 * 
	 * @return int
	 */
	public static int getDay() {
		Calendar cld = Calendar.getInstance();
		cld.setTime(new java.util.Date());
		return cld.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 将中文逗号换成英文逗号
	 * 
	 * @param text
	 * @return String
	 */
	public static String replaceComma(String text) {
		if (text != null) {
			text = replace(text, "，", ",");
		}
		return text;
	}

	/**
	 * 将\n换行标记换成<br>
	 * 
	 * @param text
	 * @return String
	 */
	public static String replaceBr(String text) {
		if (text != null) {
			text = replace(text, "\n", "<BR>");
		}
		return text;
	}

	/**
	 * 获得系统时间毫秒数
	 * 
	 * @return int
	 */
	public static long getLongTime() {
		return System.currentTimeMillis();
	}

	/**
	 * 获得当前时间是星期几
	 * 
	 * @param dt
	 * @return String
	 */
	public static String getWeekOfDate(Date dt) {
		String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		Calendar cal = Calendar.getInstance();

		cal.setTime(dt);
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0)
			w = 0;
		return weekDays[w];

	}

	/**
	 * 检查一个字符串是null还是空的
	 * 
	 * @param param
	 * @return boolean
	 */
	public static boolean nullOrBlank(String param) {
		return (param == null || param.length() == 0 || param.trim().equals("") || param
				.trim().equalsIgnoreCase("null")) ? true : false;
	}

	/**
	 * 将字符串去除空格，如果字符串为空则返回""
	 * 
	 * @param param
	 * @return String
	 */
	public static String notNull(String param) {
		return param == null ? "" : param.trim();
	}

	/**
	 * 将字符串转换成Boolean，如果字符串开头为1,y,Y,t,T都被认为是true
	 * 
	 * @param param
	 * @return boolean
	 */
	public static boolean parseBoolean(String param) {
		if (nullOrBlank(param)) {
			return false;
		}
		switch (param.charAt(0)) {
		case '1':
		case 'y':
		case 'Y':
		case 't':
		case 'T':
			return true;
		}
		return false;
	}

	/**
	 * dateToString(Date inDate) 把日期型转换成字符型"yyyy-MM-dd HH:mm:ss"
	 * 
	 * @param inDate
	 *            Date
	 * @return String
	 */
	public static String dateToString(Date inDate) {
		String outDateStr = "";
		if (inDate != null) {
			SimpleDateFormat formatter = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			outDateStr = formatter.format(inDate);
		}
		return outDateStr;
	}

	/**
	 * 把日期型转换成字符型"yyyy-MM-dd"
	 * 
	 * @param inDate
	 *            Date 需要转换的日期时间
	 * @return outDateStr String
	 */
	public static String dateToSimpleStr(Date inDate) {
		String outDateStr = "";
		if (inDate != null) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			outDateStr = formatter.format(inDate);
		}
		return outDateStr;
	}

	/**
	 * 把字符型"yyyy-MM-dd HH:mm:ss"转换成日期型
	 * 
	 * @param s
	 *            String 需要转换的日期时间字符串
	 * @return theDate Date
	 */
	public static Date stringToDateWithTime(String s) {
		Date theDate = new Date();
		try {
			if (s != null) {
				SimpleDateFormat dateFormatter = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				theDate = dateFormatter.parse(s);
			} else {
				theDate = null;
			}
		} catch (ParseException pe) {
			pe.printStackTrace();
		}
		return theDate;
	}

	/**
	 * 把字符型"yyyy-MM-dd"转换成日期型
	 * 
	 * @param s
	 *            String 需要转换的日期时间字符串
	 * @return theDate Date
	 */
	public static Date stringToDate(String s) {
		Date theDate = new Date();
		try {
			if (s != null) {
				SimpleDateFormat dateFormatter = new SimpleDateFormat(
						"yyyy-MM-dd");
				theDate = dateFormatter.parse(s);
			} else {
				theDate = null;
			}
		} catch (ParseException pe) {
			pe.printStackTrace();
		}
		return theDate;
	}

	/**
	 * Date +/- int = 新的Date
	 * 
	 * @param inDate
	 *            Date 原日期
	 * @param AddDateInt
	 *            int 要加减的天数
	 * @return ReturnDate Date 新的Date
	 */
	public static Date dateAddInt(Date inDate, int AddDateInt) {
		Calendar currentC = Calendar.getInstance();
		currentC.setTime(inDate);
		currentC.add(Calendar.DAY_OF_YEAR, AddDateInt);
		return currentC.getTime();
	}

	/**
	 * Date +/- int = 新的Date
	 * 
	 * @param inDate
	 *            Date 原日期,字符串型
	 * @param AddDateInt
	 *            int 要加减的天数
	 * @return ReturnDate Date 新的Date
	 */
	public static Date dateAddInt(String inDate, int AddDateInt) {
		try {
			Date date = new Date();
			if (inDate != null) {
				SimpleDateFormat formatter = new SimpleDateFormat("yy-MM-dd");
				date = formatter.parse(inDate);
			}
			Calendar currentC = Calendar.getInstance();
			currentC.setTime(date);
			currentC.add(Calendar.DAY_OF_YEAR, AddDateInt);
			return currentC.getTime();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * 获得星期几 <br>
	 * 星期日0--星期六6
	 * 
	 * @param inDate
	 * @return dayOfWeek int 获得星期几
	 */
	public static int dayOfWeek(Date inDate) {
		int dayOfWeek = 0;
		Calendar theCalendar = new GregorianCalendar();
		String DateStr = dateToString(inDate);
		theCalendar.set(Integer.parseInt(DateStr.substring(0, 4)),
				Integer.parseInt(DateStr.substring(5, 7)) - 1,
				Integer.parseInt(DateStr.substring(8, 10)));
		dayOfWeek = theCalendar.get(Calendar.DAY_OF_WEEK) - 1;
		if (dayOfWeek == 7) {
			dayOfWeek = 0;
		}
		return dayOfWeek;
	}

	public static int dayOfWeek1(Date inDate) {
		int dayOfWeek = 0;
		Calendar theCalendar = new GregorianCalendar();
		String DateStr = dateToString(inDate);
		theCalendar.set(Integer.parseInt(DateStr.substring(0, 4)),
				Integer.parseInt(DateStr.substring(5, 7)) - 1,
				Integer.parseInt(DateStr.substring(8, 10)));
		dayOfWeek = theCalendar.get(Calendar.DAY_OF_WEEK);
		if (dayOfWeek == 2) {
			dayOfWeek = 0;
		}
		return dayOfWeek;
	}

	/**
	 * 获得星期几 <br>
	 * 星期日0--星期六6 这里输入的是字符串型
	 * 
	 * @param inDate
	 * @return dayOfWeek int 获得星期几
	 */
	public static int dayOfWeek(String inDate) {
		int dayOfWeek = 0;
		Calendar theCalendar = new GregorianCalendar();
		String DateStr = inDate;
		theCalendar.set(Integer.parseInt(DateStr.substring(0, 4)),
				Integer.parseInt(DateStr.substring(5, 7)) - 1,
				Integer.parseInt(DateStr.substring(8, 10)));
		dayOfWeek = theCalendar.get(Calendar.DAY_OF_WEEK) - 1;
		if (dayOfWeek == 7) {
			dayOfWeek = 0;
		}
		return dayOfWeek;
	}

	/**
	 * minusDate 计算两个日期的相隔天数
	 * 
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            开始日期
	 * @return result long
	 */
	public static long minusDate(Date beginDate, Date endDate) {
		long result = (beginDate.getTime() - endDate.getTime())
				/ (1000 * 60 * 60 * 24);
		return result;
	}

	/**
	 * 计算两个时间中间隔的小时
	 * 
	 * @param beginDate
	 * @param endDate
	 * @return long
	 */
	public static long minusHour(Date beginDate, Date endDate) {
		long result = (beginDate.getTime() - endDate.getTime())
				/ (1000 * 60 * 60);
		return result;
	}

	/**
	 * 计算两个时间中间隔的分钟
	 * 
	 * @param beginDate
	 * @param endDate
	 * @return long
	 */
	public static long minusMinute(Date beginDate, Date endDate) {
		long result = (beginDate.getTime() - endDate.getTime()) / (1000 * 60);
		return result;
	}

	/**
	 * 根据生日计算年龄，周岁
	 * 
	 * @param brithday
	 *            生日的字符串(yyyy-mm-dd)
	 * @return age int
	 */
	public static int getAge(String brithday) {
		int age = 0;
		// try {
		// Calendar birth = Calendar.getInstance();
		// int year = Integer.parseInt(brithday.substring(0, 4));
		// int month = Integer.parseInt(brithday.substring(5, 7)) - 1;
		// int day = Integer.parseInt(brithday.substring(8, 10));
		// birth.set(year, month, day);
		// Calendar today = Calendar.getInstance();
		// if (today.get(Calendar.MONTH) > birth.get(Calendar.MONTH)
		// || (today.get(Calendar.MONTH) == birth.get(Calendar.MONTH))
		// && today.get(Calendar.DATE) >= birth.get(Calendar.DATE))
		// age = today.get(Calendar.YEAR) - birth.get(Calendar.YEAR);
		// else
		// age = today.get(Calendar.YEAR) - birth.get(Calendar.YEAR) - 1;
		// } catch (Exception ex) {
		// ex.printStackTrace();
		// }
		// return age;
		try {
			String[] brithStr = brithday.split("-");
			Calendar birthday = new GregorianCalendar(
					Integer.parseInt(brithStr[0]),
					Integer.parseInt(brithStr[1]),
					Integer.parseInt(brithStr[2]));
			Calendar today = new GregorianCalendar();
			age = today.get(Calendar.YEAR) - birthday.get(Calendar.YEAR);
			// 取得周岁，即如果生日还没过，那么将年龄减1
			today.add(Calendar.YEAR, age);
			if (birthday.before(today)) {
				age--;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return age;

	}

	/**
	 * 将日期字符串yyyy-mm-dd转换为long型
	 * 
	 * @param dateStr
	 *            日期字符串yyyy-mm-dd
	 * @return long theDay
	 */
	public static long dateStrToLong(String dateStr) {
		long theDate = getLongTime();
		Date thisDate = stringToDate(dateStr);
		if (thisDate != null) {
			theDate = thisDate.getTime();
		}
		return theDate;
	}

	/**
	 * 日期字符串yyyy-MM-dd HH:mm:ss转换为long型
	 * 
	 * @param dateStr
	 *            日期字符串yyyy-MM-dd HH:mm:ss
	 * @return long theDay
	 */
	public static long dateStrWithTimeToLong(String dateStr) {
		long theDate = getLongTime();
		Date thisDate = stringToDateWithTime(dateStr);
		if (thisDate != null) {
			theDate = thisDate.getTime();
		}
		return theDate;
	}

	/**
	 * long型日期转换成yyyy-MM-dd格式
	 * 
	 * @param theDateLong
	 * @return String
	 */
	public static String longToDateStr(long theDateLong) {
		String dateStr = "1970-01-01";
		try {
			dateStr = Integer.toString(getYear(theDateLong)) + "-"
					+ getMonth(theDateLong) + "-" + getDay(theDateLong);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return dateStr;
	}

	/**
	 * long型日期转换成yyyy-MM-dd HH:mm:ss格式
	 * 
	 * @param theDateLong
	 * @return String DateStr yyyy-MM-dd HH:mm:ss
	 */
	public static String longToDateWithTimeStr(long theDateLong) {
		String dateStr = "1970-01-01 00:00:00";
		try {
			dateStr = Integer.toString(getYear(theDateLong)) + "-"
					+ getMonth(theDateLong) + "-" + getDay(theDateLong) + " "
					+ getHour(theDateLong) + ":" + getMinute(theDateLong) + ":"
					+ getSecond(theDateLong);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return dateStr;
	}

	/**
	 * 反转数组
	 * 
	 * @param src
	 *            源数组
	 * @return 转换后的数组
	 */
	public static int[] ArrayConvert(int[] src) {
		if (src == null) {
			return null;
		}
		int length = src.length;
		int temp = 0;
		// 反转
		for (int i = 0; i < length / 2; i++) {
			temp = src[i];
			src[i] = src[length - i - 1];
			src[length - i - 1] = temp;
		}
		return src;
	}

	/**
	 * 数字转成字符转ABCD
	 * 
	 * @param i
	 * @return ABCD
	 */
	public static String getColName(int iCol) {
		int zs = iCol / 26;//
		int ys = iCol % 26;//
		String cCol = "";
		String tmp = "";
		if (ys == 0) {
			tmp = "Z";
		} else {
			tmp = String.valueOf((char) (ys + 64));
		}
		if (zs > 1 || (zs == 1 && ys > 0))//
		{
			if (ys == 0) {
				zs = zs - 1;
			}
			cCol = getColName(zs) + cCol + tmp;
		} else {//
			if (ys == 0) {
				cCol += "Z";
			} else {
				cCol += String.valueOf((char) (ys + 64));
			}
		}
		return cCol;
	}

	/**
	 * 去除字符串中的空格、回车、换行符、制表符
	 * 
	 * @param inStr
	 * @return String
	 */
	public static String replaceBlank(String inStr) {
		Pattern p = Pattern.compile("\\s*|\t|\r|\n");
		Matcher m = p.matcher(inStr);
		return m.replaceAll("");
	}

	/**
	 * 去除字符串中的回车、换行符、制表符，但保留空格
	 * 
	 * @param inStr
	 * @return String
	 */
	public static String replaceBlank2(String inStr) {
		Pattern p = Pattern.compile("\\[s\\s]|\t|\r|\n");
		Matcher m = p.matcher(inStr);
		return m.replaceAll("");
	}

	/**
	 * 对URL进行UTF-8编码
	 * 
	 * @param inStr
	 * @return url
	 */
	public static String encodeUrlByUTF8(String inStr) {
		try {
			return java.net.URLEncoder.encode(inStr, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return inStr;
	}

	/**
	 * 将Unicode转换成String
	 * 
	 * @param str
	 * @return String
	 */
	public static String UnicodeToString(String str) {
		String res = null;
		StringBuffer sb = new StringBuffer();
		try {
			while (str.length() > 0) {
				if (str.startsWith("\\u")) {
					int x = Integer.parseInt(str.substring(2, 6), 16);
					sb.append((char) x);
					str = str.substring(6);
				} else {
					sb.append(str.charAt(0));
					str = str.substring(1);
				}
			}
			res = sb.toString();
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
		return res;
	}

	/**
	 * 将String转换为Unicode
	 * 
	 * @param szOrg
	 * @return String
	 */
	public static String StringToUnicode(String szOrg) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < szOrg.length(); i++) {
			// if (szOrg.substring(i, i + 1).matches("[\u4e00-\u9fa5]+"))
			// sb.append("\\u" + Integer.toHexString(szOrg.charAt(i)));
			// else
			// sb.append(szOrg.charAt(i));
			if (szOrg.charAt(i) > 128) {
				sb.append("\\u" + Integer.toHexString(szOrg.charAt(i)));
			} else
				sb.append(szOrg.charAt(i));
		}
		return sb.toString();
	}

	/**
	 * 生成一个10位的数字ID
	 * 
	 * @param userId
	 *            ,可传入一个数字
	 * @return
	 */
	public static String generateID(String userId) {
		if (userId == null || "".equals(userId))
			userId = "1";
		int iUserId = Integer.parseInt(userId);
		Date d = new Date();
		StringBuffer sb = new StringBuffer();
		sb.append(toValue(1900 + d.getYear()))
				.append(toValue(d.getMonth() + 1)).append(toValue(d.getDate()));
		sb.append(toValue(d.getMinutes())).append(toValue(d.getSeconds()));
		sb.append(iUserId).append(Math.round(Math.random() * 100));
		sb.append(Math.round(Math.random() * 100));
		return formatString(sb.toString());
	}

	private static String toValue(int value) {
		String strRet = String.valueOf(value);
		if (value < 10) {
			strRet = "0" + Integer.toString(value);
		}
		return strRet;
	}

	private static String formatString(String value) {
		if (value.length() < 9) {
			for (int i = 0; i < 9 - value.length(); i++) {
				value = "1" + value;
			}
		}
		if (value.length() > 9) {
			value = value.substring(value.length() - 9, value.length());
		}
		return value;
	}

	/**
	 * 检查URL是否存在
	 * 
	 * @param URLName
	 * @return boolean
	 */
	public static boolean urlExists(String URLName) {
		// try {
		// URL u = new URL(URLName);
		// URLConnection c = u.openConnection();
		// c.connect();
		// System.out.println(c.getDate());
		// if (c.getContentType() == null || c.getExpiration() == 0)
		// return false;
		// // System.out.println("内容类型: "+c.getContentType());
		// // System.out.println("内容长度: "+c.getContentLength());
		// // System.out.println("创建日期: "+new Date(c.getDate()));
		// // System.out.println("最后修改日期: "+new Date(c.getLastModified()));
		// // System.out.println("终止日期: "+new Date(c.getExpiration()));
		//
		// return true;
		// } catch (Exception e) {
		// e.printStackTrace();
		// return false;
		// }
		try {
			HttpURLConnection.setFollowRedirects(false);
			HttpURLConnection con = (HttpURLConnection) new URL(URLName)
					.openConnection();
			con.setRequestMethod("HEAD");
			return (con.getResponseCode() == HttpURLConnection.HTTP_OK);
		} catch (Exception e) {
			// e.printStackTrace();
			return false;
		}
	}

	/**
	 * 清除重复的List中的对象
	 * 
	 * @param args
	 */
	public static List emptyList(List list) {
		Set<Object> set = new HashSet<Object>();
		for (Object o : list) {
			set.add(o);
		}
		List li = new ArrayList<Object>();
		for (Object o : set) {
			li.add(o);
		}
		list.removeAll(list);
		return li;
	}

	/**
	 * 将1,2,3,4,6,8,9,10,13转换成 1-4,6,8-10,13
	 * 
	 * @param str
	 * @return
	 */
	public static String convertKkzc(String str) {
		StringBuffer stringBuffer = new StringBuffer();
		String[] strlist = str.split(",");
		stringBuffer.append(strlist[0]);
		if (strlist.length > 1) {
			for (int i = 0; i < strlist.length; i++) {
				String m = "";
				String n = "";
				int j = i + 1;
				for (; j < strlist.length; j++) {
					if (Integer.parseInt(strlist[j]) == Integer
							.parseInt(strlist[j - 1]) + 1) {
						m = strlist[j];
						continue;
					} else {
						n = strlist[j];
						break;
					}
				}
				i = j - 1;
				if (!"".equals(m)) {
					stringBuffer.append("-").append(m);
				}
				if (!"".equals(n)) {
					stringBuffer.append(",").append(n);
				}
			}
		}
		return stringBuffer.toString();
	}

	/**
	 * 将1-4,6,8-10,13转换成1,2,3,4,6,8,9,10,13
	 * 
	 * @param str
	 * @return
	 */
	public static String reserveKkzc(String str) {
		if (StringUtils.nullOrBlank(str)) {
			return "";
		}
		StringBuffer stringBuffer = new StringBuffer();
		String[] strlist = str.split(",");

		for (int i = 0; i < strlist.length; i++) {
			if (StringUtils.nullOrBlank(strlist[i])) {
				continue;
			}

			if (strlist[i].indexOf("-") <= 0) {
				stringBuffer.append(strlist[i]);
				stringBuffer.append(",");
				continue;
			}

			String[] strList2 = strlist[i].split("-");

			for (int j = Integer.parseInt(strList2[0]); j <= Integer
					.parseInt(strList2[1]); ++j) {
				stringBuffer.append(j);
				stringBuffer.append(",");
			}
		}

		if (stringBuffer.toString().endsWith(",")) {
			stringBuffer.deleteCharAt(stringBuffer.length() - 1);
		}
		return stringBuffer.toString();
	}

	public static String clearRegx(String str, String regx) {
		if (nullOrBlank(str) || regx == null) {
			return str;
		}
		String r = str.substring(str.length() - 1, str.length());
		if (r.equals(regx)) {
			str = str.substring(0, str.length() - 1);
		}
		return str;
	}

	public static void main(String[] args) {
		// System.out.println(reserveKkzc("1-4,6,8-10,13"));
		System.out.println(clearRegx(",", ","));
	}

	/**
	 * 活的客户端可IP
	 * 
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	/**
	 * 将【1,2,3,4】转化成【'1','2','3','4'】
	 * 
	 * @param request
	 * @return
	 */
	public static String getsringDou(String str) {
		String[] zhstr = str.split(",");
		String returnstring = "";
		for (int i = 0; i < zhstr.length; i++) {
			if (i == 0) {
				returnstring = returnstring + "'" + zhstr[i] + "'";
			} else {
				returnstring = returnstring + ",'" + zhstr[i] + "'";
			}
		}
		return returnstring;
	}

	/**
	 * 根据标志位去单双周。flag=0,1,2单双周标志,0 全部 1 单周 2双周
	 * 
	 * @param request
	 * @return
	 */
	public static String getDsKKzc(String str, String flag) {

		// jx0408.setKkzc(kkzcmx.substring(1,kkzcmx.length()-1));
		String[] kkzclist = str.split(",");
		StringBuffer kkzcnew = new StringBuffer("");
		for (int i = 0; i < kkzclist.length; i++) {
			if (kkzclist[i].indexOf("-") > -1) {
				kkzcnew.append(kkzclist[i]).append(",");

			} else {
				if ("1".equals(flag)) {
					if (Integer.parseInt(kkzclist[i]) % 2 != 0) {
						kkzcnew.append(kkzclist[i]).append(",");
					}
				} else if ("2".equals(flag)) {
					if (Integer.parseInt(kkzclist[i]) % 2 == 0) {
						kkzcnew.append(kkzclist[i]).append(",");
					}
				} else {
					kkzcnew.append(kkzclist[i]).append(",");
				}

			}

		}

		return kkzcnew.toString().substring(0, kkzcnew.length() - 1);
	}

	/**
	 * 序列化对象
	 * 
	 * @param o
	 *            要序列化的对象
	 * @return 序列化后的字符串
	 * @throws IOException
	 */
	//public static String objectToString(Object o) throws IOException {
	/*	ByteArrayOutputStream bs = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(bs);

		oos.writeObject(o);

		byte[] b = bs.toByteArray();
		return Base64.encodeBytes(b);*/
//	}

	/**
	 * 反序列化对象
	 * 
	 * @param <E>
	 * @param byteString
	 *            序列化后的字符串
	 * @return 反序列化后的对象
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	//public static <E> E stringToObject(String byteString) throws IOException,
	//		ClassNotFoundException {
	/*
		byte[] b = Base64.decode(byteString);

		ByteArrayInputStream bi = new ByteArrayInputStream(b);
		ObjectInputStream bis = new ObjectInputStream(bi);
		return (E) bis.readObject();
	*/
	//}

	/**
	 * 将数组转成IN SQL
	 * 
	 * @param param
	 * @return
	 */
	public String getInSql(String[] param) {
		String sql = "";
		for (int i = 0; param != null && i < param.length; i++) {
			sql += "'" + param[i] + "'";
			if (i < param.length - 1) {
				sql += ",";
			}
		}
		return sql;
	}

	/**
	 * 将中文逗号换成英文逗号
	 * 
	 * @param text
	 * @return String
	 */
	public static String replaceString(String text) {
		if (text != null) {
			text = replace(text, "'", "''");
		}
		return text;
	}

	/**
	 * 获得用来加密的字符串
	 */
	private static void getCode() {
		int zs = 0;
		for (int i = 0; i < len; i++) {
			int j = (int) (Math.random() * 3 + 1);
			xh = xh + j;
			zs = zs + j;
		}
		// 根据总长度生成验证码
		Random random = new Random();
		for (int i = 0; i < zs; i++) {
			boolean b = random.nextBoolean();
			if (b) { // 字符串
				// int choice = random.nextBoolean() ? 65 : 97; 取得65大写字母还是97小写字母
				int randomrum = 65 + random.nextInt(57);
				if (randomrum > 90 && randomrum < 97) {
					randomrum = randomrum + 6;
				}
				code += (char) (randomrum);// 取得大写字母
			} else { // 数字
				code += String.valueOf(random.nextInt(10));
			}
		}
	}

	/**
	 * 字符串加密
	 * 
	 * @param str
	 * @return
	 */
	public static String encrypt(String str) {
		String sstr = "";
		if ("".equals(xh)) {
			getCode();
		} else {
			String sxh = xh;
			String scode = code;
			for (int i = 0; i < str.length(); i++) {
				if (i < len) {
					sstr = sstr
							+ str.substring(i, i + 1)
							+ scode.substring(0,
									Integer.parseInt(sxh.substring(i, i + 1)));
					scode = scode.substring(
							Integer.parseInt(sxh.substring(i, i + 1)),
							scode.length());
				} else {
					sstr = sstr + str.substring(i, str.length());
					i = str.length();
				}
			}
		}
		return sstr;
	}

	/**
	 * 字符串解密
	 * 
	 * @param str
	 * @return
	 */
	public static String decrypt(String str) {
		String encoded = str;
		if ("".equals(xh) || xh == null) {
			return "false";
		}
		String sesscode = code;
		String sessxh = xh;
		String dstr = "";
		String dcode = "";
		for (int x = 0; x < len; x++) {
			dstr = dstr + encoded.substring(0, 1);
			encoded = encoded.substring(1, encoded.length());
			int j = Integer.parseInt(sessxh.substring(x, x + 1));
			String sesscodepart = sesscode.substring(0, j);
			sesscode = sesscode.substring(j, sesscode.length());
			if (encoded.length() >= sesscodepart.length()) {
				try {

					dcode = dcode + encoded.substring(0, sesscodepart.length());
					encoded = encoded.substring(sesscodepart.length(),
							encoded.length());
				} catch (Exception e) {
					e.printStackTrace();
					return "false";

				}
			} else {
				dstr = dstr + encoded.substring(0, encoded.length());
				break;
			}
			if (encoded.length() == 0) {
				break;
			}
			if (x == (len - 1)) {
				dstr = dstr + encoded.substring(0, encoded.length());
			}
		}
		if (code.indexOf(dcode) == -1) {
			return "false";
		}
		return dstr;
	}

	/**
	 * 校验非法关键字
	 * 
	 * @param str
	 * @return
	 */
	public static boolean checkIllegally(String str) {
		if (str == null || "".equals(str)) {
			return false;
		}
		String[] rexgs = { "exec", "execute", "insert", "create", "drop",
				"table", "grant", "column_name", "delete", "update",
				"truncate", "declare", "frmuser", "frmfunctioncell",
				"frmfunctioncellurl", "frmsecondfunctionuser",
				"frmrolefunction", "all_tables", "dba_tables", "user_tables",
				"tabs", "user_tab_columns", "user_tab_cols", "table_name" };

		boolean bool = false;
		for (int a = 0; a < rexgs.length; a++) {
			if (str.toLowerCase().indexOf(rexgs[a]) > -1) {
				bool = true;
				break;
			}
		}
		return bool;
	}

	/**
	 * 特殊字符替换
	 * 
	 * @param str
	 * @return
	 */
	public static String replaceSpecial(String str) {
		if (str != null) {
			str = str.replaceAll("&#59;", ";").replaceAll("&amp;", "&")
					.replaceAll("&lt;", "<").replaceAll("&gt;", ">")
					.replaceAll("&#97;nd", "and").replaceAll("&#111;r", "or")
					.replaceAll("&#108;ike", "like")
					.replaceAll("&#103;rant", "grant")
					.replaceAll("&#117;nion", "union")
					.replaceAll("&#119;here", "where")
					.replaceAll("&#102;rom", "from")
					.replaceAll("&#114;estore", "restore")
					.replaceAll("&#109;aster.", "master.")
					.replaceAll("&#101;xists", "exists")
					.replaceAll("&#97;lter", "alter")
					.replaceAll("&#116;runcate", "truncate")
					.replaceAll("&#114;ename", "rename")
					.replaceAll("&#99;reate", "create")
					.replaceAll("&#100;rop", "drop")
					.replaceAll("&#100;elete", "delete")
					.replaceAll("&#117;pdate", "update")
					.replaceAll("&#105;nsert", "insert")
					.replaceAll("&#115;elect", "select")
					.replaceAll("&#101;xec", "exec");
		}
		return str;
	}

}
