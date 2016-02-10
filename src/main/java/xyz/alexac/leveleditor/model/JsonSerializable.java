/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.alexac.leveleditor.model;

import javax.json.JsonObjectBuilder;

/**
 *
 * @author alex-ac
 */
public interface JsonSerializable {
  public JsonObjectBuilder toJSON();
}
