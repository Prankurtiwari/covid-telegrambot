
package com.bot.telegrambot.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "centers"
})
@Generated("jsonschema2pojo")
public class AllCenters implements Serializable {

    @JsonProperty("centers")
    private List<Center> centers = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("centers")
    public List<Center> getCenters() {
        return centers;
    }

    @JsonProperty("centers")
    public void setCenters(List<Center> centers) {
        this.centers = centers;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        return "CenterList{\n" +
                "centers=" + centers +
                ", additionalProperties=" + additionalProperties +
                "\n}";
    }
}
