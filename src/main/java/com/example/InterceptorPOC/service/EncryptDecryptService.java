package com.example.InterceptorPOC.service;

import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

@Service
public class EncryptDecryptService {

    private static String ALGORITHM = "AES";
    private static String AES_CBS_PADDING = "AES/CBC/PKCS5Padding";

    // Initialization Vector should be different for every encryption performed and randomly generated
    // for POC sake, keeping constant
    private static String IV = "abcdabcdabcdabcd";

    // for POC sake keeping key here
    // it should be kept at vault or config files
    private static String KEY = "ThisIsSecretKey";

    public byte[] doEncryption(String data) {
        try {
            final Cipher cipher = Cipher.getInstance(AES_CBS_PADDING);
            final SecretKeySpec keySpec = new SecretKeySpec(KEY.getBytes(), ALGORITHM);
            final IvParameterSpec ivSpec = new IvParameterSpec(IV.getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
            return cipher.doFinal(data.getBytes());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public byte[] doDecryption(String data) {
        try {
            final Cipher cipher = Cipher.getInstance(AES_CBS_PADDING);
            final SecretKeySpec keySpec = new SecretKeySpec(KEY.getBytes(), ALGORITHM);
            final IvParameterSpec ivSpec = new IvParameterSpec(IV.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
            return cipher.doFinal(data.getBytes());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
