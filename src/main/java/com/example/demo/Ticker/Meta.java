package com.example.demo.Ticker;

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
"symbol",
"interval",
"currency",
"exchange_timezone",
"exchange",
"type"
})
@ToString
@EqualsAndHashCode
public class Meta {

@JsonProperty("symbol")
private String symbol;
@JsonProperty("interval")
private String interval;
@JsonProperty("currency")
private String currency;
@JsonProperty("exchange_timezone")
private String exchangeTimezone;
@JsonProperty("exchange")
private String exchange;
@JsonProperty("type")
private String type;
@JsonIgnore
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

@JsonProperty("symbol")
public String getSymbol() {
return symbol;
}

@JsonProperty("symbol")
public void setSymbol(String symbol) {
this.symbol = symbol;
}

@JsonProperty("interval")
public String getInterval() {
return interval;
}

@JsonProperty("interval")
public void setInterval(String interval) {
this.interval = interval;
}

@JsonProperty("currency")
public String getCurrency() {
return currency;
}

@JsonProperty("currency")
public void setCurrency(String currency) {
this.currency = currency;
}

@JsonProperty("exchange_timezone")
public String getExchangeTimezone() {
return exchangeTimezone;
}

@JsonProperty("exchange_timezone")
public void setExchangeTimezone(String exchangeTimezone) {
this.exchangeTimezone = exchangeTimezone;
}

@JsonProperty("exchange")
public String getExchange() {
return exchange;
}

@JsonProperty("exchange")
public void setExchange(String exchange) {
this.exchange = exchange;
}

@JsonProperty("type")
public String getType() {
return type;
}

@JsonProperty("type")
public void setType(String type) {
this.type = type;
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