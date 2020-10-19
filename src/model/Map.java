package model;
import java.util.List;

public class Map {
  /**
   * Default constructor
   */
  private List<Intersection> intersections;
  private List<Segment> segments;
  private float minLong;
  private float minLat;
  private float maxLong;
  private float maxLat;

  public Map(List<Intersection> intersections, List<Segment> segments) {
    this.intersections = intersections;
    this.segments = segments;
  }

  public String toString() {
    String message = "";
    message += "Intersections :\n";
    for (Intersection i : intersections) {
      message += i + "\n";
    }
    message += "\n\nSegments :\n";
    for (Segment s : segments) {
      message += s + "\n";
    }
    return message;
  }

  public List<Intersection> getInstersections(){
    return intersections;
  }

  public List<Segment> getSegments(){
    return segments;
  }

  public float getMinLong(){
    return minLong;
  }

  public float getMinLat(){
    return minLat;
  }

  public float getMaxLong(){
    return maxLong;
  }

  public float getMaxLat(){
    return maxLat;
  }
}
