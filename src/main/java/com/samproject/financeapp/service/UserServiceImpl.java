package com.samproject.financeapp.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.samproject.financeapp.model.User;
import com.samproject.financeapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;
    @Override
    public User createOrFetchFromGoogleProfile(GoogleIdToken.Payload payload) {
        String email = payload.getEmail();
        String userId = payload.getSubject();
        String name = (String) payload.get("name");
        String pictureUrl = (String) payload.get("picture");
        String locale = (String) payload.get("locale");
        String familyName = (String) payload.get("family_name");
        String givenName = (String) payload.get("given_name");
        boolean emailVerified = payload.getEmailVerified();

        Optional<User> maybeUser = userRepository.findByGoogleId(userId);

        User user;
        if(maybeUser.isPresent()){
            user = maybeUser.get();
        } else {
            user = new User();
            user.setEmail(email);
            user.setGoogleId(userId);
            user.setName(name);
            user.setImageUrl(pictureUrl);
            user.setFamilyName(familyName);
            user.setGivenName(givenName);
            user.setEmailVerified(emailVerified);
            userRepository.save(user);
        }
        return user;
    }
}
