package rest.storage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.recombee.api_client.RecombeeClient;
import com.recombee.api_client.api_requests.AddBookmark;
import com.recombee.api_client.api_requests.DeleteBookmark;
import com.recombee.api_client.api_requests.ListItemBookmarks;
import com.recombee.api_client.api_requests.ListUserBookmarks;
import com.recombee.api_client.api_requests.Request;
import com.recombee.api_client.bindings.Bookmark;
import com.recombee.api_client.exceptions.ApiException;

import rest.connection.Connection;
import rest.model.LocalPreference;

public class RecombeePreference {
	//aka Preferences that we store to have initial info from users
	static RecombeeClient client = Connection.createRecombeeClient();
	public static void addPreference(String userId, String itemId) throws ApiException {
		Date timestamp = new Date();
		Request r = new AddBookmark(userId, itemId)
				  .setTimestamp(timestamp)
				  .setCascadeCreate(true);
		client.send(r);
		//LocalPreference newPref = RecombeePreference.getPreference(itemId, timestamp);
		return;
	}
	
	private static LocalPreference getPreference(String itemId, Date timestamp) throws ApiException {
		Bookmark[] result = client.send(new ListItemBookmarks(itemId));
		Bookmark pref = new Bookmark();
		for (int i =0; i<result.length;i++)
			if(result[i].getTimestamp().compareTo(timestamp)==0) {
				pref = result[i];
				break;
			}
		LocalPreference lPref = convert(pref);
		return lPref;
	}

	private static LocalPreference convert(Bookmark pref) {
		LocalPreference lPref = new LocalPreference(pref.getUserId(), pref.getItemId(), pref.getTimestamp());
		return lPref;
	}

	public static void deletePreference(String userId, String itemId, String timestamp) throws ParseException, ApiException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		Date date = formatter.parse(timestamp);
		client.send(new DeleteBookmark(userId, itemId)
				  .setTimestamp(date)
				);
	}
	
	public static LocalPreference[] listItemPreferences(String itemId) throws ApiException {
		Bookmark[] result = client.send(new ListItemBookmarks(itemId));
		LocalPreference lPref[];
		lPref = new LocalPreference[result.length];
		for (int i=0;i<result.length;i++) {
			lPref[i]=convert(result[i]);
		}
		return lPref;
	}
	
	public static LocalPreference[] listUserPreferences(String userId) throws ApiException {
		Bookmark[] result = client.send(new ListUserBookmarks(userId));
		LocalPreference lPref[];
		lPref = new LocalPreference[result.length];
		for (int i=0;i<result.length;i++) {
			lPref[i]=convert(result[i]);
		}
		return lPref;
	}

	
}
