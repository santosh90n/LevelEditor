/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.alexac.leveleditor.model;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;
import javax.json.Json;
import javax.json.JsonWriter;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**

 @author alex-ac
 */
public class OctreeTest {

  public OctreeTest() {
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

  private String serrialize(Octree tree) {
    ByteArrayOutputStream s = new ByteArrayOutputStream();
    JsonWriter writer = Json.createWriter(s);
    writer.writeObject(tree.toJSON().build());
    writer.close();
    return s.toString();
  }

  private void assertBoundingBox(Map<Vector3D, Integer> values, Box boundingBox,
                                 Octree tree) {
    for (int x = boundingBox.origin.x; x < boundingBox.origin.x
            + boundingBox.size.x; ++x) {
      for (int y = boundingBox.origin.y; y < boundingBox.origin.y
              + boundingBox.size.y; ++y) {
        for (int z = boundingBox.origin.z; z < boundingBox.origin.z
                + boundingBox.size.z; ++z) {
          Vector3D position = new Vector3D(x, y, z);
          try {
            if (values.containsKey(position)) {
              assertEquals(values.get(position).intValue(), tree.get(x, y, z));
            } else {
              assertEquals(0, tree.get(x, y, z));
            }
          } catch (AssertionError e) {
            System.out.println("Position: " + position);
            System.out.println("Tree: " + serrialize(tree));
            throw e;
          }
        }
      }
    }
  }

  @Test
  public void testInitialState() {
    System.out.println("testInitialState");
    Octree instance = new Octree();
    Box boundingBox = instance.getBoundingBox();
    assertEquals(new Box(-1, -1, -1, 2, 2, 2), boundingBox);
    Map<Vector3D, Integer> values = new HashMap<>();
    assertBoundingBox(values, boundingBox, instance);
    assertEquals(0, instance.get(50, 300, 600));
    assertEquals(0, instance.get(-52, 76, -85));
  }

  @Test
  public void testInsertValue() {
    System.out.println("testInsertValue");
    Octree instance = new Octree();
    instance.set(0, 0, 0, 1);
    Box boundingBox = instance.getBoundingBox();
    Map<Vector3D, Integer> values = new HashMap<>();
    values.put(new Vector3D(0, 0, 0), 1);
    assertEquals(new Box(-1, -1, -1, 2, 2, 2), boundingBox);
    assertBoundingBox(values, boundingBox, instance);
  }

  @Test
  public void testExpandBoundingBox() {
    System.out.println("testExpandBoundingBox");
    Octree instance = new Octree();
    instance.set(7, 7, 7, 1);
    Box boundingBox = instance.getBoundingBox();
    assertEquals(new Box(-8, -8, -8, 16, 16, 16), boundingBox);
    Map<Vector3D, Integer> values = new HashMap<>();
    values.put(new Vector3D(7, 7, 7), 1);
    assertBoundingBox(values, boundingBox, instance);
  }

  @Test
  public void testCollapseBoundingBox() {
    System.out.println("testCollapseBoundingBox");
    Octree instance = new Octree();
    instance.set(7, 7, 7, 1);
    Box boundingBox = instance.getBoundingBox();
    assertEquals(new Box(-8, -8, -8, 16, 16, 16), boundingBox);
    instance.set(7, 7, 7, 0);
    boundingBox = instance.getBoundingBox();
    assertEquals(new Box(-1, -1, -1, 2, 2, 2), boundingBox);
  }
}
