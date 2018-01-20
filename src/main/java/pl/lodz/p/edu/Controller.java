package pl.lodz.p.edu;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

@RestController
public class Controller {

    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }

    @RequestMapping("/test")
    public String getData() throws IOException {
        String result;
        String result2;
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT,true);

        RestTemplate restTemplate = new RestTemplate();
//      result = restTemplate.getForObject("https://api.citybik.es/v2/networks/velov",String.class);
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

        JsonNode arrNode = new ObjectMapper().readTree(result2).get("network");
        arrNode = arrNode.get("stations");
        String r = arrNode.toString();

        List<Station> stations =  objectMapper.readValue(r, new TypeReference<List<Station>>(){});
        return stations.get(0).getLatitude() +" "+ stations.get(0).getLongitude();
    }
}
