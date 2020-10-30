package com.linkkap.entity;

/**
 * @author yeeq
 * @date 2020/10/29
 */
public class FileData {

    private Integer id;
    private String publisher;
    private String prospectusUrl;
    private String reportUrl;

    public FileData() {

    }

    public FileData(Integer id, String publisher, String prospectusUrl, String reportUrl) {
        this.id = id;
        this.publisher = publisher;
        this.prospectusUrl = prospectusUrl;
        this.reportUrl = reportUrl;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getProspectusUrl() {
        return prospectusUrl;
    }

    public void setProspectusUrl(String prospectusUrl) {
        this.prospectusUrl = prospectusUrl;
    }

    public String getReportUrl() {
        return reportUrl;
    }

    public void setReportUrl(String reportUrl) {
        this.reportUrl = reportUrl;
    }

    @Override
    public String toString() {
        return "FileData{" +
                "id='" + id + '\'' +
                ", publisher='" + publisher + '\'' +
                ", prospectusUrl='" + prospectusUrl + '\'' +
                ", reportUrl='" + reportUrl + '\'' +
                '}';
    }
}
