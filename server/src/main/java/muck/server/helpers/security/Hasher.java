package muck.server.helpers.security;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

/**
 * Security class for the user passwords.
 */
public class Hasher {
    private byte[] salt;            //randomised bytes for the hash generation
    private byte[] hashedPassword;  //the hashed password

    /**
     * Generates the seeds for randomisation
     */
    private void generateSalt() {
        //randomise/salt the hash
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        this.salt = salt;
    }

    /**
     * Creates the hashed password from the generated random seeds (created in generateSalt())
     *
     * @param password The password to be secured by hashing
     */
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

    /**
     * Generates the salt (random bytes) and hashed password (using salt)
     *
     * @param password Password to be secured by hashing
     */
    public void setNewPasswordHash(String password) {
        generateSalt();
        generateHash(password);
    }

    /**
     * Retrieves the hashed password
     *
     * @return the hashed password
     */
    public byte[] getHashedPassword() {
        return hashedPassword;
    }

    /**
     * Retrieves the salt used to generate the hashed password
     *
     * @return the salt used to generate the hashed password
     */
    public byte[] getSalt() {
        return salt;
    }

    /**
     * Used to match passwords.
     *
     * @param password Old password that needs matching
     * @param sal salt used to generate the hash on the old password
     * @param hash hashed old password
     * @return true if passwords match. false if they do not.
     */
    public Boolean passwordMatches(String password, byte[] sal, byte[] hash) {
        setOldPasswordHash(password, sal);
        return Arrays.equals(hashedPassword, hash);
    }

    /**
     * Hash a password with an existing salt
     *
     * @param password password that needs hashing
     * @param salt salt to be used to hash the password
     */
    public void setOldPasswordHash(String password, byte[] salt) {
        this.salt = salt;
        generateHash(password);
    }


}
