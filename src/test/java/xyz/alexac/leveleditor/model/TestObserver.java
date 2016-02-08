/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.alexac.leveleditor.model;

import java.util.Observable;
import java.util.Observer;

/**
 *
 * @author alex-ac
 */
public class TestObserver implements Observer {
  public boolean updated = false;

  @Override
  public void update(Observable o, Object arg) {
    updated = true;
  }

}
