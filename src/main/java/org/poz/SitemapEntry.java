package org.poz;


import org.apache.commons.lang3.builder.HashCodeBuilder;

public class SitemapEntry {
    private String url;
    private SitemapEntry parent;


    public SitemapEntry(String url, SitemapEntry parent) {
        this.url = url;
        this.parent = parent;

    }


    public String getUrl() {
        return url;
    }

    public SitemapEntry getParent() {
        return parent;
    }




    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(url).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof SitemapEntry) == false) {
            return false;
        }
        return this.url.equals(((SitemapEntry)other).url);
    }
}
