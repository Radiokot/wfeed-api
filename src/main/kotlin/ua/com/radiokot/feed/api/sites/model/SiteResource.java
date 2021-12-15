package ua.com.radiokot.feed.api.sites.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.jasminb.jsonapi.annotations.Type;
import ua.com.radiokot.feed.api.jsonapi.BaseResource;
import ua.com.radiokot.feed.sites.model.FeedSite;

@Type("sites")
public class SiteResource extends BaseResource {
    @JsonProperty("name")
    public String name;

    public SiteResource(FeedSite s) {
        super(s.getId());
        this.name = s.getName();
    }

    public SiteResource(String id) {
        super(id);
    }
}
