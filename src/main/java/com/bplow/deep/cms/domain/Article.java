package com.bplow.deep.cms.domain;

import java.util.Date;

import com.bplow.deep.base.jackson.CustomDateSerializer;
import com.bplow.deep.base.pagination.PageInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class Article extends PageInfo {

	private static final long serialVersionUID = 6765214388929373905L;

	private Integer id;

    private String catalogId;

    private Integer type;

    private String title;

    private String fullTitle;

    private String intro;

    private String images;

    private String author;

    private String modifier;

    private String state;

    private Date gmtCreate;

    private Date gmtModify;

    private Integer times;

    @JsonIgnore
    private byte[] content;
    
    private String cnt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(String catalogId) {
        this.catalogId = catalogId == null ? null : catalogId.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getFullTitle() {
        return fullTitle;
    }

    public void setFullTitle(String fullTitle) {
        this.fullTitle = fullTitle == null ? null : fullTitle.trim();
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro == null ? null : intro.trim();
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images == null ? null : images.trim();
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author == null ? null : author.trim();
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier == null ? null : modifier.trim();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
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

    public Integer getTimes() {
        return times;
    }

    public void setTimes(Integer times) {
        this.times = times;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

	public String getCnt() {
		return cnt;
	}

	public void setCnt(String cnt) {
		this.cnt = cnt;
	}
    

}