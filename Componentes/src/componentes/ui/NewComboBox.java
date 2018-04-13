/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package componentes.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.border.AbstractBorder;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicComboBoxUI;

/**
 *
 * @author Chrigarc
 */
public class NewComboBox extends BasicComboBoxUI{
    
    private static Icon flechitas;
    private static Color fondoDisabled = new Color(237, 238, 240);

    public static ComponentUI createUI(JComponent component) {
        return new NewComboBox();
    }

    @Override
    protected JButton createArrowButton() {
        if (flechitas == null) {
            flechitas = new javax.swing.ImageIcon(getClass().getResource("/assets/img/flechitas.png"));
        }
        JButton b = new JButton(flechitas);
        b.setBorder(null);
        b.setBorderPainted(false);
        b.setContentAreaFilled(false);
        return b;
    }

    @Override
    protected Dimension getDefaultSize() {
        Dimension d = super.getDefaultSize(); //To change body of generated methods, choose Tools | Templates.
        d.height = 20;
        return d;
    }

    @Override
    protected Dimension getDisplaySize() {
        Dimension d = super.getDisplaySize(); //To change body of generated methods, choose Tools | Templates.
        d.height = 20;
        return d;
    }

    @Override
    public void paint(Graphics g, JComponent c) {
        hasFocus = comboBox.hasFocus();

        if (comboBox != null && comboBox.getParent() != null) {
            //System.out.println(comboBox.getParent() + " color: " + comboBox.getParent().getBackground());
            g.setColor(comboBox.getParent().getBackground());
            g.fillRect(0, 0, comboBox.getWidth(), comboBox.getHeight());
        }
        g.setColor(Color.WHITE);
        if (c.getBorder() != null) {
            c.setBorder(null);
        }
        g.fillRoundRect(0, 0, c.getWidth() - 1, c.getHeight() - 1, 5, 5);
        if (!comboBox.isEditable()) {
            Rectangle r = rectangleForCurrentValue();
            r.x += 2;
            paintCurrentValueBackground(g, r, hasFocus);
            paintCurrentValue(g, r, hasFocus);
        }
        g.setColor(Color.GRAY);
        g.drawRoundRect(0, 0, c.getWidth() - 1, c.getHeight() - 1, 5, 5);
    }

    @Override
    public void paintCurrentValueBackground(Graphics g, Rectangle bounds, boolean hasFocus) {
//        if (comboBox != null && comboBox.getParent() != null) {
//            g.setColor(comboBox.getParent().getBackground());
//            g.fillRect(0, 0, comboBox.getWidth(), comboBox.getHeight());
//        }
        g.setColor(Color.WHITE);
        g.fillRoundRect(0, 0, bounds.width - 1, bounds.height - 1, 5, 5);
        //super.paintCurrentValueBackground(g, bounds, hasFocus);
    }

    @Override
    public void paintCurrentValue(Graphics g, Rectangle bounds, boolean hasFocus) {
        ListCellRenderer renderer = comboBox.getRenderer();
        Component c;

        if (hasFocus && !isPopupVisible(comboBox)) {
            c = renderer.getListCellRendererComponent(listBox,
                    comboBox.getSelectedItem(),
                    -1,
                    true,
                    false);
        } else {
            c = renderer.getListCellRendererComponent(listBox,
                    comboBox.getSelectedItem(),
                    -1,
                    false,
                    false);
            c.setBackground(Color.WHITE);
        }
        c.setFont(comboBox.getFont());
        if (hasFocus && !isPopupVisible(comboBox)) {
            //c.setForeground(listBox.getSelectionForeground());
            c.setForeground(Color.BLACK);
            c.setBackground(Color.WHITE);
        } else {
            if (comboBox.isEnabled()) {
                //c.setForeground(comboBox.getForeground());
                c.setForeground(Color.BLACK);
                c.setBackground(Color.WHITE);
            } else {
                c.setForeground(Color.GRAY);
                c.setBackground(fondoDisabled);
            }
        }

        // Fix for 4238829: should lay out the JPanel.
        boolean shouldValidate = false;
        if (c instanceof JPanel) {
            shouldValidate = true;
        }

        int x = bounds.x, y = bounds.y, w = bounds.width, h = bounds.height;
        if (padding != null) {
            x = bounds.x + padding.left;
            y = bounds.y + padding.top;
            w = bounds.width - (padding.left + padding.right);
            h = bounds.height - (padding.top + padding.bottom);
        }

        currentValuePane.paintComponent(g, c, comboBox, x, y, w, h, shouldValidate);
    }

    @Override
    public void addEditor() {
        removeEditor();
        editor = comboBox.getEditor().getEditorComponent();
        if (editor != null) {
            configureEditor();
            comboBox.add(editor);
            if (comboBox.isFocusOwner()) {
                // Switch focus to the editor component
                editor.requestFocusInWindow();
            }
            if (editor instanceof JComponent) {
                JComponent tmp = (JComponent) editor;
                tmp.setBorder(new LeftRoundBorder(Color.GRAY, 1, 5));
            }
            //aqui se añade el listener para editar los jcmbox
        }
    }

    private class LeftRoundBorder extends AbstractBorder {

        private Color borde;
        private int grosor;
        private int arc;

        public LeftRoundBorder(Color borde, int grosor, int arc) {
            this.borde = borde;
            this.grosor = grosor;
            this.arc = arc;
        }

        public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
            Graphics2D g2d = ((Graphics2D) g);
            if (c.getParent() != null && c.getParent().getParent() != null) {
                //System.out.println(c.getParent().getParent());
                g.setColor(comboBox.getParent().getBackground());
                g.fillRect(0, 0, 1, comboBox.getHeight());
            }
            g2d.setColor(Color.WHITE);
            g2d.fillRoundRect(0, 0, 1, c.getHeight() - 1, 5, 5);
            g2d.setColor(borde);
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            //g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE);
            g2d.setStroke(new BasicStroke(grosor));
            g.setColor(borde);
            g.drawRoundRect(0, 0, c.getWidth(), c.getHeight() - 1, arc, arc);
            g.setColor(Color.white);
            g.fillRect(c.getWidth() - 5, 1, c.getWidth(), c.getHeight() - 2);
            g.setColor(borde);
            g.drawLine(c.getWidth() - 5, 0, c.getWidth(), 0);
            g.drawLine(c.getWidth() - 5, c.getHeight() - 1, c.getWidth(), c.getHeight() - 1);
        }

    }
}
