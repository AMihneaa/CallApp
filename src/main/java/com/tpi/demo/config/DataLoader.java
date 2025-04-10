package com.tpi.demo.config;

import com.tpi.demo.models.Airplane.Airplane;
import com.tpi.demo.models.Airplane.AirplaneRepository;
import com.tpi.demo.models.Bus.Bus;
import com.tpi.demo.models.Bus.BusRepository;
import com.tpi.demo.models.Enums.TransportType;
import com.tpi.demo.models.Point.StopPoint;
import com.tpi.demo.models.Privilege.Privilege;
import com.tpi.demo.models.Privilege.PrivilegeRepository;
import com.tpi.demo.models.Role.Role;
import com.tpi.demo.models.Role.RoleRepository;
import com.tpi.demo.models.Route.Route;
import com.tpi.demo.models.Route.RouteRepository;
import com.tpi.demo.models.User.User;
import com.tpi.demo.models.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.time.LocalDateTime;
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

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private BusRepository busRepository;

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
                new Airplane("Boeing 737", 20,"Lufthansa")
        );

        airplanes.add(
                new Airplane("TestModel", 100, "Loky")
        );

        airplanes.add(
                new Airplane("CEVAModel", 2, "Ceva")
        );

        airplanes.forEach(
                plane -> airplaneRepository.findAirplaneByModel(plane.getModel())
                            .orElseGet(
                                    () -> airplaneRepository.save(plane)
                            )
        );
    }

    public void AddRoutes(){
        List<StopPoint> bucuresti_constanta = new ArrayList<>();

        bucuresti_constanta.add(new StopPoint("bucuresti", LocalDateTime.of(2025, 4, 10, 9, 0), LocalDateTime.of(2025, 4, 10, 9, 10)));
        bucuresti_constanta.add(new StopPoint("fetesti", LocalDateTime.of(2025, 4, 10, 11, 30), LocalDateTime.of(2025, 4, 10, 11, 30)));
        bucuresti_constanta.add(new StopPoint("constanta", LocalDateTime.of(2025, 4, 10, 13, 45), null));

        List<StopPoint> festesti_constanta = new ArrayList<>();

        festesti_constanta.add(new StopPoint("fetesti", LocalDateTime.of(2025, 3, 10, 11, 30), LocalDateTime.of(2025, 4, 10, 11, 30)));
        festesti_constanta.add(new StopPoint("constanta", LocalDateTime.of(2025, 4, 10, 13, 45), null));


        List<Route> routes = new ArrayList<>();

        List<Airplane> airplane = airplaneRepository.findAll();

        routes.add(
                new Route(bucuresti_constanta, airplane.get(0).getId(), TransportType.AIRPLANE, 20)
        );
        routes.add(
                new Route(festesti_constanta, airplane.get(1).getId(), TransportType.AIRPLANE, 30)
        );

        routes.forEach(route -> {
            boolean exists = routeRepository.existsByTransportIdAndStops_DepartureTime(
                    route.getTransportId(),
                    route.getStops().get(0).getDepartureTime()
            );

            if (!exists) {
                routeRepository.save(route);
            }
        });
    }

    private void AddBuses(){
        List<Bus> buses = new ArrayList<>();

        buses.add(
                new Bus("model 1", 10, "company1")
        );
        buses.add(
                new Bus("model 2", 15, "company2")
        );

        buses.forEach(
                bus -> busRepository.findByModel(bus.getModel())
                        .orElseGet(
                                () -> busRepository.save(bus)
                        )
        );
    }

    @Override
    public void run(String... args) throws Exception{
        if (args.length > 0) {
            switch (args[0].toLowerCase()) {
                case "user":
                    AddUsers();
                    break;
                case "plane":
                    AddPlanes();
                    break;
                case "route":
                    AddRoutes();
                    break;
                case "bus":
                    AddBuses();
                    break;
                default:
                    System.out.println("Invalid argument. Use 'user', 'plane', or 'route'.");
            }
        } else {
            System.out.println("No arguments provided. Use 'user', 'plane', or 'route'.");
        }
    }

}
