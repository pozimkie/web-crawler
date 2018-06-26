package org.poz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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

            FileOutputStream outputStream = new FileOutputStream("urls_" + mycrawler.getDomain() + ".txt");
            printSitemap(sitemap, outputStream);
            outputStream.close();

            logger.info("Exiting");

        }
        catch (MalformedURLException e) {
            logger.error("Invalid URL provided ", e.getMessage());
        }
        catch (FileNotFoundException e) {
            logger.error("Cannot create output file: ", e.getMessage());
        }
        catch (IOException e) {
            logger.error("Cannot write to file: ", e.getMessage());
        }
        catch (Exception e) {
            logger.error("Got exception ", e.getMessage());
        }


    }



    private static void printSitemap(Map<String, SitemapEntry> sitemap, FileOutputStream outputStream) throws IOException {

        SitemapEntry root = sitemap.values()
                .stream()
                .filter(e -> e.getParent() == null)
                .findAny()
                .orElse(null);
        outputStream.write((root.getUrl()+ "\n").getBytes());
        sitemap.remove(root.getUrl()); //root (entry with null parent) removed to prevent NullPointerException in printChilds
        printChilds(sitemap, outputStream, root, "\t");

    }

    private static void printChilds(Map<String, SitemapEntry> sitemap, FileOutputStream outputStream, SitemapEntry parent, String tab) throws IOException {

        sitemap.values()
                .stream()
                .filter(e -> e.getParent().equals(parent))
                .collect(Collectors.toSet()).forEach(
                (SitemapEntry entry) -> {
                    try {
                        outputStream.write((tab + entry.getUrl() + "\n").getBytes());
                        printChilds(sitemap, outputStream, entry, tab + "\t");
                    } catch (IOException e) {
                        logger.error("Cannot  write to file: ", e.getMessage());
                    }

                }

        );
    }


}


