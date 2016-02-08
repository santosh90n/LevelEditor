/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.alexac.leveleditor.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JComponent;
import xyz.alexac.leveleditor.model.LevelElement;
import xyz.alexac.leveleditor.model.ProjectSettings;
import xyz.alexac.leveleditor.model.Voxel;

/**
 *
 * @author alex-ac
 */
public class ObjectView extends JComponent implements Observer, MouseListener,
                                                      MouseMotionListener,
                                                      MouseWheelListener {
  public static final int MODE_GRID = 0;
  public static final int MODE_VOXEL = 1;

  private ProjectSettings settings_ = null;
  private double scale_ = 1.0;
  private Point origin_ = null;
  private LevelElement element_ = null;
  private boolean tracking_ = false;
  private Point lastMousePoint_ = null;
  private int mode_ = ObjectView.MODE_GRID;
  private int xy_ = 0;

  public Point gridCoord(Point p) {
    double x = p.x - origin_.x;
    double y = p.y - origin_.y;

    double w = settings_.getTileWidth() / 2 * scale_;
    double h = settings_.getTileHeight() / 2 * scale_;

    double l = Math.sqrt(w * w + h * h);

    y *= -w / h;

    double f = Math.PI / 4;
    double x1 = Math.cos(f) * x + Math.sin(f) * y;
    double y1 = -Math.sin(f) * x + Math.cos(f) * y;

    x = x1 / l;
    y = y1 / l;

    int tileX = (int) Math.floor(x);
    int tileY = (int) Math.floor(y);

    return new Point(tileX, tileY);
  }

  public Point tileOrigin(Point p) {
    double w = settings_.getTileWidth() / 2 * scale_;
    double h = settings_.getTileHeight() / 2 * scale_;

    return new Point(
            origin_.x + (int) w * (p.x - p.y),
            origin_.y - (int) h * (p.x + p.y));
  }

  public ObjectView() {
    addMouseListener(this);
    addMouseWheelListener(this);
  }

  public ObjectView(ProjectSettings settings) {
    settings_ = settings;
    settings_.addObserver(this);
    addMouseListener(this);
    addMouseWheelListener(this);
  }

  public void setSettings(ProjectSettings settings) {
    if (settings_ != null) {
      settings_.deleteObserver(this);
    }
    settings_ = settings;
    if (settings_ != null) {
      settings_.addObserver(this);
      repaint();
    }
  }

  @Override
  public void setBounds(int x, int y, int width, int height) {
    int w = getWidth();
    int h = getHeight();
    if (origin_ == null) {
      origin_ = new Point(width / 2, height / 2);
    } else {
      int dw = width - w;
      int dh = height - h;
      origin_.x += dw / 2;
      origin_.y += dh / 2;
    }
    super.setBounds(x, y, width, height);
  }

  public void paintGrid(Graphics g) {
    int tileWidth = (int) (settings_.getTileWidth() * scale_);
    int tileHeight = (int) (settings_.getTileHeight() * scale_);
    int w = getWidth();
    int h = getHeight();

    if (element_ != null) {
      g.setColor(Color.GREEN);
      for (Iterator<Point> it = element_.getOccupiedTiles(); it.hasNext();) {
        Point p = tileOrigin(it.next());
        g.drawLine(p.x, p.y, p.x - tileWidth / 2, p.y - tileHeight / 2);
        g.drawLine(p.x - tileWidth / 2, p.y - tileHeight / 2, p.x, p.y -
                                                                   tileHeight);
        g.drawLine(p.x, p.y - tileHeight, p.x + tileWidth / 2, p.y -
                                                               tileHeight / 2);
        g.drawLine(p.x + tileWidth / 2, p.y - tileHeight / 2, p.x, p.y);
      }
    }
  }

  @Override
  public void paint(Graphics g) {
    if (settings_ == null) {
      return;
    }
    int tileWidth = (int) (settings_.getTileWidth() * scale_);
    int tileHeight = (int) (settings_.getTileHeight() * scale_);
    int w = getWidth();
    int h = getHeight();
    if (origin_ == null) {
      origin_ = new Point(w / 2, h / 2);
    }

    g.setColor(Color.DARK_GRAY);
    g.fillRect(0, 0, w, h);

    if (element_ != null && element_.getImage() != null) {
      Image image = element_.getImage();
      int imageWidth = image.getWidth(null);
      int imageHeight = image.getHeight(null);
      g.drawImage(image, origin_.x - (int) (imageWidth / 2 * scale_),
                  origin_.y - (int) (imageHeight * scale_),
                  origin_.x + (int) (imageWidth / 2 * scale_),
                  origin_.y,
                  0, 0, imageWidth, imageHeight, null);
    }

    g.setColor(Color.WHITE);

    int y = origin_.y;
    while (y > 0) {
      y -= tileHeight;
    }

    while (y < h) {
      int x = origin_.x - tileWidth / 2;
      while (x > 0) {
        x -= tileWidth;
      }

      while (x < w) {
        g.drawLine(x, y + tileHeight / 2, x + tileWidth / 2, y);
        g.drawLine(x + tileWidth / 2, y, x + tileWidth, y + tileHeight / 2);
        g.drawLine(x + tileWidth, y + tileHeight / 2, x + tileWidth / 2, y +
                                                                         tileHeight);
        g.drawLine(x + tileWidth / 2, y + tileHeight, x, y + tileHeight / 2);
        x += tileWidth;
      }
      y += tileHeight;
    }

    switch (mode_) {
      case ObjectView.MODE_GRID:
        paintGrid(g);
        break;
      case ObjectView.MODE_VOXEL:
        paintVoxel(g);
        break;
      default:
        break;
    }

    g.setColor(Color.RED);
    g.drawLine(origin_.x, origin_.y, origin_.x + tileWidth / 4, origin_.y -
                                                                tileHeight / 4);
    g.setColor(Color.GREEN);
    g.drawLine(origin_.x, origin_.y, origin_.x - tileWidth / 4, origin_.y -
                                                                tileHeight / 4);
    g.setColor(Color.BLUE);
    g.drawLine(origin_.x, origin_.y, origin_.x, origin_.y - tileHeight / 2);

    g.setColor(Color.YELLOW);
    g.fillOval(origin_.x - 4, origin_.y - 4, 8, 8);

    String scale = new DecimalFormat("#0.0#").format(scale_, new StringBuffer(),
                                                     new FieldPosition(0)).
           toString();
    g.drawString("Scale: " + scale, w - 100, h - 20);
  }

  @Override
  public void update(Observable o, Object arg) {
    repaint();
  }

  void setElement(LevelElement element) {
    if (element_ != null) {
      element_.deleteObserver(this);
    }
    element_ = element;
    if (element_ != null) {
      element_.addObserver(this);
    }
    repaint();
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    switch (mode_) {
      case ObjectView.MODE_GRID:
        Point p = gridCoord(e.getPoint());
        if (element_ != null) {
          element_.toggleOccupied(p);
        }
        break;
      case ObjectView.MODE_VOXEL:
        Voxel v = voxelCoord(e.getPoint());
        if (element_ != null) {
          element_.toggleOccupied(v);
        }
        break;
      default:
        break;
    }
  }

  @Override
  public void mousePressed(MouseEvent e) {
    tracking_ = true;
    lastMousePoint_ = e.getPoint();
    addMouseMotionListener(this);
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    tracking_ = false;
    removeMouseMotionListener(this);
  }

  @Override
  public void mouseEntered(MouseEvent e) {
  }

  @Override
  public void mouseExited(MouseEvent e) {
  }

  @Override
  public void mouseDragged(MouseEvent e) {
    Point point = e.getPoint();
    int dx = point.x - lastMousePoint_.x;
    int dy = point.y - lastMousePoint_.y;
    lastMousePoint_ = point;
    origin_.x += dx;
    origin_.y += dy;
    repaint();
  }

  @Override
  public void mouseMoved(MouseEvent e) {
  }

  private void paintVoxel(Graphics g) {
    Point p = tileOrigin(new Point(xy_, xy_ + 1));

    int tileWidth = (int) (settings_.getTileWidth() * scale_);
    int tileHeight = (int) (settings_.getTileHeight() * scale_);
    int w = getWidth();
    int h = getHeight();

    int x = p.x;
    while (x > 0) {
      x -= tileWidth;
    }

    g.setColor(Color.CYAN);
    while (x < w) {
      for (int y = p.y; y > 0; y -= tileHeight) {
        g.drawLine(x, y, x, y - tileHeight);
        g.drawLine(x, y - tileHeight, x + tileWidth, y - tileHeight);
        g.drawLine(x + tileWidth, y - tileHeight, x + tileWidth, y);
        g.drawLine(x + tileWidth, y, x, y);
      }
      x += tileWidth;
    }

    g.setColor(Color.GREEN);
    if (element_ != null) {
      for (Iterator<Voxel> it = element_.getOccupiedVoxels(); it.hasNext();) {
        Voxel v = it.next();
        System.out.println(v);
        Point vp = tileOrigin(v);
        System.out.println(vp);
        g.drawLine(vp.x, vp.y, vp.x, vp.y - tileHeight);
        g.drawLine(vp.x, vp.y, vp.x - tileWidth / 2, vp.y - tileHeight / 2);
        g.drawLine(vp.x, vp.y, vp.x + tileWidth / 2, vp.y - tileHeight / 2);
        g.drawLine(vp.x - tileWidth / 2, vp.y - tileHeight / 2, vp.x -
                                                                tileWidth / 2,
                   vp.y - 3 * tileHeight / 2);
        g.drawLine(vp.x + tileWidth / 2, vp.y - tileHeight / 2, vp.x +
                                                                tileWidth / 2,
                   vp.y - 3 * tileHeight / 2);
        g.drawLine(vp.x, vp.y - tileHeight, vp.x - tileWidth / 2, vp.y - 3 *
                                                                         tileHeight /
                                                                         2);
        g.drawLine(vp.x, vp.y - tileHeight, vp.x + tileWidth / 2, vp.y - 3 *
                                                                         tileHeight /
                                                                         2);
        g.drawLine(vp.x, vp.y - 2 * tileHeight, vp.x - tileWidth / 2, vp.y - 3 *
                                                                             tileHeight /
                                                                             2);
        g.drawLine(vp.x, vp.y - 2 * tileHeight, vp.x + tileWidth / 2, vp.y - 3 *
                                                                             tileHeight /
                                                                             2);
      }
    }

    g.setColor(Color.YELLOW);
    g.drawString("x + y: " + xy_, w - 100, h - 40);
  }

  void setMode(int i) {
    mode_ = i;
    repaint();
  }

  private Voxel voxelCoord(Point p) {
    Point zOrigin = new Point(xy_, xy_ + 1);
    if (zOrigin.y < p.y) {
      Point planePoint = gridCoord(p);
      return new Voxel(planePoint.x, planePoint.y, -1);
    }

    double tileWidth = settings_.getTileWidth() * scale_;
    double tileHeight = settings_.getTileHeight() * scale_;

    int offset = (int) Math.ceil((p.x - zOrigin.x) / tileWidth);
    int z = (int) Math.ceil((p.y - zOrigin.y) / tileHeight);

    return new Voxel(xy_ + offset, xy_ - offset, z);
  }

  private Point tileOrigin(Voxel v) {
    Point p = tileOrigin(new Point(v.x, v.y));
    double offset = v.z * settings_.getTileHeight() * scale_;
    p.y -= (int) offset;
    return p;
  }

  @Override
  public void mouseWheelMoved(MouseWheelEvent e) {
    if (mode_ == ObjectView.MODE_VOXEL && e.isShiftDown()) {
      xy_ += e.getWheelRotation();
      repaint();
    } else {
      double scale = scale_ + e.getWheelRotation() * 0.01;
      if (scale <= 0) {
        scale = 0.1;
      }
      if (scale_ != scale) {
        scale_ = scale;
        repaint();
      }
    }
  }
}
