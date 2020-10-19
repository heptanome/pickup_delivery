package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class Tour {
	public Map map;
	public SetOfRequests setOfRequests;
	private PropertyChangeSupport support;
	
	public Tour() {
		support = new PropertyChangeSupport(this);
		this.map = null;
	}
	
	public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }
 
    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        support.removePropertyChangeListener(pcl);
    }

	
	public void setMap(String mapPath) {
		Map oldMap = this.map;
		MapParser mp = new MapParser(mapPath);
		this.map = mp.loadMap();
        //support.firePropertyChange("map", oldMap, this.map);
		support.firePropertyChange("map", "e", "d");
		System.out.println("INSIDE SETMAP TOUR");
    }
}
