package com.sc.domain;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

/** User Profile containing user info
 * 
 * @author anila
 *
 */
@XmlRootElement
public class User {
	public static final String STATUS_ACTIVE = "A";
	public static final String STATUS_DELETED = "D";
	public static final String STATUS_INACTIVE = "I";
	
	private Integer id;
	private String facebookId;
	private String facebookToken;
	private String name;
	private String password;
	private String passwordToken;
	private Date passwordTokenExpires;
	private Date birthDay;
	private String city;
	private String state;
	private String country;
	private String gender;
	private String email;
	private String status;
	private String image;
	
	private Date created;
	private Date modified;
	private Date lastLogin;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer uid) {
		this.id = uid;
	}
	public String getFacebookId() {
		return facebookId;
	}
	public void setFacebookId(String facebookId) {
		this.facebookId = facebookId;
	}
	public String getFacebookToken() {
		return facebookToken;
	}
	public void setFacebookToken(String facebookToken) {
		this.facebookToken = facebookToken;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPasswordToken() {
		return passwordToken;
	}
	public void setPasswordToken(String passwordToken) {
		this.passwordToken = passwordToken;
	}
	public Date getPasswordTokenExpires() {
		return passwordTokenExpires;
	}
	public void setPasswordTokenExpires(Date passwordTokenExpires) {
		this.passwordTokenExpires = passwordTokenExpires;
	}
	public Date getBirthDay() {
		return birthDay;
	}
	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public Date getModified() {
		return modified;
	}
	public void setModified(Date modified) {
		this.modified = modified;
	}
	public Date getLastLogin() {
		return lastLogin;
	}
	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}
	
	
}
