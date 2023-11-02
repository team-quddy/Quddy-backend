package com.team_quddy.quddy.global.cipher;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Base64;
@Service
public class CipherService {
    public String encodeResultId(Integer id, String usersId, String secret) throws Exception{
        StringBuilder sb = new StringBuilder();
        sb.append(id).append(" ").append(usersId);
        return encode(sb.toString(), secret);
    }
    public String encode(String str, String secret) throws Exception{
        byte[] secretBytes = secret.getBytes();
        SecretKey secretKey = new SecretKeySpec(secretBytes, "AES");

        // 암호화
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedBytes = cipher.doFinal(str.getBytes());
        String encodeToString = Base64.getEncoder().encodeToString(encryptedBytes);

        return URLEncoder.encode(encodeToString, "UTF-8");
    }

    public String decode(String str, String secret) throws Exception{
        byte[] secretBytes = secret.getBytes();
        SecretKey secretKey = new SecretKeySpec(secretBytes, "AES");

        // 복호화
        str = URLDecoder.decode(str, "UTF-8");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(str));

        return new String(decryptedBytes);
    }
}
