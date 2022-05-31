package uz.pdp.appnewssiteindependent.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appnewssiteindependent.entity.Role;
import uz.pdp.appnewssiteindependent.payload.ApiResponse;
import uz.pdp.appnewssiteindependent.payload.RoleDto;
import uz.pdp.appnewssiteindependent.repository.RoleRepository;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    @Autowired
    RoleRepository roleRepository;

    public ApiResponse createRole(RoleDto roleDto) {
        boolean exists = roleRepository.existsByName(roleDto.getName());
        if (exists)
            return new ApiResponse("This role exists!", false);

        roleRepository.save(new Role(
                roleDto.getName(),
                roleDto.getPermissions(),
                roleDto.getDescription()
        ));

        return new ApiResponse("Role created", true);
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public Role getRole(Long id) {
        Optional<Role> optionalRole = roleRepository.findById(id);
        return optionalRole.orElse(null);
    }

    public ApiResponse editRole(Long id, RoleDto roleDto) {
        boolean exists = roleRepository.existsByNameAndIdNot(roleDto.getName(), id);
        if (exists)
            return new ApiResponse("This role already exists!", false);

        Optional<Role> optionalRole = roleRepository.findById(id);
        if (optionalRole.isEmpty())
            return new ApiResponse("Role not found!", false);

        Role role = optionalRole.get();
        role.setName(roleDto.getName());
        role.setPermissions(roleDto.getPermissions());
        role.setDescription(roleDto.getDescription());
        roleRepository.save(role);

        return new ApiResponse("Role edited!", true);
    }

    public ApiResponse delete(Long id) {
        if (!roleRepository.existsById(id))
            return new ApiResponse("Role not found!", false);

        roleRepository.deleteById(id);
        return new ApiResponse("Role deleted!", true);
    }
}
