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

import rest.model.LocalItem;
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
	@Path("activity")
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public LocalItem createNewActivity(@DefaultValue("")@QueryParam("topic") String topic, @DefaultValue("")@QueryParam("city") String city, @DefaultValue("")@QueryParam("name") String name, @DefaultValue("")@QueryParam("from") String from, @DefaultValue("")@QueryParam("to") String to) throws ApiException {
		System.out.println("--> ItemResource request...");
		System.out.println("--> URI = "+uriInfo);
		System.out.println("--> request = "+request);
		LocalItem newItem = RecombeeItems.addActivityItem(topic,city, name, from, to);
		return newItem;
	}
	
	@POST
	@Path("restaurant")
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public LocalItem createNewRestaurant(@DefaultValue("")@QueryParam("topic") String topic, @DefaultValue("")@QueryParam("city") String city, @DefaultValue("")@QueryParam("name") String name, @DefaultValue("")@QueryParam("address") String address, @DefaultValue("")@QueryParam("rating") String rating) throws ApiException {
		System.out.println("--> ItemResource request...");
		System.out.println("--> URI = "+uriInfo);
		System.out.println("--> request = "+request);
		LocalItem newItem = RecombeeItems.addRestaurantItem(topic,city, name, address, rating);
		return newItem;
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
	public LocalItem[] searchtems(@DefaultValue("")@QueryParam("filter") String filter, @QueryParam("count") String count) throws ApiException {
		System.out.println("--> ItemResource request...");
		System.out.println("--> URI = "+uriInfo);
		System.out.println("--> request = "+request);

		LocalItem[] items;
		if (count==null) {
			items = RecombeeItems.search(filter);
		}else {
			items = RecombeeItems.searchWithCount(filter, Long.parseLong(count));
		}
		
		return items;
	}
	
	@GET
	@Produces({MediaType.TEXT_XML, MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Path("all")
	public LocalItem[] listAllItems() throws ApiException {
		System.out.println("--> ItemResource request...");
		System.out.println("--> URI = "+uriInfo);
		System.out.println("--> request = "+request);
		LocalItem[] items = RecombeeItems.listAllItems();
		return items;
	}
	
	@GET
	@Produces({MediaType.TEXT_XML, MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Path("activities")
	public LocalItem[] listActivities(@QueryParam("count") String count) throws ApiException {
		System.out.println("--> ItemResource request...");
		System.out.println("--> URI = "+uriInfo);
		System.out.println("--> request = "+request);
		LocalItem[] items;
		if (count==null) {
			items = RecombeeItems.listActivities();
		}else {
			items = RecombeeItems.listActivitiesWithCount(Long.parseLong(count));
		}
		
		return items;
	}
	
	@GET
	@Produces({MediaType.TEXT_XML, MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Path("restaurants")
	public LocalItem[] listRestaurants(@QueryParam("count") String count) throws ApiException {
		System.out.println("--> ItemResource request...");
		System.out.println("--> URI = "+uriInfo);
		System.out.println("--> request = "+request);
		LocalItem[] items;
		
		if (count==null) {
			items = RecombeeItems.listRestaurants();
		}else {
			items = RecombeeItems.listRestaurantsWithCount(Long.parseLong(count));
		}
		
		return items;
	}
	
	
}
