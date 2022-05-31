package uz.pdp.appnewssiteindependent.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.appnewssiteindependent.entity.enums.Permission;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto {

    @NotBlank(message = "name is mandatory")
    private String name;

    @NotEmpty(message = "Permissions must no be empty")
    private List<Permission> permissions;

    private String description;

}
