package ua.com.radiokot.feed.api.status.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.jasminb.jsonapi.annotations.Type;
import ua.com.radiokot.feed.api.jsonapi.BaseResource;
import ua.com.radiokot.feed.auhtors.model.FeedSite;

import java.util.Date;
import java.util.Map;

@Type("status")
public class StatusResource extends BaseResource {
    @JsonProperty("last_post_date")
    public Map<FeedSite, Date> lastPostDate;

    public StatusResource(Map<FeedSite, Date> lastPostDate) {
        super(Long.toString(System.currentTimeMillis()));
        this.lastPostDate = lastPostDate;
    }
}