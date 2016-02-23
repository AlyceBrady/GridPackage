// Class: ColorChoiceDDMenu
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


package edu.kzoo.grid.gui;

import edu.kzoo.grid.display.TextAndIconRenderer;

import edu.kzoo.util.NamedColor;
import edu.kzoo.util.RandNumGenerator;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import java.util.Random;

/**
 *  Grid GUI Support Package:<br>
 *
 *  The <code>ColorChoiceDDMenu</code> class provides a
 *  drop-down menu for choosing a color.
 *
 *  <p>
 *  A number of predefined color choices,
 *  <code>ColorChoiceDDMenu.RED, ORANGE, YELLOW, GREEN, BLUE, INDIGO,
 *  VIOLET, WHITE, GRAY, BLACK, RANDOM,</code> and <code>CUSTOM</code>,
 *  have been provided for specifying the choices that should be made
 *  available in the menu and for specifying the initial selected color
 *  choice.  This set of choices comprises the standard set.
 *
 *  @author Alyce Brady (based on code by Julie Zelenski)
 *  @version 15 December 2003
 **/
public class ColorChoiceDDMenu extends JComboBox<ColorChoiceDDMenu.ColorChoice>
{
    /** Unique constant object representing a random color (to compare == to) */
    public static final Color RANDOM_COLOR = new Color(0, 0, 0);

    // Constants that indicate pre-defined color choices that may be used
    // in a color choice drop-down menu.
    public static final ColorChoice
        RED = new ColorChoice("Red", NamedColor.RED),
        ORANGE = new ColorChoice("Orange", NamedColor.PUMPKIN),
        YELLOW = new ColorChoice("Yellow", NamedColor.YELLOW),
        GREEN = new ColorChoice("Green", NamedColor.MEDIUM_GREEN),
        BLUE = new ColorChoice("Blue", NamedColor.BLUE),
        INDIGO = new ColorChoice("Indigo", NamedColor.INDIGO),
        VIOLET = new ColorChoice("Violet", NamedColor.VIOLET),
        WHITE = new ColorChoice("White", NamedColor.WHITE),
        GRAY = new ColorChoice("Gray", NamedColor.GRAY),
        BLACK = new ColorChoice("Black", NamedColor.BLACK),
        RANDOM = new ColorChoice("Random", RANDOM_COLOR),
        CUSTOM = new ColorChoice("Other ...", NamedColor.LIGHT_GRAY);
    public static final ColorChoice[] STANDARD_CHOICES = {
        RED, ORANGE, YELLOW, GREEN, BLUE, INDIGO, VIOLET, WHITE, GRAY, BLACK,
        RANDOM, CUSTOM };

  // static methods

    /** Gets the color choice from <code>STANDARD_CHOICES</code> with the
     *  specified name.  Returns <code>null</code> if there is no such
     *  color choice in <code>STANDARD_CHOICES</code>.
     **/
    public static ColorChoice getChoice(String name)
    {
        for ( int i = 0; i < STANDARD_CHOICES.length; i++ )
        {
            ColorChoice choice = STANDARD_CHOICES[i];
            if ( choice.toString().equals(name) )
                return choice;
        }
        return null;
    }


  // constructors

    /** Creates a color choice menu in which the selected choice to begin
     *  with is the "Random" color choice.
     **/
    public ColorChoiceDDMenu()
    {
        this(RANDOM);
    }

    /** Creates a color choice menu in which <code>startingColorChoice</code>
     *  is the selected one when the menu is created.
     *  (Precondition: <code>startingColorChoice</code> is one of the
     *  color choices in <code>STANDARD_CHOICES</code>, whose labels are
     *  "Red", "Orange", "Yellow", "Green", "Blue", "Indigo", "Violet",
     *  "White", "Gray", "Black", "Random", and "Other ...".)
     *      @param startingColorChoice the initial selected color choice
     **/
    public ColorChoiceDDMenu(String startingColorChoice)
    {
        this(STANDARD_CHOICES, getChoice(startingColorChoice));
    }

    /** Creates a color choice menu in which <code>startingColorChoice</code>
     *  is the selected one when the menu is created.
     *  (Precondition: <code>startingColorChoice</code> is one of the
     *  color choices in <code>STANDARD_CHOICES</code>.)
     *      @param startingColorChoice the initial selected color choice
     **/
    public ColorChoiceDDMenu(ColorChoice startingColorChoice)
    {
        this(STANDARD_CHOICES, startingColorChoice);
    }

