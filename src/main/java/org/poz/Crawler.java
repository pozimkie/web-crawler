package org.poz;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


public class Crawler {

    private String domain;
    private String root_url;

    private static final Logger logger = LoggerFactory.getLogger(Crawler.class);

    public Crawler(String url) throws MalformedURLException {
        this.root_url = url;
        URL myUrl = new URL(url);
        this.domain = myUrl.getHost();
    }

    public Map<String, SitemapEntry> createSitemap() {

        String entry_url = root_url;
        Map<String, SitemapEntry> sitemap = new HashMap<>();

        SitemapEntry current_entry = new SitemapEntry(entry_url, null);
        long links_to_visit = 1;
        sitemap.put(entry_url, current_entry);


        while (links_to_visit > 0) {

            //pick up a sitemap entry not visited yet
            current_entry = pickUrlToVisit(sitemap);
            if (current_entry == null) break;


            try {
                logger.info("Visiting {}", current_entry.getUrl());
                current_entry.setVisited();
                sitemap.replace(current_entry.getUrl(), current_entry);
                Document doc = Jsoup.connect(current_entry.getUrl()).get();

                Elements links = doc.select("a[href]");

                logger.debug("Links found: {}", links.size());

                for (Element link : links) {
                    String new_url = parseUrl(link.attr("abs:href"));

                    SitemapEntry new_entry = new SitemapEntry(new_url, current_entry, isExternalUrl(new_url));
                    if (!sitemap.containsValue(new_entry)) {
                        sitemap.put(new_url, new_entry);
                    }

                }
            } catch (IOException e) {
                logger.warn("Got exception:", e);

            }

            logger.debug("Sitemap size:" + sitemap.size());
            links_to_visit = countLinksToVists(sitemap);
            logger.debug("Links to visit:" + links_to_visit);
        }
        return sitemap;
    }


    private long countLinksToVists(Map<String, SitemapEntry> sitemap) {
        return sitemap.values()
                .stream()
                .filter(e -> (!e.isVisited() && !e.isExternal()))
                .map(SitemapEntry::getUrl)
                .count();

    }

    private SitemapEntry pickUrlToVisit(Map<String, SitemapEntry> sitemap) {
        return sitemap.values()
                .stream()
                .filter(e -> (!e.isVisited() && !e.isExternal()))
                .findAny()
                .orElse(null);
    }

    boolean isExternalUrl(String url) {

        return !url.matches("(http|https)://(.*\\.)*" + domain + "(.*)");

    }

    /**
     * Removes everything after fragment identifier (#) in url
     * @param url
     * @return base url without #part
     */
    String parseUrl(String url) {
        return url.split("#")[0];

    }


}
