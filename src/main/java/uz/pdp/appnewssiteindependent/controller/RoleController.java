package uz.pdp.appnewssiteindependent.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appnewssiteindependent.entity.Role;
import uz.pdp.appnewssiteindependent.payload.ApiResponse;
import uz.pdp.appnewssiteindependent.payload.RoleDto;
import uz.pdp.appnewssiteindependent.service.RoleService;

import javax.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/role")
public class RoleController {

    @Autowired
    RoleService roleService;

    @PreAuthorize(value = "hasAuthority('ADD_ROLE')")
    @PostMapping
    public HttpEntity<?> createRole(@Valid @RequestBody RoleDto roleDto){
        ApiResponse apiResponse = roleService.createRole(roleDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }


    @PreAuthorize(value = "hasAuthority('VIEW_ROLES')")
    @GetMapping
    public HttpEntity<?> getAllRoles(){
        List<Role> roleList = roleService.getAllRoles();
        return ResponseEntity.ok(roleList);
    }

    @PreAuthorize(value = "hasAuthority('VIEW_ROLES')")
    @GetMapping("/{id}")
    public HttpEntity<?> getRole(@PathVariable Long id){
        Role role = roleService.getRole(id);
        return ResponseEntity.status(role != null ? 200 : 409).body(role);
    }

    @PreAuthorize(value = "hasAuthority('EDIT_ROLE')")
    @PostMapping("/{id}")
    public HttpEntity<?> editRole(@PathVariable Long id,@Valid @RequestBody RoleDto roleDto){
        ApiResponse apiResponse = roleService.editRole(id, roleDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PreAuthorize(value = "hasAuthority('DELETE_ROLE')")
    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteRole(@PathVariable Long id){
        ApiResponse apiResponse = roleService.delete(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

}
