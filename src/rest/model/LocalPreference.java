package rest.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;

@Entity
public class LocalPreference  implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String userId;
	private String itemId;
	private Date timestamp;
	
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	public LocalPreference(String userId, String itemId, Date date) {
		super();
		this.userId = userId;
		this.itemId = itemId;
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
}
