package com.example.myproject.User;

import com.example.myproject.entities.User;
import com.example.myproject.entities.UserVerificationToken;
import com.example.myproject.repositories.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.Random;

@Service
public class VerificationTokenService {

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    public VerificationTokenService(VerificationTokenRepository verificationTokenRepository) {
        this.verificationTokenRepository = verificationTokenRepository;
    }

    public UserVerificationToken createVerificationToken(User user) {
        String token = generateVerificationToken();
        LocalDateTime expiryDate = LocalDateTime.now().plusDays(1);

        UserVerificationToken verificationToken = new UserVerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);
        verificationToken.setExpiryDate(expiryDate);
        verificationTokenRepository.save(verificationToken);

        return verificationToken;
    }

    public UserVerificationToken findByToken(String token) {
        return verificationTokenRepository.findByToken(token);
    }

    public void saveVerificationToken(UserVerificationToken verificationToken) {
        verificationTokenRepository.save(verificationToken);
    }

    // méthode pour valider si le jeton est encore valide
    public boolean isValidVerificationToken(UserVerificationToken verificationToken) {
        return verificationToken != null && !isExpired();
    }
    public boolean isExpired() {
        UserVerificationToken verificationToken = new UserVerificationToken();
        LocalDateTime now = LocalDateTime.now();
        return now.isAfter(verificationToken.getExpiryDate());
    }
    // méthode pour générer un jeton aléatoire
    public String generateVerificationToken() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        int length = 30;
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            sb.append(characters.charAt(index));
        }
        return sb.toString();
    }
}
