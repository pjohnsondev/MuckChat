package muck.server.models;

import java.nio.charset.StandardCharsets;
import java.security.DigestException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import muck.server.database.Database;

public class User {
    private Database db;

    public User() {
        db = new Database();
    }

    // based on this tutorial: https://www.baeldung.com/java-password-hashing
    // there are better hashing algorithms but it still seems to be one of the more secure ones: https://en.wikipedia.org/wiki/Secure_Hash_Algorithms
    private byte[] generateHash(String password) throws NoSuchAlgorithmException {
        //randomise/salt the hash
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);

        // hash function
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        md.update(salt);
        byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));
        return hashedPassword;    
    }
}
