package com.util;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.Key;

//import com.sumit.common.except.PasswordCodeException;
public class UserPasswordUtil {
	private static final String KEY = "SMARTPAY";//8位密码钥匙，只有知道这个，就能进行加密解密。
	private static Cipher cipher = null;
	private static Key key;
	private static void desEncrypt(String strkey) throws Exception{
		byte[] secretKey = strkey.getBytes();
		SecretKeyFactory kf = SecretKeyFactory.getInstance("DES");//DES加密方法，如MD5
		DESKeySpec keySpec = new DESKeySpec(secretKey);
		key = kf.generateSecret(keySpec);
		cipher = Cipher.getInstance("DES");
	}
	public static String decode(String obj) throws Exception{
		if(obj==null) return null;
		String resultStr = null;
		desEncrypt(KEY);
		cipher.init(Cipher.DECRYPT_MODE, key);
		Base64 base64 = new Base64();
		byte[] objByte = base64.decode(obj.getBytes("UTF-8"));
		byte[] result = cipher.doFinal(objByte);
		resultStr = new String(result, "UTF-8");
		return resultStr;
	}
	public static String encode(String obj)throws Exception {
		if(obj==null) return null;
		desEncrypt(KEY);
		String resultStr = null;
		cipher.init(Cipher.ENCRYPT_MODE, key);
		Base64 base64 = new Base64();
		byte[] objByte = obj.getBytes("UTF-8");
		byte[] result = cipher.doFinal(objByte);
		resultStr = new String(base64.encode(result), "UTF-8");
		return resultStr;
	}
//	public static void main(String[] a) throws Exception {
//		String pwd = "123";
//		String pwd_encoded = UserPasswordUtil.encode(pwd);//进行加密
//		System.out.println("encoded = " + pwd_encoded);
//		String pwd_decoded = UserPasswordUtil.decode(pwd_encoded);//进行解密
//		System.out.println("decoded = " + pwd_decoded);
//	}
}
