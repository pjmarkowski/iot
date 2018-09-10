package pl.lodz.p.edu;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import pl.lodz.p.edu.entity.History;
import pl.lodz.p.edu.entity.Stations;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
public class Controller {
    @Autowired
    private HistoryRepository historyRepository;
    @Autowired
    private StationsRepository stationsRepository;

    @GetMapping(path="/add")
    public @ResponseBody String addNewRecord (@RequestParam String bikeNumber, @RequestParam String stationNumber, @RequestParam String date) {
        History h = new History();
        h.setBikeNumber(bikeNumber);
        h.setStationNumber(stationNumber);
        h.setDate(date);
        historyRepository.save(h);
        return "saved";
    }
    @GetMapping(path="/addStation")
    public @ResponseBody String addNewStation (@RequestParam String stationNumber, @RequestParam String stationName, @RequestParam
            Float latitude , @RequestParam Float longtitude) {

        Stations s  = new Stations();
        s.setStationNumber(stationNumber);
        s.setStationName(stationName);
        s.setLatitude(latitude);
        s.setLongtitude(longtitude);
        stationsRepository.save(s);
        return "saved";
    }

    @GetMapping(path="/getByBikeNumber")
    public @ResponseBody Iterable<History> getHistoryByBikeNumber(String bikeNumber) {
        Iterable<History> history = historyRepository.findByBikeNumber(bikeNumber);
        return history;
    }

    @GetMapping(path="/getAllBikes")
    public @ResponseBody List<String> getAllBikeNumber() {
         return historyRepository.getAllBikeNumber();
    }

    @GetMapping(path="/getStationByNumber")
    public @ResponseBody Iterable<Stations> getStationByNumber (String stationNumber) {

        Iterable<Stations> station  = stationsRepository.findByStationNumber(stationNumber);
        return station;
    }

    @GetMapping(path="/allStations")
    public @ResponseBody Iterable<Stations> searchAllStations() {
        return stationsRepository.findAll();
    }

    @GetMapping(path="/allHistory")
    public @ResponseBody Iterable<History> getAllHistory() {
        return historyRepository.findAll();
    }


    public void getAllStations() throws IOException {
        String result;
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT,true);
        RestTemplate restTemplate = new RestTemplate();
        result = restTemplate.getForObject("https://api.citybik.es/v2/networks/lodzki-rower-publiczny-lodz",String.class);
        JsonNode arrNode = new ObjectMapper().readTree(result).get("network");
        arrNode = arrNode.get("stations");
        String r = arrNode.toString();
        System.out.println(r);
        List<Station> stations =  objectMapper.readValue(r, new TypeReference<List<Station>>(){});
        for(int i=0;i<stations.size();i++){
            if(!(stations.get(i).getExtra().getBikes()==null)) {
               // System.out.print("Size: "+stations.get(i).getExtra().getBikes().size());
                for(int j=0;j<stations.get(i).getExtra().getBikes().size();j++){
                    System.out.print("Bike: "+stations.get(i).getExtra().getBikes().get(j));
                    System.out.print(" Station: "+stations.get(i).getExtra().getNumber());
                    System.out.println(" Time: "+stations.get(i).getTimestamp());

                    addNewRecord(stations.get(i).getExtra().getBikes().get(j),stations.get(i).getExtra().getNumber(),stations.get(i).getTimestamp());
                }
            }
        }
    }
    public void addAllStations() throws IOException {
        String result;
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT,true);
        RestTemplate restTemplate = new RestTemplate();
        result = restTemplate.getForObject("https://api.citybik.es/v2/networks/lodzki-rower-publiczny-lodz",String.class);
        JsonNode arrNode = new ObjectMapper().readTree(result).get("network");
        arrNode = arrNode.get("stations");
        String r = arrNode.toString();
        List<Station> stations =  objectMapper.readValue(r, new TypeReference<List<Station>>(){});
        for(int i=0;i<stations.size();i++){

                    System.out.print("station number: "+stations.get(i).getName());
                    System.out.print(" Station: "+stations.get(i).getExtra().getNumber());
                    System.out.println(" Time: "+stations.get(i).getTimestamp());

                    addNewStation(stations.get(i).getExtra().getNumber(), stations.get(i).getName(),stations.get(i).getLatitude(), stations.get(i).getLongitude());

        }
    }

    @RequestMapping("/test")
    public String getData() throws IOException {
        String result;
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT,true);

        RestTemplate restTemplate = new RestTemplate();
        result = restTemplate.getForObject("https://api.citybik.es/v2/networks/lodzki-rower-publiczny-lodz",String.class);
        JsonNode arrNode = new ObjectMapper().readTree(result).get("network");
        arrNode = arrNode.get("stations");
        String r = arrNode.toString();

        return r;
    }


}
