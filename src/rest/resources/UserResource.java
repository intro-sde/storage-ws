package rest.resources;

import java.text.ParseException;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import com.recombee.api_client.bindings.User;
import com.recombee.api_client.exceptions.ApiException;

import rest.storage.RecombeeUsers;

@Stateless
@LocalBean
@Path("/rdb/users")
public class UserResource {
	
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	
	@POST
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public void createNewUser(@DefaultValue("")@QueryParam("firstname") String firstname, @DefaultValue("")@QueryParam("lastname") String lastname, @DefaultValue("")@QueryParam("email") String email, @DefaultValue("")@QueryParam("birthyear") String birthyear) throws ApiException {
		System.out.println("--> UserResource request...");
		System.out.println("--> URI = "+uriInfo);
		System.out.println("--> request = "+request);
		RecombeeUsers.addUser(firstname, lastname, email, birthyear);
		return;
	}
	
	@DELETE
	public void deleteUser(@DefaultValue("")@QueryParam("userId") String userId) throws ApiException, ParseException {
		System.out.println("--> UserResource request...");
		System.out.println("--> URI = "+uriInfo);
		System.out.println("--> request = "+request);
		RecombeeUsers.deleteUser(userId);
		return;
	}
	
	@GET
	@Produces({MediaType.TEXT_XML, MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public User[] listAllItems() throws ApiException {
		System.out.println("--> UserResource request...");
		System.out.println("--> URI = "+uriInfo);
		System.out.println("--> request = "+request);
		User[] users = RecombeeUsers.listAllUsers();
		return users;
	}
	
	
}
