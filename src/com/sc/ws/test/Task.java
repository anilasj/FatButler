package com.sc.ws.test;

import java.net.URI;

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

public class Task {

	public static void main(String[] args){
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		try{
			//add simple task
	
			String uid = "2";
		//	Integer taskId = addTask(client);
			Integer taskId = new Integer(10);
			
			//setup reminder task with due date
		//	setupReminder(taskId.toString(), client);
			
			//setupSnooze(taskId, client);
			//addComment(taskId.toString(), uid, "Testing comments again and again", client);
			//getComments(taskId.toString(), client);
			//addNotes("10", "1", "Testin notes 1", client);
			//updateNotes("5", "1", "Testin notes 1", client);
			//deleteNotes("5", "1", "Testin notes 1", client);
			//getNotes("4","1", client);
			
			//assign(taskId, uid, client);
			//confirmAssignment(client);
			//markDone(taskId, client);
			//deleteTask(client);
			
			getTasks("1", client);
		}catch (Exception ex){
			ex.printStackTrace();
		}
		
	}
	
	private static Integer addTask(Client client){
		WebResource service = client.resource(getTaskBaseURI() + "add");
		
		MultivaluedMap formData = new MultivaluedMapImpl();
		formData.add("name", "Go grocery shopping");
		formData.add("created","1");
		formData.add("due", "05/28/2012 10:10:10");
		formData.add("api_key","78516001Ase0Z87nc2kxioQmyluSBEat8");
		ClientResponse response = service.type("application/x-www-form-urlencoded").post(ClientResponse.class,formData);
		if (response.getStatus() == 200){
			Response res = response.getEntity(Response.class);
			if (res != null)
			 return res.getRecordKey();
		}
		return null;
	}
	
	private static String setupReminder(String taskId, Client client) {
		WebResource service = client.resource(getTaskBaseURI() + "reminder");
		
		MultivaluedMap formData = new MultivaluedMapImpl();
		formData.add("ref", taskId);
		formData.add("due","05/29/2012 10:10:10");
		formData.add("snooze","20");
		formData.add("reminder","OFF");
		formData.add("type", "YLY");
		formData.add("api_key","78516001Ase0Z87nc2kxioQmyluSBEat8");
		ClientResponse response = service.type("application/x-www-form-urlencoded").post(ClientResponse.class,formData);
		return null;
	}
	
	private static String setupSnooze(String taskId, Client client) {
		WebResource service = client.resource(getTaskBaseURI() + "reminder");
		
		MultivaluedMap formData = new MultivaluedMapImpl();
		formData.add("ref", taskId);
		formData.add("due","05/25/2012 10:10:10");
		formData.add("snooze","15");
		formData.add("reminder", "ON");
		formData.add("type","ONE");
		formData.add("api_key","78516001Ase0Z87nc2kxioQmyluSBEat8");
		ClientResponse response = service.type("application/x-www-form-urlencoded").post(ClientResponse.class,formData);
		return null;
	}
	
	private static String addComment(String taskId, String uId, String comment, Client client) {
		WebResource service = client.resource(getTaskDetailsBaseURI() + "comments");
		
		MultivaluedMap formData = new MultivaluedMapImpl();
		formData.add("ref", taskId);
		formData.add("uid",uId);
		formData.add("comments", comment);
		formData.add("api_key","78516001Ase0Z87nc2kxioQmyluSBEat8");
		ClientResponse response = service.type("application/x-www-form-urlencoded").post(ClientResponse.class,formData);
		return null;
	}
	
	private static String addNotes(String taskId, String uId, String comment, Client client) {
		WebResource service = client.resource(getTaskDetailsBaseURI() + "notes/add");
		
		MultivaluedMap formData = new MultivaluedMapImpl();
		formData.add("ref", taskId);
		formData.add("uid",uId);
		formData.add("comments", comment);
		formData.add("api_key","78516001Ase0Z87nc2kxioQmyluSBEat8");
		ClientResponse response = service.type("application/x-www-form-urlencoded").post(ClientResponse.class,formData);
		return null;
	}
	
	private static String updateNotes(String taskId, String uId, String comment, Client client) {
		WebResource service = client.resource(getTaskDetailsBaseURI() + "notes/update");
		
		MultivaluedMap formData = new MultivaluedMapImpl();
		formData.add("ref", taskId);
		formData.add("uid",uId);
		formData.add("comments", comment);
		formData.add("api_key","78516001Ase0Z87nc2kxioQmyluSBEat8");
		ClientResponse response = service.type("application/x-www-form-urlencoded").post(ClientResponse.class,formData);
		return null;
	}
	
