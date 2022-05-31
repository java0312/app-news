package uz.pdp.appnewssiteindependent.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import uz.pdp.appnewssiteindependent.entity.User;
import uz.pdp.appnewssiteindependent.exceptions.ForbiddenException;

@Component
@Aspect
public class CheckPermissionExecutor {

    //@CheckPermission() annotatsiyasiga kelganda shu metod ishlaydi
    //agar @CheckPermission ichidagi qiyat sistemadagi userning rollari orasida bolmasa xatolik beradi va
    //pastidagi metodni ishlatmaydi
    @Before(value = "@annotation(checkPermission)")
    public void checkUserPermissionMyMethod(CheckPermission checkPermission){
        String value = checkPermission.value();

        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        for (GrantedAuthority authority : principal.getAuthorities()) {
            if (authority.getAuthority().equals(value))
                return;
        }

        throw new ForbiddenException(checkPermission.value(), "Not allowed");
    }

}
