import java.util.List;
import java.util.Date;
import java.text.SimpleDateFormat;

/**
 *
 */
public class Tour {
  private String idDepot;
  private Date departureTime;
  private List<Request> requests;

  public Tour(String idDepot, Date departure, List<Request> req) {
    this.idDepot = idDepot;
    this.departureTime = departure;
    this.requests = req;
  }

  public String toString() {
	SimpleDateFormat format = new SimpleDateFormat("HH:MM:ss");
	String departureString = format.format(departureTime);
	//pour une raison qui m'échappe, le date to string ne fonctionne pas
    String message = "Departure at " + departureString + " from " + idDepot + "\nRequests :\n";
    for(Request r : requests) {
    	message += r + "\n";
    }
    return message;
  }
}
