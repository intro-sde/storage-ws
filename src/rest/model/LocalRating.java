package rest.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;

@Entity
public class LocalRating  implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String userId;
	private String itemId;
	private double rating;
	private Date timestamp;
	
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	public LocalRating(String userId, String itemId, double rating, Date date) {
		super();
		this.userId = userId;
		this.itemId = itemId;
		this.rating = rating;
		this.timestamp = date;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public double getRating() {
		return rating;
	}
	public void setRating(double rating) {
		this.rating = rating;
	}
	
}
