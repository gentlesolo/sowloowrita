package com.sowloo.sowloowrita;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class SowlooWritaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SowlooWritaApplication.class, args);
    }

//    @Bean
//    PasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public BCryptPasswordEncoder bCryptPasswordEncoder(){
//        return new BCryptPasswordEncoder();
//    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("https://sowloowrita.netlify.app");
            }
        };
    }

//    @Bean
//    CommandLineRunner run(AppUserService appUserService){
//        return args -> {
//            appUserService.saveRole(new Role(null, "ROLE_USER"));
//            appUserService.saveRole(new Role(null, "ROLE_MANAGER"));
//            appUserService.saveRole(new Role(null, "ROLE_ADMIN"));
//            appUserService.saveRole(new Role(null, "ROLE_SUPER_ADMIN"));
//
//            appUserService.saveAppUser(new AppUser(null, "John", "Travolta", "john","me@me.com", "1234",
//                    new ArrayList<>()));
//            appUserService.saveAppUser(new AppUser(null, "Will", "smith", "will","me@me.com", "1234",
//                    new ArrayList<>()));
//            appUserService.saveAppUser(new AppUser(null, "Jim","carry", "jim","me@me.com", "1234",
//                    new ArrayList<>()));
//            appUserService.saveAppUser(new AppUser(null, "Tunde", "afolabi", "tunde","me@me.com", "1234",
//                    new ArrayList<>()));
//
//            appUserService.addRoleToUser("john","ROLE_USER");
//            appUserService.addRoleToUser("will","ROLE_MANAGER");
//            appUserService.addRoleToUser("jim","ROLE_ADMIN");
//            appUserService.addRoleToUser("tunde","ROLE_SUPER_ADMIN");
//        };
//    }

}
