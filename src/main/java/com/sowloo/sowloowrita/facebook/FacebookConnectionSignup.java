package com.sowloo.sowloowrita.facebook;

import com.sowloo.sowloowrita.data.models.AppUser;
import com.sowloo.sowloowrita.data.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;

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
