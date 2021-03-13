package com.example.demo.Ticker;

import java.util.HashMap;
import java.util.Map;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"datetime",
"open",
"high",
"low",
"close",
"volume"
})
@ToString
@EqualsAndHashCode
public class Value {

@JsonProperty("datetime")
private String datetime;
@JsonProperty("open")
private String open;
@JsonProperty("high")
private String high;
@JsonProperty("low")
private String low;
@JsonProperty("close")
private String close;
@JsonProperty("volume")
private String volume;
@JsonIgnore
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

@JsonProperty("datetime")
public String getDatetime() {
return datetime;
}

@JsonProperty("datetime")
public void setDatetime(String datetime) {
this.datetime = datetime;
}

@JsonProperty("open")
public String getOpen() {
return open;
}

@JsonProperty("open")
public void setOpen(String open) {
this.open = open;
}

@JsonProperty("high")
public String getHigh() {
return high;
}

@JsonProperty("high")
public void setHigh(String high) {
this.high = high;
}

@JsonProperty("low")
public String getLow() {
return low;
}

@JsonProperty("low")
public void setLow(String low) {
this.low = low;
}

@JsonProperty("close")
public String getClose() {
return close;
}

@JsonProperty("close")
public void setClose(String close) {
this.close = close;
}

@JsonProperty("volume")
public String getVolume() {
return volume;
}

@JsonProperty("volume")
public void setVolume(String volume) {
this.volume = volume;
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