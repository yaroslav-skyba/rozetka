package com.gitlab.yaroslavskyba.rozetka;

import com.gitlab.yaroslavskyba.rozetka.dto.RoleDto;
import com.gitlab.yaroslavskyba.rozetka.dto.UserDto;
import com.gitlab.yaroslavskyba.rozetka.service.RoleService;
import com.gitlab.yaroslavskyba.rozetka.service.UserService;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {
    private RoleService roleService;
    private UserService userService;

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    private void createAdmin() {
        final String admin = "admin";

        roleService.create(new RoleDto(null, admin));
        userService.create(new UserDto(null, roleService.get(admin).getUuid(), admin, admin, admin + "@" + admin,
                                       "Y", "S", LocalDate.of(2002, 3, 11)));
    }

    public void start(String[] args) {
        SpringApplication.run(Main.class, args);
        createAdmin();
    }

    public static void main(String[] args) {
        new Main().start(args);
    }
}
