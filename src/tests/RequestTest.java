package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Request;

class RequestTest {

	public String deliveryAddress;
	public String pickupAddress;
	public int deliveryDuration;
	public int pickupDuration;
	public Request request;
	
	@BeforeEach
	void setUp() throws Exception {
		deliveryAddress = "55444215";
		pickupAddress = "21992645";
		deliveryDuration = 480;
		pickupDuration = 360;
		request = new Request(deliveryAddress,pickupAddress,deliveryDuration,pickupDuration);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testGetPickupAdress() {
		assertEquals(request.getPickupAddress(), pickupAddress);
	}
	
	@Test
	void testGetDeliveryAddress() {
		assertEquals(request.getDeliveryAddress(), deliveryAddress);
	}

	@Test
	void testToString() {
		assertEquals(request.toString(), "From " + pickupAddress + " (" + pickupDuration + ") to " + deliveryAddress + " ("
		        + deliveryDuration + ").");
	}
}
