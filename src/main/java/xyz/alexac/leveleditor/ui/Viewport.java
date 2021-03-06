/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.alexac.leveleditor.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JComponent;
import xyz.alexac.leveleditor.model.Project;
import xyz.alexac.leveleditor.model.Vector2D;
import xyz.alexac.leveleditor.model.Vector3D;

/**

 @author alex-ac
 */
public class Viewport
        extends JComponent
        implements Observer,
                   MouseListener,
                   MouseMotionListener,
                   MouseWheelListener,
                   KeyListener {
  public final static int MODE_DEFAULT = 0;
  public final static int MODE_VOXEL = 1;
  public final static int MODE_TILES = 2;

  private Vector2D origin = null;
  private Project project = null;
  private int gridWidth = 256;
  private int gridHeight = 128;
  private Vector2D lastPoint = null;
  private float scale = 1.0f;
  private Vector3D cursor = new Vector3D();

  private ViewportController controller = null;

  public Viewport() {
    addMouseListener(this);
    addMouseMotionListener(this);
    addMouseWheelListener(this);
    addKeyListener(this);
    setFocusable(true);
    setRequestFocusEnabled(true);
  }

  public void setController(ViewportController controller) {
    if (this.controller == controller) {
      return;
    }
    this.controller = controller;
    repaint();
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    requestFocusInWindow();
  }

  @Override
  public void mouseDragged(MouseEvent e) {
    origin = origin.add(e.getX(), e.getY()).subtract(lastPoint).toInteger();
    lastPoint = new Vector2D(e.getX(), e.getY());
    repaint();
  }

  @Override
  public void mouseEntered(MouseEvent e) {
  }

  @Override
  public void mouseExited(MouseEvent e) {
  }

  @Override
  public void mouseMoved(MouseEvent e) {
  }

  @Override
  public void mousePressed(MouseEvent e) {
    this.lastPoint = new Vector2D(e.getX(), e.getY());
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    this.lastPoint = null;
  }

  @Override
  public void mouseWheelMoved(MouseWheelEvent e) {
    float scale = this.scale + e.getWheelRotation() * 0.01f;
    if (scale < 0.1f) {
      scale = 0.1f;
    }
    if (scale > 4) {
      scale = 4;
    }
    if (this.scale != scale) {
      this.scale = scale;
      repaint();
    }
  }

  @Override
  public void setBounds(int x, int y, int width, int height) {
    if (origin == null) {
      origin = new Vector2D(width / 2, height / 2);
    } else if (getWidth() != width || getHeight() != height) {
      origin = origin.add(width / 2, height / 2)
              .subtract(getWidth() / 2, getHeight() / 2).toInteger();
    }
    super.setBounds(x, y, width, height);
  }

  public void setProject(Project project) {
    if (this.project == project) {
      return;
    }
    if (this.project != null) {
      project.deleteObserver(this);
    }
    this.project = project;
    if (this.project != null) {
      project.addObserver(this);
      gridWidth = project.getGridWidth();
      gridHeight = project.getGridHeight();
      repaint();
    }
  }

  private void paintMainGrid(Graphics g) {
    final int gridWidth = (int) (this.gridWidth * scale);
    final int gridHeight = (int) (this.gridHeight * scale);

    int startX = origin.x;
    while (startX > 0) {
      startX -= gridWidth;
    }
    int startY = origin.y;
    while (startY > 0) {
      startY -= gridHeight;
    }

    g.setColor(Color.WHITE);
    for (int x = startX; x < getWidth() + gridWidth / 2; x += gridWidth) {
      for (int y = startY; y < getHeight() + gridHeight / 2 && y <= origin.y;
              y += gridHeight) {
        g.drawLine(x - gridWidth / 2, y - gridHeight / 2,
                   x + gridWidth / 2, y + gridHeight / 2);
        g.drawLine(x - gridWidth / 2, y + gridHeight / 2,
                   x + gridWidth / 2, y - gridHeight / 2);
      }
    }
  }

  private void paintVoxel(Graphics g, Vector3D v) {
    Vector2D origin = findOrigin(v.x, v.y)
            .subtract(0, v.z * gridHeight * scale)
            .toInteger();
    final int dw = (int) (gridWidth * scale / 2);
    final int dh = (int) (gridHeight * scale / 2);
    g.drawLine(origin.x, origin.y, origin.x - dw, origin.y - dh);
    g.drawLine(origin.x, origin.y, origin.x + dw, origin.y - dh);
    g.drawLine(origin.x, origin.y, origin.x, origin.y - 2 * dh);
    g.drawLine(origin.x - dw, origin.y - dh, origin.x - dw, origin.y - 3 * dh);
    g.drawLine(origin.x + dw, origin.y - dh, origin.x + dw, origin.y - 3 * dh);
    g.drawLine(origin.x - dw, origin.y - 3 * dh, origin.x, origin.y - 2 * dh);
    g.drawLine(origin.x + dw, origin.y - 3 * dh, origin.x, origin.y - 2 * dh);
    g.drawLine(origin.x - dw, origin.y - 3 * dh, origin.x, origin.y - 4 * dh);
    g.drawLine(origin.x + dw, origin.y - 3 * dh, origin.x, origin.y - 4 * dh);
  }

  private void paintVoxels(Graphics g) {
    if (controller != null && controller.getMode() == Viewport.MODE_VOXEL) {
      for (Vector3D v : controller.getVoxels()) {
        g.setColor(Color.ORANGE);
        paintVoxel(g, v);
      }
    }
  }

  private void paintCursor(Graphics g) {
    if (controller != null && controller.getMode() == Viewport.MODE_VOXEL) {
      g.setColor(controller.getCursorColor(cursor));
      paintVoxel(g, cursor);
    }
  }

  private void paintOrigin(Graphics g) {
    final int gridWidth = (int) (this.gridWidth * scale);
    final int gridHeight = (int) (this.gridHeight * scale);

    g.setColor(Color.BLUE);
    g.fillOval(origin.x - 4, origin.y - 4, 8, 8);
  }

  private void paintText(Graphics g) {
    String scaleStr = new DecimalFormat("#0.0#")
            .format(scale, new StringBuffer(), new FieldPosition(0))
            .toString();
    g.setColor(Color.YELLOW);
    g.drawString("Scale: " + scaleStr, getWidth() - 100, getHeight() - 20);
  }

  private void renderImages(Graphics g) {
    if (controller != null) {
      for (ViewportController.Image image : controller.getImages()) {
        Vector2D iOrigin = origin.add(image.offset.multiply(scale)).toInteger();
        g.drawImage(image.image, iOrigin.x, iOrigin.y,
                    iOrigin.x + (int) (image.image.getWidth() * scale),
                    iOrigin.y + (int) (image.image.getHeight() * scale), 0, 0,
                    image.image.getWidth(), image.image.getHeight(), null);
      }
    }
  }

  @Override
  public void paint(Graphics g) {
    final int gridWidth = (int) (this.gridWidth * scale);
    final int gridHeight = (int) (this.gridHeight * scale);
    g.setColor(Color.DARK_GRAY);
    g.fillRect(0, 0, getWidth(), getHeight());

    renderImages(g);

    paintMainGrid(g);
    paintVoxels(g);
    paintCursor(g);

    paintOrigin(g);

    paintText(g);
  }

  @Override
  public void update(Observable o, Object arg) {
    if (project == o) {
      switch ((String) arg) {
        case "gridWidth":
          gridWidth = project.getGridWidth();
          repaint();
          break;
        case "gridHeight":
          gridHeight = project.getGridHeight();
          repaint();
          break;
      }
    }
  }

  private Vector2D findOrigin(double x, double y) {
    return origin.add((int) ((x - y) * gridWidth / 2 * scale),
                      (int) (-(x + y) * gridHeight / 2 * scale)).toInteger();
  }

  @Override
  public void keyTyped(KeyEvent e) {
  }

  @Override
  public void keyPressed(KeyEvent e) {
  }

  @Override
  public void keyReleased(KeyEvent e) {
    if (controller != null && controller.getMode() == Viewport.MODE_VOXEL) {
      switch (e.getKeyCode()) {
        case KeyEvent.VK_SPACE:
          controller.voxelClicked(cursor);
          repaint();
          break;
        case KeyEvent.VK_UP:
          if (e.isShiftDown()) {
            if (cursor.z < 16) {
              cursor = cursor.add(new Vector3D(0, 0, 1));
            }
          } else {
            cursor = cursor.add(new Vector3D(0, 1, 0));
          }
          repaint();
          break;
        case KeyEvent.VK_DOWN:
          if (e.isShiftDown()) {
            if (cursor.z > -1) {
              cursor = cursor.add(new Vector3D(0, 0, -1));
            }
          } else {
            cursor = cursor.add(new Vector3D(0, -1, 0));
          }
          repaint();
          break;
        case KeyEvent.VK_LEFT:
          cursor = cursor.add(new Vector3D(-1, 0, 0));
          repaint();
          break;
        case KeyEvent.VK_RIGHT:
          cursor = cursor.add(new Vector3D(1, 0, 0));
          repaint();
          break;
      }
    }
  }

}
