package com.linkkap.entity;

/**
 * @author yeeq
 * @date 2020/10/29
 */
public class ProjectDynamicData {

    private Integer id;
    private String stockAuditNum;
    private String publisher;
    private String url;

    public ProjectDynamicData() {

    }

    public ProjectDynamicData(Integer id, String stockAuditNum, String publisher, String url) {
        this.id = id;
        this.stockAuditNum = stockAuditNum;
        this.publisher = publisher;
        this.url = url;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStockAuditNum() {
        return stockAuditNum;
    }

    public void setStockAuditNum(String stockAuditNum) {
        this.stockAuditNum = stockAuditNum;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "LinkTypeData{" +
                "id=" + id +
                ", stockAuditNum='" + stockAuditNum + '\'' +
                ", publisher='" + publisher + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
