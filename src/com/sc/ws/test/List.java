package com.sc.ws.test;

import java.net.URI;
import java.util.ArrayList;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriBuilder;

import com.sc.domain.Response;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class List {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		try{
			//Integer id = addList(client);
			Integer id = 1;
			//updateList(id, client);
			//addListItem(id, "Get Milk", client);
			//addListItem(id, "Get Eggs", client);
			//Integer tid = makeListToDo(id, client);
			//Integer tid = 8;
			//markDone(id, tid, client);
			//deleteList(id, client);
			java.util.List<com.sc.domain.List> lists = getLists("1", client);
			//getListItems("1", client);
		}catch (Exception ex){
			ex.printStackTrace();
		}
	}
	private static java.util.List<com.sc.domain.List> getLists(String uid, Client client) {
		java.util.List<com.sc.domain.List> lists = new ArrayList<com.sc.domain.List> ();
		WebResource service = client.resource(UriBuilder.fromPath("http://localhost:8080/scapp").build());
		System.out.println("JSON-one=" + service.path("api").path("list/78516001Ase0Z87nc2kxioQmyluSBEat8/" + uid).accept(MediaType.APPLICATION_JSON).get(String.class));
		
		return lists;
	}
	private static void getListItems(String id, Client client) {
		java.util.List<com.sc.domain.List> lists = new ArrayList<com.sc.domain.List> ();
		WebResource service = client.resource(UriBuilder.fromPath("http://localhost:8080/scapp").build());
		System.out.println("JSON-one=" + service.path("api").path("list/items/78516001Ase0Z87nc2kxioQmyluSBEat8/" + id).accept(MediaType.APPLICATION_JSON).get(String.class));

	}
	private static Integer addList(Client client) {
		WebResource service = client.resource(getBaseURI() + "add");
		
		MultivaluedMap formData = new MultivaluedMapImpl();
		formData.add("name", "Travel List");
		formData.add("created","1");
		formData.add("api_key","78516001Ase0Z87nc2kxioQmyluSBEat8");
		ClientResponse response = service.type("application/x-www-form-urlencoded").post(ClientResponse.class,formData);
		if (response.getStatus() == 200){
			Response res = response.getEntity(Response.class);
			if (res != null)
			 return res.getRecordKey();
		}
		return null;
	}
	private static void addListItem(Integer id, String name, Client client) {
		WebResource service = client.resource(getBaseURI() + "add");
		
		MultivaluedMap formData = new MultivaluedMapImpl();
		formData.add("name", name);
		formData.add("parent", id.toString());
		formData.add("created","1");
		formData.add("api_key","78516001Ase0Z87nc2kxioQmyluSBEat8");
		ClientResponse response = service.type("application/x-www-form-urlencoded").post(ClientResponse.class,formData);
		
		
	}
	private static void markDone(Integer id, Integer taskId, Client client) {
		WebResource service = client.resource(getBaseURI() + "markDone");
		
		MultivaluedMap formData = new MultivaluedMapImpl();
		formData.add("tid", taskId.toString());
		formData.add("ref", id.toString());
		formData.add("done","y");
		formData.add("api_key","78516001Ase0Z87nc2kxioQmyluSBEat8");
		ClientResponse response = service.type("application/x-www-form-urlencoded").post(ClientResponse.class,formData);
		
		
	}
	private static Integer makeListToDo(Integer id,  Client client) {
		WebResource service = client.resource(getBaseURI() + "addTask");
		
		MultivaluedMap formData = new MultivaluedMapImpl();
		formData.add("ref", id.toString());
		formData.add("uid","1");
		formData.add("api_key","78516001Ase0Z87nc2kxioQmyluSBEat8");
		ClientResponse response = service.type("application/x-www-form-urlencoded").post(ClientResponse.class,formData);
		if (response.getStatus() == 200){
			Response res = response.getEntity(Response.class);
			if (res != null)
			 return res.getRecordKey();
		}
		return null;
	}
	private static void updateList(Integer id, Client client) {
		WebResource service = client.resource(getBaseURI() + "update");
		
		MultivaluedMap formData = new MultivaluedMapImpl();
		formData.add("name", "Grocery List - Safeway");
		formData.add("ref",id.toString());
		formData.add("api_key","78516001Ase0Z87nc2kxioQmyluSBEat8");
		ClientResponse response = service.type("application/x-www-form-urlencoded").post(ClientResponse.class,formData);
		
	}
	private static void deleteList(Integer id, Client client) {
		WebResource service = client.resource(getBaseURI() + "delete");
		
		MultivaluedMap formData = new MultivaluedMapImpl();
		formData.add("ref",id.toString());
		formData.add("api_key","78516001Ase0Z87nc2kxioQmyluSBEat8");
		ClientResponse response = service.type("application/x-www-form-urlencoded").post(ClientResponse.class,formData);
		
	}
	private static URI getBaseURI(){
		return UriBuilder.fromPath("http://localhost:8080/scapp/api/list/").build();
	}

}
