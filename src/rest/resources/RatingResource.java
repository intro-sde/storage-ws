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

import com.recombee.api_client.bindings.Rating;
import com.recombee.api_client.exceptions.ApiException;

import rest.model.LocalRating;
import rest.storage.RecombeeRatings;

@Stateless
@LocalBean
@Path("/rdb/ratings")
public class RatingResource {
	
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	
	@POST
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public LocalRating createNewRating(@DefaultValue("")@QueryParam("userId") String userId, @DefaultValue("")@QueryParam("itemId") String itemId, @DefaultValue("")@QueryParam("rating") String ratingString) throws ApiException {
		System.out.println("--> RatingResource request...");
		System.out.println("--> URI = "+uriInfo);
		System.out.println("--> request = "+request);
		LocalRating newRating = RecombeeRatings.addRating(userId, itemId, Double.parseDouble(ratingString));
		return newRating;
	}
	
	@DELETE
	public void deleteItem(@DefaultValue("")@QueryParam("itemId") String userId, @DefaultValue("")@QueryParam("itemId") String itemId, @DefaultValue("")@QueryParam("timestamp") String timestamp) throws ApiException, ParseException {
		System.out.println("--> RatingResource request...");
		System.out.println("--> URI = "+uriInfo);
		System.out.println("--> request = "+request);
		RecombeeRatings.deleteRating(userId, itemId, timestamp);
		return;
	}
	
	@GET
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Produces({MediaType.TEXT_XML, MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Path("by_item")
	public LocalRating[] listItemRatings(@DefaultValue("")@QueryParam("itemId") String itemId) throws ApiException {
		System.out.println("--> RatingResource request...");
		System.out.println("--> URI = "+uriInfo);
		System.out.println("--> request = "+request);
		LocalRating[] itemRatings = RecombeeRatings.listItemRatings(itemId);
		return itemRatings;
	}
	
	@GET
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Produces({MediaType.TEXT_XML, MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Path("by_user")
	public LocalRating[] listUserRatings(@DefaultValue("")@QueryParam("userId") String userId) throws ApiException {
		System.out.println("--> RatingResource request...");
		System.out.println("--> URI = "+uriInfo);
		System.out.println("--> request = "+request);
		LocalRating[] userRatings = RecombeeRatings.listUserRatings(userId);
		return userRatings;
	}
	
}
