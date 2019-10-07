package com.nurul.simpakat.common.util;

import java.math.BigInteger;
import java.security.MessageDigest;

public class EncryptionUtils {

    public static String getSHA256(String stringToEncrypt) {
        MessageDigest md;
        byte[] digest;
        String passwordHash = "";

        try {
            md = MessageDigest.getInstance("SHA-256");
            if (md != null) {
                md.update(stringToEncrypt.getBytes());
                digest = md.digest();
                if (digest != null && digest.length > 0) {
                    passwordHash = String.format("%064x", new BigInteger(1, digest));
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return passwordHash;
    }
}
