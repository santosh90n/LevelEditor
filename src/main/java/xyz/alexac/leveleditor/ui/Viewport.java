/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.alexac.leveleditor.ui;

import java.awt.Color;
import java.awt.Graphics;
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

/**
 *
 * @author alex-ac
 */
public class Viewport extends JComponent implements Observer, MouseListener,
                                                    MouseMotionListener,
                                                    MouseWheelListener {
  private Vector2D origin = null;
  private Project project = null;
  private int gridWidth = 256;
  private int gridHeight = 128;
  private Vector2D lastPoint = null;
  private float scale = 1.0f;
  private int zPlane = 0;
  private boolean zPlaneEnabled = false;

  public Viewport() {
    this.addMouseListener(this);
    this.addMouseWheelListener(this);
  }

  @Override
  public void mouseClicked(MouseEvent e) {
  }

  @Override
  public void mouseDragged(MouseEvent e) {
    origin = origin.plus(e.getX(), e.getY()).minus(lastPoint);
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
    this.addMouseMotionListener(this);
    this.lastPoint = new Vector2D(e.getX(), e.getY());
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    this.removeMouseMotionListener(this);
    this.lastPoint = null;
  }

  @Override
  public void mouseWheelMoved(MouseWheelEvent e) {
    if (e.isShiftDown()) {
      if (zPlaneEnabled) {
        zPlane += e.getWheelRotation();
        repaint();
      }
    } else {
      float scale = this.scale + e.getWheelRotation() * 0.01f;
      if (scale < 0.1f) {
        scale = 0.1f;
      }
      if (this.scale != scale) {
        this.scale = scale;
        repaint();
      }
    }
  }

  @Override
  public void setBounds(int x, int y, int width, int height) {
    if (origin == null) {
      origin = new Vector2D(width / 2, height / 2);
    } else if (getWidth() != width || getHeight() != height) {
      origin = origin.plus(width / 2, height / 2)
      .minus(getWidth() / 2, getHeight() / 2);
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

  public void setZPlaneEnabled(boolean enabled) {
    if (zPlaneEnabled == enabled) {
      return;
    }
    zPlaneEnabled = enabled;
    repaint();
  }

  @Override
  public void paint(Graphics g) {
    int gridWidth = (int) (this.gridWidth * scale);
    int gridHeight = (int) (this.gridHeight * scale);
    g.setColor(Color.DARK_GRAY);
    g.fillRect(0, 0, getWidth(), getHeight());

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
      for (int y = startY; y < getHeight() + gridHeight / 2; y += gridHeight) {
        g.drawLine(x - gridWidth / 2, y - gridHeight / 2,
                   x + gridWidth / 2, y + gridHeight / 2);
        g.drawLine(x - gridWidth / 2, y + gridHeight / 2,
                   x + gridWidth / 2, y - gridHeight / 2);
      }
    }

    if (zPlaneEnabled) {
      Vector2D zPlaneOrigin = findOrigin(zPlane, 1);
      startX = zPlaneOrigin.x;
      startY = zPlaneOrigin.y;
      while (startX > 0) {
        startX -= gridWidth;
      }
      g.setColor(Color.CYAN);
      for (int x = startX; x < getWidth(); x += gridWidth) {
        for (int y = startY; y > 0; y -= gridHeight) {
          g.drawLine(x, y, x, y - gridHeight);
          g.drawLine(x, y, x + gridWidth, y);
        }
      }
    }

    g.setColor(Color.BLUE);
    g.fillOval(origin.x - 4, origin.y - 4, 8, 8);

    String scaleStr = new DecimalFormat("#0.0#")
           .format(scale, new StringBuffer(), new FieldPosition(0))
           .toString();
    g.setColor(Color.YELLOW);
    g.drawString("Scale: " + scaleStr, getWidth() - 100, getHeight() - 20);
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

  private Vector2D findOrigin(int x, int y) {
    return origin.plus((int) ((x - y) * gridWidth / 2 * scale),
                       (int) (-(x + y) * gridHeight / 2 * scale));
  }
}
