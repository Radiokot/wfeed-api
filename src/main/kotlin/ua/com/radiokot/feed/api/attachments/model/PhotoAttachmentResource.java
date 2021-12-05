package ua.com.radiokot.feed.api.attachments.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.jasminb.jsonapi.annotations.Type;
import ua.com.radiokot.feed.attachments.model.FeedAttachment;

@Type("photo-attachments")
public class PhotoAttachmentResource extends AttachmentResource {
    @JsonProperty("width")
    public Integer width;

    @JsonProperty("height")
    public Integer height;

    @JsonProperty("url_130")
    public String url130;

    @JsonProperty("url_604")
    public String url604;

    @JsonProperty("url_807")
    public String url807;

    @JsonProperty("url_1280")
    public String url1280;

    @JsonProperty("url_2560")
    public String url2560;

    public PhotoAttachmentResource(FeedAttachment.Photo p) {
        super(p.getId(), p.getApiId());
        this.width = p.getWidth();
        this.height = p.getHeight();
        this.url130 = p.getUrl130();
        this.url604 = p.getUrl604();
        this.url807 = p.getUrl807();
        this.url1280 = p.getUrl1280();
        this.url2560 = p.getUrl2560();
    }
}
