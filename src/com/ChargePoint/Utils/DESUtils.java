package com.ChargePoint.Utils; 
import java.io.IOException;
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
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
 
/**
 *encrypt(data, key)
 *decrypt(data, key)
 */
public class DESUtils {
	private final static String KEYSUFFIX = "SZS@!CJE";
	private final static String DES = "DES";
    public static void main(String[] args) throws Exception {
//        String data = " 456";
//        String key = "@!uy";
        String data = "712344e哈哈121212";  
        String key = "712344e哈哈121212";  
        //加密   
        System.err.println(encrypt(data, key));
        System.err.println(decrypt("VZaqCt6RZKc8mFRuaxM/ZMx6XIcdHAlf", key));
    }
    
    /**
     * Description 根据键值进行加密
     * @param data 
     * @param key  加密键String(at lest 8)
     * @return
     * @throws Exception
     */
    public static String encrypt(String data, String key) throws Exception {
        key += KEYSUFFIX;
        if (data == null)
            return null;
    	byte[] bt = encrypt(data.getBytes(), key.getBytes());
        String strs = new BASE64Encoder().encode(bt);
        return strs;
    }
     
        /**
         * Description 根据键值进行解密
         * @param data
         * @param key  加密键String
         * @return
         * @throws IOException
         * @throws Exception
         */
		public static String  decrypt(String data, String key) throws IOException,
                Exception {
			 key += KEYSUFFIX;
            if (data == null)
                return null;
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] buf = decoder.decodeBuffer(data);
            byte[] bt = decrypt(buf,key.getBytes());
            return new String(bt);
        }
     
        /**
         * Description 根据键值进行加密
         * @param data
         * @param key  加密键byte数组
         * @return
         * @throws Exception
         */
        private static byte[] encrypt(byte[] data, byte[] key) throws Exception {
            // 生成一个可信任的随机数源
            SecureRandom sr = new SecureRandom();
     
            // 从原始密钥数据创建DESKeySpec对象
            DESKeySpec dks = new DESKeySpec(key);
     
            // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
            SecretKey securekey = keyFactory.generateSecret(dks);
     
            // Cipher对象实际完成加密操作
            Cipher cipher = Cipher.getInstance(DES);
     
            // 用密钥初始化Cipher对象
            cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);
     
            return cipher.doFinal(data);
        }
         
         
        /**
         * Description 根据键值进行解密
         * @param data
         * @param key  加密键byte数组
         * @return
         * @throws Exception
         */
        private static byte[] decrypt(byte[] data, byte[] key) throws Exception {
            // 生成一个可信任的随机数源
            SecureRandom sr = new SecureRandom();
     
            // 从原始密钥数据创建DESKeySpec对象
            DESKeySpec dks = new DESKeySpec(key);
     
            // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
            SecretKey securekey = keyFactory.generateSecret(dks);
     
            // Cipher对象实际完成解密操作
            Cipher cipher = Cipher.getInstance(DES);
     
            // 用密钥初始化Cipher对象
            cipher.init(Cipher.DECRYPT_MODE, securekey, sr);
     
            return cipher.doFinal(data);
        }
        
        
        /**将二进制转换成16进制 
         * @param buf 
         * @return 
         */  
        private static String parseByte2HexStr(byte buf[]) {  
                StringBuffer sb = new StringBuffer();  
                for (int i = 0; i < buf.length; i++) {  
                        String hex = Integer.toHexString(buf[i] & 0xFF);  
                        if (hex.length() == 1) {  
                                hex = '0' + hex;  
                        }  
                        sb.append(hex.toUpperCase());  
                }  
                return sb.toString();  
        }  
        
        /**将16进制转换为二进制 
         * @param hexStr 
         * @return 
         */  
        private static byte[] parseHexStr2Byte(String hexStr) {  
                if (hexStr.length() < 1)  
                        return null;  
                byte[] result = new byte[hexStr.length()/2];  
                for (int i = 0;i< hexStr.length()/2; i++) {  
                        int high = Integer.parseInt(hexStr.substring(i*2, i*2+1), 16);  
                        int low = Integer.parseInt(hexStr.substring(i*2+1, i*2+2), 16);  
                        result[i] = (byte) (high * 16 + low);  
                }  
                return result;  
        }  
	
}
    
