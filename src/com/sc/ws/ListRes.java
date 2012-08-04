package com.sc.ws;

import java.util.ArrayList;
import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;

import com.sc.domain.Error;
import com.sc.domain.List;
import com.sc.domain.Response;
import com.sc.domain.Task;
import com.sc.service.ListService;
import com.sc.service.TaskService;
import com.sc.util.Formatter;
/**
 * RESTFUL Webservice that encapsulates all operations regarding processing tasks
 * All operations to this service requires an API Key.
 * @author anila
 *
 */
@Path("/list")
public class ListRes extends Base{

	@POST
	@Path("add")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response add(
			@FormParam("api_key") String apiKey,
			@FormParam("name") String name,
			@FormParam("parent") String parentId,
			@FormParam("created") String uid) throws Exception {
		
		Response response = new Response();
		java.util.List<Error> errors = new ArrayList<Error>();
		
		try {
			errors = super.authRequest(apiKey);
			if (errors != null && !errors.isEmpty()) {
				response.setErrors(errors);
				return response;
			}
			
			if (StringUtils.isBlank(name)) 
				return response;
			
			//form list Obj
			List list = new List();
			Integer uidInt = null;
			if (StringUtils.isBlank(uid))
				errors.add(new Error(Error.MISSING_API_KEY, "Missing value for User Id"));
			else if (!StringUtils.isNumeric(uid))
				errors.add(new Error(Error.BAD_FORMAT, "Invalid User Id"));
			else
				uidInt = new Integer(uid);
			
			list.setName(name);
			list.setCreatedUid(uidInt);
			list.setStatus(List.STATUS_ACTIVE);
			
			try {
				Integer pidInt = new Integer(parentId);
				list.setParentId(pidInt);
			}catch (Exception ex){}
							
			if (errors.isEmpty()){
				ListService.getService().insertList(list);
			}
			if (errors != null && !errors.isEmpty()){
				response.setErrors(errors);
				return response;
			}
			else{
				response.setRecordKey(list.getId());
				return response;
			}
				
		}catch (Exception ex){
			errors.add(new Error(Error.ACC_ERR, "Error accessing api"));
			response.setErrors(errors);
			return response;
		}
	}
	
	@POST
	@Path("update")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response update(
			@FormParam("api_key") String apiKey,
			@FormParam("name") String name,
			@FormParam("ref") String id) throws Exception {
		
		Response response = new Response();
		java.util.List<Error> errors = new ArrayList<Error>();
		
		try {
			errors = super.authRequest(apiKey);
			if (errors != null && !errors.isEmpty()) {
				response.setErrors(errors);
				return response;
			}
			
			if (StringUtils.isBlank(name)) 
				return response;
			
			//form list Obj
			List list = new List();
			Integer listId = null;
			if (StringUtils.isBlank(id))
				errors.add(new Error(Error.MISSING_API_KEY, "Missing value for Ref"));
			else if (!StringUtils.isNumeric(id))
				errors.add(new Error(Error.BAD_FORMAT, "Invalid Ref Id"));
			else
				listId = new Integer(id);
			
			list.setId(listId);
			list.setName(name);
			list.setStatus(List.STATUS_ACTIVE);
						
			if (errors.isEmpty()){
				ListService.getService().updateList(list);
			}
			if (errors != null && !errors.isEmpty()){
				response.setErrors(errors);
				return response;
			}
			else{
				return response;
			}
				
		}catch (Exception ex){
			errors.add(new Error(Error.ACC_ERR, "Error accessing api"));
			response.setErrors(errors);
			return response;
		}
	}
	
	@POST
	@Path("delete")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response delete(
			@FormParam("api_key") String apiKey,
			@FormParam("ref") String id) throws Exception {
		
		Response response = new Response();
		java.util.List<Error> errors = new ArrayList<Error>();
		
		try {
			errors = super.authRequest(apiKey);
			if (errors != null && !errors.isEmpty()) {
				response.setErrors(errors);
				return response;
			}
			

			Integer listId = null;
			if (StringUtils.isBlank(id))
				errors.add(new Error(Error.MISSING_API_KEY, "Missing value for Ref"));
			else if (!StringUtils.isNumeric(id))
				errors.add(new Error(Error.BAD_FORMAT, "Invalid Ref Id"));
			else
				listId = new Integer(id);

						
			if (errors.isEmpty()){
				ListService.getService().deleteList(listId);
			}
			if (errors != null && !errors.isEmpty()){
				response.setErrors(errors);
				return response;
			}
			else{
				return response;
			}
				
		}catch (Exception ex){
			errors.add(new Error(Error.ACC_ERR, "Error accessing api"));
			response.setErrors(errors);
			return response;
		}
	}
	
