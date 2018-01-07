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

import com.recombee.api_client.bindings.Bookmark;
import com.recombee.api_client.exceptions.ApiException;

import rest.model.LocalPreference;
import rest.storage.RecombeePreference;

@Stateless
@LocalBean
@Path("/rdb/preferences")
public class PreferenceResource {
	
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	
	@POST
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public LocalPreference createNewPreference(@DefaultValue("")@QueryParam("userId") String userId, @DefaultValue("")@QueryParam("itemId") String itemId) throws ApiException {
		System.out.println("--> BookmarkResource request...");
		System.out.println("--> URI = "+uriInfo);
		System.out.println("--> request = "+request);
		LocalPreference newPref = RecombeePreference.addPreference(userId, itemId);
		return newPref;
	}
	
	@DELETE
	public void deleteItem(@DefaultValue("")@QueryParam("itemId") String userId, @DefaultValue("")@QueryParam("itemId") String itemId, @DefaultValue("")@QueryParam("timestamp") String timestamp) throws ApiException, ParseException {
		System.out.println("--> BookmarkResource request...");
		System.out.println("--> URI = "+uriInfo);
		System.out.println("--> request = "+request);
		RecombeePreference.deletePreference(userId, itemId, timestamp);
		return;
	}
	
	
	@GET
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Produces({MediaType.TEXT_XML, MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Path("by_item")
	public LocalPreference[] listItemPreferences(@DefaultValue("")@QueryParam("itemId") String itemId) throws ApiException {
		System.out.println("--> BookmarkResource request...");
		System.out.println("--> URI = "+uriInfo);
		System.out.println("--> request = "+request);
		LocalPreference[] itemPref = RecombeePreference.listItemPreferences(itemId);
		return itemPref;
	}
	
	@GET
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Produces({MediaType.TEXT_XML, MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Path("by_user")
	public LocalPreference[] listUserPreferences(@DefaultValue("")@QueryParam("userId") String userId) throws ApiException {
		System.out.println("--> BookmarkResource request...");
		System.out.println("--> URI = "+uriInfo);
		System.out.println("--> request = "+request);
		LocalPreference[] userPref= RecombeePreference.listUserPreferences(userId);
		return userPref;
	}
	
}
