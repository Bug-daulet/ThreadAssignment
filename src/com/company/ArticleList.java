package com.company;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ArticleList extends Thread {
    ArrayList<Article> articles;
    ArrayList<Report> reports;
    String path;

    public ArticleList(String path) {
        this.articles = new ArrayList<>();
        this.reports = new ArrayList<>();
        this.path = path;
    }

    @Override
    public void run() {
        try {
            parseFile();
            produceReport();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void parseFile() throws IOException {
        BufferedReader csvReader = new BufferedReader(new FileReader(path));
        String row = csvReader.readLine();
        while ((row = csvReader.readLine()) != null) {
            String[] data = row.split(",");
            if (data.length == 6) {
                Article article = new Article(data);
                articles.add(article);
            }
        }
        csvReader.close();
    }

    private void produceReport() {
        boolean found;
        for (Article a: articles) {
            found = false;
            for (Report r: reports) {
                if (a.source_id.equals(r.id)) {
                    found = true;
                    r.articleCnt++;
                    if (a.published_at.isAfter(r.publishedTo)) {
                        r.publishedTo = a.published_at;
                    }
                    if (a.published_at.isBefore(r.publishedFrom)) {
                        r.publishedFrom = a.published_at;
                    }
                    r.contentLen += calculateContentLength(a.content);
                    break;
                }
            }
            if (!found) {
                reports.add(new Report(a.source_id, a.source_name, calculateContentLength(a.content), a.published_at, a.published_at));
            }
        }
        for (Report r: reports) {
            r.setContentAvg();
        }
    }

    public int calculateContentLength(String content) {
        int len = 0;
        len += content.length();
        if (content.contains("chars]")) {
            String temp = content.split("\\[\\+")[1];
            temp = temp.split(" chars]")[0];
            len += Integer.parseInt(temp);
            len -= (9 + temp.length());
        }
        return len;
    }

    public synchronized void produceFinalReport(ArrayList<Report> finalReport) {
        boolean found;
        for (Report r: reports) {
            found = false;
            for (Report fr: finalReport) {
                if (r.id.equals(fr.id)) {
                    found = true;
                    fr.articleCnt += r.articleCnt;
                    if (r.publishedTo.isAfter(fr.publishedTo)) {
                        fr.publishedTo = r.publishedTo;
                    }
                    if (r.publishedFrom.isBefore(fr.publishedFrom)) {
                        fr.publishedFrom = r.publishedFrom;
                    }
                    fr.contentLen += r.contentLen;
                }
            }
            if (!found) {
                finalReport.add(r);
            }
        }
        for (Report r: finalReport) {
            r.setContentAvg();
        }
    }
}
