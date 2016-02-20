// Class: ColorChoiceMenu
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

package edu.kzoo.grid.gui.nuggets;

import edu.kzoo.grid.gui.ColorChoiceDDMenu;
import edu.kzoo.util.NamedColor;

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
     *  introducing it into a panel.  The color choices are those
     *  defined in <code>ColorChoiceDDMenu.STANDARD_CHOICES</code>.
     *    @param label  label for color chooser
     **/
    public ColorChoiceMenu(String label)
    {
        this(label, ColorChoiceDDMenu.STANDARD_CHOICES,
             ColorChoiceDDMenu.RED);
    }

    /** Constructs a menu of color choices.  Puts the menu and a label
     *  introducing it into a panel.
     *  (Precondition: <code>defaultColor</code> is one of the labels
     *  from <code>ColorChoiceDDMenu.STANDARD_CHOICES</code>:
     *  "Red", "Orange", "Yellow", "Green", "Blue", "Indigo", "Violet",
     *  "White", "Gray", "Black", "Random", and "Other ...".)
     *    @param label  label for color chooser
     *    @param defaultColor  the color that should appear as the default
     *                         on the menu when it is first constructed
     **/
    public ColorChoiceMenu(String label, String defaultColor)
    {
        this(label, ColorChoiceDDMenu.STANDARD_CHOICES,
             ColorChoiceDDMenu.getChoice(defaultColor));
    }

    /** Constructs a menu of color choices.  Puts the menu and a label
     *  introducing it into a panel.
     *  (Precondition: <code>defaultColor</code> is one of the
     *  color choices in <code>colorChoices</code>.)
     *    @param label  label for color chooser
     *    @param colorChoices  the set of color choices to show in the
     *                         drop-down menu
     *    @param defaultColor  the color that should appear as the default
     *                         on the menu when it is first constructed
     **/
    public ColorChoiceMenu(String label,
                           ColorChoiceDDMenu.ColorChoice[] colorChoices,
                           ColorChoiceDDMenu.ColorChoice defaultColor)
    {
        // Add the label and drop-down menu to the panel.
        setLayout(new GridLayout(1, 0));
        setBorder(BorderFactory.createEmptyBorder(10, 4, 10, 4));

        add(new JLabel(label));
        colorMenu = new ColorChoiceDDMenu(colorChoices, defaultColor);
        colorMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
                { act(); }});
        add(colorMenu);
    }

    /** Adds a new menu item to the menu with the given color, using its
     *  name as the name in the menu.
     **/
    public void addColorChoice(NamedColor color)
    {
        colorMenu.addItem(new ColorChoiceDDMenu.ColorChoice(color));
    }

    /** Adds a new menu item to the menu with the given name and color.
     **/
    public void addColorChoice(String colorName, Color color)
    {
        colorMenu.addItem(new ColorChoiceDDMenu.ColorChoice(colorName, color));
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
