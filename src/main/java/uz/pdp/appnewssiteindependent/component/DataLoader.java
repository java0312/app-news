package uz.pdp.appnewssiteindependent.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.pdp.appnewssiteindependent.entity.Role;
import uz.pdp.appnewssiteindependent.entity.User;
import uz.pdp.appnewssiteindependent.entity.enums.Permission;
import uz.pdp.appnewssiteindependent.repository.RoleRepository;
import uz.pdp.appnewssiteindependent.repository.UserRepository;
import uz.pdp.appnewssiteindependent.utils.AppConstants;

import java.util.Arrays;

import static uz.pdp.appnewssiteindependent.entity.enums.Permission.*;

@Component
public class DataLoader implements CommandLineRunner {


    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Value("${spring.sql.init.mode}")
    private String initMode;

    @Override
    public void run(String... args) throws Exception {

        if (initMode.equals("never"))
            return;

        Permission[] values = Permission.values();

        Role adminRole = roleRepository.save(new Role(
                AppConstants.ADMIN,
                Arrays.stream(values).toList(),
                "This is Admin"
        ));


        Role userRole = roleRepository.save(new Role(
                AppConstants.USER,
                Arrays.asList(ADD_COMMENT, EDIT_COMMENT, DELETE_MY_COMMENT),
                "This is User"
        ));

        userRepository.save(new User(
                "Admin",
                "admin",
                passwordEncoder.encode("admin123"),
                adminRole,
                true
        ));

        userRepository.save(new User(
                "User",
                "user",
                passwordEncoder.encode("user123"),
                userRole,
                true
        ));

    }
}
