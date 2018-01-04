package rest.storage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.recombee.api_client.RecombeeClient;
import com.recombee.api_client.api_requests.AddRating;
import com.recombee.api_client.api_requests.DeleteRating;
import com.recombee.api_client.api_requests.ListItemRatings;
import com.recombee.api_client.api_requests.ListUserRatings;
import com.recombee.api_client.bindings.Rating;
import com.recombee.api_client.exceptions.ApiException;

import rest.connection.Connection;

public class RecombeeRatings {
	static RecombeeClient client = Connection.createRecombeeClient();
	
	public static void addRating(String userId, String itemId, double rating) throws ApiException {
		client.send(new AddRating(userId, itemId, rating)
				  .setTimestamp(new Date())
				  .setCascadeCreate(true)
				);
	}
	
	public static void deleteRating(String userId, String itemId, String timestamp) throws ParseException, ApiException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		Date date = formatter.parse(timestamp);
		client.send(new DeleteRating(userId, itemId)
				  .setTimestamp(date)
				);
	}
	
	public static Rating[] listItemRatings(String itemId) throws ApiException {
		Rating[] result = client.send(new ListItemRatings(itemId));
		return result;
	}
	
	public static Rating[] listUserRatings(String userId) throws ApiException {
		Rating[] result = client.send(new ListUserRatings(userId));
		return result;
	}
}
