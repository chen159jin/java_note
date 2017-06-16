package charsetTest;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class »õ±ÒÖĞÎÄ´óĞ´ {
	public static void main(String[] args) {
		System.out.println(ChineseCurrency(123000080.123));
	}
	
	public static String ChineseCurrency(double num){
		String s = new DecimalFormat("#.00").format(num);
		System.out.println(s);
		char[] digit = { 'Áã', 'Ò¼', '·¡', 'Èş', 'ËÁ', 'Îé', 'Â½', 'Æâ', '°Æ', '¾Á' };
		String unit = "Çª°ÛÊ°Õ×Çª°ÛÊ°ÒÚÇª°ÛÊ°ÍòÇª°ÛÊ°Ôª½Ç·Ö";
		String str=s.replaceAll("\\.", "");
//		System.out.println(str);
		//char[] strs=str.toCharArray();
		int l = unit.length();
		StringBuffer sb=new StringBuffer(unit.substring(l-str.length(), l));
		System.out.println(sb.toString());
		for (int i = str.length()-1; i >=0 ; i--) {
			System.out.println(str.charAt(i));
			sb = sb.insert(i, digit[(str.charAt(i)- 0x30)]);
		}
		System.out.println(sb.toString());
		s=sb.toString();
		//±íÊ¾Ìæ»»ÁãÊ°¡¢Áã°Û¡¢ÁãÇªµÄ×Ö¶Î        //Á¬ĞøÁ½¸ö»òÒÔÉÏµÄ                    //Æ¥ÅäÁãÕ×¡¢ÁãÍò¡¢ÁãÔªµÄ×Ö·ûÌæ»»³É¶ÔÓ¦µÄÖµ
		s = s.replaceAll("Áã[Ê°°ÛÇª]", "Áã").replaceAll("Áã{2,}", "Áã").replaceAll("Áã([Õ×ÍòÔª])", "$1").replaceAll("Áã[½Ç·Ö]", "");
		if (s.endsWith("½Ç"))
			s += "Áã·Ö";
		if (!s.contains("½Ç") && !s.contains("·Ö") && s.contains("Ôª"))
			s += "Õû";
		if (s.contains("·Ö") && !s.contains("Õû") && !s.contains("½Ç"))
			s = s.replace("Ôª", "ÔªÁã");
		return s;
	}
	
	/*Ò¼ÒÚ·¡ÇªÈş°ÛÁãÍòÁã°ÆÊ°¾ÁÔªÒ¼½Ç·¡·Ö
	 * public static String ChineseCurrency(double num){
		char[] str={'Áã', 'Ò¼', '·¡', 'Èş', 'ËÁ', 'Îé', 'Â½', 'Æâ', '°Æ', '¾Á'};
		String unit = "Çª°ÛÊ°Õ×Çª°ÛÊ°ÒÚÇª°ÛÊ°ÍòÇª°ÛÊ°Ôª½Ç·Ö";
	//	BigDecimal bg = new BigDecimal(num).setScale(2, RoundingMode.UP);//ËÄÉáÎåÈë
	//	double n= bg.doubleValue();
		String[] numStr=String.valueOf(num).split("\\.");
		if(numStr.length==2){
			
		}
		char[] nums = String.valueOf(numStr).toCharArray(); //½«Êı×Ö×ª»»³ÉÊı×é
		StringBuilder strBu=new StringBuilder("");
		for (int i = nums.length; i > 0; i--) {
			strBu.append(str[Integer.parseInt(nums[i] + "")]);
//			System.out.println(nums[i]);
		}
		System.out.println(strBu);
		return null;
	}*/
	
}
