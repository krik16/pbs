package com.rongyi.easy.rpb.domain;

import java.io.Serializable;

public class Users implements  Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -1306460280983603585L;

	private String password;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	private String name;
	private Integer id;
	private boolean admin= false;
	private String shopId;
	
	

	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public boolean isAdmin() {
		return admin;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAdmin(boolean b) {
		this.admin = b;
	}


	

}