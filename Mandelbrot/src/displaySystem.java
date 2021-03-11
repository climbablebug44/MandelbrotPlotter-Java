import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class displaySystem extends JFrame {

    public displaySystem(Integer sizeX, Integer sizeY) {
        super("Mandelbrot Set");
        setSize(sizeX, sizeY);
        setVisible(true);
        setBackground(new Color(0, 0, 0));
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        final myPanel panel = new myPanel();
        this.add(panel);
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                panel.repaint(false);
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_I) {
                    panel.zoomIn();
                }
                if (e.getKeyCode() == KeyEvent.VK_O) {
                    panel.zoomOut();
                }
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    panel.resetScreen();
                }
                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    panel.moveR();
                }
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    panel.moveL();
                }
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    panel.moveU();
                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    panel.moveD();
                }
                panel.repaint(false);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                panel.repaint(true);
            }
        });
    }
}

class painterThread implements Runnable {
    Integer posX, posY;
    Double cR, cI;
    Graphics g;
    mandelbrotDeviationCalculator calculator;

    public painterThread(Graphics g, Integer posX, Integer posY, Double cR, Double cI, mandelbrotDeviationCalculator calculator) {
        this.g = g;
        this.posX = posX;
        this.posY = posY;
        this.cR = cR;
        this.cI = cI;
        this.calculator = calculator;
    }

    @Override
    public void run() {
        g.setColor(calculator.mandPlot(cR, cI, 255));
        g.drawLine(posX, posY, posX, posY);
    }
}

class myPanel extends JPanel {
    Double minR, maxR, minI, maxI, del;
    boolean setZoom;
    private final mandelbrotDeviationCalculator calculator;
    Integer counter;

    public void zoomIn() {
        minR += del;
        maxR -= del;
        minI += del;
        maxI -= del;
    }

    public void zoomOut() {
        minR -= del;
        maxR += del;
        minI -= del;
        maxI += del;
    }

    public void resetScreen() {
        minR = -2.0;
        minI = -2.0;
        maxR = 2.0;
        maxI = 2.0;
    }

    public void moveR() {
        minR += del;
        maxR += del;
    }

    public void moveL() {
        minR -= del;
        maxR -= del;
    }

    public void moveU() {
        minI -= del;
        maxI -= del;
    }

    public void moveD() {
        minI += del;
        maxI += del;
    }

    public void repaint(boolean x) {
        if (x)
            super.repaint();
        else {
            if (this.counter == 10)
                super.repaint();
            else
                this.counter += 1;
        }
    }

    public myPanel() {
        minR = -2.0;
        maxR = 2.0;
        minI = -2.0;
        maxI = 2.0;
        del = (minR - maxR) / 5.0;
        setZoom = true;
        setSize(800, 800);
        setVisible(true);
        calculator = new mandelbrotDeviationCalculator();
        counter = 0;
    }


    public void paint(Graphics g) {
        final double imgHt = 800, imgWid = 800;
        final int maxIntensity = 255;
        // delta
        del = (maxR - minR) / 100.0;
        for (double y = 0; y <= imgHt; y += 1.0) {
            for (double x = 0; x <= imgWid; x += 1.0) {
                double cr = ((x * (maxR - minR)) / imgWid) + minR;
                double ci = ((y * (maxI - minI) / imgHt)) + minI;
                painterThread thread = new painterThread(g, (int) x, (int) y, cr, ci, calculator);
                thread.run();
            }
        }
    }
}