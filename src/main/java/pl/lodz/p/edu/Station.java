package pl.lodz.p.edu;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Station {
    @JsonProperty("empty_slots")
    private String emptySlots;
    @JsonProperty("free_bikes")
    private String freeBikes;
    @JsonProperty("id")
    private String id;
    @JsonProperty("latitude")
    private Float latitude;
    @JsonProperty("longitude")
    private Float longitude;
    @JsonProperty("name")
    private String name;
    @JsonProperty("timestamp")
    private String timestamp;
    @JsonProperty("extra")
    private Extra extra;


    public Station() {
    }

    public Extra getExtra() {
        return extra;
    }

    public void setExtra(Extra extra) {
        this.extra = extra;
    }

    public String getEmptySlots() {
        return emptySlots;
    }

    public void setEmptySlots(String emptySlots) {
        this.emptySlots = emptySlots;
    }

    public String getFreeBikes() {
        return freeBikes;
    }

    public void setFreeBikes(String freeBikes) {
        this.freeBikes = freeBikes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
