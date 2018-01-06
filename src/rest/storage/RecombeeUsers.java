package rest.storage;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.recombee.api_client.RecombeeClient;
import com.recombee.api_client.api_requests.AddUser;
import com.recombee.api_client.api_requests.DeleteUser;
import com.recombee.api_client.api_requests.ListUsers;
import com.recombee.api_client.api_requests.Request;
import com.recombee.api_client.api_requests.SetUserValues;
import com.recombee.api_client.bindings.User;
import com.recombee.api_client.exceptions.ApiException;

import rest.connection.Connection;

public class RecombeeUsers {
	static RecombeeClient client = Connection.createRecombeeClient();
	
	private static Map<String, Object> userValues = new HashMap<>();
	
	public static void addUser(String firstname, String lastname, String email, String birthyear) throws ApiException {
		//create new user with null attributes
		Integer lastId = getLastId();
		String userId = String.valueOf(lastId+1);
		client.send(new AddUser(userId));
		//set the property values
		userValues.put("firstname",firstname);
		userValues.put("lastname",lastname);
		userValues.put("email", email);
		userValues.put("birthyear", birthyear);
        Request r = new SetUserValues(userId, userValues).setCascadeCreate(true);
        //itemRequests.add(r);
        client.send(r);
	}
	
	public static Integer getLastId() throws ApiException {
		User[] users = RecombeeUsers.listAllUsers(); 
		List<Integer> list = new LinkedList<>();
		for (int i=0;i<users.length;i++) {
			list.add(Integer.valueOf(users[i].getUserId()));
		}
		list.sort(Comparator.naturalOrder());
		return list.get(users.length-1);
	}
	
	public static User[] listAllUsers() throws ApiException {
		User [] result = client.send(new ListUsers()
				  .setReturnProperties(true)
				);
		return result;
	}
	
	public static void deleteUser(String userId) throws ApiException {
		client.send(new DeleteUser(userId));
	}
}
