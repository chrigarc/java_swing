/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package componentes.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JScrollBar;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicScrollBarUI;


/**
 *
 * @author Chrigarc
 */
public class NewScrollBar extends BasicScrollBarUI {
    
    public final static Color COLOR_FONDO = new Color(247, 247, 248);
    public final static Color COLOR_BARRA = new Color(191, 190, 192);
    
    public static ComponentUI createUI(JComponent component) {
        return new NewScrollBar();
    }
    
    private Color colorFondo;
    private Color colorBarra;
    private Integer sizeBarra;

    public NewScrollBar() {
        super();
        this.colorFondo = COLOR_FONDO;
        this.colorBarra = COLOR_BARRA;
        this.sizeBarra = null;
    }

    public NewScrollBar(Color fondo, Color barra) {
        this();
        this.colorFondo = fondo;
        this.colorBarra = barra;
        this.sizeBarra = null;
    }

    public NewScrollBar(Color fondo, Color barra, int libreTamamio) {
        this();
        this.colorFondo = fondo;
        this.colorBarra = barra;
        this.sizeBarra = libreTamamio;
    }

    public Color getColorFondo() {
        return colorFondo;
    }

    public void setColorFondo(Color colorFondo) {
        this.colorFondo = colorFondo;
    }

    public Color getColorBarra() {
        return colorBarra;
    }

    public void setColorBarra(Color colorBarra) {
        this.colorBarra = colorBarra;
    }

    public void setSizeBarra(Integer sizeBarra) {
        this.sizeBarra = sizeBarra;
    }

    @Override
    protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {

        g.setColor(colorFondo);
//        if (isThumbRollover()) {
//            g.setColor(g.getColor().darker());
//        }
        g.fillRect(0, 0, trackBounds.width + 10, trackBounds.height + 20);

//        try {
//            Thread.sleep(10);
//        } catch (InterruptedException ex) {
//
//        }
    }

    @Override
    protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
        g.setColor(colorBarra);

        if (isThumbRollover()) {
//            g.setColor(g.getColor().brighter());
            g.setColor(g.getColor().darker());
        }
        if (scrollbar.getOrientation() == JScrollBar.VERTICAL) {

            if (thumbBounds.y < 10) {
                thumbBounds.y = 10;
            }

            if ((thumbBounds.height - 20) > 5) {
                g.fillRect(thumbBounds.x + 5, thumbBounds.y + 5, 7, thumbBounds.height - 20);

                g.fillOval(thumbBounds.x + 4, thumbBounds.y, 8, 7);
                g.fillOval(thumbBounds.x + 4, thumbBounds.y + thumbBounds.height - 20, 8, 7);
            } else {
                g.fillRect(thumbBounds.x + 5, thumbBounds.y + 5, 7, 2);
            }

        } else {
            if (thumbBounds.x < 10) {
                thumbBounds.x = 10;
            }
            g.fillRect(thumbBounds.x + 5, thumbBounds.y + 5, thumbBounds.width - 20, 7);
            g.fillOval(thumbBounds.x, thumbBounds.y + 4, 8, 8);
            g.fillOval(thumbBounds.x + thumbBounds.width - 20, thumbBounds.y + 4, 8, 8);
        }

    }

    @Override
    protected JButton createDecreaseButton(int orientation) {
        JButton btnL = new JButton("");

        btnL.setBorder(null);
        btnL.setBorderPainted(false);
        btnL.setContentAreaFilled(false);
        return btnL;
    }

    @Override
    protected JButton createIncreaseButton(int orientation) {
        return createDecreaseButton(orientation);
    }

    /**
     *
     * @param x
     * @param y
     * @param width
     * @param height Modifica el tamaño del scroll en caso de que se fije uno
     * con setSizeBarra
     */
    @Override
    protected void setThumbBounds(int x, int y, int width, int height) {

        if (sizeBarra != null && scrollbar.getOrientation() == JScrollBar.VERTICAL) {
            height = sizeBarra;
        } else if (sizeBarra != null && scrollbar.getOrientation() == JScrollBar.HORIZONTAL) {
            width = sizeBarra;
        }
        super.setThumbBounds(x, y, width, height); //To change body of generated methods, choose Tools | Templates.
    }
    
}
