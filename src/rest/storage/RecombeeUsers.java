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
import rest.model.LocalUser;

public class RecombeeUsers {
	static RecombeeClient client = Connection.createRecombeeClient();
	
	private static Map<String, Object> userValues = new HashMap<>();
	
	public static LocalUser addUser(String firstname, String lastname, String email, String birthyear) throws ApiException {
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
        client.send(r);
        LocalUser newUser = RecombeeUsers.getUser(userId);
        return newUser;
	}
	
	private static LocalUser getUser(String userId) throws ApiException {
		User [] result = client.send(new ListUsers()
				  .setReturnProperties(true)
				  .setFilter(userId+" in 'userId' ")
				);
		LocalUser newUser = convert(result[0]);
	    return newUser;
	}
	
	public static LocalUser convert(User user) {
		LocalUser lUser = new LocalUser(user.getUserId(), String.valueOf(user.getValues().get("firstname")),String.valueOf(user.getValues().get("lastname")), String.valueOf(user.getValues().get("email")), String.valueOf(user.getValues().get("birthyear")));
		return lUser;
	}

	public static Integer getLastId() throws ApiException {
		LocalUser[] users = RecombeeUsers.listAllUsers(); 
		List<Integer> list = new LinkedList<>();
		for (int i=0;i<users.length;i++) {
			list.add(Integer.valueOf(users[i].getUserId()));
		}
		list.sort(Comparator.naturalOrder());
		return list.get(users.length-1);
	}
	
	public static LocalUser[] listAllUsers() throws ApiException {
		User [] result = client.send(new ListUsers()
				  .setReturnProperties(true)
				);
		LocalUser lUsers[];
		lUsers = new LocalUser[result.length];
		for (int i=0;i<result.length;i++) {
			lUsers[i]=convert(result[i]);
		}
		return lUsers;
	}
	
	public static void deleteUser(String userId) throws ApiException {
		client.send(new DeleteUser(userId));
	}
	
	public static String getUserId(String firstname,String lastname, String email, String birthyear) throws ApiException {
		
		User [] result = client.send(new ListUsers()
				  .setReturnProperties(true)
				  .setFilter(firstname+" in 'firstname' and "+lastname+" in 'lastname' and "+email+" in 'email' and "+birthyear+" in 'birthyear' ")
				);
		LocalUser newUser = convert(result[0]);
		String userId = newUser.getUserId();
	    return userId;
	}
}
