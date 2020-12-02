package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.Rule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;

import exceptions.WrongFileFormatException;
import modele.Face;
import modele.Point;
import modele.Reader;
import modele.Repere;

class ModelTest {
	private Point point;
	private Face face;
	private Repere repere;
	private Reader reader;

	@SuppressWarnings("deprecation")
	@Rule
    public ExpectedException thrown = ExpectedException.none();
	
	@BeforeEach
	public void init() {
		this.point = new Point(3.0, 2.0, 5.0);

		List<Point> pointsList = new ArrayList<Point>();
		pointsList.add(this.point);
		pointsList.add(new Point(4.0, 1.0, 6.0));
		pointsList.add(new Point(1.0, 1.0, 4.0));
		this.face = new Face(pointsList);

		List<Face> faceList = new ArrayList<Face>();
		faceList.add(this.face);
		this.repere = new Repere(pointsList, faceList);
	}

	@AfterEach
	public void clean() {
		this.point = null;
		this.face = null;
		this.repere = null;
	}

	@Test
	public void testPointGettersAndSetters() {
		assertEquals(3.0, this.point.getX());
		assertEquals(2.0, this.point.getY());
		assertEquals(5.0, this.point.getZ());
		this.point.setX(4.0);
		this.point.setY(4.0);
		this.point.setZ(4.0);
		assertEquals(4.0, this.point.getX());
		assertEquals(4.0, this.point.getY());
		assertEquals(4.0, this.point.getZ());
	}

	@Test
	public void testFaceAverageZ() {
		assertEquals(5.0, this.face.average());
	}

	@Test
	public void testTranslation() {
		this.repere.translation(50, 50);
		assertEquals(53.0, this.repere.getPointsList().get(0).getX());
		assertEquals(52.0, this.repere.getPointsList().get(0).getY());
	}

	@Test
	public void testScaling() {
		this.repere.scaling(3);
		assertEquals(9.0, this.repere.getPointsList().get(0).getX());
		assertEquals(6.0, this.repere.getPointsList().get(0).getY());
		assertEquals(15.0, this.repere.getPointsList().get(0).getZ());
	}
	
	@Test
	public void testInitReader() {
		assertThrows(WrongFileFormatException.class, () -> this.reader = new Reader(""));
		assertThrows(WrongFileFormatException.class, () -> this.reader = new Reader("ply\n" + 
				"format ascii 1.0\n" + 
				"element vertex 2\n" + 
				"property float x\n" + 
				"property float y\n" + 
				"property float z\n" + 
				"element face 1\n" + 
				"property list uchar int vertex_index\n" + 
				"end_header\n" + 
				"0 0 \n" + 
				"0 0 100\n" + 
				"4 0 1 0 1"));
	}

	@Test
	public void faceSorting() {

	}
}