package ua.com.radiokot.feed.api.posts.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.jasminb.jsonapi.annotations.Relationship;
import com.github.jasminb.jsonapi.annotations.Type;
import ua.com.radiokot.feed.api.attachments.model.AttachmentResource;
import ua.com.radiokot.feed.api.authors.model.AuthorResource;
import ua.com.radiokot.feed.api.jsonapi.BaseResource;
import ua.com.radiokot.feed.posts.model.FeedPost;

import java.util.Date;
import java.util.List;

@Type("posts")
public class PostResource extends BaseResource {
    @JsonProperty("api_id")
    public String apiId;

    @JsonProperty("date")
    public Date date;

    @JsonProperty("text")
    public String text;

    @JsonProperty("url")
    public String url;

    @Relationship("author")
    public AuthorResource author;

    @Relationship("attachments")
    public List<AttachmentResource> attachments;

    public PostResource(FeedPost p) {
        super(p.getId());
        this.apiId = p.getApiId();
        this.date = p.getDate();
        this.text = p.getText();
        this.url = p.getUrl();
        this.author = new AuthorResource(p.getAuthorId());
    }

    public PostResource(FeedPost p,
                        AuthorResource author,
                        List<AttachmentResource> attachments) {
        this(p);
        this.author = author;
        this.attachments = attachments;
    }
}
