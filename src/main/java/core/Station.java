package core;

public class Station implements Comparable<Station>
{
    private String numberLine;
    private String name;
    private String lineName;
    private String date;
    private String depth;
    private boolean hasConnection;


    public Station(String name)
    {
        this.name = name;
    }

    public Station(String numberLine, String name, String lineName, String date, String depth, boolean hasConnection)
    {
        this.numberLine = numberLine;
        this.name = name;
        this.lineName = lineName;
        this.date = date;
        this.depth = depth;
        this.hasConnection = hasConnection;
    }

    public Station(String name, String numberLine)
    {
        this.name = name;
        this.numberLine = numberLine;
    }

    public String getNumberLine() {
        return numberLine;
    }

    public void setNumberLine(String numberLine) {
        this.numberLine = numberLine;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDepth() {
        return depth;
    }

    public void setDepth(String depth) {
        this.depth = depth;
    }

    public boolean isHasConnection() {
        return hasConnection;
    }

    public void setHasConnection(boolean hasConnection) {
        this.hasConnection = hasConnection;
    }

    @Override
    public String toString()
    {
        return name;
    }

    @Override
    public int compareTo(Station station)
    {
        int lineComp = numberLine.compareToIgnoreCase(station.getNumberLine());
        if(lineComp != 0) {
            return lineComp;
        }
        return name.compareToIgnoreCase(station.getName());
    }

    @Override
    public boolean equals(Object obj) {
        return compareTo((Station) obj) == 0;
    }
}