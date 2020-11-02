package model;

/**
 *
 */
public class Request {
  private Intersection delivery;
  private Intersection pickup;
  private int deliveryDuration;
  private int pickupDuration;

  public Request(Intersection delivAdd, Intersection pickupAddr, int delivDur, int pickupDur) {
    delivery = delivAdd;
    pickup = pickupAddr;
    deliveryDuration = delivDur;
    pickupDuration = pickupDur;
  }

  public String toString() {
    return "From " + pickup.getNumber() + " (" + pickupDuration + ") to " + delivery.getNumber() + " ("
        + deliveryDuration + ").";
  }

  public Intersection getPickup(){
    return pickup;
  }
  
  public String getPickupAddress() {
	  return pickup.getNumber();
  }

  public Intersection getDelivery(){
    return delivery;
  }
  
  public String getDeliveryAddress() {
	  return delivery.getNumber();
  }
  
  public int getDeliveryDuration(){
	    return deliveryDuration;
  }
  
  public int getPickupDuration(){
	    return pickupDuration;
  }

  public void setPickupAddress(String s){
    pickupAddress = s;
  }

  public void setDeliveryAddress(String s){
    deliveryAddress = s;
  }

  public void setPickupDuration(int i){
    pickupDuration = i;
  }

  public void setDeliveryDuration(int i){
   deliveryDuration = i;
  }
}
