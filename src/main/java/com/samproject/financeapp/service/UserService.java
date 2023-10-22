package com.samproject.financeapp.service;

import com.samproject.financeapp.model.User;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;

public interface UserService {


    User createOrFetchFromGoogleProfile(Payload payload);


}
