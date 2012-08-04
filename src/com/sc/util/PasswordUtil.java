package com.sc.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PasswordUtil {
	private static final String ALGORITHM = "SHA-1";
	public static final int minPwdLen = 8;
	public static final int maxPwdLen = 10;
	public static final int minNumChars = 0;
	public static final int minAlphaChars = 0;
	static char[] chars = new char[]{
		'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z',
		'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
	
	public static String generatePassword(){
		StringBuffer sb= new StringBuffer();
		Random r= new Random(System.currentTimeMillis());
		int numCnt=0;
		for (int i=0; i < minPwdLen; i++){
			if (i+1 >=sb.length()- minNumChars && 
					numCnt < minNumChars){
				sb.append(getInt(r));
				numCnt++;
			}else {
				if (r.nextBoolean())
					sb.append(getChar(r));
				else{
					sb.append(getInt(r));
					numCnt++;
				}
					
			}
		}
		return sb.toString();
	}
	
	private static String getChar(Random rnd){
		return Character.toString(chars[rnd.nextInt(chars.length -1)]);
	}
	private static String getInt(Random rnd){
		return Integer.toString(rnd.nextInt(9));
	}
	
	public static boolean isValidPassword(String pwd){
		if (pwd.length() < minPwdLen || pwd.length() > maxPwdLen)
			return false;
		
		/*int numCount=0;
		char pchars[] = pwd.toCharArray();
		for (int i=0; i < pchars.length; i++){
			if (Character.isDigit(pchars[i]))
				numCount++;
			else {
				for (int j=0; j< chars.length; j++){
					if (pchars[i] != chars[j])
						return false;
					
				}
			}
		}
		if (numCount < minNumChars ||
				((pwd.length() - numCount) < minAlphaChars)){
			return false;
		}*/
		char pchars[] = pwd.toCharArray();
		for (int i=0; i < pchars.length; i++){
			boolean foundInChar = false;
			if (!Character.isDigit(pchars[i])){
				for (int j=0; j< chars.length; j++){
					if (pchars[i] == chars[j]){
						foundInChar = true;
						break;
					}
					
				}
				if (!foundInChar)
					return false;
			}
		}
		return true;
		
	}
	/**
	 * Encrypts a string to a hex number string
	 * @param clearStr a plain string
	 * @return a list of 2 strings, first entry is a hex number string. For SHA-1 algorithm, the return string is 20 char long. Second entry is the salt
	 */
	public static List<String> getEncrypted(String clearStr){
		return getEncrypted(clearStr, null);
	}
	/**
	 * Encrypts a string to a hex number string
	 * @param clearStr a plain string
	 * @return a hex number string. For SHA-1 algorithm, the return string is 20 char long
	 */
	public static List<String> getEncrypted(String clearStr, String salt){
		List<String> str = new ArrayList(2);
		MessageDigest algorithm = null;
		byte[] buf = clearStr.getBytes();
		//generate Salt
		if (salt == null){
			Random r= new Random(System.currentTimeMillis());
			salt = String.valueOf(r.nextInt());
			
		}
		byte[] saltB = salt.getBytes();
		try{
			algorithm = MessageDigest.getInstance(ALGORITHM);
		}catch (NoSuchAlgorithmException e){
			//this line should never be executed
			e.printStackTrace();
		}
		algorithm.reset();
		//algorithm.update(buf);
		//byte[] digest = algorithm.digest();
		algorithm.update(saltB);
		byte[] digest = algorithm.digest(buf);
		
		
		String encrypted = "";
		int usbyte = 0; 
		for (int i=0; i< digest.length; i++){
			usbyte = digest[i] & 0xFF;
			if (usbyte <16)
				encrypted += "0" + Integer.toHexString(usbyte);
			else
				encrypted += Integer.toHexString(usbyte);
		}
		str.add(encrypted);
		str.add(salt);
		return str;
		
	}
	
	/**
	 * Given a plain str and an encrypted string, check if the plain string 
	 * could be encrypted to the encrypted string
	 * 
	 * @param clearStr -A plain string
	 * @param encryptStr -Encrypted string
	 * @param salt - A plain salt
	 * @return <code>true</code> if plain string can be encrypted to the same string as encryptStr
	 * 		   <code>false</code> if not.
	 */
	public static boolean compares(String clearStr, String encryptStr, String salt) {
		if (clearStr == null && encryptStr == null)
			return true;
		else if (clearStr == null || encryptStr == null)
			return false;
		List<String> encData = getEncrypted(clearStr, salt);
		return (encryptStr.equals(encData.get(0)));
	}
}
