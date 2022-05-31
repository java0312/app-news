package uz.pdp.appnewssiteindependent.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.appnewssiteindependent.payload.ApiResponse;
import uz.pdp.appnewssiteindependent.payload.LoginDto;
import uz.pdp.appnewssiteindependent.payload.RegisterDto;
import uz.pdp.appnewssiteindependent.service.AuthService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/register")
    public HttpEntity<?> registerUser(@Valid @RequestBody RegisterDto registerDto){
        ApiResponse apiResponse = authService.registerUser(registerDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PostMapping("/login")
    public HttpEntity<?> loginUser(@Valid @RequestBody LoginDto loginDto){
        ApiResponse apiResponse = authService. loginUser(loginDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200: 409).body(apiResponse);
    }

}
