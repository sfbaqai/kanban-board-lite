package me.lejenome.kanban_board_lite.server.db;

import me.lejenome.kanban_board_lite.common.Account;
import me.lejenome.kanban_board_lite.common.AccountExistsException;
import me.lejenome.kanban_board_lite.common.AccountNotFoundException;
import me.lejenome.kanban_board_lite.common.AuthenticationException;

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

public class AccountEntity implements Account {
    private static SecureRandom rand = new SecureRandom();
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private byte[] password;
    private byte[] salt;


    private AccountEntity(String firstName, String lastName, String email) {
        this.id = -1;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public AccountEntity(String firstName, String lastName, String email, String password) {
        this(firstName, lastName, email);
        byte[][] salt_pw = hashPassword(password, null);
        this.salt = salt_pw[0];
        this.password = salt_pw[1];
    }

    public static AccountEntity get(final int id) throws AccountNotFoundException {
        AccountEntity acc = null;
        try (ResultSet res = Connection.executeQuery("Select * from Account where id = ?",
                id, Types.INTEGER)) {
            res.next();
            acc = new AccountEntity(res.getString("first_name"), res.getString("last_name"), res.getString("email"));
            acc.id = res.getInt("id");
            acc.password = res.getBytes("password");
            acc.salt = res.getBytes("salt");
            return acc;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new AccountNotFoundException();
        }
    }

    public static AccountEntity get(final String email) throws AccountNotFoundException {
        AccountEntity acc = null;
        try (ResultSet res = Connection.executeQuery("Select * from Account where email = ?",
                email, Types.VARCHAR)) {

            res.next();
            acc = new AccountEntity(res.getString("first_name"), res.getString("last_name"), res.getString("email"));
            acc.id = res.getInt("id");
            acc.password = res.getBytes("password");
            acc.salt = res.getBytes("salt");
            return acc;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new AccountNotFoundException();
        }
    }

    public static AccountEntity authenticate(final String email, final String password) throws AccountNotFoundException, AuthenticationException {
        AccountEntity acc = get(email);
        byte[][] salt_pw = hashPassword(password, acc.salt);

        if (Arrays.equals(acc.password, salt_pw[1]))
            return acc;
        else
            throw new AuthenticationException();
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

    public void save() throws AccountExistsException {
        if (id > 0) {
            try {
                Connection.execute("Update Account Set first_name = ?, last_name = ? Where id = ?",
                        firstName, Types.VARCHAR,
                        lastName, Types.VARCHAR,
                        id, Types.INTEGER);
            } catch (SQLException e) {
                e.printStackTrace();
                new RuntimeException(); // FIXME concurent exceptions OR delete from other login session
            }
        } else {
            try {
                Connection.execute("Insert Into Account (first_name, last_name, email, password, salt) values (?, ? , ?, ?, ?)",
                        firstName, Types.VARCHAR,
                        lastName, Types.VARCHAR,
                        email, Types.VARCHAR,
                        password, Types.VARBINARY,
                        salt, Types.VARBINARY);
                // TODO update id
            } catch (SQLException e) {
                e.printStackTrace();
                throw new AccountExistsException();
            }
        }
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email +
                '}';
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String getEmail() {
        return email;
    }
}
