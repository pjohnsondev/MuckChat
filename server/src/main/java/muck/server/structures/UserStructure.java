package muck.server.structures;

public class UserStructure {
    public Integer id;
    public String username;
    public String password;
    public byte[] hashedPassword;
    public byte[] salt;

    public String displayName;
}
