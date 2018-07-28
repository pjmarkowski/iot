package pl.lodz.p.edu.entity;

import javax.persistence.*;

@Entity
@Table(name="stations")
public class Stations {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String stationNumber;
    private String stationName;
    private Float latitude;
    private Float longtitude;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStationNumber() {
        return stationNumber;
    }

    public void setStationNumber(String stationNumber) {
        this.stationNumber = stationNumber;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(Float longtitude) {
        this.longtitude = longtitude;
    }
}
