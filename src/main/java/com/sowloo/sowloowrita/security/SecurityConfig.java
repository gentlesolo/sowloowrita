package com.sowloo.sowloowrita.security;

import com.sowloo.sowloowrita.facebook.FacebookConnectionSignup;
import com.sowloo.sowloowrita.facebook.FacebookSignInAdapter;
import com.sowloo.sowloowrita.filter.CustomAuthenticationFilter;
import com.sowloo.sowloowrita.service.MyUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.mem.InMemoryUsersConnectionRepository;
import org.springframework.social.connect.support.ConnectionFactoryRegistry;
import org.springframework.social.connect.web.ProviderSignInController;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;

import javax.servlet.http.HttpServletResponse;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor

public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    MyUserDetailsService userDetailsService;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private FacebookConnectionSignup facebookConnectionSignup ;
    @Value("${spring.social.facebook.appSecret}")
    String appSecret;

    @Value("${spring.social.facebook.appId}")
    String appId;

    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().exceptionHandling().authenticationEntryPoint((request, response, exception)->response.sendError(HttpServletResponse.SC_UNAUTHORIZED))
                .and()
                .addFilter(new CustomAuthenticationFilter(authenticationManager()))
                .authorizeRequests()
                .antMatchers("/api/login/**", "/api/token/refresh/**").permitAll()
                .antMatchers(GET, "/api/appuser/**").hasAnyAuthority("ROLE_USER")
                .antMatchers(POST, "/api/appuser/save/**").hasAnyAuthority("ROLE_ADMIN")
                .antMatchers(HttpMethod.POST, "/facebook/sign-in").permitAll()
                .anyRequest().authenticated();
    }
    @Bean
    public ProviderSignInController providerSignInController() {
        ConnectionFactoryLocator connectionFactoryLocator =
                connectionFactoryLocator();
        UsersConnectionRepository usersConnectionRepository =
                getUsersConnectionRepository(connectionFactoryLocator);
        ((InMemoryUsersConnectionRepository) usersConnectionRepository)
                .setConnectionSignUp(facebookConnectionSignup);
        return new ProviderSignInController(connectionFactoryLocator,
                usersConnectionRepository, new FacebookSignInAdapter());
    }

    private ConnectionFactoryLocator connectionFactoryLocator() {
        ConnectionFactoryRegistry registry = new ConnectionFactoryRegistry();
        registry.addConnectionFactory(new FacebookConnectionFactory(appId, appSecret));
        return registry;
    }

    private UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        return new InMemoryUsersConnectionRepository(connectionFactoryLocator);
    }

    /*
    An async js code to include in the HTML file in order to load the FB SDK on all website's pages

<script>
  window.fbAsyncInit = function() {
    FB.init({
      appId      : '{your-app-id}',
      cookie     : true,
      xfbml      : true,
      version    : '{api-version}'
    });

    FB.AppEvents.logPageView();

  };

  (function(d, s, id){
     var js, fjs = d.getElementsByTagName(s)[0];
     if (d.getElementById(id)) {return;}
     js = d.createElement(s); js.id = id;
     js.src = "https://connect.facebook.net/en_US/sdk.js";
     fjs.parentNode.insertBefore(js, fjs);
   }(document, 'script', 'facebook-jssdk'));
</script>

Adding the login button ideally right after the opening body tag.

<div id="fb-root"></div>
<script async defer crossorigin="anonymous" src="https://connect.facebook.net/en_GB/sdk.js#xfbml=1&version=v13.0&appId=1355138698283323&autoLogAppEvents=1" nonce="BAuB3cpv"></script>

Add this code where you want the button to reflect

<div class="fb-login-button" data-width="" data-size="large" data-button-type="continue_with" data-layout="default" data-auto-logout-link="false" data-use-continue-as="false"></div>


To check the login status


FB.getLoginStatus(function(response) {
    statusChangeCallback(response);
});


<fb:login-button
  scope="public_profile,email"
  onlogin="checkLoginState();">
</fb:login-button>


function checkLoginState() {
  FB.getLoginStatus(function(response) {
    statusChangeCallback(response);
  });
}
     */
}
