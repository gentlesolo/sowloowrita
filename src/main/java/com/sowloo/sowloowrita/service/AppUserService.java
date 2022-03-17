package com.sowloo.sowloowrita.service;

import com.sowloo.sowloowrita.data.models.AppUser;
import com.sowloo.sowloowrita.data.models.Role;

import java.util.List;

public interface AppUserService {
    AppUser saveAppUser(AppUser user);
    Role saveRole(Role role);
    void addRoleToUser(String username, String roleName);
    AppUser getAppUser(String username);
    List<AppUser> getAppUsers();
}
