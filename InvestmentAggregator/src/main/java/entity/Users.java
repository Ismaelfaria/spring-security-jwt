package entity;

import java.time.Instant;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tdUsers")
public class Users {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID userID;
	@Column(name = "userName")
	private String userName;
	@Column(name = "email")
	private String email;
	@Column(name = "password")
	private String password;
	@CreationTimestamp
	private Instant creationTimestamp;
	@UpdateTimestamp
	private Instant cpdateTimestamp;
	
	
	public Users() {
		super();
	}


	public Users(UUID userID, String userName, String email, String password, Instant creationTimestamp,
			Instant cpdateTimestamp) {
		super();
		this.userID = userID;
		this.userName = userName;
		this.email = email;
		this.password = password;
		this.creationTimestamp = creationTimestamp;
		this.cpdateTimestamp = cpdateTimestamp;
	}


	public UUID getUserID() {
		return userID;
	}


	public void setUserID(UUID userID) {
		this.userID = userID;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public Instant getCreationTimestamp() {
		return creationTimestamp;
	}


	public void setCreationTimestamp(Instant creationTimestamp) {
		this.creationTimestamp = creationTimestamp;
	}


	public Instant getCpdateTimestamp() {
		return cpdateTimestamp;
	}


	public void setCpdateTimestamp(Instant cpdateTimestamp) {
		this.cpdateTimestamp = cpdateTimestamp;
	}


}