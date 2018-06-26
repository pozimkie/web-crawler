package org.poz;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;



public class WebCrawlerApp {


    public static void main(String[] args) {

        Document doc;
        
        try {
            doc = Jsoup.connect("http://wiprodigital.com/").get();

            Elements links = doc.select("a[href]");

            System.out.println("Links found:" + links.size() );

            for (Element link : links) {
                System.out.println(link.attr("abs:href"));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}