    /** Creates a color choice menu with the specified color choices and
     *  with <code>startingColorChoice</code>
     *  as the selected one when the menu is created.  The color choices
     *  may be any of the pre-defined color choices,
     *  <code>ColorChoiceDDMenu.RED, ORANGE, YELLOW, GREEN, BLUE, INDIGO,
     *  VIOLET, WHITE, BLACK, RANDOM,</code> or <code>CUSTOM</code>,
     *  or may ones constructed with the
     *  <code>ColorChoiceDDMenu.ColorChoice</code> constructor.
     *  (Precondition: <code>startingColorChoice</code> is one of the
     *  color choices in <code>colorChoices</code>.)
     *      @param colorChoices the set of color choices to show in the
     *                          drop-down menu
     *      @param startingColorChoice the initial selected color choice
     **/
    public ColorChoiceDDMenu(ColorChoice[] colorChoices,
                             ColorChoice startingColorChoice)
    {
        super(colorChoices);
        if ( startingColorChoice != null )
            setSelectedItem(startingColorChoice);
        setRenderer(new TextAndIconRenderer(this));
        setAlignmentX(LEFT_ALIGNMENT);
        addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { chooseColor(); }});
    }


  // methods

    /** Follows up when the user picks a new choice from the
     *  drop-down menu (specified listener action).
     **/
    protected void chooseColor()
    {
       ColorChoice cc = (ColorChoice)getSelectedItem();
       if (cc == CUSTOM) 
       {
            hidePopup();
            Component parentFrame = JOptionPane.getFrameForComponent(this);
            Color chosen = JColorChooser.showDialog(parentFrame, 
                                                    "Choose object's color",
                                                    cc.getColor());
            if (chosen != null)
                cc.setColor(chosen);
        }
    }
    
    /** Returns the currently selected color. If random color is selected,
     *  returns a new randomly generated color.
     *  @return the currently selected color
     **/
    public Color currentColor()
    {
        ColorChoice cc = (ColorChoice)getSelectedItem();
        if (cc == RANDOM) 
        {
            Random rng = RandNumGenerator.getInstance();
            return new Color(rng.nextInt(256), rng.nextInt(256), rng.nextInt(256));
        }
        else
            return cc.getColor();
    }

    /** Nested class used to hold the per-item information 
     *  for the entries in the combo box of color
     *  choices. Each item represents a color choice which
     *  is basically just a Color object, an icon, and a name.
     */
    public static class ColorChoice extends JLabel
    {
        private Color color;

        public ColorChoice(NamedColor c)
            { super(c.toString(), new ColorIcon(c, 16, 16),
                    SwingConstants.LEFT);
              color = c;
            }
        public ColorChoice(String name, Color c)
            { super(name, new ColorIcon(c, 16, 16), SwingConstants.LEFT);
              color = c;
            }
        protected void setColor(Color c) { color = c; }
        public Color getColor() { return color; }
        public String toString() { return getText(); }
    }

    /** Nested class used to draw the color swatch icon used
     *  for color choice entries in the color combo box.
     *  This simple class just draws a rectangle filled with the
     *  color and edged with a black border.
     */
    protected static class ColorIcon implements Icon 
    {
        private static final int MARGIN = 2;
        
        private Color color;
        private int width, height;
    
        public ColorIcon(Color c, int w, int h) { color = c; width = w; height = h; }
        public void setColor(Color c) { color = c; }
        public int getIconWidth() { return width; }
        public int getIconHeight() { return height; }
        public void paintIcon(Component comp, Graphics g, int x, int y) 
        {
            Graphics2D g2 = (Graphics2D)g;
            Rectangle r = new Rectangle(x + MARGIN, y + MARGIN, width - 2*MARGIN, height - 2*MARGIN);
            if (color != RANDOM_COLOR) 
            {
                g2.setColor(color);
                g2.fill(r);
            } 
            else for (int k = 0; k < r.width; k++)  // draw rainbow lines
            {
                g2.setColor(Color.getHSBColor((float)k/r.width, .95f, 1));
                g2.drawLine(r.x + k, r.y, r.x + k, r.y + r.height);
            }
            g2.setColor(Color.black);
            g2.draw(r);
        }
    }

}
