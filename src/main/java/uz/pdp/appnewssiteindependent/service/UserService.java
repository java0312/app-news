package uz.pdp.appnewssiteindependent.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appnewssiteindependent.entity.Role;
import uz.pdp.appnewssiteindependent.entity.User;
import uz.pdp.appnewssiteindependent.payload.ApiResponse;
import uz.pdp.appnewssiteindependent.payload.AppointmentRoleDto;
import uz.pdp.appnewssiteindependent.payload.EnabledDto;
import uz.pdp.appnewssiteindependent.payload.UserDto;
import uz.pdp.appnewssiteindependent.repository.RoleRepository;
import uz.pdp.appnewssiteindependent.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;


    public ApiResponse addUser(UserDto userDto) {
        boolean existsByUsername = userRepository.existsByUsername(userDto.getUsername());
        if (existsByUsername)
            return new ApiResponse("This username already exists!", false);

        Optional<Role> optionalRole = roleRepository.findById(userDto.getRoleId());
        if (optionalRole.isEmpty())
            return new ApiResponse("Role not found!", false);

        userRepository.save(new User(
                userDto.getFullName(),
                userDto.getUsername(),
                userDto.getPassword(),
                optionalRole.get(),
                true
        ));

        return new ApiResponse("User added!", true);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUser(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.orElse(null);
    }

    public ApiResponse editUser(Long id, UserDto userDto) {
        boolean exists = userRepository.existsByUsernameAndIdNot(userDto.getUsername(), id);
        if (exists)
            return new ApiResponse("This User exists!", false);

        Optional<Role> optionalRole = roleRepository.findById(userDto.getRoleId());
        if (optionalRole.isEmpty())
            return new ApiResponse("Role not found!", false);

        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty())
            return new ApiResponse("User not found!", false);

        User user = optionalUser.get();
        user.setFullName(userDto.getFullName());
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setRole(optionalRole.get());
        userRepository.save(user);

        return new ApiResponse("User edited!", true);
    }

    public ApiResponse deleteUser(Long id) {
        boolean exists = userRepository.existsById(id);
        if (exists)
            return new ApiResponse("User not found!", false);

        userRepository.deleteById(id);
        return new ApiResponse("User deleted!", true);
    }

    public ApiResponse enabled(EnabledDto enabledDto) {
        Optional<User> optionalUser = userRepository.findById(enabledDto.getUserId());
        if (optionalUser.isEmpty())
            return new ApiResponse("User not found!", false);

        User user = optionalUser.get();
        user.setEnabled(enabledDto.isEnabled());
        userRepository.save(user);
        return new ApiResponse("User enabled: " + user.isEnabled(), true);
    }
}
