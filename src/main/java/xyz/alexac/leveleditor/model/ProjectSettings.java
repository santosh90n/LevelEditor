/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.alexac.leveleditor.model;

import java.util.Observable;

/**

 @author alex-ac
 */
public class ProjectSettings extends Observable {
  private int tileWidth_;
  private int tileHeight_;
  public ProjectSettings() {
    tileWidth_ = 256;
    tileHeight_ = 128;
  }
  
  public void setTileWidth(int w) {
    tileWidth_ = w;
    setChanged();
    notifyObservers();
  }
  public void setTileHeight(int h) {
    tileHeight_ = h;
    setChanged();
    notifyObservers();
  }
  public int getTileWidth() {
    return tileWidth_;
  }
  public int getTileHeight() {
    return tileHeight_;
  }
}
