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

    public Request(Intersection delivAdd, Intersection pickupAddr, int delivDur, int pickupDur) {
      delivery = delivAdd;
      pickup = pickupAddr;
      deliveryDuration = delivDur;
      pickupDuration = pickupDur;
    }

    public Intersection getPickup() {
      return pickup;
    }

    public String getPickupAddress() {
      return pickup.getNumber();
    }

    public Intersection getDelivery() {
      return delivery;
    }

    public String getDeliveryAddress() {
      return delivery.getNumber();
    }

    public int getDeliveryDuration() {
      return deliveryDuration;
    }

    public int getPickupDuration() {
      return pickupDuration;
    }

    public String toString() {
      return "From " + pickup.getNumber() + " (" + pickupDuration + ") to " + delivery.getNumber() + " ("
          + deliveryDuration + ").";
    }

    public void setPickupAddress(Intersection s){
      pickup = s;
    }

    public void setDeliveryAddress(Intersection s){
      delivery = s;
    }

    public void setPickupDuration(int i){
      pickupDuration = i;
    }

    public void setDeliveryDuration(int i){
      deliveryDuration = i;
    }
}
