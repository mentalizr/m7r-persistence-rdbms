package org.mentalizr.persistence.rdbms.utils;

import java.util.HashSet;
import java.util.Set;

public class PasswordGenerator {

	public static final String CAP_LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static final String LOW_LETTERS = "abcdefghijklmnopqrstuvwxyz";
	public static final String FIGURES = "0123456789";
	public static final String TYPEWRITE = "23456789qwertzupasdfghjyxcvbnmQWERTZUPASDFGHJLYXCVBNM";
	public static final String CAP_TYPEWRITE = "23456789QWERTZUPASDFGHJLYXCVBNM";
	public static final String LOW_TYPEWRITE = "23456789qwertzupasdfghjyxcvbnm";
	public static final String SOME_SPECIAL_CHARS = "!§$%&()=?+*#:_-";

	private final Set<Character> characterSet;
	private final Set<String> blockedPasswords;
	private int length = 0;

	public PasswordGenerator(int length, String... characterSet) {
		this.characterSet = new HashSet<>();
		this.blockedPasswords = new HashSet<>();
		this.length = length;
		for (String characterSetArgument : characterSet) {
			this.addToCharacterSet(characterSetArgument);
		}
	}

	public PasswordGenerator(int length, Set<String> blockedPasswords, String... characterSet) {
		this.characterSet = new HashSet<>();
		this.blockedPasswords = blockedPasswords;
		this.length = length;
		for (String characterSetArgument : characterSet) {
			this.addToCharacterSet(characterSetArgument);
		}
	}

	private void addToCharacterSet(String characters) {
		byte[] charBytes = characters.getBytes();
		for (byte charByte : charBytes) {
			this.characterSet.add((char) charByte);
		}
	}

	public String getPassword() {
		
		int min = 0;
		int max = this.characterSet.size() - 1;
		Object[] characterSetAsObjects = this.characterSet.toArray();
		
		StringBuilder password = new StringBuilder();
		
		for (int i = 1; i <= this.length; i++) {
			int nRandom = (int) (min + Math.floor(Math.random() * (max - min + 1)));
			Character curCharacter = (Character) characterSetAsObjects[nRandom];
			password.append(curCharacter.toString());
		}
		
		return password.toString();
	}

	public Set<String> getPasswords(int number) {
		Set<String> passwords = new HashSet<>();
		for (int i = 1; i <= number; i++) {
			passwords.add(getPassword());
		}
		return passwords;
	}
	
	public String getDistinctPassword() {
		
		long searchTimeMillis = 10000;		
		long startTimestamp = System.currentTimeMillis();
		
		while (System.currentTimeMillis() < startTimestamp + searchTimeMillis) {
			String password = this.getPassword();
			if (!this.blockedPasswords.contains(password)) {
				this.blockedPasswords.add(password);
				return password;
			}
		}
		
		throw new RuntimeException("No further distinct password found. Timeout after searching for " + searchTimeMillis + " msec.");
	}

	public Set<String> getDistinctPasswords(int number) {
		Set<String> passwords = new HashSet<>();
		for (int i = 1; i <= number; i++) {
			passwords.add(getDistinctPassword());
		}
		return passwords;
	}

}
