package org.poz;

import org.junit.Before;
import org.junit.Test;
import org.poz.SitemapEntry;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;


public class SitemapTest {

    private static Map<String, SitemapEntry> sitemap = new HashMap<>();
    private static SitemapEntry entry1 = new SitemapEntry("http://www.foo.com/", null);
    private static SitemapEntry entry2;
    private static SitemapEntry entry3;
    private static SitemapEntry entry4;
    private static SitemapEntry entry5, entry5_duplicate;


    @Before
    public void setUp() {
        sitemap.put(entry1.getUrl(), entry1);
        entry2 = new SitemapEntry("http://www.foo.com/contact", entry1);
        entry3 = new SitemapEntry("http://www.foo.com/contact/sales", entry2);
        entry4 = new SitemapEntry("http://www.foo.com/contact/support", entry2);
        entry5 = new SitemapEntry("http://www.foo.com/products", entry1);
        entry5_duplicate = new SitemapEntry("http://www.foo.com/products", null);
        sitemap.put(entry2.getUrl(), entry2);
        sitemap.put(entry3.getUrl(), entry3);
        sitemap.put(entry4.getUrl(), entry4);
        sitemap.put(entry5.getUrl(), entry5);
    }

    @Test
    public void testEquals() {
        assertTrue(entry5.equals(entry5_duplicate));
        assertFalse(entry3.equals(entry4));
    }

    @Test
    public void testContain() {
        assertTrue(sitemap.containsValue(entry5));
        assertTrue(sitemap.containsValue(entry5_duplicate));
    }

    @Test
    public void testAssureUnique() {
        int size_before = sitemap.size();
        sitemap.put(entry5_duplicate.getUrl(), entry5_duplicate);
        assertEquals(size_before, sitemap.size());

    }





}
