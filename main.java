import java.awt.*;
import javax.swing.*;
//import java.util.*;
import java.awt.event.*;

class triplet<A> {
    private final A a;
    private final A b;
    private final A c;

    triplet(final A a, final A b, final A c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    A getx() {
        return a;
    }

    A gety() {
        return b;
    }

    A getz() {
        return c;
    }
}

class MandelbrotCalc {

    public static triplet<Integer> mandplot(final double compx, final double compy, final int maxiter) {
        int deviation = 0;
        double zre = 0, zcomp = 0;
        while (deviation <= maxiter && ((zre * zre) + (zcomp * zcomp)) < 4.0) {
            final double temp = (zre * zre) - (zcomp * zcomp) + compx;
            zcomp = (2.0 * (zre * zcomp)) + compy;
            zre = temp;
            deviation++;
        }
        if (deviation > 254)
            return new triplet<Integer>(Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0));

        switch (deviation % 16) {
            case 0:
                return new triplet<Integer>(Integer.valueOf(66), Integer.valueOf(30), Integer.valueOf(15));
            case 1:
                return new triplet<Integer>(Integer.valueOf(25), Integer.valueOf(7), Integer.valueOf(26));
            case 2:
                return new triplet<Integer>(Integer.valueOf(9), Integer.valueOf(1), Integer.valueOf(47));
            case 3:
                return new triplet<Integer>(Integer.valueOf(4), Integer.valueOf(4), Integer.valueOf(73)); // done
            case 4:
                return new triplet<Integer>(Integer.valueOf(0), Integer.valueOf(7), Integer.valueOf(100));
            case 5:
                return new triplet<Integer>(Integer.valueOf(12), Integer.valueOf(44), Integer.valueOf(138));
            case 6:
                return new triplet<Integer>(Integer.valueOf(24), Integer.valueOf(82), Integer.valueOf(117));
            case 7:
                return new triplet<Integer>(Integer.valueOf(57), Integer.valueOf(125), Integer.valueOf(209));
            case 8:
                return new triplet<Integer>(Integer.valueOf(134), Integer.valueOf(181), Integer.valueOf(229));
            case 9:
                return new triplet<Integer>(Integer.valueOf(211), Integer.valueOf(236), Integer.valueOf(248));
            case 10:
                return new triplet<Integer>(Integer.valueOf(241), Integer.valueOf(233), Integer.valueOf(191));
            case 11:
                return new triplet<Integer>(Integer.valueOf(248), Integer.valueOf(201), Integer.valueOf(95));
            case 12:
                return new triplet<Integer>(Integer.valueOf(255), Integer.valueOf(170), Integer.valueOf(0));
            case 13:
                return new triplet<Integer>(Integer.valueOf(204), Integer.valueOf(128), Integer.valueOf(0));
            case 14:
                return new triplet<Integer>(Integer.valueOf(153), Integer.valueOf(87), Integer.valueOf(0));
            case 15:
                return new triplet<Integer>(Integer.valueOf(106), Integer.valueOf(52), Integer.valueOf(3));
            default:
                return new triplet<Integer>(Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0));
        }
    }

}

class Myframe extends JFrame {
    Myframe(final int a, final int b) {
        super("Mandelbrot Set");
        setSize(a, b);
        setVisible(true);
        setBackground(new Color(0, 0, 0));
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}

class DrawPane extends JPanel implements KeyListener {
    public int imght, imgwid;
    int xprev = 0, yprev = 0;
    double minR, maxR, minI, maxI, del;
    boolean setzoom;

    DrawPane() {
        //addMouseListener(this);
        addKeyListener(this);
        //addMouseMotionListener(this);
        setSize(800, 800);
        setVisible(true);
        minR = -2.0;
        minI = -2.0;
        maxR = 2.0;
        maxI = 2.0;
        del = ((double) minR - (double) maxR) / (double) 5;
        setzoom = true;
    }

    public boolean isFocusTraversable() {
        return true;
    }

    //KeyListeners [start]
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
        del = (double) (maxR - minR) / (double) 100;

        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            minR = -2;
            minI = -2;
            maxR = 2;
            maxI = 2;
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

    public void keyReleased(KeyEvent e) {
        //repaint();
    }

    public void keyTyped(KeyEvent e) {
        //repaint();
    }

    //KeyListeners [End]
    public void paint(final Graphics g) {
        final double imght = 800, imgwid = 800;
        final int intensity = 255;

        for (double y = 0; y <= imght; y++) {
            for (double x = 0; x <= imgwid; x++) {
                final double cr = ((x * (maxR - minR)) / imgwid) + minR;
                final double ci = ((y * (maxI - minI) / imght)) + minI;
                final triplet<Integer> trip = MandelbrotCalc.mandplot(cr, ci, intensity);
                final Color newcolor = new Color(trip.getx(), trip.gety(), trip.getz());
                g.setColor(newcolor);
                g.drawLine((int) x, (int) y, (int) x, (int) y);
            }
        }
    }
}

public class main {

    public static void main(final String[] args) {
        try {
            final DrawPane mand = new DrawPane();
            final Myframe f = new Myframe(800, 800);
            f.getContentPane().add(mand);
        } catch (final Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("Finished Successfully");
        }
    }
}
