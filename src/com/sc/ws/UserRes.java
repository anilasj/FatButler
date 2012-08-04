package com.sc.ws;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.sc.domain.User;
import com.sc.service.UserService;

/**
 * RESTFUL Webservice that encapsulates all operations regarding processing users
 * All operations to this service requires an API Key.
 * @author anila
 *
 */
@Path("/user")
public class UserRes extends Base {

	@POST
	@Path("signUp")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String add(
			@FormParam("api_key") String apiKey,
			@FormParam("username") String name,
			@FormParam("email") String email,
			@FormParam("password") String password,
			@FormParam("country") String country,
			@FormParam("state") String state,
			@FormParam("city") String city) throws Exception {
		
		try {
			String authError = super.authRequest(apiKey);
			if (authError != null) return authError;
			
			//form user Obj
			User user = new User();
			user.setName(name);
			user.setEmail(email);
			user.setPassword(password);
			user.setCountry(country);
			user.setState(state);
			user.setCity(city);
			
			/*if (GenericValidator.isBlankOrNull(user.getEmail()) ||
					GenericValidator.isBlankOrNull(user.getPassword()) || GenericValidator.isBlankOrNull(password2)
					|| GenericValidator.isBlankOrNull(user.getUserid()))
				errors.add("user",new ValidationError("error.allfields.req"));
								
			if (!errors.hasErrors()){
			
				Validator validate = new Validator(user);
				validate.testString("userService.","userid", false,3, 20, errors);
				validate.testEmail("userService.","email", false, 4, 50, errors);
			
				if (!user.getPassword().equals(password2)){
					errors.add("error.userService.req",new ValidationError("error.userService.password.mismatch"));
				}else if (!PasswordHelper.isValidPassword(user.getPassword())){
					errors.add("error.userService.req",new ValidationError("error.userService.password.invalid"));
				}
			}*/
			
			//if (!errors.hasErrors()){
				UserService.getService().insertUser(user);
			//}
			/*if (errors != null && errors.hasErrors())
				return errors.getErrorsAsString();
			else*/
				return WS_SUCCESS;
				
		}catch (Exception ex){
			ex.printStackTrace();
			return WS_ACC_ERR;
		}
	}
}
