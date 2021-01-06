package com.company;

import java.time.LocalDateTime;

public class Report {
    String id;
    String name;
    long contentAvg;
    long articleCnt;
    long contentLen;
    LocalDateTime publishedFrom;
    LocalDateTime publishedTo;

    public Report(String id, String name, long contentLen, LocalDateTime publishedFrom, LocalDateTime publishedTo) {
        this.id = id;
        this.name = name;
        this.contentLen = contentLen;
        this.publishedFrom = publishedFrom;
        this.publishedTo = publishedTo;
        this.articleCnt = 1;
    }

    public void setContentAvg() {
        this.contentAvg = contentLen/articleCnt;
    }

    @Override
    public String toString() {
        return "Report{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", contentAvg=" + contentAvg +
                ", publishedFrom=" + publishedFrom +
                ", publishedTo=" + publishedTo +
                '}';
    }
}
