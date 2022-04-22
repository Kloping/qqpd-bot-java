package io.github.kloping.qqbot.entitys;

import java.util.Objects;

/**
 * @author github.kloping
 */
public class UrlPack {
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "UrlPack{" +
                "url='" + url + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UrlPack urlPack = (UrlPack) o;
        return Objects.equals(url, urlPack.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url);
    }
}
