package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import view.GraphicalSegment;

class GraphicalSegmentTest {
	
	public static int xOriginPixel;
	public static int yOriginPixel;
	public static int xDestPixel;
	public static int yDestPixel;
	public static GraphicalSegment graphicalSegment;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		xOriginPixel = 20;
		yOriginPixel = 25;
		xDestPixel = 56;
		yDestPixel = 129;
		graphicalSegment = new GraphicalSegment(xOriginPixel,yOriginPixel,xDestPixel,yDestPixel);
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testGetXOriginPixel() {
		assertEquals(graphicalSegment.getXOriginPixel(),xOriginPixel+4);
	}

	@Test
	void testGetYOriginPixel() {
		assertEquals(graphicalSegment.getYOriginPixel(),yOriginPixel+4);
	}

	@Test
	void testGetXDestPixel() {
		assertEquals(graphicalSegment.getXDestPixel(),xDestPixel+4);
	}

	@Test
	void testGeYDestPixel() {
		assertEquals(graphicalSegment.geYDestPixel(),yDestPixel+4);
	}

	@Test
	void testGetSetColor() {
		graphicalSegment.setColor(Color.black);
		assertEquals(graphicalSegment.getColor(),Color.black);
		graphicalSegment.setColor(Color.green);
		assertEquals(graphicalSegment.getColor(),Color.green);
	}

}
