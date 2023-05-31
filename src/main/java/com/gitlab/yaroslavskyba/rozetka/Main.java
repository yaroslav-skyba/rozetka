package com.gitlab.yaroslavskyba.rozetka;

import com.gitlab.yaroslavskyba.rozetka.dto.RoleDto;
import com.gitlab.yaroslavskyba.rozetka.dto.UserDto;
import com.gitlab.yaroslavskyba.rozetka.service.RoleService;
import com.gitlab.yaroslavskyba.rozetka.service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.time.LocalDate;

@SpringBootApplication
public class Main {
    private final RoleService roleService;
    private final UserService userService;

    public Main(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    /*@PostConstruct
    private void createAdmin() {
        final String admin = "admin";

        roleService.create(new RoleDto(null, admin));
        userService.create(new UserDto(null, roleService.get(admin).getUuid(), admin, admin, admin + "@" + admin,
                "Y", "S", LocalDate.of(2002, 3, 11)));
    }*/
}
