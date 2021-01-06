package com.company;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Article {
    int id;
    String source_id;
    String source_name, title, content;
    LocalDateTime published_at;

    public Article(String[] data) {
        this.id = Integer.parseInt(data[0]);
        this.source_id = data[1];
        this.source_name = data[2];
        this.title = data[3];
        this.content = data[4];
        this.published_at = stringToDate(data[5]);
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + this.id +
                ", source_id='" + this.source_id + '\'' +
                ", source_name='" + this.source_name + '\'' +
                ", title='" + this.title + '\'' +
                ", content='" + this.content + '\'' +
                ", published_at=" + this.published_at +
                '}';
    }

    public LocalDateTime stringToDate(String s) {
        if (s.contains(".")){
            s = s.split("\\.")[0];
        } else if (s.contains("+")){
            s = s.split("\\+")[0];
        } else {
            s = s.split("Z")[0];
        }
        s = s.replaceAll("T", " ");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(s, formatter);
    }
}
