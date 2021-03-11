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
    }
}

class painterThread implements Runnable {
    public painterThread(Integer posX, Integer posY, Graphics g) {

    }

    @Override
    public void run() {

    }
}

class myPanel extends JPanel implements KeyListener {
    Double minR, maxR, minI, maxI, del;
    boolean setZoom;
    private final mandelbrotDeviationCalculator calculator;

    public myPanel() {
        minR = -2.0;
        maxR = 2.0;
        minI = -2.0;
        maxI = 2.0;
        del = (minR - maxR) / 5.0;
        setZoom = true;
        addKeyListener(this);
        setSize(800, 800);
        setVisible(true);
        setFocusable(true);
        calculator = new mandelbrotDeviationCalculator();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_I) {
            minR += del;
            maxR -= del;
            minI += del;
            maxI -= del;
        }
        if (e.getKeyCode() == KeyEvent.VK_O) {
            minR -= del;
            maxR += del;
            minI -= del;
            maxI += del;
        }
        del = (maxR - minR) / 100.0;

        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            minR = -2.0;
            minI = -2.0;
            maxR = 2.0;
            maxI = 2.0;
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            minR += del;
            maxR += del;
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            minR -= del;
            maxR -= del;
        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            minI -= del;
            maxI -= del;
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            minI += del;
            maxI += del;
        }
        repaint();

    }

    @Override
    public void keyReleased(KeyEvent e) {
        repaint();
    }

    public void paint(Graphics g)
    {
        final double imgHt = 800, imgWid = 800;
        final int maxIntensity = 255;

        for (double y = 0; y <= imgHt; y+=1.0) {
            for (double x = 0; x <= imgWid; x+=1.0) {
                double cr = ((x * (maxR - minR)) / imgWid) + minR;
                double ci = ((y * (maxI - minI) / imgHt)) + minI;
                g.setColor(calculator.mandPlot(cr, ci, maxIntensity));
                g.drawLine((int)x , (int)y, (int)x, (int)y);
            }
        }
    }
}