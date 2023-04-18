import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import core.Connections;
import core.MetroMap;
import core.Station;

import java.util.List;
import java.util.Map;
import java.util.TreeSet;

public class Main
{
    public static void main(String[] args) throws Exception
    {
        String url = "https://skillbox-java.github.io";
        Parser parser = new Parser(url);

        JsonArray linesArray = parser.parseLine();
        JsonObject stationsObj = parser.parseStation();
        parser.parseConnection();
        List<Station> stations = parser.getContainerStations().getStations();
        TreeSet<Connections> connections = parser.getContainerStations().getConnections();
        JsonArray conArray = parser.writeConnectionInJson(connections);
        MetroMap metro = new MetroMap(stationsObj, linesArray, conArray);

        JsonCreator jsonCreator = new JsonCreator();
        jsonCreator.writeInJsonFile(metro.getMetroObj(), "metro.json");
        JsonReader jsonReader = new JsonReader();
        jsonReader.detAmountOfStations("metro.json");

        DataCollector collector = new DataCollector();
//        collector.fileReader("data");
        Map<String, Station> listStation = collector.getListStation();

        setParamHasCon(connections, listStation);
        setParamLineName(stations, linesArray,listStation);

        JsonObject stationObject = new JsonObject();
        JsonArray stationsArray = new JsonArray();
        for(Map.Entry<String, Station> entry : listStation.entrySet())
        {
            JsonObject stationObj = new JsonObject();
            if(entry.getValue().getName() != null)
            {
                stationObj.addProperty("name", entry.getValue().getName());
            }
            if(entry.getValue().getLineName() != null)
            {
                stationObj.addProperty("line", entry.getValue().getLineName());
            }
            if(entry.getValue().getDate() != null)
            {
                stationObj.addProperty("date", entry.getValue().getDate());
            }
            if(entry.getValue().getDepth() != null)
            {
                stationObj.addProperty("depth", entry.getValue().getDepth());
            }
            stationObj.addProperty("hasConnection", entry.getValue().isHasConnection());
            stationsArray.add(stationObj);
        }
        stationObject.add("stations", stationsArray);
        JsonCreator jsonCreator1 = new JsonCreator();
        jsonCreator1.writeInJsonFile(stationObject, "stations.json");
    }

    private static void setParamHasCon(TreeSet<Connections> connections, Map<String, Station> listStations)
    {
        listStations.keySet().forEach(k -> {
            for(Connections con : connections)
            {
                for(Station st : con.getConnectionStations())
                {
                    if(st.getName().equals(k))
                    {
                        listStations.get(k).setHasConnection(true);
                    }
                }
            }
        });
    }

    private static void setParamLineName(List<Station> stations, JsonArray linesArray, Map<String, Station> listStations)
    {
        listStations.keySet().forEach(k -> {
            for(Station st : stations)
            {
                if(st.getName().equals(k))
                {
                    linesArray.forEach(line -> {
                        JsonObject lineJsonObj = (JsonObject) line;
                        if(lineJsonObj.get("number").equals(st.getNumberLine()))
                        {
                            listStations.get(k).setLineName(String.valueOf(lineJsonObj.get("name")));
                        }
                    });
                }
            }
        });
    }
}
