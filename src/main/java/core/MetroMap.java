package core;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class MetroMap
{
    private final JsonObject metroObj;

    public MetroMap(JsonObject stationsObj, JsonArray lineArray, JsonArray connectionsArray)
    {
        metroObj = new JsonObject();
        metroObj.add("stations", stationsObj);
        metroObj.add("lines", lineArray);
        metroObj.add("connections", connectionsArray);
    }

    public JsonObject getMetroObj() {
        return metroObj;
    }
}