	@POST
	@Path("addTask")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response makeListToDo(
			@FormParam("api_key") String apiKey,
			@FormParam("uid") String uid,
			@FormParam("ref") String id) throws Exception {
		
		Response response = new Response();
		java.util.List<Error> errors = new ArrayList<Error>();
		
		try {
			errors = super.authRequest(apiKey);
			if (errors != null && !errors.isEmpty()) {
				response.setErrors(errors);
				return response;
			}
			

			Integer listId = null;
			if (StringUtils.isBlank(id))
				errors.add(new Error(Error.MISSING_API_KEY, "Missing value for Ref"));
			else if (!StringUtils.isNumeric(id))
				errors.add(new Error(Error.BAD_FORMAT, "Invalid Ref Id"));
			else
				listId = new Integer(id);

			Integer uidInt = null;
			if (StringUtils.isBlank(uid))
				errors.add(new Error(Error.MISSING_API_KEY, "Missing value for User Id"));
			else if (!StringUtils.isNumeric(uid))
				errors.add(new Error(Error.BAD_FORMAT, "Invalid User Id"));
			else
				uidInt = new Integer(uid);
						
			if (errors.isEmpty()){
				Integer taskId = ListService.getService().makeListToDo(listId, uidInt);
				response.setRecordKey(taskId);
				return response;
			}else{
				response.setErrors(errors);
				return response;
			}
			
				
		}catch (Exception ex){
			errors.add(new Error(Error.ACC_ERR, "Error accessing api"));
			response.setErrors(errors);
			return response;
		}
	}
	
	@POST
	@Path("markDone")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response markListDone(
			@FormParam("api_key") String apiKey,
			@FormParam("tid") String tid,
			@FormParam("ref") String id,
			@FormParam("done") String done) throws Exception {
		
		Response response = new Response();
		java.util.List<Error> errors = new ArrayList<Error>();
		
		try {
			errors = super.authRequest(apiKey);
			if (errors != null && !errors.isEmpty()) {
				response.setErrors(errors);
				return response;
			}
			

			Integer listId = null;
			if (StringUtils.isBlank(id))
				errors.add(new Error(Error.MISSING_API_KEY, "Missing value for Ref"));
			else if (!StringUtils.isNumeric(id))
				errors.add(new Error(Error.BAD_FORMAT, "Invalid Ref Id"));
			else
				listId = new Integer(id);

			Integer taskId = null;
			if (StringUtils.isBlank(tid))
				errors.add(new Error(Error.MISSING_API_KEY, "Missing value for Task Id"));
			else if (!StringUtils.isNumeric(tid))
				errors.add(new Error(Error.BAD_FORMAT, "Invalid Task Id"));
			else
				taskId = new Integer(tid);
			
			if (!StringUtils.isBlank(done)){
				if (!done.equalsIgnoreCase("y") && !done.equalsIgnoreCase("n"))
					errors.add(new Error(Error.BAD_FORMAT, "Invalid Done value"));
			}else
				errors.add(new Error(Error.MISSING_FIELD, "Missing value for Done"));
				
			if (errors.isEmpty()){
				ListService.getService().markTaskListDone(taskId, listId, done.equalsIgnoreCase("y"));
			}
			if (errors != null && !errors.isEmpty()){
				response.setErrors(errors);
				return response;
			}
			else{
				return response;
			}
				
		}catch (Exception ex){
			errors.add(new Error(Error.ACC_ERR, "Error accessing api"));
			response.setErrors(errors);
			return response;
		}
	}
	
	@GET
	@Path ("/{apiKey}/{uid}")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public java.util.List<List> getLists(@PathParam("apiKey") String apiKey,
			@PathParam("uid") String uid, @MatrixParam("lname") String name){
		
		java.util.List<Error> errors = new ArrayList<Error>();
		try {
			errors = super.authRequest(apiKey);
			if (errors != null && !errors.isEmpty()) {
				return null;
			}
			
			if (StringUtils.isBlank(uid) || !StringUtils.isNumeric(uid))
				return null;
			
			return ListService.getService().getLists(new Integer(uid), name);
		}catch (Exception ex){
			return null;
		}
	}
	
	@GET
	@Path("items/{apiKey}/{id}")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public List getItems(@PathParam("apiKey") String apiKey,
			@PathParam("id") String id){
		
		java.util.List<Error> errors = new ArrayList<Error>();
		try {
			errors = super.authRequest(apiKey);
			if (errors != null && !errors.isEmpty()) {
				getErrorsAsString(errors);
			}
			
			if (StringUtils.isBlank(id) || !StringUtils.isNumeric(id))
				return null;
			
			ListService service = ListService.getService();
			List list = service.getList(new Integer(id));
			if (list != null){
				java.util.List<List> items =  service.getListItems(new Integer(id));
				if (items != null)
					list.setItems(items);
			}
			return list;
		}catch (Exception ex){
			return null;
		}
	}
}
