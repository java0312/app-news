package uz.pdp.appnewssiteindependent.payload;

import lombok.Data;

@Data
public class EnabledDto {
    private Long userId;
    private boolean enabled;
}
