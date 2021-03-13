package com.example.demo.Ticker;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"meta",
"values",
"status"
})
@ToString
@EqualsAndHashCode
public class Ticker {

@JsonProperty("meta")
private Meta meta;
@JsonProperty("values")
private List<Value> values = null;
@JsonProperty("status")
private String status;
@JsonIgnore
@JsonSerialize
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

@JsonProperty("meta")
public Meta getMeta() {
return meta;
}

@JsonProperty("meta")
public void setMeta(Meta meta) {
this.meta = meta;
}

@JsonProperty("values")
public List<Value> getValues() {
return values;
}

@JsonProperty("values")
public void setValues(List<Value> values) {
this.values = values;
}

@JsonProperty("status")
public String getStatus() {
return status;
}

@JsonProperty("status")
public void setStatus(String status) {
this.status = status;
}

@JsonAnyGetter
public Map<String, Object> getAdditionalProperties() {
return this.additionalProperties;
}

@JsonAnySetter
public void setAdditionalProperty(String name, Object value) {
this.additionalProperties.put(name, value);
}

}