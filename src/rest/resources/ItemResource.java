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

import com.recombee.api_client.bindings.Item;
import com.recombee.api_client.exceptions.ApiException;

import rest.storage.RecombeeItems;

@Stateless
@LocalBean
@Path("/rdb/items")
public class ItemResource {
	//TODO:addrestaurant item
	
	
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	
	@POST
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public void createNewItem(@DefaultValue("")@QueryParam("topic") String topic, @DefaultValue("")@QueryParam("city") String city, @DefaultValue("")@QueryParam("name") String name, @DefaultValue("")@QueryParam("from") String from, @DefaultValue("")@QueryParam("to") String to) throws ApiException {
		System.out.println("--> ItemResource request...");
		System.out.println("--> URI = "+uriInfo);
		System.out.println("--> request = "+request);
		RecombeeItems.addActivityItem(topic,city, name, from, to);
		return;
	}
	
	@DELETE
	public void deleteItem(@DefaultValue("")@QueryParam("itemId") String itemId) throws ApiException, ParseException {
		System.out.println("--> ItemResource request...");
		System.out.println("--> URI = "+uriInfo);
		System.out.println("--> request = "+request);
		RecombeeItems.deleteItem(itemId);
		return;
	}
	
	@GET
	@Produces({MediaType.TEXT_XML, MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Item[] listAllItems() throws ApiException {
		System.out.println("--> ItemResource request...");
		System.out.println("--> URI = "+uriInfo);
		System.out.println("--> request = "+request);
		Item[] items = RecombeeItems.listAllItems();
		return items;
	}
	
	@GET
	@Produces({MediaType.TEXT_XML, MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Item[] listActivities() throws ApiException {
		System.out.println("--> ItemResource request...");
		System.out.println("--> URI = "+uriInfo);
		System.out.println("--> request = "+request);
		Item[] items = RecombeeItems.listActivities();
		return items;
	}
	
}