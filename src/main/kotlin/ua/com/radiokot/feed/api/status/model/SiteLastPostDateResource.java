package ua.com.radiokot.feed.api.status.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.jasminb.jsonapi.annotations.Relationship;
import com.github.jasminb.jsonapi.annotations.Type;
import ua.com.radiokot.feed.api.jsonapi.BaseResource;
import ua.com.radiokot.feed.api.sites.model.SiteResource;
import ua.com.radiokot.feed.sites.model.FeedSite;

import java.time.Instant;

@Type("site-last-post-dates")
public class SiteLastPostDateResource extends BaseResource {
    @JsonProperty("date")
    public Instant date;

    @Relationship("site")
    public SiteResource site;

    public SiteLastPostDateResource(FeedSite site, Instant date) {
        super(site.getId() + "_" + System.currentTimeMillis());
        this.date = date;
        this.site = new SiteResource(site);
    }
}
