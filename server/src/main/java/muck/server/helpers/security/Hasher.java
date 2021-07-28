package muck.server.helpers.security;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;


public class Hasher {

    // based on this tutorial: https://www.baeldung.com/java-password-hashing
    // This is not the most secure hashing algorithm, but it should be good enough for out puposes: https://en.wikipedia.org/wiki/Secure_Hash_Algorithms
    /**
     * This is a hashing function built to create passwords. Pass the password
     * into the hash function and get a secure hash for that password. 
     * 
     * @param password  Can be any string you would like to hash.
     * @return  returns a hashed bytes array.
     * @throws NoSuchAlgorithmException
     */
    public byte[] generateHash(String password) throws NoSuchAlgorithmException {
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
