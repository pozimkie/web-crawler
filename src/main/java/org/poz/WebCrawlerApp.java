package org.poz;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.util.Map;
import java.util.stream.Collectors;

public class WebCrawlerApp {

    private static String entry_url;

    private static final Logger logger = LoggerFactory.getLogger(WebCrawlerApp.class);

    public static void main(String[] args) {

        Crawler mycrawler;

        try {
            if (args.length == 0) throw new Exception("Input URL not provided!");
            entry_url = args[0];
            mycrawler = new Crawler(entry_url);
            Map<String, SitemapEntry> sitemap = mycrawler.createSitemap();

            printSitemap(sitemap);

        } catch (MalformedURLException e) {
            logger.error("Invalid URL provided ", e.getMessage());
        } catch (Exception e) {
            logger.error("Got exception ", e.getMessage());
        }


    }


    private static void printSitemap(Map<String, SitemapEntry> sitemap) {

        SitemapEntry root = sitemap.values()
                .stream()
                .filter(e -> e.getParent() == null)
                .findAny()
                .orElse(null);
        logger.debug((root.getUrl() + "\n"));
        sitemap.remove(root.getUrl()); //root (entry with null parent) removed to prevent NullPointerException in printChilds
        printChilds(sitemap, root, "\t");

    }

    private static void printChilds(Map<String, SitemapEntry> sitemap, SitemapEntry parent, String tab) {

        sitemap.values()
                .stream()
                .filter(e -> e.getParent().equals(parent))
                .collect(Collectors.toSet()).forEach(
                (SitemapEntry entry) -> {

                    System.out.println(tab + entry.getUrl());
                    printChilds(sitemap, entry, tab + "\t");


                }

        );
    }


}


