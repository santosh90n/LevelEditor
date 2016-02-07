/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.alexac.leveleditor.model;

import java.awt.Image;
import java.awt.Point;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Observable;

/**

 @author alex-ac
 */
public class LevelElement extends Observable {
  private Image image_ = null;
  private final HashSet<Point> occupiedTiles_;
  private final HashSet<Voxel> occupiedVoxels_;
  
  public LevelElement() {
    occupiedTiles_ = new HashSet<>();
    occupiedVoxels_ = new HashSet<>();
  }
  
  public void setImage(Image image) {
    image_ = image;
    setChanged();
    notifyObservers();
  }
  
  public Image getImage() {
    return image_;
  }

  public void toggleOccupied(Point p) {
    if (occupiedTiles_.contains(p))
      occupiedTiles_.remove(p);
    else
      occupiedTiles_.add(p);
    setChanged();
    notifyObservers();
  }
  
  public void toggleOccupied(Voxel v) {
    if (occupiedVoxels_.contains(v))
      occupiedVoxels_.remove(v);
    else
      occupiedVoxels_.add(v);
    setChanged();
    notifyObservers();
  }
  
  public Iterator<Point> getOccupiedTiles() {
    return occupiedTiles_.iterator();
  }

  public Iterator<Voxel> getOccupiedVoxels() {
    return occupiedVoxels_.iterator();
  }
}
