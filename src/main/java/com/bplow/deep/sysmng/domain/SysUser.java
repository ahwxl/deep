package com.bplow.deep.sysmng.domain;

import java.util.Date;

import com.bplow.deep.authority.User;
import com.bplow.deep.base.jackson.CustomDateSerializer;
import com.bplow.deep.base.pagination.PageInfo;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class SysUser extends PageInfo implements User{

	private static final long serialVersionUID = 1L;

	private String userId;

    private String userName;

    private String nickName;

    private String realName;

    private String password;

    private String sex;

    private String birthday;

    private String phone;

    private String email;

    private String mobile;

    private String status;

    private Integer districtId;

    private String address;

    private String level;

    private String remark;

    private String type;

    private String salt;

    private Date gmtCreate;

    private Date gmtModify;

    private Date latestAccessTime;
    
    private String organizationId;
    
    private boolean checked;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt == null ? null : salt.trim();
    }

    @JsonSerialize(using = CustomDateSerializer.class)
    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    @JsonSerialize(using = CustomDateSerializer.class)
    public Date getGmtModify() {
        return gmtModify;
    }

    public void setGmtModify(Date gmtModify) {
        this.gmtModify = gmtModify;
    }

    @Override
    public boolean getLocked() {
        return false;
    }

    @Override
    public String getCredentialsSalt() {
        return this.getUserId()+this.salt;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getDistrictId() {
		return districtId;
	}

	public void setDistrictId(Integer districtId) {
		this.districtId = districtId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getLatestAccessTime() {
		return latestAccessTime;
	}

	public void setLatestAccessTime(Date latestAccessTime) {
		this.latestAccessTime = latestAccessTime;
	}
    
}