import com.google.gson.JsonObject;

import java.io.FileWriter;

public class JsonCreator
{
    public JsonCreator() {}

    public void writeInJsonFile(JsonObject object, String path) throws Exception
    {
        FileWriter file = new FileWriter(path);
        file.write(object.toString());
        file.flush();
        file.close();
    }
}
