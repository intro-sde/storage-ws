package rest.model;

import java.io.Serializable;

import javax.persistence.Entity;

@Entity
public class LocalItem  implements Serializable {
	private static final long serialVersionUID = 1L;
	private String itemId;
	private String type;
	private String name;
	private String city;
	private String topic;
	private String from;
	private String to;
	private String rating;
	private String address;
	
	
	public LocalItem(String itemId, String type, String name, String city, String topic, String from, String to,
			String rating, String address) {
		super();
		this.itemId = itemId;
		this.type = type;
		this.name = name;
		this.city = city;
		this.topic = topic;
		this.from = from;
		this.to = to;
		this.rating = rating;
		this.address = address;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
}
