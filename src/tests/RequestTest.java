package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Intersection;
import model.Request;

class RequestTest {

	public String deliveryAddress;
	public float deliveryAddressLatitude;
	public float deliveryAddressLongitude;
	public String pickupAddress;
	public float pickupAddressLatitude;
	public float pickupAddressLongitude;
	public int deliveryDuration;
	public int pickupDuration;
	public Intersection deliveryIntersection;
	public Intersection pickupIntersection;
	public Request request;
	
	@BeforeEach
	void setUp() throws Exception {
		deliveryAddress = "55444215";
		deliveryAddressLatitude = (float) 12.56;
		deliveryAddressLongitude = (float) 15.786;
		deliveryIntersection = new Intersection(deliveryAddress,deliveryAddressLatitude,deliveryAddressLongitude);
		
		pickupAddress = "21992645";
		pickupAddressLatitude = (float) 18.98;
		pickupAddressLongitude = (float) 45.3;
		pickupIntersection = new Intersection(pickupAddress,pickupAddressLatitude,pickupAddressLongitude);
		
		deliveryDuration = 480;
		pickupDuration = 360;
		
		request = new Request(deliveryIntersection,pickupIntersection,deliveryDuration,pickupDuration);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void getPickup() {
		assertEquals(pickupIntersection,request.getPickup());
	}
	
	@Test
	void testGetPickupAdress() {
		assertEquals(pickupAddress, request.getPickupAddress());
	}
	
	@Test
	void getDelivery() {
		assertEquals(deliveryIntersection,request.getDelivery());
	}
	
	@Test
	void testGetDeliveryAddress() {
		assertEquals(deliveryAddress, request.getDeliveryAddress());
	}
	
	@Test
	void getDeliveryDuration() {
		assertEquals(deliveryDuration, request.getDeliveryDuration());
	}
	
	@Test
	void getPickupDuration() {
		assertEquals(pickupDuration, request.getPickupDuration());
	}

	@Test
	void testToString() {
		assertEquals(request.toString(), "From " + pickupAddress + " (" + pickupDuration + ") to " + deliveryAddress + " ("
		        + deliveryDuration + ").");
	}
}
