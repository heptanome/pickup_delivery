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
	private static String idOrigin;
	private static String idDest;
	public static GraphicalSegment graphicalSegment;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		idOrigin = "idOrigin";
		idDest = "idDest";
		xOriginPixel = 20;
		yOriginPixel = 25;
		xDestPixel = 56;
		yDestPixel = 129;
		graphicalSegment = new GraphicalSegment(idOrigin, idDest, xOriginPixel,yOriginPixel,xDestPixel,yDestPixel);
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}
	
	@Test
	void testOrigin() {
		assertEquals(graphicalSegment.getOrigin(),idOrigin);
	}
	
	@Test
	void testDestination() {
		assertEquals(graphicalSegment.getDestination(),idDest);
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
