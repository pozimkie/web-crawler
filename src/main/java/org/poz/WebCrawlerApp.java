package org.poz;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;


public class WebCrawlerApp {

    private static String entry_url = "https://wiprodigital.com";
    private static Map<String, SitemapEntry> sitemap = new HashMap<>();
    private static Stack<String> to_be_visited = new Stack<>();

    public static void main(String[] args) {

        SitemapEntry parent = null;
        SitemapEntry current_entry = new SitemapEntry(entry_url, parent);
        to_be_visited.add(entry_url);
        sitemap.put(current_entry.getUrl(), current_entry);
        parent = current_entry;

        while(to_be_visited.size() > 0) {
            String current_url = to_be_visited.pop();

            try {
                System.out.println("Visiting " + current_url);
                Document doc = Jsoup.connect(current_url).get();
                to_be_visited.remove(current_url);
                Elements links = doc.select("a[href]");

                System.out.println("Links found:" + links.size() );

                for (Element link : links) {
                    String new_url = link.attr("abs:href");
                    if(isExternalUrl(new_url)) {
                        SitemapEntry new_entry = new SitemapEntry(new_url, parent);
                        if(!sitemap.containsValue(new_entry)) {
                            sitemap.put(new_url, new_entry);
                            System.out.println("adding " + new_url);
                            to_be_visited.add(new_url);
                        }

                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Sitemap size: " + sitemap.size() );
            System.out.println("To be visited: " + to_be_visited.size() );
        }


        sitemap.forEach(
                (k, entry) -> System.out.println("url:" + entry.getUrl() + ", parent:" + entry.getParent())
        );
        System.out.println("Sitemap size:" + sitemap.size() );
        System.out.println("to_be_visited size:" + to_be_visited.size() );


    }

    private static boolean isExternalUrl(String url) {
        if(url.startsWith(entry_url)) return true;
        return false;
    }



}


