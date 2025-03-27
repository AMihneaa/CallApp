package com.tpi.demo.config;

import com.tpi.demo.models.Airplane.Airplane;
import com.tpi.demo.models.Airplane.AirplaneRepository;
import com.tpi.demo.models.Privilege.Privilege;
import com.tpi.demo.models.Privilege.PrivilegeRepository;
import com.tpi.demo.models.Role.Role;
import com.tpi.demo.models.Role.RoleRepository;
import com.tpi.demo.models.User.User;
import com.tpi.demo.models.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AirplaneRepository airplaneRepository;

    private Privilege createPrivilegeIfNotFound(String name){
        return privilegeRepository.findByName(name)
                .orElseGet(
                        () -> privilegeRepository.save(new Privilege(name))
                );
    }

    private Role createRoleIfNotFound(String name, Set<Privilege> privileges){
        return roleRepository.findByName(name)
                .orElseGet(
                        () -> {
                            Role role = new Role(name, privileges);

                            return roleRepository.save(role);
                        }
                );
    }

    public void AddUsers(){
        Privilege user_viewTest = createPrivilegeIfNotFound("user_viewTest");
        Privilege admin_viewTest = createPrivilegeIfNotFound("admin_viewTest");

        Role USER_ROLE = createRoleIfNotFound("ROLE_USER", Set.of(user_viewTest));
        Role ADMIN_ROLE = createRoleIfNotFound("ROLE_ADMIN", Set.of(admin_viewTest));


        List<User> userList = new ArrayList<>();

        userList.add(
                new User(
                        "user",
                        "user@gmail.com",
                        passwordEncoder.encode("userPass"),
                        Set.of(USER_ROLE)
                )
        );

        userList.add(
                new User(
                        "admin",
                        "admin@gmail.com",
                        passwordEncoder.encode("adminPass"),
                        Set.of(USER_ROLE, ADMIN_ROLE)
                )
        );

        userList.forEach(
                user -> userRepository.findByEmail(user.getEmail())
                    .orElseGet(() -> userRepository.save(user))
        );
    }

    public void AddPlanes(){
        List<Airplane> airplanes = new ArrayList<>();
        airplanes.add(
                new Airplane("Boeing 737", 20,"Lufthansa", "ACTIVE")
        );

        airplanes.add(
                new Airplane("TestModel", 100, "Loky", "MAINTENANCE")
        );

        airplanes.add(
                new Airplane("CEVAModel", 2, "Ceva", "ACTIVE")
        );

        airplanes.forEach(
                plane -> airplaneRepository.findAirplaneByModel(plane.getModel())
                            .orElseGet(
                                    () -> airplaneRepository.save(plane)
                            )
        );
    }

    @Override
    public void run(String... args) throws Exception{
        AddUsers();
        AddPlanes();
    }

}
