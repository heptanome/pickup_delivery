
import java.util.*;

/**
 * 
 */
public class Request {

    private String deliveryAddress;
    private String pickupAddress;
    private int deliveryDuration
    private int pickupDuration;
    
    public Request(String delivAdd, String pickupAdd, int delivDur, int pickupDur) {
    	deliveryAddress = delivAdd;
    	pickupAddress = pickupAddr;
    	deliveryDuration = delivDur;
    	pickupDuration = pickupDur;
    }

    public String toString() {
    	return "From "+pickupAddress+"("+pickupDuration+") to "+deliveryAddress+"("+deliveryDuration+")."
    }

}