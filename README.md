# Storage service

RESTful web-service that implements CRUD administration tasks of entities. In this project we use Recombee for data storage therefore this service is a mixture of adaptive and data layer, since it serves as storage service, but also communicates to Recombee API. The following entities are used as wrapper classes for Recombee objects:

- LocalUser (implemented as RecombeeUser):
	{
        "userId": "1",
        "firstname": "Julia",
        "lastname": "Hermann",
        "email": "julia@gmail.com",
        "birthyear": "1993"
    }
- LocalItem (implemented as RecombeeItem):
	{
        "itemId": "12",
        "type": "activity",
        "name": "TEAM RELAY VENICE LONG DISTANCE - Team Relay",
        "city": "Venice",
        "topic": "Triathlon",
        "from": "2018-06-02T22:00:00Z",
        "to": "2018-06-03T21:59:59Z",
        "rating": "null",
        "address": "null"
    }
- LocalRating (implemented as RecombeeRating):
	 {
        "userId": "1",
        "itemId": "2",
        "rating": "3.5",
        "timestamp": 1515254313792
    }
- LocalPreference (implemented as RecombeeBookmark):
	 {
        "userId": "1",
        "itemId": "2",
        "timestamp": 1515254313792
    }

The following methods for the different endpoints are available in this service:

https://sde-storage-ws.herokuapp.com/rdb/users:
- @POST  | [firstname, lastname, email, birthyear] | Creates new user, where id is assigned automatically.
- @DELETE | [userId] | Deletes user with given id.
- @GET | [] | Lists all user in database.

https://sde-storage-ws.herokuapp.com/rdb/items:
- /activity @POST | [topic, city, name, from, to] | Creates new activity item. (The columns that are not relevant will be null.)
- /restaurant @POST | [topic, city, name, address, rating] | Creates new restaurant item. (The columns that are not relevant will be null.)
- @DELETE | [itemId] | Deletes item with given id.
- @GET | [filter] | Get list of items with given filter. (Filter is a ReQL query returning boolean for each record.)
- /all @GET | [] | Lists all items.
- /activities @GET |[]| Returns list of activities.
- /restaurants @ GET |[]| Returns list of restaurants.

https://sde-storage-ws.herokuapp.com/rdb/ratings:
- @POST  | [userId, itemId, rating] | Creates new rating with automatic timestamp.
- @DELETE | [userId, itemId, timestamp] | Deletes rating with given user and item and timestamp.
- /by_item @GET | [itemId] | Lists all ratings regarding given item.
- /by_user @GET | [userId] | Lists all ratings regarding given user.

https://sde-storage-ws.herokuapp.com/rdb/preferences:
- @POST  | [userId, itemId] | Creates new preference with automatic timestamp.
- @DELETE | [userId, itemId, timestamp] | Deletes preference with given user and item and timestamp.
- /by_item @GET | [itemId] | Lists all preferences regarding given item.
- /by_user @GET | [userId] | Lists all preferences regarding given user.

*every query parameter is a String. 


TODO: Update RecombeeItems.java and ItemResource;java for restaurants.

Reference:
Recombee API (version 1.6.0), Available at: https://docs.recombee.com/api.html.
