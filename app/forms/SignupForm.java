package forms;

import models.User;
import play.data.validation.Constraints;
import play.data.validation.ValidationError;

import javax.validation.Constraint;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lubuntu on 8/20/16.
 */
public class SignupForm {

    @Constraints.Required
    public String email;

    @Constraints.Required
    public String password;


    @Constraints.Required
    public String firstName;

    @Constraints.Required
    public String lastName;

    /* validate method that handles a list of validation errors (play calls this)*/
    public  List<ValidationError> validate() {
        List<ValidationError> errors = new ArrayList<>();
        User user = User.find.where().eq("email", email).findUnique(); /* in "email" column , check if email already exists*/
        if(user != null) {
            errors.add(new ValidationError("message", "Email already exists"));
            errors.add(new ValidationError("error","true"));  /* So that js at client script handles it*/
        }
        return errors;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
