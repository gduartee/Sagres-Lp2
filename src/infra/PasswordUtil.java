package infra;

import java.security.*;
import java.util.HexFormat;

public class PasswordUtil {
    private static final SecureRandom RNG = new SecureRandom();

    public static String randomSaltHex(int bytes) {
        byte[] salt = new byte[bytes];
        RNG.nextBytes(salt);
        return HexFormat.of().formatHex(salt);
    }

    public static String sha256Hex(String saltHex, String rawPassword) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] salt = HexFormat.of().parseHex(saltHex);
            md.update(salt);
            byte[] digest = md.digest(rawPassword.getBytes(java.nio.charset.StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(digest);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean verify(String saltHex, String rawPassword, String expectedHashHex) {
        return sha256Hex(saltHex, rawPassword).equalsIgnoreCase(expectedHashHex);
    }
}
