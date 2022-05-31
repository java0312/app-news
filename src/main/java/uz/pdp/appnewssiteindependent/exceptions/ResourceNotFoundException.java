package uz.pdp.appnewssiteindependent.exceptions;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.function.Supplier;

@ResponseStatus(HttpStatus.NOT_FOUND)
@AllArgsConstructor
@NoArgsConstructor
public class ResourceNotFoundException extends RuntimeException{

    private String resourceName;

    private String resourceField;

    private Object object;

}

