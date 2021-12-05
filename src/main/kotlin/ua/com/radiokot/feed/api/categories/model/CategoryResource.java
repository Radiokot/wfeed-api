package ua.com.radiokot.feed.api.categories.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.jasminb.jsonapi.annotations.Type;
import ua.com.radiokot.feed.api.jsonapi.BaseResource;
import ua.com.radiokot.feed.categories.model.FeedCategoryWithAuthorCount;

import java.util.HashMap;
import java.util.Map;

@Type("categories")
public class CategoryResource extends BaseResource {
    @JsonProperty("thumb_url")
    public String thumbUrl;

    @JsonProperty("author_count")
    public Integer authorCount;

    @JsonProperty("description")
    public Map<String, String> description;

    public CategoryResource(FeedCategoryWithAuthorCount c) {
        super(c.getCategory().getId());
        this.thumbUrl = c.getCategory().getThumbUrl();
        this.authorCount = c.getAuthorCount();
        this.description = new HashMap<>();
        this.description.put("uk", c.getCategory().getDescriptionUk());
        this.description.put("en", c.getCategory().getDescriptionEn());
        this.description.put("ru", c.getCategory().getDescriptionRu());
    }

    public CategoryResource(String id) {
        super(id);
    }
}