	private static String deleteNotes(String taskId, String uId, String comment, Client client) {
		WebResource service = client.resource(getTaskDetailsBaseURI() + "notes/delete");
		
		MultivaluedMap formData = new MultivaluedMapImpl();
		formData.add("ref", taskId);
		formData.add("uid",uId);
		formData.add("api_key","78516001Ase0Z87nc2kxioQmyluSBEat8");
		ClientResponse response = service.type("application/x-www-form-urlencoded").post(ClientResponse.class,formData);
		return null;
	}
	
	private static String confirmAssignment(Client client) {
		WebResource service = client.resource(getTaskBaseURI() + "assignConfirm");
		
		MultivaluedMap formData = new MultivaluedMapImpl();
		formData.add("ref", "3");
		formData.add("uid","2");
		formData.add("api_key","78516001Ase0Z87nc2kxioQmyluSBEat8");
		ClientResponse response = service.type("application/x-www-form-urlencoded").post(ClientResponse.class,formData);
		return null;
	}
	
	private static String markDone(Integer taskId, Client client) {
		WebResource service = client.resource(getTaskBaseURI() + "markDone");
		
		MultivaluedMap formData = new MultivaluedMapImpl();
		formData.add("ref", taskId.toString());
		formData.add("uid","1");
		formData.add("api_key","78516001Ase0Z87nc2kxioQmyluSBEat8");
		ClientResponse response = service.type("application/x-www-form-urlencoded").post(ClientResponse.class,formData);
		return null;
	}
	
	private static String deleteTask(Client client) {
		WebResource service = client.resource(getTaskBaseURI() + "delete");
		
		MultivaluedMap formData = new MultivaluedMapImpl();
		formData.add("ref", "3");
		formData.add("uid","2");
		formData.add("api_key","78516001Ase0Z87nc2kxioQmyluSBEat8");
		ClientResponse response = service.type("application/x-www-form-urlencoded").post(ClientResponse.class,formData);
		return null;
	}
	private static String assign(String taskId, String uId, Client client) {
		WebResource service = client.resource(getTaskBaseURI() + "assign");
	
		MultivaluedMap formData = new MultivaluedMapImpl();
		formData.add("ref", taskId);
		//formData.add("name","Anila");
		formData.add("fromId","1");
		//formData.add("toId", "2");
		formData.add("email", "anila.arthanari@gmail.com");
		formData.add("api_key","78516001Ase0Z87nc2kxioQmyluSBEat8");
		ClientResponse response = service.type("application/x-www-form-urlencoded").post(ClientResponse.class,formData);
		return null;
	}
	
	private static String getNotes(String taskId, String uId, Client client) {
		WebResource service = client.resource(UriBuilder.fromPath("http://localhost:8080/scapp").build());
		System.out.println("JSON-one=" + service.path("api").path("taskDetail/notes/78516001Ase0Z87nc2kxioQmyluSBEat8/" + uId + "/"+ taskId).accept(MediaType.APPLICATION_JSON).get(String.class));
		return null;
	}
	
	private static String getTasks(String uId, Client client) {
		WebResource service = client.resource(UriBuilder.fromPath("http://localhost:8080/scapp").build());
		System.out.println("JSON-one=" + service.path("api").path("task/mine/78516001Ase0Z87nc2kxioQmyluSBEat8/" + uId + ";done=N;startDt=2012-05-29;dtLimit=3;path=f;inclStart=n").accept(MediaType.APPLICATION_JSON).get(String.class));
		return null;
	}
	
	private static String getComments(String taskId, Client client) {
		WebResource service = client.resource(UriBuilder.fromPath("http://localhost:8080/scapp").build());
		System.out.println("JSON-one=" + service.path("api").path("taskDetail/comments/78516001Ase0Z87nc2kxioQmyluSBEat8/" + taskId + "").accept(MediaType.APPLICATION_JSON).get(String.class));
		return null;
	}
	
	private static URI getTaskBaseURI(){
		return UriBuilder.fromPath("http://localhost:8080/scapp/api/task/").build();
	}
	private static URI getTaskDetailsBaseURI(){
		return UriBuilder.fromPath("http://localhost:8080/scapp/api/taskDetail/").build();
	}
}
