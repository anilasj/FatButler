package com.sc.ws;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;

import com.sc.domain.Comment;
import com.sc.domain.Error;
import com.sc.domain.Response;
import com.sc.domain.Task;
import com.sc.service.EventService;
import com.sc.service.TaskService;
import com.sc.util.Formatter;

/**
 * RESTFUL Webservice that encapsulates all operations regarding processing tasks
 * All operations to this service requires an API Key.
 * @author anila
 *
 */
@Path("/taskDetail")
public class TaskDetailRes extends Base{

	@POST
	@Path("notes/add")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response add(
			@FormParam("api_key") String apiKey,
			@FormParam("uid") String uid,
			@FormParam("ref") String id,
			@FormParam("comments") String comments) throws Exception {
		
		Response response = new Response();
		List<Error> errors = new ArrayList<Error>();
		
		try {
			errors = super.authRequest(apiKey);
			if (errors != null && !errors.isEmpty()) {
				response.setErrors(errors);
				return response;
			}
			
			errors = validateNotes(uid, id);
			
			if (errors.isEmpty()){
				TaskService.getService().insertNotes(new Integer(id),  new Integer(uid), comments);
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
	@Path("notes/update")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response update(
			@FormParam("api_key") String apiKey,
			@FormParam("uid") String uid,
			@FormParam("ref") String id,
			@FormParam("comments") String comments) throws Exception {
		
		Response response = new Response();
		List<Error> errors = new ArrayList<Error>();
		
		try {
			errors = super.authRequest(apiKey);
			if (errors != null && !errors.isEmpty()) {
				response.setErrors(errors);
				return response;
			}
			
			errors = validateNotes(uid, id);
			
			if (errors.isEmpty()){
				TaskService.getService().updateNotes(new Integer(id), new Integer(uid), comments);
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
	@Path("notes/delete")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response update(
			@FormParam("api_key") String apiKey,
			@FormParam("uid") String uid,
			@FormParam("ref") String id) throws Exception {
		
		Response response = new Response();
		List<Error> errors = new ArrayList<Error>();
		
		try {
			errors = super.authRequest(apiKey);
			if (errors != null && !errors.isEmpty()) {
				response.setErrors(errors);
				return response;
			}
			
			errors = validateNotes(uid, id);
			
			if (errors.isEmpty()){
				TaskService.getService().deleteNotes(new Integer(id), new Integer(uid));
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
	@Path("notes/{apiKey}/{uid}/{ref}")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public String notes(@PathParam("apiKey") String apiKey,
			@PathParam("op") String operation,
			@PathParam("uid") String uid,
			@PathParam("ref") String id){
		
		List<Error> errors = new ArrayList<Error>();
		
		try {
			errors = super.authRequest(apiKey);
			if (errors != null && !errors.isEmpty()) {
				return null;
			}
			
			errors = validateNotes(uid, id);
			if (errors.isEmpty()){
				return TaskService.getService().getNotes(new Integer(id), new Integer(uid));
			}
			return null;

		}catch (Exception ex){
			return null;
		}
	}
	
	private List<Error> validateNotes(String uid, String taskId) throws Exception {
		List<Error> errors = new ArrayList<Error>();
		
		Integer tid = null;
		if (StringUtils.isBlank(taskId))
			errors.add(new Error(Error.MISSING_API_KEY, "Missing value for Ref"));
		else if (!StringUtils.isNumeric(taskId))
			errors.add(new Error(Error.BAD_FORMAT, "Invalid Ref Id"));
		else
			tid = new Integer(taskId);
		
		Integer uidInt = null;
		if (StringUtils.isBlank(uid))
			errors.add(new Error(Error.MISSING_API_KEY, "Missing value for User Id"));
		else if (!StringUtils.isNumeric(uid))
			errors.add(new Error(Error.BAD_FORMAT, "Invalid User Id"));
		else
			uidInt = new Integer(uid);

		TaskService service = TaskService.getService();
		Task task = service.getUserTask(tid, uidInt);
		if (task == null)
			errors.add(new Error(Error.INVALID_FIELD, "Invalid Task or Assignment"));
		return errors;
	}
	
	@POST
	@Path("comments")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response addComment(@FormParam("api_key") String apiKey,
			@FormParam("uid") String uid,
			@FormParam("ref") String id,
			@FormParam("comments") String comments){
		Response response = new Response();
		List<Error> errors = new ArrayList<Error>();
		
		try {
			errors = super.authRequest(apiKey);
			if (errors != null && !errors.isEmpty()) {
				response.setErrors(errors);
				return response;
			}
			
			Integer tid = null;
			if (StringUtils.isBlank(id))
				errors.add(new Error(Error.MISSING_API_KEY, "Missing value for Ref"));
			else if (!StringUtils.isNumeric(id))
				errors.add(new Error(Error.BAD_FORMAT, "Invalid Ref Id"));
			else
				tid = new Integer(id);
			
			Integer uidInt = null;
			if (StringUtils.isBlank(uid))
				errors.add(new Error(Error.MISSING_API_KEY, "Missing value for User Id"));
			else if (!StringUtils.isNumeric(uid))
				errors.add(new Error(Error.BAD_FORMAT, "Invalid User Id"));
			else
				uidInt = new Integer(uid);
			
			if (errors.isEmpty()){
				EventService.getService().addComment(tid, uidInt, comments);
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
	@Path("comments/{apiKey}/{ref}")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public List<Comment> getComments(@PathParam("apiKey") String apiKey,
			@PathParam("ref") String id, @DefaultValue("100") @MatrixParam("limit") String limit,
			@MatrixParam("pg") String pgCnt){
		
		List<Error> errors = new ArrayList<Error>();
		
		try {
			errors = super.authRequest(apiKey);
			if (errors != null && !errors.isEmpty()) {
				return null;
			}
			
			if (errors.isEmpty()){
				Integer pageCnt = null;
				if (pgCnt != null && StringUtils.isNumeric(pgCnt))
					pageCnt = new Integer(pgCnt);
				
				return EventService.getService().getComments(new Integer(id), new Integer(limit), pageCnt);
			}
			return null;

		}catch (Exception ex){
			return null;
		}
	}
}
