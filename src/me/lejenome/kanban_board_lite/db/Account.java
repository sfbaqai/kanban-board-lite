package me.lejenome.kanban_board_lite.db;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Arrays;

public class Account {
    private int id;
    private String first_name;
    private String last_name;
    private String email;
    private byte[] password;
    private byte[] salt;
    private static SecureRandom rand = new SecureRandom();

    private Account() {
    }

    private Account(String first_name, String last_name, String email) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
    }

    public static Account create(String first_name, String last_name, String email, String password) {
        Account acc = new Account(first_name, last_name, email);
        byte[][] salt_pw = hashPassword(password, null);
        int res = Connection.executeUpdate("Insert Into Account (first_name, last_name, email, password, salt) values (?, ? , ?, ?, ?)",
                first_name, Types.VARCHAR,
                last_name, Types.VARCHAR,
                email, Types.VARCHAR,
                salt_pw[1], Types.VARBINARY,
                salt_pw[0], Types.VARBINARY);
        if (res == -1)
            return null;
        else
            return acc;
    }

    public static Account get(final String email) {
        Account acc = null;
        ResultSet res = Connection.executeQuery("Select * from Account where email = ?",
                email, Types.VARCHAR);
        try {
            res.next();
            acc = new Account(res.getString("first_name"), res.getString("last_name"), res.getString("email"));
            acc.id = res.getInt("id");
            acc.password = res.getBytes("password");
            acc.salt = res.getBytes("salt");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return acc;
    }

    public static Account authenticate(final String email, final String password) {
        Account acc = get(email);
        byte[][] salt_pw = hashPassword(password, acc.salt);

        if (Arrays.equals(acc.password, salt_pw[1]))
            return acc;
        else
            return null;
    }


    public static byte[][] hashPassword(final String password, byte[] salt) {
        // Based on OWASP Hashing Example
        if (salt == null) {
            salt = new byte[64];
            rand.nextBytes(salt);
        }
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
            PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 10_000, 512);
            SecretKey key = skf.generateSecret(spec);
            byte[] res = key.getEncoded();
            return new byte[][]{salt, res};

        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", email='" + email +
                '}';
    }
}
