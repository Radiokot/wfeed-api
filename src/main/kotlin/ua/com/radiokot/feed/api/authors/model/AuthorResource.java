package ua.com.radiokot.feed.api.authors.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.jasminb.jsonapi.annotations.Relationship;
import com.github.jasminb.jsonapi.annotations.Type;
import ua.com.radiokot.feed.api.categories.model.CategoryResource;
import ua.com.radiokot.feed.api.jsonapi.BaseResource;
import ua.com.radiokot.feed.api.sites.model.SiteResource;
import ua.com.radiokot.feed.auhtors.model.FeedAuthor;
import ua.com.radiokot.feed.sites.model.FeedSite;

@Type("authors")
public class AuthorResource extends BaseResource {
    @JsonProperty("api_id")
    public String apiId;

    @JsonProperty("name")
    public String name;

    @JsonProperty("photo_url")
    public String photoUrl;

    @Relationship("category")
    public CategoryResource category;

    @Relationship("site")
    public SiteResource site;

    public AuthorResource(FeedAuthor a) {
        super(a.getId());
        this.apiId = a.getApiId();
        this.site = new SiteResource(a.getSiteId());
        this.name = a.getName();
        this.photoUrl = a.getPhotoUrl();
        this.category = new CategoryResource(a.getCategoryId());
    }

    public AuthorResource(FeedAuthor a, FeedSite s) {
        this(a);
        this.site = new SiteResource(s);
    }

    public AuthorResource(String id) {
        super(id);
    }
}
