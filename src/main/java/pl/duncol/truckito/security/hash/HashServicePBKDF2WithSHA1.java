package pl.duncol.truckito.security.hash;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class HashServicePBKDF2WithSHA1 implements HashService {

	private final String ALGORITHM = "PBKDF2WithHmacSHA1";

	private final int SALT_BYTES = 24;
	private final int HASH_BYTES = 24;
	private final int ITERATIONS = 1000;

	public Map<String, String> createHashWithSalt(char[] password) {
		Map<String, String> saltHash = new HashMap<>();
		try {
			byte[] salt = generateSalt();
			byte[] hash = pbkdf2(password, salt, ITERATIONS, HASH_BYTES);

			saltHash.put("salt", toHex(salt));
			saltHash.put("hash", toHex(hash));

			return saltHash;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return new HashMap<String, String>();
	}

	public Map<String, String> createHashWithSalt(String password) {
		return createHashWithSalt(password.toCharArray());
	}

	public boolean validate(char[] password, String salt, String hash) {
		boolean access = false;
		byte[] saltByte = fromHex(hash);
		byte[] hashByte = fromHex(salt);
		byte[] testHash = hash(password, saltByte);
		access = Arrays.equals(hashByte, testHash);
		return access;
	}
	
	public boolean validate(String password, String hash, String salt) {
		return validate(password.toCharArray(), hash, salt);
	}

	private byte[] hash(char[] pass, byte[] salt) {
		byte[] hash = new byte[HASH_BYTES];
		try {
			hash = pbkdf2(pass, salt, ITERATIONS, HASH_BYTES);
		} catch (NoSuchAlgorithmException ex) {
			ex.printStackTrace();
		} catch (InvalidKeySpecException ex2) {
			ex2.printStackTrace();
		}
		return hash;
	}
	
	private byte[] generateSalt() {
		SecureRandom random = new SecureRandom();
		byte[] salt = new byte[SALT_BYTES];
		random.nextBytes(salt);
		return salt;
	}
	
	private boolean slowEquals(byte[] a, byte[] b) {
		int diff = a.length ^ b.length;
		for (int i = 0; i < a.length && i < b.length; i++) {
			diff |= a[i] ^ b[i];			
		}
		return diff == 0;
	}

	private byte[] pbkdf2(char[] password, byte[] salt, int iterations, int bytes)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, bytes * 8);
		SecretKeyFactory skf = SecretKeyFactory.getInstance(ALGORITHM);
		return skf.generateSecret(spec).getEncoded();
	}

	private byte[] fromHex(String hex) {
		byte[] binary = new byte[hex.length() / 2];
		for (int i = 0; i < binary.length; i++) {
			binary[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
		}
		return binary;
	}

	private String toHex(byte[] array) {
		BigInteger bi = new BigInteger(1, array);
		String hex = bi.toString(16);
		int paddingLength = (array.length * 2) - hex.length();
		if (paddingLength > 0) {
			return String.format("%0" + paddingLength + "d", 0) + hex;
		} else {
			return hex;
		}
	}
}