package uz.pdp.appnewssiteindependent.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appnewssiteindependent.entity.User;
import uz.pdp.appnewssiteindependent.payload.*;
import uz.pdp.appnewssiteindependent.service.UserService;

import javax.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;

    //ADMIN tomonidan user qo'shish
    @PreAuthorize(value = "hasAuthority('ADD_USER')")
    @PostMapping
    public HttpEntity<?> addUser(@Valid @RequestBody UserDto userDto) {
        ApiResponse apiResponse = userService.addUser(userDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PreAuthorize(value = "hasAuthority('VIEW_USERS')")
    @GetMapping
    public HttpEntity<?> getAllUsers() {
        List<User> userList = userService.getAllUsers();
        return ResponseEntity.ok(userList);
    }

    @PreAuthorize(value = "hasAuthority('VIEW_USERS')")
    @GetMapping("/{id}")
    public HttpEntity<?> getUser(@PathVariable Long id) {
        User user = userService.getUser(id);
        return ResponseEntity.status(user != null ? 200 : 409).body(user);
    }

    @PreAuthorize(value = "hasAuthority('EDIT_USER')")
    @PutMapping("/{id}")
    public HttpEntity<?> editUser(@PathVariable Long id, @Valid @RequestBody UserDto userDto){
        ApiResponse apiResponse = userService.editUser(id, userDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PreAuthorize(value = "hasAuthority('DELETE_USER')")
    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteUser(@PathVariable Long id){
        ApiResponse apiResponse = userService.deleteUser(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    //Userni o'chirish, yoqish
    @PreAuthorize(value = "hasAuthority('EDIT_USER')")
    @PutMapping("/enabled")
    public HttpEntity<?> enabled(@RequestBody EnabledDto enabledDto){
        ApiResponse apiResponse = userService.enabled(enabledDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

}
