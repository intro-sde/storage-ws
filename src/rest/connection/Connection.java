package rest.connection;

import com.recombee.api_client.RecombeeClient;

public class Connection {
	
	public static RecombeeClient createRecombeeClient() {
		RecombeeClient client = new RecombeeClient(RecombeeToken.DB_ID, RecombeeToken.SECRET_TOKEN);
		return client;
	}
	
}

