package model;

/**
 * A request is represented by two intersections, a delivery point and a pickup
 * point. On top of that, we store the time it takes to pick up the package
 * (pickupDuration) and the time it takes to deliver it (deliveryDuration).
 */
public class Request {
    private Intersection delivery;
    private Intersection pickup;
    private int deliveryDuration;
    private int pickupDuration;
    private int numero;

    /**
     * Constructor
     * @param delivAdd
     * 			Intersection representing the delivery address 
     * @param pickupAddr
     * 			Intersection representing the pickup address 
     * @param delivDur
     * 			time in minutes needed by the delivery man to deliver the item
     * @param pickupDur
     * 			time in minutes needed by the delivery man to pick the item
     */
    public Request(Intersection delivAdd, Intersection pickupAddr, int delivDur, int pickupDur, int numeroReq) {
      delivery = delivAdd;
      pickup = pickupAddr;
      deliveryDuration = delivDur;
      pickupDuration = pickupDur;
      numero = numeroReq;
    }
    /**
     * Constructor
     * @param delivAdd
     * 			Intersection representing the delivery address 
     * @param pickupAddr
     * 			Intersection representing the pickup address 
     * @param delivDur
     * 			time in minutes needed by the delivery man to deliver the item
     * @param pickupDur
     * 			time in minutes needed by the delivery man to pick the item
     */
    public Request(Intersection delivAdd, Intersection pickupAddr, int delivDur, int pickupDur) {
      delivery = delivAdd;
      pickup = pickupAddr;
      deliveryDuration = delivDur;
      pickupDuration = pickupDur;
      numero = 0;
    }

    /**
	 * Get the pickup intersection of a request
	 * @return the pickup intersection of a request
	 */
    public Intersection getPickup() {
      return pickup;
    }

    /**
	 * Get the address of a pickup intersection of a request
	 * @return the address of a pickup intersection of a request
	 */
    public String getPickupAddress() {
      return pickup.getNumber();
    }

    /**
	 * Get the delivery intersection of a request
	 * @return the delivery intersection of a request
	 */
    public Intersection getDelivery() {
      return delivery;
    }

    /**
	 * Get the address of a delivery intersection of a request
	 * @return the address of a delivery intersection of a request
	 */
    public String getDeliveryAddress() {
      return delivery.getNumber();
    }

    /**
     * Get the time the delivery man needs to deliver the item
     * @return time in minutes the delivery man needs to deliver the item
     */
    public int getDeliveryDuration() {
      return deliveryDuration;
    }

    /**
     * Get the time the delivery man needs to pick up the item
     * @return time in minutes the delivery man needs to pick up the item
     */
    public int getPickupDuration() {
      return pickupDuration;
    }

    /**
	 * Convert information of a Request to a String
	 */
    public String toString() {
      return "From " + pickup.getNumber() + " (" + pickupDuration + ") to " + delivery.getNumber() + " ("
          + deliveryDuration + ").";
    }

    /**
     * Set the pickup address of the request
     * @param newPickup
     * 			new intersection for the pickup address
     */
    public void setPickupAddress(Intersection newPickup){
      pickup = newPickup;
    }

    /**
     * Set the delivery address of the request
     * @param newDelivery
     * 			new intersection for the delivery address
     */
    public void setDeliveryAddress(Intersection newDelivery){
      delivery = newDelivery;
    }

    /**
     * Set the pickup duration of the request
     * @param duration
     * 			Time in minutes the delivery man needs to pick up the item
     */
    public void setPickupDuration(int duration){
      pickupDuration = duration;
    }

    /**
     * Set the delivery duration of the request
     * @param duration
     * 			Time in minutes the delivery man needs to deliver the item
     */
    public void setDeliveryDuration(int duration){
      deliveryDuration = duration;
    }
    
    public void setNumero(int val) {
    	this.numero=val;
    }
    
    public int getNumero() {
    	return numero;
    }
}
