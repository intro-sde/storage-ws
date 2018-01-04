package rest.storage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.recombee.api_client.RecombeeClient;
import com.recombee.api_client.api_requests.AddBookmark;
import com.recombee.api_client.api_requests.DeleteBookmark;
import com.recombee.api_client.api_requests.ListItemBookmarks;
import com.recombee.api_client.api_requests.ListUserBookmarks;
import com.recombee.api_client.bindings.Bookmark;
import com.recombee.api_client.exceptions.ApiException;

import rest.connection.Connection;

public class RecombeeBookmarks {
	//aka Preferences that we store to have initial info from users
	static RecombeeClient client = Connection.createRecombeeClient();
	public static void addPreference(String userId, String itemId) throws ApiException {
		client.send(new AddBookmark(userId, itemId)
				  .setTimestamp(new Date())
				  .setCascadeCreate(true)
				);
	}
	
	public static void deletePreference(String userId, String itemId, String timestamp) throws ParseException, ApiException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		Date date = formatter.parse(timestamp);
		client.send(new DeleteBookmark(userId, itemId)
				  .setTimestamp(date)
				);
	}
	
	public static Bookmark[] listItemBookmarks(String itemId) throws ApiException {
		Bookmark[] result = client.send(new ListItemBookmarks(itemId));
		return result;
	}
	
	public static Bookmark[] listUserBookmarks(String userId) throws ApiException {
		Bookmark[] result = client.send(new ListUserBookmarks(userId));
		return result;
	}
	
}
