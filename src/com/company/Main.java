package com.company;

import java.io.*;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws InterruptedException, IOException {
        ArrayList<ArticleList> lists = new ArrayList<>();
        String[] paths = {
                "C:\\Users\\HP Omen 15\\Desktop\\csv\\part1.csv",
                "C:\\Users\\HP Omen 15\\Desktop\\csv\\part2.csv",
                "C:\\Users\\HP Omen 15\\Desktop\\csv\\part3.csv",
                "C:\\Users\\HP Omen 15\\Desktop\\csv\\part4.csv"
        };
        for (int i = 0; i < paths.length; i++) {
            lists.add(new ArticleList(paths[i]));
            lists.get(i).start();
        }
        for (ArticleList t: lists) {
            t.join();
        }
        ArrayList<Report> finalReport = new ArrayList<>();
        for (ArticleList al: lists) {
            al.produceFinalReport(finalReport);
        }

        boolean needDataTitles = false;
        File myReport = new File("C:\\Users\\HP Omen 15\\Desktop\\csv\\report.csv");
        if (myReport.createNewFile()) {
            needDataTitles = true;
        }
        PrintWriter writer = new PrintWriter(myReport);
        if (needDataTitles) {
            writer.write("name,ID,published_from,published_to,avg_content_length\n");
        }
        for (Report r: finalReport) {
            StringBuilder sb = new StringBuilder();
            sb.append(r.name);
            sb.append(",");
            sb.append(r.id);
            sb.append(",");
            sb.append(r.publishedFrom);
            sb.append(",");
            sb.append(r.publishedTo);
            sb.append(",");
            sb.append(r.contentAvg);
            sb.append("\n");
            writer.write(sb.toString());
        }
        writer.flush();
        writer.close();
    }
}
