import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileReader;

public class JsonReader
{
    public void detAmountOfStations(String path) throws Exception
    {
        JsonParser parser = new JsonParser();
        Object object = parser.parse(new FileReader(path));
        JsonObject metroJsonObject = (JsonObject) object;
        JsonObject stationsObj = (JsonObject) metroJsonObject.get("stations");
        stationsObj.keySet().forEach(k -> {
            JsonArray stationsArray = (JsonArray) stationsObj.get(k);
            System.out.println("Линия " + k + ". Количество станций: " + stationsArray.size() + ".");
        });
    }
}
