package model;

/**
 *
 */
public class Request {
  private String deliveryAddress;
  private String pickupAddress;
  private int deliveryDuration;
  private int pickupDuration;

  public Request(String delivAdd, String pickupAddr, int delivDur, int pickupDur) {
    deliveryAddress = delivAdd;
    pickupAddress = pickupAddr;
    deliveryDuration = delivDur;
    pickupDuration = pickupDur;
  }

  public String toString() {
    return "From " + pickupAddress + " (" + pickupDuration + ") to " + deliveryAddress + " ("
        + deliveryDuration + ").";
  }

  public String getPickupAddress(){
    return pickupAddress;
  }

  public String getDeliveryAddress(){
    return deliveryAddress;
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
