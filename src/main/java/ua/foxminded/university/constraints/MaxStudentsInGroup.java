package ua.foxminded.university.constraints;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = MaxStudentsInGroupValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface MaxStudentsInGroup {
    String message() default "Group exceeds the maximum number of students allowed";
    Class <?> [] groups() default {};
    Class <? extends Payload> [] payload() default {};
}
