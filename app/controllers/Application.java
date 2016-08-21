package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import forms.LoginForm;
import forms.SignupForm;
import models.Profile;
import models.User;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;

/**
 * Created by lubuntu on 8/21/16.
 */
public class Application extends Controller {

    @Inject   /*Dependency Injection to convert JSOn to form object */
    FormFactory formfactory;

    @Inject   /*Dependency Injection to convert form object to JSON */
    ObjectMapper objectMapper;

    public Result signup(){
        Form<SignupForm> form = formfactory.form(SignupForm.class).bindFromRequest();   /* validate method s called by bindFrom reqquest*/
        if (form.hasErrors()) {    /* if there s anything wrong in validation*/
            return ok(form.errorsAsJson());   /*return errors as JSON */
        }
        Profile profile = new Profile(form.data().get("firstName"),form.data().get("lastName"));
        Profile.db().save(profile);

        User user = new User(form.data().get("email"), form.data().get("password"));
        user.profile = profile;
        User.db().save(user);

        return ok((JsonNode) objectMapper.valueToTree(user));
    }

    public Result login(){
        Form<LoginForm> form = formfactory.form(LoginForm.class).bindFromRequest();
        if(form.hasErrors()){
            return ok(form.errorsAsJson());
        }
        return  ok(); /* Success with 200 as value */
    }
}