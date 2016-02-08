/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.alexac.leveleditor.model;

import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.Iterator;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author alex-ac
 */
public class LevelElementTest {

  public LevelElementTest() {
  }

  @BeforeClass
  public static void setUpClass() {
  }

  @AfterClass
  public static void tearDownClass() {
  }

  @Before
  public void setUp() {
  }

  @After
  public void tearDown() {
  }

  /**
   * Test of setImage method, of class LevelElement.
   */
  @Test
  public void testSetImage() {
    System.out.println("setImage");
    Image image = new BufferedImage(256, 256, BufferedImage.TYPE_4BYTE_ABGR);
    LevelElement instance = new LevelElement();
    TestObserver o = new TestObserver();

    instance.addObserver(o);
    instance.setImage(image);
    assertEquals(true, o.updated);
    assertEquals(image, instance.getImage());
  }

  /**
   * Test of toggleOccupied method, of class LevelElement.
   */
  @Test
  public void testToggleOccupied_Point() {
    System.out.println("toggleOccupied");
    Point p = new Point(0, 0);
    LevelElement instance = new LevelElement();
    TestObserver o = new TestObserver();

    instance.addObserver(o);
    instance.toggleOccupied(p);

    assertEquals(true, o.updated);
    for (Iterator<Point> it = instance.getOccupiedTiles(); it.hasNext();) {
      assertEquals(p, it.next());
      assertEquals(false, it.hasNext());
    }
    o.updated = false;

    instance.toggleOccupied(new Point(1, 1));
    assertEquals(true, o.updated);
    boolean hasFirst = false;
    boolean hasSecond = false;
    for (Iterator<Point> it = instance.getOccupiedTiles(); it.hasNext();) {
      Point t = it.next();
      if (!hasFirst && p.equals(t)) {
        hasFirst = true;
      } else if (!hasSecond && new Point(1, 1).equals(t)) {
        hasSecond = true;
      } else {
        fail("Unexpected point: " + t);
      }
    }
    assertEquals(true, hasFirst);
    assertEquals(true, hasSecond);
    o.updated = false;

    instance.toggleOccupied(new Point(1, 1));
    assertEquals(true, o.updated);
    for (Iterator<Point> it = instance.getOccupiedTiles(); it.hasNext();) {
      assertEquals(p, it.next());
      assertEquals(false, it.hasNext());
    }
  }

  /**
   * Test of toggleOccupied method, of class LevelElement.
   */
  @Test
  public void testToggleOccupied_Voxel() {
    System.out.println("toggleOccupied");
    Voxel v = new Voxel();
    LevelElement instance = new LevelElement();
    TestObserver o = new TestObserver();

    instance.addObserver(o);
    instance.toggleOccupied(v);

    assertEquals(true, o.updated);
    for (Iterator<Voxel> it = instance.getOccupiedVoxels(); it.hasNext();) {
      assertEquals(v, it.next());
      assertEquals(false, it.hasNext());
    }
    o.updated = false;

    instance.toggleOccupied(new Voxel(1, 1, 1));
    assertEquals(true, o.updated);
    boolean hasFirst = false;
    boolean hasSecond = false;
    for (Iterator<Voxel> it = instance.getOccupiedVoxels(); it.hasNext();) {
      Voxel t = it.next();
      if (!hasFirst && v.equals(t)) {
        hasFirst = true;
      } else if (!hasSecond && new Voxel(1, 1, 1).equals(t)) {
        hasSecond = true;
      } else {
        fail("Unexpected voxel: " + t);
      }
    }
    assertEquals(true, hasFirst);
    assertEquals(true, hasSecond);
    o.updated = false;

    instance.toggleOccupied(new Voxel(1, 1, 1));
    assertEquals(true, o.updated);
    for (Iterator<Voxel> it = instance.getOccupiedVoxels(); it.hasNext();) {
      assertEquals(v, it.next());
      assertEquals(false, it.hasNext());
    }
  }

}
