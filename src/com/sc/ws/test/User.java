package com.sc.ws.test;

import java.net.URI;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class User {

	public static void main(String[] args){
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		WebResource service = client.resource(getBaseURI());
		try{
		//Get XML
		System.out.println("User " + service.path("api").path("user/add?api_key=78516001Ase0Z87nc2kxioQmyluSBEat8&email=anilasj@hotmail.com&username=anila&password=eternity&city=Phoenix&state=Arizona&country=USA").accept(MediaType.TEXT_PLAIN).get(String.class));
    	}catch (Exception ex){
			ex.printStackTrace();
		}
		
	}
	
	private static URI getBaseURI(){
		return UriBuilder.fromPath("http://localhost:8080/scapp").build();
	}

}
