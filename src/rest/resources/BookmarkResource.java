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

import rest.storage.RecombeeBookmarks;

@Stateless
@LocalBean
@Path("/rdb/preferences")
public class BookmarkResource {
	
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	
	@POST
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public void createNewPreference(@DefaultValue("")@QueryParam("userId") String userId, @DefaultValue("")@QueryParam("itemId") String itemId) throws ApiException {
		System.out.println("--> BookmarkResource request...");
		System.out.println("--> URI = "+uriInfo);
		System.out.println("--> request = "+request);
		RecombeeBookmarks.addPreference(userId, itemId);
		return;
	}
	
	@DELETE
	public void deleteItem(@DefaultValue("")@QueryParam("itemId") String userId, @DefaultValue("")@QueryParam("itemId") String itemId, @DefaultValue("")@QueryParam("timestamp") String timestamp) throws ApiException, ParseException {
		System.out.println("--> BookmarkResource request...");
		System.out.println("--> URI = "+uriInfo);
		System.out.println("--> request = "+request);
		RecombeeBookmarks.deletePreference(userId, itemId, timestamp);
		return;
	}
	
	@GET
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Produces({MediaType.TEXT_XML, MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Bookmark[] listItemBookmarks(@DefaultValue("")@QueryParam("itemId") String itemId) throws ApiException {
		System.out.println("--> BookmarkResource request...");
		System.out.println("--> URI = "+uriInfo);
		System.out.println("--> request = "+request);
		Bookmark[] itemBookmarks = RecombeeBookmarks.listItemBookmarks(itemId);
		return itemBookmarks;
	}
	
	@GET
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Produces({MediaType.TEXT_XML, MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Bookmark[] listUserBookmarks(@DefaultValue("")@QueryParam("userId") String userId) throws ApiException {
		System.out.println("--> BookmarkResource request...");
		System.out.println("--> URI = "+uriInfo);
		System.out.println("--> request = "+request);
		Bookmark[] userBookmarks = RecombeeBookmarks.listUserBookmarks(userId);
		return userBookmarks;
	}
	
}
