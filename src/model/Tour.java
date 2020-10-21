package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class Tour {
	public CityMap map;
	public SetOfRequests setOfRequests;
	private PropertyChangeSupport support;

	public Tour() {
		// observable object
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
		CityMap oldMap = this.map;
		MapParser mp = new MapParser(mapPath);
		this.map = mp.loadMap();
		// signal the observers the map has changed
		support.firePropertyChange("updateMap", oldMap, this.map);
	}

	public void setRequests(String reqPath) {
		SetOfRequests oldReq = this.setOfRequests;
		RequestParser rp = new RequestParser(reqPath);
		this.setOfRequests = rp.loadRequests();
		// signal the observers the set of requests has changed
		support.firePropertyChange("updateRequests", oldReq, this.setOfRequests);
	}
}
