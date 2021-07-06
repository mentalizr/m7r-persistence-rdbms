package org.mentalizr.persistence.rdbms.utils;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class PasswordGeneratorTest {

    @Test
    void getPassword() {
        PasswordGenerator passwordGenerator = new PasswordGenerator(8, PasswordGenerator.CAP_LETTERS, PasswordGenerator.LOW_LETTERS, PasswordGenerator.FIGURES);
        String password = passwordGenerator.getPassword();
        System.out.println(password);
        assertEquals(8, password.length());
    }

    @Test
    void getDistinctPasswords() {
        PasswordGenerator passwordGenerator = new PasswordGenerator(8, PasswordGenerator.CAP_LETTERS, PasswordGenerator.LOW_LETTERS, PasswordGenerator.FIGURES);
        Set<String> passwords = passwordGenerator.getDistinctPasswords(10);
        assertEquals(10, passwords.size());
    }


}