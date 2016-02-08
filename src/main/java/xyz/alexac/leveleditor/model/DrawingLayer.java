/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.alexac.leveleditor.model;

import java.util.Observable;

/**
 *
 * @author alex-ac
 */
public class DrawingLayer extends Observable {
  private boolean collisions_ = false;
  private String name_ = "layer";
  private boolean visible_ = true;
  private double z_ = 0.0;

  public DrawingLayer() {
  }

  DrawingLayer(String name, double z) {
    z_ = z;
    name_ = name;
  }

  public boolean isCollisions() {
    return collisions_;
  }

  public boolean isVisible() {
    return visible_;
  }

  public double getZ() {
    return z_;
  }

  public String getName() {
    return name_;
  }

  public void setCollisions(boolean enabled) {
    collisions_ = enabled;
    setChanged();
    notifyObservers();
  }

  public void setVisible(boolean visible) {
    visible_ = visible;
    setChanged();
    notifyObservers();
  }

  public void setZ(double z) {
    z_ = z;
    setChanged();
    notifyObservers();
  }

  public void setName(String name) {
    name_ = name;
    setChanged();
    notifyObservers();
  }

  @Override
  public String toString() {
    return "xyz.alexac.leveleditor.model.DrawingLayer[name=" + name_ +
           ",z=" + z_ +
           ",visible=" + visible_ +
           ",collisions=" + collisions_ + "]";
  }
}
