package it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * This class provides hash utilities.
 */
public final class HashUtil {

    public static final String HASH_DELIMITER = ":";
    public static final String HASH_TYPE = "SHA-256";

    private HashUtil() {
        // empty
    }

    /**
     * Concatenate the provided strings with {@link #HASH_DELIMITER}
     * and return the SHA-256 hash of the resulting string.
     *
     * @param plaintexts the strings to concatenate and hash
     * @return the SHA-256 hash of the concatenated input string
     * @throws NoSuchAlgorithmException if the SHA-256 is not implemented
     * @throws IOException if an I/O error occurs during digest computation
     */
    public static String hash256(String... plaintexts) throws NoSuchAlgorithmException, IOException {
        String plaintext = String.join(HASH_DELIMITER, plaintexts);
        return hash256(plaintext);
    }

    /**
     * Return the SHA-256 hash of the plaintext.
     *
     * @param plaintext the string to hash
     * @return the SHA-256 hash of the input string
     * @throws NoSuchAlgorithmException if the SHA-256 is not implemented
     * @throws IOException if an I/O error occurs during digest computation
     */
    private static String hash256(String plaintext) throws NoSuchAlgorithmException, IOException {
        MessageDigest md = MessageDigest.getInstance(HASH_TYPE);
        byte[] hashInBytes = checksum(plaintext, md);
        return bytesToHex(hashInBytes);
    }

    private static byte[] checksum(String plaintext, MessageDigest md) throws IOException {
        InputStream is = new ByteArrayInputStream(plaintext.getBytes());

        try (DigestInputStream dis = new DigestInputStream(is, md)) {
            // Need to turn checkstyle off because it can't
            // handle empty while functions the right way
            // CHECKSTYLE:OFF
            while (dis.read() != -1) {
                // Empty loop to clear the data
            }
            // CHECKSTYLE:ON
            md = dis.getMessageDigest();
        }

        return md.digest();

    }

    private static String bytesToHex(byte[] hashInBytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : hashInBytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();

    }

}
