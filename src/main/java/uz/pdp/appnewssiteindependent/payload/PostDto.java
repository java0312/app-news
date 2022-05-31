package uz.pdp.appnewssiteindependent.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {

    @NotBlank(message = "title is mandatory")
    private String title;

    @NotBlank(message = "text is mandatory")
    private String text;

    @NotBlank(message = "url is mandatory")
    private String url;

}
