package uts.edu.java.crud.corte3.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class UserLogin {
	@Id	
	private Integer id;
	private String email;
	private String password;
	public UserLogin(Integer id, String email, String password) {
		super();
		this.id = id;
		this.email = email;
		this.password = password;
	}
	public UserLogin() {
		super();
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	@Override
	public String toString() {
		return "UserLogin [id=" + id + ", email=" + email + ", password=" + password + "]";
	}

	
}
