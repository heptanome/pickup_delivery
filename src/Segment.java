
import java.util.*;

public class Segment extends Object {
  /**
   * Default constructor
   */

  private String idOrigin;
  private String idDestination;
  private String name;
  private float length;

  public Segment(String idOrigin, String idDestination, String name, float length) {
    this.idOrigin = idOrigin;
    this.idDestination = idDestination;
    this.name = name;
    this.length = length;
  }

  public String toString() {
    return "Name : " + name + ", origin : " + idOrigin + ", destination : " + idDestination
        + " (length : " + length + ").";
  }

  public String getIdOrigin(){
    return idOrigin;
  }

  public String getIdDestination(){
    return idDestination;
  }
}
