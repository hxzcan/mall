package com.ican.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5加密
 * @author Administrator
 *
 */
public class MD5Util {

	public static String[] hexDigits={"0","1","2","3","4","5","6","7","8","9","a","b","c","d","e","f"};

	private static String byteArray2HexString(byte[] bytes ){
		StringBuffer stringBuffer=new StringBuffer();
		for (int i = 0; i < bytes.length; i++) {
			stringBuffer.append(byte2HexString(bytes[i]));
		}
		return stringBuffer.toString();
	}

	private static String byte2HexString(byte b){
		int n=b;
		if (n<0){
			n+=256;
		}
		int d1=n/16;
		int d2=n%16;
		return hexDigits[d1]+hexDigits[d2];
	}

	private static String MD5Encode(String origin,String charsetName){
		String MD5String=null;
		try {
			MD5String=new String(origin);
			MessageDigest digest=MessageDigest.getInstance("MD5");
			if (charsetName==null||"".equals(charsetName)){
				MD5String=byteArray2HexString(digest.digest(MD5String.getBytes()));
			}else {
				MD5String=byteArray2HexString(digest.digest(MD5String.getBytes(charsetName)));
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return MD5String.toUpperCase();
	}

	public static String MD5EncodeUTF8(String origin){
		return MD5Encode(origin,"UTF-8");
	}

	/*public final static String MD5(String s){

		byte[] bytes=s.getBytes();
		try {
			//获得MD5摘要算法的MessageDigest
			MessageDigest mDigest=MessageDigest.getInstance("MD5");
			//使用指定的字节更新摘要
			mDigest.update(bytes);
			//获得密文
			byte[] md=mDigest.digest();
			//把密文转化为16进制的字符串形式
			int len=md.length;
			char[] chars=new char[len*2];
			int k=0;
			for (int i = 0; i < len; i++) {
				byte byte0= md[i];
				chars[k++]=hexDigits[byte0>>>4&0xf];
				chars[k++]=hexDigits[byte0&0xf];
			}
			return new String(chars);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	*/

}
