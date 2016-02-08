/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.alexac.leveleditor.model;

import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author alex-ac
 */
public class VoxelTest {

  public VoxelTest() {
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
   * Test of equals method, of class Voxel.
   */
  @Test
  public void testEquals() {
    System.out.println("equals");
    Voxel instance = new Voxel();
    assertEquals(false, instance.equals(null));
    assertEquals(true, instance.equals(instance));
    assertEquals(false, instance.equals(new Voxel(1, 1, 1)));
  }

  /**
   * Test of hashCode method, of class Voxel.
   */
  @Test
  public void testHashCode() {
    System.out.println("hashCode");
    Voxel instance = new Voxel();
    assertEquals(97 * 97 * 97 * 7, instance.hashCode());
    assertEquals(97 * (97 * (97 * 7 + 1) + 1) + 1, new Voxel(1, 1, 1).hashCode());
  }

  /**
   * Test of toString method, of class Voxel.
   */
  @Test
  public void testToString() {
    System.out.println("toString");
    Voxel instance = new Voxel();
    String expResult = "xyz.alexac.leveleditor.model.Voxel[x=0,y=0,z=0]";
    String result = instance.toString();
    assertEquals(expResult, result);
  }

}
