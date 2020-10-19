import java.util.*;

public class Intersection {
  /**
   * Default constructor
   */

  private String id;
  private float latitude;
  private float longitude;

  public Intersection(String id, float latitude, float longitude) {
    this.id = id;
    this.latitude = latitude;
    this.longitude = longitude;
  }

  public String toString() {
    return "id : " + id + " {" + latitude + ", " + longitude + "}.";
  }

  public float getLatitude(){
    return latitude;
  }

  public float getLongitude(){
    return longitude;
  }

  public String getId(){
    return id;
  }
}
