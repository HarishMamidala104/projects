package com.ojas.hiring.config;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Cyp {

    private static SecretKeySpec secretKey;
    private static byte[] key;
    public static String keyPrefix = "ojas";
    public static String sessionValDelim = "<>";

    public static void setKey(final String myKey) {
        MessageDigest sha = null;
        try {
            key = myKey.getBytes("UTF-8");
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            secretKey = new SecretKeySpec(key, "AES");
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public static String encrypt(final String strToEncrypt, String secret) {
        try {
            if (secret == null) {
                DateFormat formatter = new SimpleDateFormat("ddMMyyyy");
                Date today = new Date();
                secret = keyPrefix + formatter.format(today);
            }
            setKey(secret);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
        } catch (Exception e) {
            System.out.println("Error while encrypting: " + e.toString());
        }
        return null;
    }

    public static String decrypt(final String strToDecrypt, String secret) {
        try {
            if (secret == null) {
                DateFormat formatter = new SimpleDateFormat("ddMMyyyy");
                Date today = new Date();
                secret = keyPrefix + formatter.format(today);
            }
            setKey(secret);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        } catch (Exception e) {
            System.out.println("Error while decrypting: " + e.toString());
        }
        return null;
    }

    public static int getCurrDay() {
        int day = getLocalDate().getDayOfMonth();
        return day;
    }

    public static int getCurrMonth() {
        int month = getLocalDate().getMonthValue();
        return month;
    }

    public static int getCurrYear() {
        int year = getLocalDate().getYear();
        return year;
    }
    
    public static String genDelim() {
        int d = Cyp.getCurrDay();
        int m = Cyp.getCurrMonth();
        int y = Cyp.getCurrYear();
        return d + "" + m + "" + y;
    }

    public static int getCurrYearTwoDigit() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy");
        String text = getLocalDate().format(formatter);
        return Integer.parseInt(text);
    }

    public static LocalDate getLocalDate() {
        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return localDate;
    }

    public static void mainz(String[] args) {
        String message = "vg22285|Jack ANd Jill|Went up the hill|And along came a spider";
        DateFormat formatter = new SimpleDateFormat("ddMMyyyy");
        Date today = new Date();
        String key = formatter.format(today);
        key = null;
        String enc = encrypt(message, key);
        System.out.println("\nEncrpyted msg:" + enc);
        String dec = decrypt(enc, key);
        System.out.println("\nDecrpyted msg:" + dec);
    } //main method ends
}
