package core;

import com.google.gson.JsonObject;

public class Line implements Comparable<Line>
{
    private final JsonObject jsonLine;
    private final String name;
    private final String number;

    public Line(String name, String number)
    {
        jsonLine = new JsonObject();
        jsonLine.addProperty("name", name);
        jsonLine.addProperty("number", number);
        this.name = name;
        this.number = number;
    }

    public JsonObject getJsonLine() {
        return jsonLine;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    @Override
    public int compareTo(Line line)
    {
        return number.compareToIgnoreCase(line.getNumber());
    }

    @Override
    public boolean equals(Object obj) {
        return compareTo((Line) obj) == 0;
    }

    @Override
    public String toString() {
        return name;
    }
}
