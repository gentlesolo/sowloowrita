package com.sowloo.sowloowrita.facebook;

import com.sowloo.sowloowrita.data.models.AppUser;
import com.sowloo.sowloowrita.data.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

@Component
public class FacebookConnectionSignup implements ConnectionSignUp {
    @Autowired
    private AppUserRepository appUserRepository;


    @Override
    public String execute(Connection<?> connection) {
        AppUser user = new AppUser();
        user.setUsername(connection.getDisplayName());
        user.setPassword(randomAlphabetic(8));
        appUserRepository.save(user);
        return user.getUsername();
    }
}
