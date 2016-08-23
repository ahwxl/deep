package com.bplow.deep.authority;

import java.io.Serializable;

public class ShiroUser implements Serializable {
	private static final long serialVersionUID = 1L;
	private long id;
	private String userName;
	private String password;
	private String name;

	public ShiroUser(long id, String userName, String name, String password) {
		this.id = id;
		this.userName = userName;
		this.name = name;
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public long getId() {
		return id;
	}

	public String getUserName() {
		return userName;
	}

	public String getName() {
		return name;
	}

	/**
	 * 本函数输出将作为默认的<shiro:principal/>输出.
	 */
	@Override
	public String toString() {
		return userName;
	}

	/**
	 * 重载hashCode,只计算loginName;
	 */
	/*@Override
	public int hashCode() {
		return Objects.hashCode(userName);
	}*/

	/**
	 * 重载equals,只计算loginName;
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ShiroUser other = (ShiroUser) obj;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}
}
