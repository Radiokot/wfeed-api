package ua.com.radiokot.feed.api.status.model;

import com.github.jasminb.jsonapi.annotations.Relationship;
import com.github.jasminb.jsonapi.annotations.Type;
import ua.com.radiokot.feed.api.jsonapi.BaseResource;
import ua.com.radiokot.feed.sites.model.FeedSite;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Type("status")
public class StatusResource extends BaseResource {
    @Relationship("last_post_dates")
    public List<SiteLastPostDateResource> lastPostDates;

    public StatusResource(Map<FeedSite, Instant> lastPostDates) {
        super(Long.toString(System.currentTimeMillis()));
        this.lastPostDates = new ArrayList<>(lastPostDates.size());
        lastPostDates.forEach((site, lastPostDate) ->
                this.lastPostDates.add(new SiteLastPostDateResource(site, lastPostDate))
        );
    }
}
