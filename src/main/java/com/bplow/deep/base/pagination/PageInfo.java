package com.bplow.deep.base.pagination;

import java.io.Serializable;

/**
 * Created by ajan on 2016/7/31.
 */
public class PageInfo implements Serializable{
    /**  */
    private static final long serialVersionUID = 1238074254814244084L;
    
    private int pageNo = 1;
    private int pageSize = 10;
    private int totalPage;
    private int totalCount;
    private int iDisplayStart;
    private String sSearch;
    
    public String getsSearch() {
        return sSearch;
    }

    public void setsSearch(String sSearch) {
        this.sSearch = sSearch;
    }

    public int getiDisplayStart() {
        return iDisplayStart;
    }

    public void setiDisplayStart(int iDisplayStart) {
        this.iDisplayStart = iDisplayStart;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        if (pageNo >= 0) {
            this.pageNo = pageNo;
        }
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        if (totalCount >= 0) {
            this.totalCount = totalCount;
            this.setTotalPage();
        }
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        if(totalPage >= 0){
            this.totalPage = totalPage;
        }
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        if(pageSize > 0){
            this.pageSize = pageSize;
        }
    }

    private void setTotalPage() {
        this.totalPage = 0;
        if (this.pageSize > 0 && this.totalCount > 0) {
            this.totalPage = this.totalCount / this.pageSize;
            if (this.totalCount % this.pageSize > 0) {
                this.totalPage += 1;
            }
        }
    }

    public boolean hasMore() {
        if (this.totalCount > 0 && this.totalPage > 0
                && this.pageNo > 0 && this.totalPage > this.pageNo) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "PageInfo{" +
                "pageNo=" + pageNo +
                ", pageSize=" + pageSize +
                ", totalPage=" + totalPage +
                ", totalCount=" + totalCount +
                '}';
    }
}
