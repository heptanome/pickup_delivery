package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Intersection;
import view.graphical.GraphicalPoint;
import view.graphical.GraphicalSegment;

class GraphicalSegmentTest {
	
	public static GraphicalSegment graphicalSegment;
	public static GraphicalPoint originGP;
	public static GraphicalPoint destinationGP;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {

		Intersection originI = new Intersection("25319255", 3, 11);
		Intersection destinationI  = new Intersection( "1370403192", 10 , 4);
		originGP = new GraphicalPoint(originI, 1 , 20 , 3 , 18);
		destinationGP = new GraphicalPoint(destinationI, 1 , 20 , 3 , 18);
		graphicalSegment = new GraphicalSegment(originGP, destinationGP);
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}
	
	@Test
	void testGetters() {
		assertEquals(graphicalSegment.getXOriginPixel(),originGP.getXPixel() +4);
		assertEquals(graphicalSegment.getYOriginPixel(),originGP.getYPixel() +4);
		assertEquals(graphicalSegment.getXDestPixel(),destinationGP.getXPixel() +4);
		assertEquals(graphicalSegment.geYDestPixel(),destinationGP.getYPixel() +4);
	}
	
	@Test
	void testGetOrigin() {
		assertEquals(graphicalSegment.getOrigin(),"25319255");
	}
	
	@Test
	void testGetDestination() {
		assertEquals(graphicalSegment.getDestination(),"1370403192");
	}

	@Test
	void testGetSetColor() {
		graphicalSegment.setColor(Color.black);
		assertEquals(graphicalSegment.getColor(),Color.black);
		graphicalSegment.setColor(Color.green);
		assertEquals(graphicalSegment.getColor(),Color.green);
	}
	
	@Test
	void testPath() {
		int pathInit = 0;
		int path1 = 134;
		int path2 = 34;
		assertEquals(graphicalSegment.getOnPath(),pathInit);
		graphicalSegment.setOnPath(path1);
		assertEquals(graphicalSegment.getOnPath(),path1);
		graphicalSegment.setOnPath(path2);
		assertEquals(graphicalSegment.getOnPath(),path2);
	}

}
