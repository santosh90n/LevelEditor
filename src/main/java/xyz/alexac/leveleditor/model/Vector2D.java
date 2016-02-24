/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.alexac.leveleditor.model;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

/**

 @author alex-ac
 */
public class Vector2D
        implements JsonSerializable,
                   BaseVector2D {
  public class ExprVector2D
          implements BaseVector2D {
    public final double x;
    public final double y;

    public ExprVector2D(double x, double y) {
      this.x = x;
      this.y = y;
    }

    public Vector2D toInteger() {
      return new Vector2D((int) Math.ceil(x), (int) Math.ceil(y));
    }

    public ExprVector2D add(double x, double y) {
      return new ExprVector2D(this.x + x, this.y + y);
    }

    public ExprVector2D add(BaseVector2D v) {
      return add(v.getX(), v.getY());
    }

    public ExprVector2D multiply(double x, double y) {
      return new ExprVector2D(this.x * x, this.y * y);
    }

    public ExprVector2D multiply(BaseVector2D v) {
      return multiply(v.getX(), v.getY());
    }

    public ExprVector2D multiply(double s) {
      return multiply(s, s);
    }

    public ExprVector2D subtract(double x, double y) {
      return new ExprVector2D(this.x - x, this.y - y);
    }

    public ExprVector2D subtract(BaseVector2D v) {
      return subtract(v.getX(), v.getY());
    }

    public ExprVector2D divide(double x, double y) {
      return new ExprVector2D(this.x / x, this.y / y);
    }

    public ExprVector2D divide(BaseVector2D v) {
      return divide(v.getX(), v.getY());
    }

    public ExprVector2D divide(double s) {
      return divide(s, s);
    }

    public ExprVector2D rotate(double a) {
      return new ExprVector2D(
              x * Math.cos(a) - y * Math.sin(a),
              x * Math.sin(a) + y * Math.cos(a));
    }

    public ExprVector2D negate() {
      return new ExprVector2D(-x, -y);
    }

    @Override
    public double getX() {
      return x;
    }

    @Override
    public double getY() {
      return y;
    }

    @Override
    public String toString() {
      return "ExprVector2D(" + x + ", " + y + ")";
    }
  };

  public final int x;
  public final int y;

  @Override
  public double getX() {
    return x;
  }

  @Override
  public double getY() {
    return y;
  }

  public ExprVector2D rotate(double a) {
    return expr().rotate(a);
  }

  public ExprVector2D add(double x, double y) {
    return expr().add(x, y);
  }

  public ExprVector2D add(BaseVector2D v) {
    return expr().add(v);
  }

  public BaseVector2D negate() {
    return new Vector2D(-x, -y);
  }

  public ExprVector2D multiply(double x, double y) {
    return expr().multiply(x, y);
  }

  public ExprVector2D multiply(BaseVector2D v) {
    return expr().multiply(v);
  }

  public ExprVector2D multiply(double s) {
    return expr().multiply(s);
  }

  public ExprVector2D subtract(double x, double y) {
    return expr().subtract(x, y);
  }

  public ExprVector2D subtract(BaseVector2D v) {
    return expr().subtract(v);
  }

  public ExprVector2D divide(double x, double y) {
    return expr().divide(x, y);
  }

  public ExprVector2D divide(BaseVector2D v) {
    return expr().divide(v);
  }

  public ExprVector2D divide(double s) {
    return expr().divide(s);
  }

  private ExprVector2D expr() {
    return new ExprVector2D(x, y);
  }

  public Vector2D() {
    x = 0;
    y = 0;
  }

  public Vector2D(int x, int y) {
    this.x = x;
    this.y = y;
  }

  @Override
  public JsonObjectBuilder toJSON() {
    return Json.createObjectBuilder()
            .add("x", x)
            .add("y", y);
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o == null) {
      return false;
    }
    if (!(o instanceof Vector2D)) {
      return false;
    }
    Vector2D other = (Vector2D) o;
    return x == other.x && y == other.y;
  }

  @Override
  public String toString() {
    return "Vector2D(" + x + "," + y + ")";
  }

  static Vector2D fromJSON(JsonObject object) {
    return new Vector2D(object.getInt("x"), object.getInt("y"));
  }
}
