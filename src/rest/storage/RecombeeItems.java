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
import com.recombee.api_client.api_requests.ListUsers;
import com.recombee.api_client.api_requests.Request;
import com.recombee.api_client.api_requests.SetItemValues;
import com.recombee.api_client.bindings.Item;
import com.recombee.api_client.bindings.User;
import com.recombee.api_client.exceptions.ApiException;

import rest.connection.Connection;
import rest.model.LocalItem;
import rest.model.LocalUser;

public class RecombeeItems {
	//TODO: list items(this will be used in item-search-ws)
	static RecombeeClient client = Connection.createRecombeeClient();
	private static Map<String, Object> itemValues = new HashMap<>();
	
	public static LocalItem addActivityItem(String topic, String city, String name, String from, String to) throws ApiException {
		//create new item with null attributes
		Integer lastId = getLastId();
		String itemId = String.valueOf(lastId+1);
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
        LocalItem newActivity = RecombeeItems.getItem(itemId);
        return newActivity;
	}
	
	public static LocalItem addRestaurantItem(String topic, String city, String name, String address, String rating) throws ApiException {
		//create new item with null attributes
		Integer lastId = getLastId();
		String itemId = String.valueOf(lastId+1);
		client.send(new AddItem(itemId));
		//set the property values
		itemValues.put("type","restaurant");
		itemValues.put("name",name);
		itemValues.put("city", city);
		itemValues.put("topic", topic);
		itemValues.put("address", address);
		itemValues.put("rating", rating);
		Request r = new SetItemValues(itemId, itemValues).setCascadeCreate(true);
		//itemRequests.add(r);
		client.send(r);
		LocalItem newRestaurant = RecombeeItems.getItem(itemId);
		return newRestaurant;
	}
	
	private static LocalItem getItem(String itemId) throws ApiException {
		itemId = "\""+itemId+"\"";
		Item [] result = client.send(new ListItems()
				  .setReturnProperties(true)
				  .setFilter(itemId+" in 'itemId' ")
				);
		LocalItem newItem = convert(result[0]);
	    return newItem;
	}

	public static LocalItem convert(Item item) {
		LocalItem lItem = new LocalItem(item.getItemId(), String.valueOf(item.getValues().get("type")),String.valueOf(item.getValues().get("name")), String.valueOf(item.getValues().get("city")), String.valueOf(item.getValues().get("topic")), String.valueOf(item.getValues().get("from")), String.valueOf(item.getValues().get("to")),String.valueOf(item.getValues().get("rating")),String.valueOf(item.getValues().get("address")));
		return lItem;
	}

	public static void deleteItem(String itemId) throws ApiException {
		client.send(new DeleteItem(itemId));
	}
	
	public static LocalItem[] listAllItems() throws ApiException {
		Item [] result = client.send(new ListItems()
				  .setReturnProperties(true)
				);
		LocalItem lItem[];
		lItem = new LocalItem[result.length];
		for (int i=0;i<result.length;i++) {
			lItem[i]=convert(result[i]);
		}
		return lItem;
	}
	
	public static LocalItem[] listActivities() throws ApiException {
		Item [] result = client.send(new ListItems()
				  .setFilter(" \"activity\" in 'type' ")
				  .setReturnProperties(true)
				);
		LocalItem lItem[];
		lItem = new LocalItem[result.length];
		for (int i=0;i<result.length;i++) {
			lItem[i]=convert(result[i]);
		}
		return lItem;
	}
	
	public static LocalItem[] listActivitiesWithCount(long count) throws ApiException {
		Item [] result = client.send(new ListItems()
				  .setFilter(" \"activity\" in 'type' ")
				  .setReturnProperties(true)
				  .setCount(count)
				);
		LocalItem lItem[];
		lItem = new LocalItem[result.length];
		for (int i=0;i<result.length;i++) {
			lItem[i]=convert(result[i]);
		}
		return lItem;
	}
	
	public static LocalItem[] search(String filter) throws ApiException {
		Item [] result = client.send(new ListItems()
				  .setFilter(filter)
				  .setReturnProperties(true)
				);
		LocalItem lItem[];
		lItem = new LocalItem[result.length];
		for (int i=0;i<result.length;i++) {
			lItem[i]=convert(result[i]);
		}
		return lItem;
	}
	
	public static Integer getLastId() throws ApiException {
		LocalItem[] items = RecombeeItems.listActivities(); 
		List<Integer> list = new LinkedList<>();
		for (int i=0;i<items.length;i++) {
			list.add(Integer.valueOf(items[i].getItemId()));
		}
		list.sort(Comparator.naturalOrder());
		return list.get(items.length-1);
	}
	
	public static LocalItem[] listRestaurants() throws ApiException {
		Item [] result = client.send(new ListItems()
				  .setFilter(" \"restaurant\" in 'type' ")
				  .setReturnProperties(true)
				);
		LocalItem lItem[];
		lItem = new LocalItem[result.length];
		for (int i=0;i<result.length;i++) {
			lItem[i]=convert(result[i]);
		}
		return lItem;
	}
	
	public static LocalItem[] listRestaurantsWithCount(long count) throws ApiException {
		Item [] result = client.send(new ListItems()
				  .setFilter(" \"restaurant\" in 'type' ")
				  .setReturnProperties(true)
				  .setCount(count)
				);
		LocalItem lItem[];
		lItem = new LocalItem[result.length];
		for (int i=0;i<result.length;i++) {
			lItem[i]=convert(result[i]);
		}
		return lItem;
	}

	public static LocalItem[] searchWithCount(String filter, long count) throws ApiException {
		Item [] result = client.send(new ListItems()
				  .setFilter(filter)
				  .setReturnProperties(true)
				  .setCount(count)
				);
		LocalItem lItem[];
		lItem = new LocalItem[result.length];
		for (int i=0;i<result.length;i++) {
			lItem[i]=convert(result[i]);
		}
		return lItem;
	}
 }
