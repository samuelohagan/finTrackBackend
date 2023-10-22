package com.samproject.financeapp.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.io.*;
import java.nio.file.Files;

import java.nio.file.Paths;

import org.springframework.stereotype.Component;

@Component
public class JwtKeyManager {

    private final String filePath = "src/main/resources/jwt.key";

    public SecretKey loadOrGenerateKey() {
        try {
            if (Files.exists(Paths.get(filePath))) {
                return loadKeyFromFile();
            } else {
                SecretKey key = Jwts.SIG.HS256.key().build();
                saveKeyToFile(key);
                return key;
            }
        } catch (Exception e) {
            throw new RuntimeException("Error loading or generating JWT key.", e);
        }
    }

    private SecretKey loadKeyFromFile() throws IOException, ClassNotFoundException {
        try (FileInputStream fis = new FileInputStream(filePath);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            return (SecretKey) ois.readObject();
        }
    }

    private void saveKeyToFile(SecretKey key) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(filePath);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(key);
        }
    }
}
