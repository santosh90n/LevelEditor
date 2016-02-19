/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.alexac.leveleditor.model;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import javax.imageio.ImageIO;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

/**
 *
 * @author alex-ac
 */
public class Block extends Observable implements JsonSerializable {
  private String name = "block";
  private BufferedImage defaultImage = null;
  private final Map<String, BufferedImage> themedImages = new TreeMap<>();
  private final List<Voxel> voxels = new ArrayList<>();
  private Vector2D offset = new Vector2D();

  public String getName() {
    return name;
  }

  public void setName(String name) {
    if (this.name == name) {
      return;
    }
    this.name = name;
    setChanged();
    notifyObservers("name");
  }

  public BufferedImage getDefaultImage() {
    return defaultImage;
  }

  public void setDefaultImage(BufferedImage image) {
    if (image == defaultImage) {
      return;
    }
    defaultImage = image;
    setChanged();
    notifyObservers("defaultImage");
  }

  public Set<String> getThemes() {
    return themedImages.keySet();
  }

  public BufferedImage getImage(String theme) {
    if (themedImages.containsKey(theme)) {
      return themedImages.get(theme);
    }
    return getDefaultImage();
  }

  public void removeTheme(String theme) {
    if (!themedImages.containsKey(theme)) {
      return;
    }
    themedImages.remove(theme);
    setChanged();
    notifyObservers("themedImages");
  }

  public void setTheme(String theme, BufferedImage image) {
    if (themedImages.containsKey(theme) &&
        themedImages.get(theme).equals(image)) {
      return;
    }

    themedImages.put(theme, image);
    setChanged();
    notifyObservers("themedImages");
    if (defaultImage == null) {
      setDefaultImage(image);
    }
  }

  public Vector2D getOffset() {
    return offset;
  }

  public void setOffset(Vector2D offset) {
    if (offset == null || this.offset.equals(offset)) {
      return;
    }
    this.offset = offset;
    setChanged();
    notifyObservers("offset");
  }

  public List<Voxel> getVoxels() {
    return new ArrayList<>(voxels);
  }

  public void setVoxel(Voxel v) {
    if (!voxels.contains(v)) {
      return;
    }
    for (Voxel v1 : voxels) {
      if (v1.doesIntersects(v)) {
        return;
      }
    }
    voxels.add(v);
    setChanged();
    notifyObservers("voxels");
  }

  public int getVoxelsCount() {
    return voxels.size();
  }

  public Voxel getVoxel(int i) {
    if (voxels.size() >= i) {
      return voxels.get(i);
    }
    return null;
  }

  private String encodeImage(BufferedImage image) throws IOException {
    ByteArrayOutputStream byteArrayStream = new ByteArrayOutputStream();
    OutputStream base64Stream = Base64.getEncoder().wrap(byteArrayStream);
    OutputStream gzipStream = new GZIPOutputStream(base64Stream);
    ImageIO.write(image, "png", gzipStream);
    gzipStream.close();
    return byteArrayStream.toString();
  }

  private static BufferedImage decodeImage(String data) throws IOException {
    ByteArrayInputStream byteArrayStream = new ByteArrayInputStream(data.
                         getBytes());
    InputStream base64Stream = Base64.getDecoder().wrap(byteArrayStream);
    InputStream gzipStream = new GZIPInputStream(base64Stream);
    BufferedImage image = ImageIO.read(gzipStream);
    gzipStream.close();
    return image;
  }

  @Override
  public JsonObjectBuilder toJSON() {
    JsonObjectBuilder themedImages = Json.createObjectBuilder();
    for (Map.Entry<String, BufferedImage> entry : this.themedImages.entrySet()) {
      try {
        themedImages.add(entry.getKey(), encodeImage(entry.getValue()));
      } catch (IOException ex) {
        Logger.getLogger(Block.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
    JsonArrayBuilder voxels = Json.createArrayBuilder();
    for (Voxel v : this.voxels) {
      voxels.add(v.toJSON());
    }

    JsonObjectBuilder builder = Json.createObjectBuilder()
                      .add("name", name)
                      .add("offset", offset.toJSON())
                      .add("themedImages", themedImages)
                      .add("voxels", voxels);
    try {
      builder.add("defaultImage", encodeImage(defaultImage));
    } catch (IOException ex) {
      Logger.getLogger(Block.class.getName()).log(Level.SEVERE, null, ex);
    }
    return builder;
  }

  static Block fromJSON(JsonObject object) {
    Block block = new Block();
    block.setName(object.getString("name"));
    block.setOffset(Vector2D.fromJSON(object.getJsonObject("offset")));
    JsonObject themedImages = object.getJsonObject("themedImages");
    try {
      block.setDefaultImage(decodeImage(object.getString("defaultImage")));
    } catch (IOException ex) {
      Logger.getLogger(Block.class.getName()).log(Level.SEVERE, null, ex);
    }
    for (String theme : themedImages.keySet()) {
      try {
        block.setTheme(theme, decodeImage(themedImages.getString(theme)));
      } catch (IOException ex) {
        Logger.getLogger(Block.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
    JsonArray voxels = object.getJsonArray("voxels");
    for (int i = 0; i < voxels.size(); ++i) {
      block.setVoxel(Voxel.fromJSON(voxels.getJsonObject(i)));
    }
    return block;
  }
}
