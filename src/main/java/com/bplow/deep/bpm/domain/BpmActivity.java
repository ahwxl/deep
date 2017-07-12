package com.bplow.deep.bpm.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BpmActivity implements java.io.Serializable{
    
    private static final long serialVersionUID = 1116562322735717739L;
    
    private String id;
    
    @JsonProperty("text")
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
