package rest.storage;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.recombee.api_client.RecombeeClient;
import com.recombee.api_client.api_requests.AddItem;
import com.recombee.api_client.api_requests.DeleteItem;
import com.recombee.api_client.api_requests.ListItems;
import com.recombee.api_client.api_requests.Request;
import com.recombee.api_client.api_requests.SetItemValues;
import com.recombee.api_client.bindings.Item;
import com.recombee.api_client.exceptions.ApiException;

import rest.connection.Connection;

public class RecombeeItems {
	//TODO: list items(this will be used in item-search-ws)
	static RecombeeClient client = Connection.createRecombeeClient();
	private static Map<String, Object> itemValues = new HashMap<>();
	
	public static void addActivityItem(String topic, String city, String name, String from, String to) throws ApiException {
		//create new item with null attributes
		String lastId = getLastId();
		String itemId = lastId+1;
		client.send(new AddItem(itemId));
		//set the property values
		itemValues.put("type","activity");
        itemValues.put("name",name);
        itemValues.put("city", city);
        itemValues.put("topic", topic);
        itemValues.put("from", from);
        itemValues.put("to", to);
        Request r = new SetItemValues(itemId, itemValues).setCascadeCreate(true);
        //itemRequests.add(r);
        client.send(r);
	}
	
	public static void addRestaurantItem(int itemId, String topic, String city, String name, String from, String to) {
		//TODO: figure  out inputs
	}
	
	public static void deleteItem(String itemId) throws ApiException {
		client.send(new DeleteItem(itemId));
	}
	
	public static Item[] listAllItems() throws ApiException {
		Item [] result = client.send(new ListItems()
				  .setReturnProperties(true)
				);
		return result;
	}
	
	public static Item[] listActivities() throws ApiException {
		Item [] result = client.send(new ListItems()
				  .setFilter(" \"activity\" in 'type' ")
				  .setReturnProperties(true)
				);
		return result;
	}
	
	public static Item[] search(String filter) throws ApiException {
		Item [] result = client.send(new ListItems()
				  .setFilter(filter)
				  .setReturnProperties(true)
				);
		return result;
	}
	
	public static String getLastId() throws ApiException {
		Item[] items = RecombeeItems.listActivities(); 
		List<Integer> list = new LinkedList<>();
		for (int i=0;i<items.length;i++) {
			list.add(Integer.valueOf(items[i].getItemId()));
		}
		list.sort(Comparator.naturalOrder());
		return String.valueOf(list.get(items.length-1));
	}
	public static Item[] listRestaurants() throws ApiException {
		Item [] result = client.send(new ListItems()
				  .setFilter(" \"restaurant\" in 'type' ")
				  .setReturnProperties(true)
				);
		return result;
	}
 }
