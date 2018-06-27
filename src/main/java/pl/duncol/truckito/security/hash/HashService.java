package pl.duncol.truckito.security.hash;

import java.util.Map;

public interface HashService {
	Map<String, String> createHashWithSalt(String password);
	boolean validate(String pass, String hash, String salt);
}