package org.poz;


import org.apache.commons.lang3.builder.HashCodeBuilder;

public class SitemapEntry {
    private String url;
    private SitemapEntry parent;
    private boolean visited;
    private boolean external;

    public SitemapEntry(String url, SitemapEntry parent) {
        this.url = url;
        this.parent = parent;
        this.visited = false;
    }

    public SitemapEntry(String url, SitemapEntry parent, boolean external) {
        this.url = url;
        this.parent = parent;
        this.visited = false;
        this.external = external;
    }

    public String getUrl() {
        return url;
    }

    public SitemapEntry getParent() {
        return parent;
    }

    public boolean isVisited() {
        return visited;
    }

    public boolean isExternal() {
        return external;
    }

    public void setVisited() {
        this.visited = true;
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
        return other instanceof SitemapEntry && this.url.equals(((SitemapEntry) other).url);
    }
}
