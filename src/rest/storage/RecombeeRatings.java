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
import rest.model.LocalItem;
import rest.model.LocalRating;

public class RecombeeRatings {
	static RecombeeClient client = Connection.createRecombeeClient();
	
	public static LocalRating addRating(String userId, String itemId, double rating) throws ApiException {
		Date timestamp = new Date();
		client.send(new AddRating(userId, itemId, rating)
				  .setTimestamp(timestamp)
				  .setCascadeCreate(true)
				);
		LocalRating newRating = RecombeeRatings.getRating(itemId, timestamp);
		return newRating;
	}
	
	private static LocalRating getRating(String itemId, Date timestamp) throws ApiException {
		Rating[] result = client.send(new ListItemRatings(itemId));
		Rating rating = new Rating();
		for (int i =0; i<result.length;i++)
			if(result[i].getTimestamp()==timestamp) {
				rating = result[i];
				break;
			}
		LocalRating lRating = convert(rating);
		return lRating;
	}

	private static LocalRating convert(Rating rating) {
		LocalRating lRating = new LocalRating(rating.getUserId(), rating.getItemId(), rating.getRating(), rating.getTimestamp());
		return lRating;
	}

	public static void deleteRating(String userId, String itemId, String timestamp) throws ParseException, ApiException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		Date date = formatter.parse(timestamp);
		client.send(new DeleteRating(userId, itemId)
				  .setTimestamp(date)
				);
	}
	
	public static LocalRating[] listItemRatings(String itemId) throws ApiException {
		Rating[] result = client.send(new ListItemRatings(itemId));
		LocalRating lRating[];
		lRating = new LocalRating[result.length];
		for (int i=0;i<result.length;i++) {
			lRating[i]=convert(result[i]);
		}
		return lRating;
	}
	
	public static LocalRating[] listUserRatings(String userId) throws ApiException {
		Rating[] result = client.send(new ListUserRatings(userId));
		LocalRating lRating[];
		lRating = new LocalRating[result.length];
		for (int i=0;i<result.length;i++) {
			lRating[i]=convert(result[i]);
		}
		return lRating;
	}
}
