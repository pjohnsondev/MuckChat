package muck.server.helpers.security;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;


public class Hasher {
    private byte[] salt;
    private byte[] hashedPassword;

    private void generateSalt() {
        //randomise/salt the hash
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        this.salt = salt;
    }

    private void generateHash(String password) {
        // hash function
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt);
            hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));    
        } catch (NoSuchAlgorithmException exception) {
            System.out.println(exception.getMessage());
        }
    }

    public void setNewPasswordHash(String password) {
        generateSalt();
        generateHash(password);
    }

    public byte[] getHashedPassword() {
        return hashedPassword;
    }

    public byte[] getSalt() {
        return salt;
    }

    public Boolean passwordMatches(String password, byte[] sal, byte[] hash) {
        setOldPasswordHash(password, sal);
        return Arrays.equals(hashedPassword, hash);
    }

    private void setOldPasswordHash(String password, byte[] salt) {
        this.salt = salt;
        generateHash(password);
    }


}
