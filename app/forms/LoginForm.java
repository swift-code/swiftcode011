package forms;

import models.User;
import play.data.validation.Constraints;
import play.data.validation.ValidationError;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lubuntu on 8/20/16.
 */
public class LoginForm {

    @Constraints.Required
    public String email;

    Constraints.Required
    public String password;

    public List<ValidationError> validate() {
        List<ValidationError> errors = new ArrayList<>();
        User user = User.authenticate("email", email); /* in "email" column , check if email already exists*/
        return errors;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
