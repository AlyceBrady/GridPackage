// Class: TextAndIconRenderer
//
// Author: Alyce Brady
//
// This class is based on the College Board's FishToolbar class,
// as allowed by the GNU General Public License.  FishToolbar is a
// black-box GUI class within the AP(r) CS Marine Biology Simulation
// case study (see
// http://www.collegeboard.com/student/testing/ap/compsci_a/case.html).
//
// License Information:
//   This class is free software; you can redistribute it and/or modify
//   it under the terms of the GNU General Public License as published by
//   the Free Software Foundation.
//
//   This class is distributed in the hope that it will be useful,
//   but WITHOUT ANY WARRANTY; without even the implied warranty of
//   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//   GNU General Public License for more details.

package edu.kzoo.grid.display;
import java.awt.Component;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 *  Grid Display Package:<br>
 *
 *  The <code>TextAndIconRenderer</code> class provides a
 *  renderer that paints both the text and the icon of a
 *  <code>JLabel</code>.  (The default renderer only displays
 *  one or the other.)
 *
 *  @author Alyce Brady (based on code by Julie Zelenski)
 *  @version 13 December 2003
 **/
public class TextAndIconRenderer extends JLabel
    implements ListCellRenderer<Object>
{
    private JComboBox<? extends JLabel> cb;

    /** Constructs a renderer that will operate on the specified
     *  combo box.
     *      @param combo  the combo box this renderer is associated with
     **/
    public TextAndIconRenderer(JComboBox<? extends JLabel> combo) 
    {
        setOpaque(true);
        setHorizontalAlignment(LEFT);
        setVerticalAlignment(CENTER);
        setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        cb = combo;
    }

    /** Returns a component that can display the specified value.
     *  (Precondition: <code>value</code> must be a <code>JLabel</code>.)
     *      @param list  the JList we're painting
     *      @param value the value returned by list.getModel().getElementAt(index)
     *      @param index the cell's index
     *      @param isSelected <code>true</code> if the specified cell was selected
     *      @param cellHasFocus <code>true</code if the specified cell has the focus
     *      @return a component whose <code>paint</code> method will render
     *              the specified value
     **/
    public Component getListCellRendererComponent(JList list,
                Object value, int index,
                boolean isSelected, boolean cellHasFocus) 
    {
        setBackground(isSelected ? list.getSelectionBackground() : list.getBackground());
        setForeground(isSelected ? list.getSelectionForeground() : list.getForeground());
        if (!cb.isEnabled())
        {
            setText("No choice"); // draw differently when disabled
            setIcon(null);
        } 
        else 
        {
            setText(value.toString());
            setIcon(((JLabel)value).getIcon());
        }
        return this;
    }

}
