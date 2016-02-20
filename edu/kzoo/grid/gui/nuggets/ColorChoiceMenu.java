// Class: ColorChoiceMenu
//
// Author: Alyce Brady
//
// This class is based on the College Board's FishToolbar class,
// as allowed by the GNU General Public License.  FishToolbar is a
// black-box GUI class within the AP(r) CS Marine Biology Simulation
// case study (see www.collegeboard.com/ap/students/compsci).
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

package edu.kzoo.grid.gui.nuggets;

import edu.kzoo.grid.gui.ColorChoiceDDMenu;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *  Grid GUI Nuggets Package (Handy Grid GUI Components):<br>
 *
 *    A <code>ColorChoiceMenu</code> object provides a drop-down menu for
 *    choosing a color.
 *
 *  @author Alyce Brady (based on code by Julie Zelenski)
 *  @version 29 July 2004
 **/
public class ColorChoiceMenu extends JPanel
{
    // Instance Variables: Encapsulated data for EACH  object
    protected ColorChoiceDDMenu colorMenu;

  // constructor

    /** Constructs a menu of color choices.  Puts the menu and a label
     *  introducing it into a panel.
     *    @param label  label for color chooser
     **/
    public ColorChoiceMenu(String label)
    {
        // Add the label and drop-down menu to the panel.
        setLayout(new GridLayout(1, 0));
        setBorder(BorderFactory.createEmptyBorder(10, 4, 10, 4));

        add(new JLabel(label));
        colorMenu = new ColorChoiceDDMenu(ColorChoiceDDMenu.WHITE);
        colorMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
                { act(); }});
        add(colorMenu);
    }

    /** Performs any actions necessary when the color changes.
     **/
    public void act()
    {
    }

    /** Returns the current color from the drop-down menu.
     **/
    public Color currentColor()
    {
        return colorMenu.currentColor();
    }

}
