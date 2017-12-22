package com.bplow.deep.base.jackson;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonTypeResolver;

public class Message {

    private String      traceId;
    private String      message;
    private String      code;
    @JsonIgnoreProperties(ignoreUnknown = true)
    private MessageData data;

    @JsonCreator
    public Message() {
        super();
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public MessageData getData() {
        return data;
    }

    public void setData(MessageData data) {
        this.data = data;
    }

    public static class MessageData {

        private String    message;
        private String    timestamp;
        private String    code;
        private String    interval;
        private String    updateTime;
        @JsonIgnoreProperties(ignoreUnknown = true)
        private innerData data;

        @JsonCreator
        public MessageData() {
            super();
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getInterval() {
            return interval;
        }

        public void setInterval(String interval) {
            this.interval = interval;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public innerData getData() {
            return data;
        }

        public void setData(innerData data) {
            this.data = data;
        }

    }

    public static class innerData {
        @JsonProperty("page")
        public int        page;
        @JsonProperty("totalPage")
        public int        totalPage;
        @JsonIgnoreProperties(ignoreUnknown = true)
        @JsonProperty("list")
        @JsonSubTypes({ @JsonSubTypes.Type(value = Data.class) })
        public List<Data> datas = new ArrayList<Data>();
        @JsonProperty("totalRecord")
        public int        totalRecord;

        @JsonCreator
        public innerData() {
            super();
        }

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public int getTotalPage() {
            return totalPage;
        }

        public void setTotalPage(int totalPage) {
            this.totalPage = totalPage;
        }

        public List<Data> getDatas() {
            return datas;
        }

        public void setDatas(List<Data> datas) {
            this.datas = datas;
        }

        public int getTotalRecord() {
            return totalRecord;
        }

        public void setTotalRecord(int totalRecord) {
            this.totalRecord = totalRecord;
        }

    }

    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "list")
    @JsonSubTypes({ @JsonSubTypes.Type(value = Data.class) })
    public static class Data {

        private String pageTypeId;
        private String pageTypeName;
        private String visitorType;
        //private String itemModel;
        private String cityName;
        private String oid;
        private String itemId;
        private String title;
        private String srcGrpName;
        private String oldVisitor;
        private String visitTime;

        @JsonCreator
        public Data() {
            super();
        }

        public String getPageTypeId() {
            return pageTypeId;
        }

        public void setPageTypeId(String pageTypeId) {
            this.pageTypeId = pageTypeId;
        }

        public String getPageTypeName() {
            return pageTypeName;
        }

        public void setPageTypeName(String pageTypeName) {
            this.pageTypeName = pageTypeName;
        }

        public String getVisitorType() {
            return visitorType;
        }

        public void setVisitorType(String visitorType) {
            this.visitorType = visitorType;
        }

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

        public String getOid() {
            return oid;
        }

        public void setOid(String oid) {
            this.oid = oid;
        }

        public String getItemId() {
            return itemId;
        }

        public void setItemId(String itemId) {
            this.itemId = itemId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSrcGrpName() {
            return srcGrpName;
        }

        public void setSrcGrpName(String srcGrpName) {
            this.srcGrpName = srcGrpName;
        }

        public String getOldVisitor() {
            return oldVisitor;
        }

        public void setOldVisitor(String oldVisitor) {
            this.oldVisitor = oldVisitor;
        }

        public String getVisitTime() {
            return visitTime;
        }

        public void setVisitTime(String visitTime) {
            this.visitTime = visitTime;
        }

    }

}
