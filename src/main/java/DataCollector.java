import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import core.Station;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataCollector
{
    Map<String, Station> listStation = new HashMap<>();
    String dataFile = "";

    public Map<String, Station> getListStation()
    {
        return listStation;
    }

    public Map<String, Station> fileReader(String path) throws Exception
    {
        File doc = new File(path);
        if(doc.isFile())
        {
            dataFile = doc.getAbsolutePath();
            if (doc.getName().endsWith(".json"))
            {
                getDatesAndDepthFromJson(doc);
            }
            else if (doc.getName().endsWith(".cvs"))
            {
                getDatesAndDepthFromCvs(doc);
            }
        }
        else {
            File[] files = doc.listFiles();
            for(File f : files) {
                fileReader(f.getAbsolutePath());
            }
        }
        return listStation;
    }

    private void getDatesAndDepthFromJson(File doc) throws Exception
    {
        JsonParser parser = new JsonParser();
        JsonArray jsonData = (JsonArray) parser.parse(getJsonFile());
        for(Object it : jsonData)
        {
            JsonObject stationJsonObject = (JsonObject) it;
            String stationName = String.valueOf(stationJsonObject.get("station_name"));
            if(!listStation.containsKey(stationName))
            {
                listStation.put(stationName, new Station(stationName));
            }
            if(doc.getName().startsWith("dates"))
            {
                String date = String.valueOf(stationJsonObject.get("dates"));
                listStation.get(stationName).setDate(date);
            }
            if (doc.getName().startsWith("depth")) {
                String depth = String.valueOf(stationJsonObject.get("depth"));
                listStation.get(stationName).setDepth(depth);
            }
        }
    }

    private void getDatesAndDepthFromCvs(File doc) throws Exception
    {
        String filePath = doc.getAbsolutePath();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        try
        {
            String splitBy = ",";
            String line = "";
            while ((line = reader.readLine()) != null)
            {
                String[] lines = line.split(splitBy, 2);
                for (int i = 0; i < lines.length; i++)
                {
                    if(i % 2 == 0)
                    {
                        String stationName = lines[i];
                        if(!listStation.containsKey(stationName))
                        {
                            listStation.put(stationName, new Station(stationName));
                        }
                        if (doc.getName().startsWith("dates"))
                        {
                            listStation.get(stationName).setDate(lines[i++]);
                        }
                        if(doc.getName().startsWith("depth"))
                        {
                            listStation.get(stationName).setDepth(lines[i++]);
                        }
                    }

                }
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    private String getJsonFile()
    {
        StringBuilder builder = new StringBuilder();
        try
        {
            List<String> lines = Files.readAllLines(Paths.get(dataFile));
            lines.forEach(l -> builder.append(l));
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return builder.toString();
    }
}
