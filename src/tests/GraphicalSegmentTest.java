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
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {

		Intersection originI = new Intersection("25319255", 3, 11);
		Intersection destinationI  = new Intersection( "1370403192", 10 , 4);
		GraphicalPoint originGP = new GraphicalPoint(originI, 1 , 20 , 3 , 18);
		GraphicalPoint destinationGP = new GraphicalPoint(destinationI, 1 , 20 , 3 , 18);
		graphicalSegment = new GraphicalSegment(originGP, destinationGP);
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}
	
	@Test
	void testOrigin() {
		assertEquals(graphicalSegment.getOrigin(),"25319255");
	}
	
	@Test
	void testDestination() {
		assertEquals(graphicalSegment.getDestination(),"1370403192");
	}
	
	@Test
	void testGetXOriginPixel() {
		assertEquals(graphicalSegment.getXOriginPixel(),94);
	}

	@Test
	void testGetYOriginPixel() {
		assertEquals(graphicalSegment.getYOriginPixel(),437);
	}

	@Test
	void testGetXDestPixel() {
		assertEquals(graphicalSegment.getXDestPixel(),389);
	}

	@Test
	void testGeYDestPixel() {
		assertEquals(graphicalSegment.geYDestPixel(),63);
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
