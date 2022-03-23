package org.mentalizr.persistence.rdbms.utils;

import de.mkammerer.argon2.Argon2Factory;

public class Argon2Hash {

    private static final int HASH_ITERATIONS = 20;
    private static final int HASH_MEMORY_KIBIBYTE = 65536;
    private static final int HASH_PARALLELISM = 1;

    public static String getHash(char[] password) {
        de.mkammerer.argon2.Argon2 argon2 = Argon2Factory.create();
        try {
            return argon2.hash(HASH_ITERATIONS, HASH_MEMORY_KIBIBYTE, HASH_PARALLELISM, password);
        } finally {
            argon2.wipeArray(password);
        }
    }

}
