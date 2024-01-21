package com.example.backlogmanagement.Backlog.Config;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.backlogmanagement.Backlog.Entity.Role;
import com.example.backlogmanagement.Backlog.Entity.User;
import com.example.backlogmanagement.Backlog.Repository.RoleRepository;
import com.example.backlogmanagement.Backlog.Repository.UserRepository;

@Component
public class UserDataLoader implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Überprüfen, ob die Rolle bereits existiert, und sie nur erstellen, wenn sie nicht existiert
        createRoleIfNotExists("ROLE_PRODUCT_OWNER");
        createRoleIfNotExists("ROLE_TEAM_MEMBER");

        // Benutzer erstellen, falls sie nicht existieren
        createUserIfNotExists("productowner", "productowner123", "ROLE_PRODUCT_OWNER");
        createUserIfNotExists("teammember", "teammember123", "ROLE_TEAM_MEMBER");
    }
    

    private void createRoleIfNotExists(String roleName) {
        roleRepository.findByName(roleName).orElseGet(() -> {
            Role role = new Role(roleName);
            return roleRepository.save(role);
        });
    }


    private void createUserIfNotExists(String username, String password, String roleName) {
        userRepository.findByUsername(username).orElseGet(() -> {
            User user = new User();
            user.setUsername(username);
            user.setPassword(passwordEncoder.encode(password));

            Role role = roleRepository.findByName(roleName).orElseGet(() -> {
                Role newRole = new Role(roleName);
                roleRepository.save(newRole); // Speichern der neuen Rolle
                return newRole;
            });

            user.setRoles(Collections.singleton(role)); // Zuweisung der vorhandenen oder neuen Rolle zum Benutzer

            return userRepository.save(user); // Speichern des Benutzers mit der zugehörigen Rolle
        });
    }


}