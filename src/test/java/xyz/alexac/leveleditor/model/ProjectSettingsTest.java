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
public class ProjectSettingsTest {

  public ProjectSettingsTest() {
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
   * Test of setTileWidth method, of class ProjectSettings.
   */
  @Test
  public void testSetTileWidth() {
    System.out.println("setTileWidth");
    int w = 0;
    ProjectSettings instance = new ProjectSettings();
    TestObserver o = new TestObserver();

    assertEquals(false, instance.hasChanged());
    instance.addObserver(o);
    assertEquals(false, o.updated);
    instance.setTileWidth(w);
    assertEquals(true, o.updated);
    assertEquals(w, instance.getTileWidth());
  }

  /**
   * Test of setTileHeight method, of class ProjectSettings.
   */
  @Test
  public void testSetTileHeight() {
    System.out.println("setTileHeight");
    int h = 0;
    ProjectSettings instance = new ProjectSettings();
    TestObserver o = new TestObserver();

    assertEquals(false, instance.hasChanged());
    instance.addObserver(o);
    assertEquals(false, o.updated);
    instance.setTileHeight(h);
    assertEquals(true, o.updated);
    assertEquals(h, instance.getTileHeight());
  }

}
