package com.sc.ws;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.Encoded;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;

import com.sc.domain.Response;
import com.sc.domain.Task;
import com.sc.domain.User;
import com.sc.domain.Error;
import com.sc.service.EventService;
import com.sc.service.ListService;
import com.sc.service.TaskService;
import com.sc.service.UserService;
import com.sc.util.Formatter;

/**
 * RESTFUL Webservice that encapsulates all operations regarding processing tasks
 * All operations to this service requires an API Key.
 * @author anila
 *
 */
@Path("/task")
public class TaskRes extends Base{

	@POST
	@Path("add")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response add(
			@FormParam("api_key") String apiKey,
			@FormParam("name") String name,
			@FormParam("due") String due,
			@FormParam("created") String uid,
			@FormParam("priority") String priority,
			@FormParam("share") String share,
			@FormParam("snooze") String snooze,
			@FormParam("action") String action) throws Exception {
		
		Response response = new Response();
		List<Error> errors = new ArrayList<Error>();
		
		try {
			errors = super.authRequest(apiKey);
			if (errors != null && !errors.isEmpty()) {
				response.setErrors(errors);
				return response;
			}
			
			//form task Obj
			Task task = new Task(name);
			if (!StringUtils.isBlank(due)){
				try {
					Date dueDt = Formatter.parseDate(due);
					if (dueDt != null)
						task.setDueDate(dueDt);
				}catch (Exception ex){}
			}
			Integer uidInt = null;
			if (StringUtils.isBlank(uid))
				errors.add(new Error(Error.MISSING_API_KEY, "Missing value for User Id"));
			else if (!StringUtils.isNumeric(uid))
				errors.add(new Error(Error.BAD_FORMAT, "Invalid User Id"));
			else
				uidInt = new Integer(uid);
			
			task.setCreatedUid(uidInt);
			task.setShareType(share == null?Task.SHARE_CUSTOM:share);
			task.setPriority(priority == null?Task.PRIORITY_NORMAL:priority);
			task.setAction(action);
			task.setStatus(Task.STATUS_ACTIVE);

				
			try {
				Integer snInt = new Integer(snooze);
				task.setSnoozeMins(snInt);
			}catch (Exception ex){}
							
			if (errors.isEmpty()){
				TaskService.getService().insertTask(task);
				response.setRecordKey(task.getId());
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
	@Path("reminder")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response setReminder(
			@FormParam("api_key") String apiKey,
			@FormParam("ref") String id,
			@FormParam("due") String dueDate,
			@FormParam("snooze") String snoozeMins,
			@FormParam("snooze_time") String snoozeDate,
			@FormParam("type") String type,
			@FormParam("reminder") String reminder) throws Exception {
		
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
			
			//form task Obj
			Task task = new Task();
			if (dueDate == null)
				errors.add(new Error(Error.MISSING_FIELD,"Missing due date"));
			else{
				Date dueDt = Formatter.parseDate(dueDate);
				if (dueDt != null)
					task.setDueDate(dueDt);
				else
					errors.add(new Error(Error.INVALID_FIELD,"Invalid due date"));
			}
			
			task.setReminder(reminder);
			task.setRecurrence(type);
			if (type != null && !task.isValidRecurrence())
				errors.add(new Error(Error.INVALID_FIELD,"Invalid reminder"));
			
			try {
				Integer snInt = new Integer(snoozeMins);
				task.setSnoozeMins(snInt);
				if (snoozeDate != null){
					Date snoozeDt = Formatter.parseDate(snoozeDate);
					if (snoozeDt != null)
						task.setSnoozeDate(snoozeDt);
				}
					
			}catch (Exception ex){}
			
			if (errors.isEmpty()){
				TaskService.getService().setReminder(tid, task);
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
	@Path("update")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})	
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response update(
			@FormParam("api_key") String apiKey,
			@FormParam("ref") String id,
			@FormParam("name") String name,
			@FormParam("priority") String priority,
			@FormParam("action") String action,
			@FormParam("shareType") String shareType,
			@FormParam("done") String doneFlg,
			@FormParam("doneDate") String doneDate) throws Exception {
		
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
			
			Task task = new Task();
			task.setId(tid);
			if (!StringUtils.isBlank(name)) task.setName(name);
			if (!StringUtils.isBlank(priority)) task.setPriority(priority);
			if (!StringUtils.isBlank(action)) task.setAction(action);
			if (!StringUtils.isBlank(shareType)) task.setShareType(shareType);
			if (!StringUtils.isBlank(doneFlg)) task.setDone(doneFlg.equalsIgnoreCase("true")?true:false);
			if (Formatter.parseDate(doneDate) != null) task.setDoneDate(Formatter.parseDate(doneDate));
			
			if (errors.isEmpty()){
				TaskService.getService().updateTask(task);
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
	@Path("assign")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response assign(
			@FormParam("api_key") String apiKey,
			@FormParam("ref") String id,
			@FormParam("name") String name,
			@FormParam("fromId") String sourceId,
			@FormParam("toId") String userId,
			@FormParam("email") String email) throws Exception {
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
			
			if (StringUtils.isBlank(sourceId) || !StringUtils.isNumeric(sourceId))
				errors.add(new Error(Error.INVALID_MISSING_FIELD, "Invalid or Missing UserId"));
			
			if (StringUtils.isBlank(name) && StringUtils.isBlank(userId) && StringUtils.isBlank(email))
				errors.add(new Error(Error.MISSING_FIELD, "Assigned To (Name, Email or Id) missing!"));
			
			if (!StringUtils.isBlank(userId) && !StringUtils.isNumeric(userId))
				errors.add(new Error(Error.BAD_FORMAT, "Invalid UserId"));
			
			if (!StringUtils.isBlank(name) && !StringUtils.isAlphaSpace(name))
				errors.add(new Error(Error.BAD_FORMAT, "Invalid name"));
			
			if (!StringUtils.isBlank(email)){
				try {
					new javax.mail.internet.InternetAddress(email, true);
				} catch (javax.mail.internet.AddressException e) {
					errors.add(new Error(Error.BAD_FORMAT, "Invalid email"));
				}
			}
			
			if (StringUtils.isBlank(userId) && StringUtils.isBlank(email) && !StringUtils.isBlank(name)){
				User user = UserService.getService().getUser(name);
				if (user == null)
					errors.add(new Error(Error.INVALID_FIELD, "Invalid name, user not found"));
			}
			if (errors.isEmpty()){
				TaskService.getService().assignTask(tid, name, email, userId, sourceId);
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
	@Path("assignConfirm")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response confirmAssign(
			@FormParam("api_key") String apiKey,
			@FormParam("ref") String id,
			@FormParam("uid") String uid) throws Exception {
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
				TaskService.getService().confirmAssignTask(tid, uidInt);
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
	@Path("markDone")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response markDone(
			@FormParam("api_key") String apiKey,
			@FormParam("ref") String id,
			@FormParam("uid") String uid) throws Exception {
			
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
			
			TaskService service = TaskService.getService();
			Task task = service.getUserTask(tid, uidInt);
			if (task == null)
				errors.add(new Error(Error.INVALID_FIELD, "Invalid Task or Assignment"));
			
			if (errors.isEmpty()){
				service.markDone(task, uidInt);
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
			@FormParam("ref") String id,
			@FormParam("uid") String uid) throws Exception {
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
			
			TaskService service = TaskService.getService();
			Task task = service.getUserTask(tid, uidInt);
			if (task == null)
				errors.add(new Error(Error.INVALID_FIELD, "Invalid Task or Assignment"));
			
			if (errors.isEmpty()){
				service.deleteTask(task);
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
	@Path ("mine/{apiKey}/{uid}")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public java.util.List<Task> getMyTasks(@PathParam("apiKey") String apiKey,
			@PathParam("uid") String uid, @MatrixParam("lname") String name,
			@Encoded @MatrixParam("dueDts") List<String> dueDts, @MatrixParam("done") String done,
			@MatrixParam("startDt") String startDt,
			@MatrixParam("inclStart") String include,
			@MatrixParam("dtLimit") String dtLimit,
			@MatrixParam("path") String path,
			@MatrixParam("overDue") String overDue,
			@MatrixParam("order") List<String> orders){
		
		java.util.List<Error> errors = new ArrayList<Error>();
		try {
			errors = super.authRequest(apiKey);
			if (errors != null && !errors.isEmpty()) {
				return null;
			}
			
			if (StringUtils.isBlank(uid) || !StringUtils.isNumeric(uid))
				return null;
			
			//verify order by
			List<String> orderBy = new ArrayList();
			if (orderBy != null && !orders.isEmpty()){
				for (String order: orders){
					if (order.equalsIgnoreCase("name") || order.equalsIgnoreCase("name desc") || order.equalsIgnoreCase("name asc"))
						orderBy.add(order);
					if (order.equalsIgnoreCase("priority") || order.equalsIgnoreCase("priority desc") || order.equalsIgnoreCase("priority asc")){
						if (order.toLowerCase().endsWith("desc"))
							orderBy.add("priority_flg desc");
						else
							orderBy.add("priority_flg");
					}
					if (order.equalsIgnoreCase("duedt") || order.equalsIgnoreCase("duedt desc") || order.equalsIgnoreCase("duedt asc")){
						if (order.toLowerCase().endsWith("desc"))
							orderBy.add("due_dtm desc");
						else
							orderBy.add("due_dtm");
					}
				}
			}
			
			int dtLimitInt =2;
			if (dtLimit != null){
				try {
					dtLimitInt = new Integer(dtLimit).intValue(); 
				}catch (Exception ex){}
			}
			if (StringUtils.isBlank(include))
				include = "y";
			
			if (StringUtils.isBlank(path) || (!path.equalsIgnoreCase("f") && !path.equalsIgnoreCase("b")))
				path = "f";
				
			return TaskService.getService().getTasks(new Integer(uid), name, startDt, include, dtLimitInt, path, dueDts, done, overDue, orderBy);
		}catch (Exception ex){
			return null;
		}
	}
	
}
