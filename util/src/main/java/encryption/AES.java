package encryption;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
/**
 * 不能处理中文加密
 * @author Jin
 *
 */
public class AES {
	public static void main(String[] args) {
		//待加密内容
		String str = "chenjin";
		//密码，长度要是8的倍数
		String password = "12345678";
		byte[] result=encryp(str,password);
		System.out.println(result);
		byte[] dencryResult= decrypt(result, password);
		//System.out.println(dencryResult.toString());
		System.out.println(new String(dencryResult));
	}
	/**
	 * 加密
	 * @param content
	 * @param password
	 * @return
	 */
	public static byte[] encryp(String content,String password){
		try {
			//此类提供（对称）密钥生成器的功能。
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			//用指定参数集和用户提供的随机源初始化此密钥生成器
			kgen.init(128,new SecureRandom(password.getBytes()));
			SecretKey secretKey=kgen.generateKey();//生成一个密钥。
			byte[] enCondeFormat= secretKey.getEncoded();//将密钥变为byte数组
			//此类以与 provider 无关的方式指定一个密钥。
			//可以使用此类来根据一个字节数组构造一个 SecretKey，而无须通过一个（基于 provider 的）SecretKeyFactory。
			SecretKeySpec key = new SecretKeySpec(enCondeFormat, "AES");
			//此类为加密和解密提供密码功能。
			//应用程序调用 Cipher 的 getInstance 方法并将所请求转换 的名称传递给它。还可以指定提供者的名称（可选）。 
			Cipher cipher=Cipher.getInstance("AES");//创建密码器
			byte[] byteContent=content.getBytes("UTF-8");
			cipher.init(Cipher.ENCRYPT_MODE,key);//初始化
			byte[] result = cipher.doFinal(byteContent);//加密
			return result;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 解密
	 * @param content
	 * @param password
	 * @return
	 */
	public static byte[] decrypt(byte[] content, String password){
		try {
			KeyGenerator kegen=KeyGenerator.getInstance("AES");
			kegen.init(128,new SecureRandom(password.getBytes()));
			SecretKey secretKey = kegen.generateKey();
			byte[] enCodeFormat= secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
			Cipher cipher= Cipher.getInstance("AES");
			cipher.init(Cipher.DECRYPT_MODE,key);
			byte[] result = cipher.doFinal(content);
			return result;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}
}
