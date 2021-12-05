package ua.com.radiokot.feed.api.attachments.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.jasminb.jsonapi.annotations.Type;
import ua.com.radiokot.feed.api.jsonapi.BaseResource;

@Type("attachments")
public abstract class AttachmentResource extends BaseResource {
    @JsonProperty("api_id")
    public String apiId;

    public AttachmentResource(String id, String apiId) {
        super(id);
        this.apiId = apiId;
    }
}
