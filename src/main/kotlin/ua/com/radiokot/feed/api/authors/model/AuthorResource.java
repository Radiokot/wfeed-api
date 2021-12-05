package ua.com.radiokot.feed.api.authors.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.jasminb.jsonapi.annotations.Relationship;
import com.github.jasminb.jsonapi.annotations.Type;
import ua.com.radiokot.feed.api.categories.model.CategoryResource;
import ua.com.radiokot.feed.api.jsonapi.BaseResource;
import ua.com.radiokot.feed.auhtors.model.FeedAuthor;
import ua.com.radiokot.feed.auhtors.model.FeedSite;

@Type("authors")
public class AuthorResource extends BaseResource {
    @JsonProperty("api_id")
    public String apiId;

    @JsonProperty("site")
    public FeedSite site;

    @JsonProperty("name")
    public String name;

    @JsonProperty("photo_url")
    public String photoUrl;

    @Relationship("category")
    public CategoryResource category;

    public AuthorResource(FeedAuthor a) {
        super(a.getId());
        this.apiId = a.getApiId();
        this.site = a.getSite();
        this.name = a.getName();
        this.photoUrl = a.getPhotoUrl();
        this.category = new CategoryResource(a.getCategoryId());
    }

    public AuthorResource(String id) {
        super(id);
    }
}
