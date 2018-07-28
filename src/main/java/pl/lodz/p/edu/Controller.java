package pl.lodz.p.edu;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import pl.lodz.p.edu.entity.History;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
public class Controller {
    @Autowired
    private HistoryRepository historyRepository;

    @GetMapping(path="/add")
    public @ResponseBody String addNewRecord (@RequestParam String bikeNumber, @RequestParam String stationNumber, @RequestParam String date) {
        History h = new History();
        h.setBikeNumber(bikeNumber);
        h.setStationNumber(stationNumber);
        h.setDate(date);
        historyRepository.save(h);
        return "saved";
    }

    @GetMapping(path="/getByBikeNumber")
    public @ResponseBody Iterable<History> getHistoryByBikeNumber(String bikeNumber) {
        Iterable<History> history = historyRepository.findByBikeNumber(bikeNumber);

        return history;
    }

    @GetMapping(path="/all")
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



    @RequestMapping("/test")
    public String getData() throws IOException {
        String result;
        String result2;
        String result3;
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT,true);

        RestTemplate restTemplate = new RestTemplate();
     result = restTemplate.getForObject("https://api.citybik.es/v2/networks/lodzki-rower-publiczny-lodz",String.class);
/*     result3 = restTemplate.getForObject("http://demo2317180.mockable.io/",String.class);
        result2 ="{\n" + "  \"network\": {\n" + "    \"company\": [\n" + "      \"JCDecaux\"\n" + "    ],\n" +
                "    \"href\": \"\\/v2\\/networks\\/velov\",\n" + "    \"id\": \"velov\",\n" + "    \"license\": {\n" +
                "      \"name\": \"Open Licence\",\n" +
                "      \"url\": \"https:\\/\\/developer.jcdecaux.com\\/#\\/opendata\\/licence\"\n" + "    },\n" +
                "    \"location\": {\n" + "      \"city\": \"Lyon\",\n" + "      \"country\": \"FR\",\n" +
                "      \"latitude\": 45.764043,\n" + "      \"longitude\": 4.835659\n" + "    },\n" +
                "    \"name\": \"V\\u00e9lo'V\",\n" + "    \"stations\": [\n" + "      {\n" +
                "        \"empty_slots\": 7,\n" + "        \"extra\": {\n" +
                "          \"address\": \"Avenue Antoine Saint Exupery\",\n" + "          \"banking\": true,\n" +
                "          \"bonus\": false,\n" + "          \"last_update\": 1516387897000,\n" +
                "          \"slots\": 15,\n" + "          \"status\": \"OPEN\",\n" + "          \"uid\": 10060\n" +
                "        },\n" + "        \"free_bikes\": 8,\n" +
                "        \"id\": \"b8cf7504ec4791deefaae0a2c18e31b7\",\n" + "        \"latitude\": 45.758805,\n" +
                "        \"longitude\": 4.87889,\n" + "        \"name\": \"10060 - SAINT EXUP\\u00c9RY\",\n" +
                "        \"timestamp\": \"2018-01-19T18:56:56.885000Z\"\n" + "      },\n" + "      {\n" +
                "        \"empty_slots\": 11,\n" + "        \"extra\": {\n" +
                "          \"address\": \"Boulevard Jean XXIII - Angle de la rue Mermoz\",\n" +
                "          \"banking\": true,\n" + "          \"bonus\": false,\n" +
                "          \"last_update\": 1516388019000,\n" + "          \"slots\": 20,\n" +
                "          \"status\": \"OPEN\",\n" + "          \"uid\": 8009\n" + "        },\n" +
                "        \"free_bikes\": 9,\n" + "        \"id\": \"6bc13cd1b76ff880a89b0bb03765fc12\",\n" +
                "        \"latitude\": 45.73648,\n" + "        \"longitude\": 4.869789,\n" +
                "        \"name\": \"8009 - JEAN XXIII \\/ MERMOZ\",\n" +
                "        \"timestamp\": \"2018-01-19T18:56:56.743000Z\"\n" + "      },\n" + "      {\n" +
                "        \"empty_slots\": 1,\n" + "        \"extra\": {\n" +
                "          \"address\": \"Place Meissonier - Angle Sud-Est de la place\",\n" +
                "          \"banking\": true,\n" + "          \"bonus\": false,\n" +
                "          \"last_update\": 1516387844000,\n" + "          \"slots\": 10,\n" +
                "          \"status\": \"OPEN\",\n" + "          \"uid\": 1005\n" + "        },\n" +
                "        \"free_bikes\": 9,\n" + "        \"id\": \"d9e7614049b450ec652c477e8138098d\",\n" +
                "        \"latitude\": 45.766073,\n" + "        \"longitude\": 4.832849,\n" +
                "        \"name\": \"1005 - MEISSONNIER\",\n" +
                "        \"timestamp\": \"2018-01-19T18:56:56.752000Z\"\n" + "      }\n" + " ]\n" + "  }\n" + "}";
*/
        JsonNode arrNode = new ObjectMapper().readTree(result).get("network");
        arrNode = arrNode.get("stations");
        String r = arrNode.toString();

        List<Station> stations =  objectMapper.readValue(r, new TypeReference<List<Station>>(){});
        for(int i=0;i<stations.size();i++){
//            System.out.println(stations.get(i).getName());
        }
        return r;
    }


}
