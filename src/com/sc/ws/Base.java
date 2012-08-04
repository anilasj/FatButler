package com.sc.ws;

import java.util.ArrayList;
import java.util.List;

import com.sc.domain.Error;
import com.sc.util.ConfigProps;

public class Base {
	
	protected List<Error> authRequest(String apiKey) {
		
		List<Error> errors = new ArrayList<Error>();
		if (apiKey == null){
			errors.add(new Error(Error.MISSING_API_KEY, "Missing Auth Api Key"));
			return errors;
		}
		
		//get ScoutMobile Auth Key
		//for now only allow our own mobile app to authenticate
		String mobileWsApiKey = ConfigProps.getString("mobile.authApi.key");
		if (mobileWsApiKey == null) {
			errors.add(new Error(Error.ACC_ERR, "Missing Auth Api Key"));
			return errors;
		}
		else if (!mobileWsApiKey.equals(apiKey)){
			errors.add(new Error(Error.INVALID_API_KEY, "Invalid Api Key"));
			return errors;
		}
		
		return errors;
	}
	
	protected String getErrorsAsString(List<Error> errors) {
		String retValue = "";
		if (errors != null && !errors.isEmpty())
			return null;
		for (Error error:errors){
			retValue += error.getId() + " - " + error.getDescription() + ",";
		}
		if (retValue.lastIndexOf(",") > 0)
			retValue = retValue.substring(0,retValue.length() - 1);
		return retValue;
	}
}
