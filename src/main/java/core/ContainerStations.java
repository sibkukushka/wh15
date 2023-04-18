package core;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class ContainerStations
{
    private final List<Station> stations;
    private final TreeSet<Connections> connections;

    public ContainerStations()
    {
        stations = new ArrayList<>();
        connections = new TreeSet<>();
    }

    public List<Station> getStations() {
        return stations;
    }

    public TreeSet<Connections> getConnections() {
        return connections;
    }

    public void addStation(Station station)
    {
        stations.add(station);
    }

    public void addConnection(Connections stationCon)
    {
        if(!containStation(stationCon)) {
            connections.add(stationCon);
        }
    }

    private boolean containStation(Connections stationCon)
    {
        for(Connections con : connections)
        {
            for(Station st : con.getConnectionStations())
            {
                for(Station stInner : stationCon.getConnectionStations())
                {
                    if(st.getName().equals(stInner.getName()) &&
                            st.getNumberLine().equals(stInner.getNumberLine()))
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}