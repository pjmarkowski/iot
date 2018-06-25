package pl.lodz.p.edu;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Extra {

    @JsonProperty("bike_uids")
    private List<String> bikes;
    @JsonProperty("number")
    private String number;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public List<String> getBikes() {
        return bikes;
    }

    public void setBikes(List<String> bikes) {
        this.bikes = bikes;
    }
}
