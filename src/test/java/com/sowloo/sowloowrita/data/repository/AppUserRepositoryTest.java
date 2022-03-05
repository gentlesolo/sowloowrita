//package com.sowloo.sowloowrita.data.repository;
//
//
//import com.sowloo.sowloowrita.data.models.AppUser;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import static org.assertj.core.api.Assertions.assertThat;
//
//
//
//@Slf4j
//@SpringBootTest
//public class AppUserRepositoryTest {
//
//    @Autowired
//    private AppUserRepository appUserRepository;
//
//    @Test
//    @DisplayName("Create a new user with cart test")
//    void whenANewUserIsCreated_ThenCreateACartTest(){
//
//
//        AppUser appUser = new AppUser();
//        appUser.setFirstName("Micheal");
//        appUser.setLastName("jeremaih");
//        appUser.setEmail("jeremiah@gmai.com");
////        appUser.setAddress("123 loldos road");
//
//        appUserRepository.save(appUser);
//
//        assertThat(appUser.getId()).isNotNull();
//        assertThat(appUser.getMyCart()).isNotNull();
//
//        log.info("App user created -> {}", appUser);
//    }
//}
