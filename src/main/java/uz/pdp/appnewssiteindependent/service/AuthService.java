package uz.pdp.appnewssiteindependent.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.appnewssiteindependent.entity.User;
import uz.pdp.appnewssiteindependent.exceptions.ResourceNotFoundException;
import uz.pdp.appnewssiteindependent.payload.ApiResponse;
import uz.pdp.appnewssiteindependent.payload.LoginDto;
import uz.pdp.appnewssiteindependent.payload.RegisterDto;
import uz.pdp.appnewssiteindependent.repository.RoleRepository;
import uz.pdp.appnewssiteindependent.repository.UserRepository;
import uz.pdp.appnewssiteindependent.security.JwtProvider;
import uz.pdp.appnewssiteindependent.utils.AppConstants;

import java.util.Optional;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        return optionalUser.orElse(null);
    }


    public ApiResponse registerUser(RegisterDto registerDto) {
        if (!registerDto.getPassword().equals(registerDto.getPrePassword()))
            return new ApiResponse("Passwords is not same", false);

        boolean existsByUsername = userRepository.existsByUsername(registerDto.getUsername());
        if (existsByUsername)
            return new ApiResponse("This username is busy", false);

        User user = new User();
        user.setUsername(registerDto.getUsername());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setFullName(registerDto.getFullName());
        user.setRole(roleRepository.findByName(AppConstants.USER).orElseThrow(
                () -> new ResourceNotFoundException("role", "name", AppConstants.USER)
        ));
        user.setEnabled(true);
        userRepository.save(user);

        return new ApiResponse("You registered successfully!", true);
    }


    public ApiResponse loginUser(LoginDto loginDto) {
        try {
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginDto.getUsername(),
                    loginDto.getPassword()
            ));

            User principal = (User) authenticate.getPrincipal();

            String token = jwtProvider.generateToken(principal.getUsername(), principal.getRole().getPermissions());

            return new ApiResponse("Your token: " + token, true);
        } catch (Exception e) {
            return new ApiResponse("Username or password is wrong!", false);
        }
    }

}
