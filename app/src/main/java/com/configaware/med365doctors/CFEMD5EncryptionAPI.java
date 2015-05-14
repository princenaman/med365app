package com.configaware.med365doctors;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Shubham on 5/13/2015.
 */
public class CFEMD5EncryptionAPI {
  /*  public static void main(String args[])
    {
        String md5msg=getEncryptedValue("Himanshu", "avenger");
        System.out.println(md5msg);
    }*/


    /**
     * @param data
     * @return
     */
    public static String getEncryptedValue(String key, String data) {
        if (data != null && data.length() > 0)
            return performEncryption(key, data);
        return null;
    }

    /**
     * @param unencryString
     * @return
     */
    private static String performEncryption(String key, String unencryString) {
        StringBuffer sb  = new StringBuffer();
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        md.update(unencryString.getBytes());
        byte bytedata[] = md.digest();

        byte secretKey[] = key.getBytes();
        //convert the byte to hex format method 1
        for (int i = 0; i < secretKey.length; i++) {
            sb.append(Integer.toString((secretKey[i] & 0xff) + 0x100, 16).substring(1));
        }

        for (int i = 0; i < bytedata.length; i++) {
            sb.append(Integer.toString((bytedata[i] & 0xff) + 0x100, 16).substring(1));
        }

        return sb.toString();
    }

    /**
     * @param oldEncryptedString
     * @param unEncryptedString
     * @return
     */
    public static boolean compareStringValues (String key, String oldEncryptedString, String unEncryptedString) {
        try {
            //MessageDigest md5 = MessageDigest.getInstance("MD5");
            //md5.isEqual(digesta, digestb)
            String encryptString = null;
            if (unEncryptedString != null && unEncryptedString.length() > 0)
                encryptString = performEncryption(key, unEncryptedString);

            if (oldEncryptedString != null && oldEncryptedString.equalsIgnoreCase(encryptString))
                return true;

        } catch (Exception e) {
            System.out.println("Unable to encrypt and compare strings.");
        }
        return false;
    }

}
