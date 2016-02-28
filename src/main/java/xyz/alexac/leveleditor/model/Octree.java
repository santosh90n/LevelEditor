/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.alexac.leveleditor.model;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;

/**

 @author alex-ac
 */
public class Octree
        implements JsonSerializable {
  private interface Node {
    public int get(long i);

    public boolean isLeaf();

    public Node expand();

    public boolean canCollapseTo(int index);

    public Node collapseTo(int index);

    public boolean collapse();

    public Node set(long index, int depth, int value);

    public JsonValue toJSON();
  }

  private class Branch
          implements Node {
    private final Node[] nodes = new Node[8];

    private Branch(int value) {
      for (int i = 0; i < 8; ++i) {
        nodes[i] = new Leaf(value);
      }
    }

    @Override
    public int get(long i) {
      return i < 0 ? 0 : nodes[(int) i & 7].get(i >> 3);
    }

    @Override
    public boolean isLeaf() {
      return false;
    }

    @Override
    public Node expand() {
      Node[] result = new Node[8];
      for (int i = 0; i < 8; ++i) {
        Branch b = new Branch(0);
        result[i] = b;
        b.nodes[~i & 7] = nodes[i];
      }
      for (int i = 0; i < 8; ++i) {
        nodes[i] = result[i];
      }
      return this;
    }

    @Override
    public boolean canCollapseTo(int index) {
      for (int i = 0; i < 8; ++i) {
        if (i == index) {
          continue;
        }
        if (!nodes[i].isLeaf() || nodes[i].get(0) != 0) {
          return false;
        }
      }
      return true;
    }

    @Override
    public Node collapseTo(int index) {
      return nodes[index];
    }

    @Override
    public boolean collapse() {
      for (int i = 0; i < 8; ++i) {
        if (!nodes[i].canCollapseTo(~i & 7)) {
          return false;
        }
      }
      for (int i = 0; i < 8; ++i) {
        nodes[i] = nodes[i].collapseTo(~i & 7);
      }
      return true;
    }

    @Override
    public Node set(long index, int depth, int value) {
      nodes[(int) index & 7] = nodes[(int) index & 7].set(
              index >> 3, depth - 1, value);
      for (int i = 0; i < 8; ++i) {
        if (!nodes[i].isLeaf() || nodes[i].get(0) != value) {
          return this;
        }
      }
      return nodes[0];
    }

    @Override
    public JsonValue toJSON() {
      JsonArrayBuilder builder = Json.createArrayBuilder();
      for (int i = 0; i < 8; ++i) {
        builder.add(nodes[i].toJSON());
      }
      return builder.build();
    }
  }

  private class Leaf
          implements Node {
    private final int value;

    private Leaf(int value) {
      this.value = value;
    }

    @Override
    public int get(long i) {
      return i < 0 ? 0 : value;
    }

    @Override
    public boolean isLeaf() {
      return true;
    }

    @Override
    public Node expand() {
      return new Branch(value).expand();
    }

    @Override
    public boolean canCollapseTo(int index) {
      return value == 0;
    }

    @Override
    public Node collapseTo(int index) {
      return this;
    }

    @Override
    public boolean collapse() {
      return value == 0;
    }

    @Override
    public Node set(long index, int depth, int value) {
      if (depth == 0) {
        return new Leaf(value);
      }
      Branch b = new Branch(this.value);
      return b.set(index, depth, value);
    }

    @Override
    public JsonValue toJSON() {
      return Json.createObjectBuilder().add("v", value).build();
    }
  }

  private int depth = 1;
  private Node root = new Leaf(0);

  public Octree() {
  }

  public Box getBoundingBox() {
    int width = 1 << depth;
    return new Box(-width / 2, -width / 2, -width / 2, width, width, width);
  }

  private long getIndex(int x, int y, int z) {
    x += 1 << (depth - 1);
    y += 1 << (depth - 1);
    z += 1 << (depth - 1);
    if (x < 0 || y < 0 || z < 0 || x > 1 << depth || y > 1 << depth || z > 1
            << depth) {
      return -1;
    }

    long index = 0;
    for (int i = 0; i < depth; ++i) {
      index <<= 1;
      index |= z & 1;
      z >>= 1;
      index <<= 1;
      index |= y & 1;
      y >>= 1;
      index <<= 1;
      index |= x & 1;
      x >>= 1;
    }

    return index;
  }

  private int getDepth(int x, int y, int z) {
    x = Math.abs(x);
    y = Math.abs(y);
    z = Math.abs(z);
    int depth = 0;
    while (x != 0 || y != 0 || z != 0) {
      ++depth;
      x >>= 1;
      y >>= 1;
      z >>= 1;
    }
    return depth + 1;
  }

  public int get(int x, int y, int z) {
    return root.get(getIndex(x, y, z));
  }

  public int get(Vector3D position) {
    return get(position.x, position.y, position.z);
  }

  public void set(int x, int y, int z, int value) {
    if (root.get(getIndex(x, y, z)) == value) {
      return;
    }

    while (getDepth(x, y, z) > depth) {
      root = root.expand();
      ++depth;
    }

    root = root.set(getIndex(x, y, z), depth, value);
    while (depth > 1 && root.collapse()) {
      --depth;
    }
  }

  public void set(Vector3D position, int value) {
    set(position.x, position.y, position.z, value);
  }

  @Override
  public JsonObjectBuilder toJSON() {
    return Json.createObjectBuilder()
            .add("depth", depth)
            .add("root", root.toJSON());
  }
}
