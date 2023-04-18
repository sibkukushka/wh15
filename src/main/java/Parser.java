import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import core.Connections;
import core.ContainerStations;
import core.Line;
import core.Station;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class Parser
{
    private Document doc;
    private List<Line> lines;
    private ContainerStations containerStations;
    private DataCollector collector;

    public Parser(String url) throws Exception
    {
        doc = Jsoup.connect(url).get();
        lines = new ArrayList<>();
        containerStations = new ContainerStations();
    }

    public List<Line> getLines() {
        return lines;
    }

    public ContainerStations getContainerStations() {
        return containerStations;
    }

    public DataCollector getCollector() {
        return collector;
    }

    public JsonArray parseLine()
    {
        Elements lineList = doc.getElementsByAttributeValueStarting("class",
                "js-metro-line t-metrostation-list-header t-icon-metroln ln");
        JsonArray linesObjArray = new JsonArray();
        for(Element e : lineList)
        {
            Line line = new Line(e.attr("data-line"), e.ownText());
            linesObjArray.add(line.getJsonLine());
            lines.add(line);
        }
        return linesObjArray;
    }

    public JsonObject parseStation()
    {
        Elements dataList = doc.getElementsByClass("js-metro-stations t-metrostation-list-table");
        JsonObject stationsObj = new JsonObject();
        for(Element e : dataList)
        {
            JsonArray stationsArray = new JsonArray();
            Elements stationsList = e.getElementsByClass("name");
            for(Element se : stationsList)
            {
                stationsArray.add(se.text());
                stationsObj.add(e.attr("data-line"), stationsArray);
                containerStations.addStation(new Station(se.text(), e.attr("data-line")));
            }
        }
        return stationsObj;
    }

    public void parseConnection()
    {
        Elements dataList = doc.getElementsByClass("js-metro-stations t-metrostation-list-table");
        for(Element e : dataList)
        {
            Elements conList = e.select("p:has(span[title])");
            for (Element conEl : conList)
            {
                String station = conEl.text();
                int indexSpace = station.lastIndexOf(";");
                String stationName = station.substring(indexSpace + 1).trim();

                Connections stationConnection = new Connections();
                stationConnection.addStation(new Station(stationName, e.attr("data-line")));

                Elements conSpanList = conEl.select("span[title]");

                for(Element conSpanEl : conSpanList)
                {
                    String line = conSpanEl.attr("class");
                    int indexDash = line.lastIndexOf("-");
                    String numberLine = line.substring(indexDash + 1);

                    String text = conSpanEl.attr("title");
                    int indexQuote = text.indexOf("«");
                    int lastIndexQuote = text.lastIndexOf("»");
                    String station1 = text.substring(indexQuote + 1, lastIndexQuote);

                    stationConnection.addStation(new Station(station1, numberLine));
                }
                containerStations.addConnection(stationConnection);
            }
        }
    }

    public JsonArray writeConnectionInJson(TreeSet<Connections> connections)
    {
        JsonArray conArray = new JsonArray();
        for(Connections stCon : connections)
        {
            JsonArray conObjArray = new JsonArray();
            for(Station s : stCon.getConnectionStations())
            {
                JsonObject conObj = new JsonObject();
                conObj.addProperty("line", s.getNumberLine());
                conObj.addProperty("station", s.getName());
                conObjArray.add(conObj);
            }
            conArray.add(conObjArray);
        }
        return conArray;
    }
}