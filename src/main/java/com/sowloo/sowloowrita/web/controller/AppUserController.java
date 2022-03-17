package com.sowloo.sowloowrita.web.controller;

import com.sowloo.sowloowrita.data.models.AppUser;
import com.sowloo.sowloowrita.data.models.Role;
import com.sowloo.sowloowrita.service.AppUserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@CrossOrigin
public class AppUserController {
    private final AppUserService appUserService;

    @GetMapping("/appusers")
    public ResponseEntity<List<AppUser>>getAppUsers(){
        return ResponseEntity.ok().body(appUserService.getAppUsers());
    }

    @PostMapping("/appuser/save")
    public ResponseEntity<AppUser>saveAppUsers(@RequestBody AppUser appUser){
        URI uri =
                URI.create(ServletUriComponentsBuilder
                        .fromCurrentContextPath()
                        .path("/api/appuser/save")
                        .toUriString());
        return ResponseEntity.created(uri).body(appUserService.saveAppUser(appUser));
    }

    @PostMapping("/role/save")
    public ResponseEntity<Role>saveAppUsers(@RequestBody Role role){
        URI uri =
                URI.create(ServletUriComponentsBuilder
                        .fromCurrentContextPath()
                        .path("/api/role/save")
                        .toUriString());
        return ResponseEntity.created(uri).body(appUserService.saveRole(role));
    }

    @PostMapping("/role/addtouser")
    public ResponseEntity<?>addRoleToUser(@RequestBody RoleToUserForm form){
        appUserService.addRoleToUser(form.getUsername(), form.getRolename());
        return ResponseEntity.ok().build();
    }

//    @GetMapping("/token/refresh")
//    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        String authorizationHeader = request.getHeader(AUTHORIZATION);
//        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
//            try {
//                String refresh_token = authorizationHeader.substring("Bearer ".length());
//                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
//                JWTVerifier verifier = JWT.require(algorithm).build();
//                DecodedJWT decodedJWT = verifier.verify(refresh_token);
//                String username = decodedJWT.getSubject();
//                AppUser appUser = appUserService.getAppUser(username);
//                String access_Token = com.auth0.jwt.JWT.create()
//                        .withSubject(appUser.getUsername())
//                        .withExpiresAt(new Date(System.currentTimeMillis() + 10*60*1000))
//                        .withIssuer(request.getRequestURL().toString())
//                        .withClaim("roles",
//                                appUser.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
//                        .sign(algorithm);
//                Map<String, String> tokens = new HashMap<>();
//                tokens.put("access_token", access_Token);
//                tokens.put("refresh_token", refresh_token);
//                response.setContentType(APPLICATION_JSON_VALUE);
//                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
//            }catch (Exception exception) {
//                response.setHeader("error", exception.getMessage());
//                response.setStatus(FORBIDDEN.value());
////                    response.sendError(FORBIDDEN.value());
//                Map<String, String> error = new HashMap<>();
//                error.put("error_message", exception.getMessage());
//                response.setContentType(APPLICATION_JSON_VALUE);
//                new ObjectMapper().writeValue(response.getOutputStream(), error);
//            }
//        }else {
//            throw new RuntimeException("Refresh token is missing");
//        }
//    }

}
@Data
class RoleToUserForm{
    private String username;
    private String rolename;
}
