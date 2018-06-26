package org.poz;

import org.junit.Test;
import org.poz.Crawler;

import java.net.MalformedURLException;

import static org.junit.Assert.*;

public class CrawlerTest {
    private Crawler test_crawler;


    @Test
    public void ExternalUrlTest() {

        try {
            test_crawler = new Crawler("http://domain.com/");

            assertFalse(test_crawler.isExternalUrl("http://domain.com/"));
            assertFalse(test_crawler.isExternalUrl("https://domain.com/"));
            assertFalse(test_crawler.isExternalUrl("https://domain.com/"));
            assertFalse(test_crawler.isExternalUrl("http://www.domain.com/"));
            assertFalse(test_crawler.isExternalUrl("http://static.domain.com/"));
            assertFalse(test_crawler.isExternalUrl("http://domain.com/contact"));

            assertTrue(test_crawler.isExternalUrl("http://facebook.com/domain.com"));

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void ParseUrlTest() {
        try {
            test_crawler = new Crawler("http://domain.com/");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        assertEquals("http://domain.com/", test_crawler.parseUrl("http://domain.com/#"));
        assertEquals("http://domain.com/", test_crawler.parseUrl("http://domain.com/#link-8"));
    }

}
