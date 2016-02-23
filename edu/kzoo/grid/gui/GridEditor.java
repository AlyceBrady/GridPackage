// Class: GridEditor
//
// Author: Alyce Brady
//
// This class is based on the College Board's EnvironmentController 
// and FishToolbar classes, as allowed by the GNU General Public License.
// EnvironmentController and FishToolbar are black-box GUI classes
// within the AP(r) CS Marine Biology Simulation case study 
// (see http://www.collegeboard.com/student/testing/ap/compsci_a/case.html).
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

import edu.kzoo.grid.BoundedGrid;
import edu.kzoo.grid.Grid;
import edu.kzoo.grid.GridObject;
import edu.kzoo.grid.Location;

import edu.kzoo.grid.display.DisplayMap;
import edu.kzoo.grid.display.GridObjectDisplay;
import edu.kzoo.grid.display.ScrollableGridDisplay;
import edu.kzoo.grid.display.TextAndIconRenderer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.JViewport;
import javax.swing.SwingConstants;

/**
 *  Grid GUI Support Package:<br>
 *
 *  The <code>GridEditor</code> class provides a window in which
 *    to edit a grid.
 *
 *  @author Alyce Brady (based on code by Julie Zelenski)
 *  @version 29 February 2004
 **/
public class GridEditor extends GridAppFrame
{
    protected GridAppFrame parentFrame = null;
    protected JButton doneButton;
    protected JComboBox<GridObjectChoice> objComboBox;


  // constructors and initialization methods

    /** Constructs an empty GridEditor window to edit the grid in the
     *  specified frame.
     *  Use the constructWindowContents method to set the properties of the
     *  window and make it visible.
     *    @param frame  the frame that invoked this grid editor
     **/
    public GridEditor(GridAppFrame frame)
    {
        parentFrame = frame;
    }

    /** Constructs the display for a GridEditor using values from the parent
     *  frame.
     **/
    public void constructWindowContents()
    {
        String title = parentFrame.getTitle() + ": Grid Editor";
        ScrollableGridDisplay parentDisplay = parentFrame.getDisplay();
        JViewport vp = parentFrame.getDisplay().getEnclosingViewport();
        Dimension windowSize = (vp != null) ? vp.getSize() : getSize();
        constructWindowContents(title, parentDisplay.backgroundColor(),
            windowSize.width, windowSize.height, 
            parentDisplay.minimumCellSize());
    }

    /** Constructs the display for a GridEditor.
     *    @param title frame title
     *    @param bgColor color to paint background of grid
     *    @param viewingWidth  the width of the viewing area
     *    @param viewingHeight the height of the viewing area
     *    @param minCellSize   minimum grid cell side length
     **/
    public void constructWindowContents(String title, Color bgColor,
                     int viewingWidth, int viewingHeight, int minCellSize)
    {
        super.constructWindowContents(title, bgColor, viewingWidth, viewingHeight, minCellSize);

        // change the location and default close operation of the new window
        setLocation(parentFrame.getX() + 40, parentFrame.getY() + 40);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        setGrid(parentFrame.getGrid());
        showGrid();
    }


  // methods that deal with defining the main panel contents

    /** Defines contents of main window panel.  Should be redefined in
     *  subclasses that require panels other than (or in addition to)
     *  an editing palette, a display, and a Done button.
     *   @return the panel that will serve as the window's content panel
     **/
    protected JPanel defineContent()
    {
        // Create a panel for the editing palette.
        JPanel content = new JPanel();
        content.add(makeEditingPalette(), BorderLayout.WEST);

        // Create a sub-panel for the grid display and Done button.
        JPanel p = new JPanel();
        p.setLayout(new BorderLayout());
        p.setBorder(BorderFactory.createTitledBorder("Click to Add Object"));
        p.add(makeDisplayPanel(), BorderLayout.NORTH);
        
        doneButton = new JButton("Done");
        doneButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
                { done(); }});
        p.add(doneButton, BorderLayout.SOUTH);
        content.add(p, BorderLayout.EAST);
        return content;
    }


  // methods and nested classes for building editing palette

    /** Creates the editing palette.
     **/
    protected Component makeEditingPalette()
    {
        JToolBar tb = new JToolBar(SwingConstants.VERTICAL);
        tb.setFloatable(false);
        tb.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Choose attribute"), 
                BorderFactory.createEmptyBorder(4, 4, 4, 4)));
        
        fillPalette(tb);

        tb.add(Box.createGlue());
        return tb;
    }

    /** Puts tools for choosing grid object attributes in
     *  the editing palette, in particular a Type tool for choosing
     *  the type of grid object.  Subclasses can redefine this
     *  method to put other types of components, such as a color
     *  choice combo box, in the palette.
     **/
    protected void fillPalette(JToolBar palette)
    {
        palette.add(new JLabel(" Type: "));
        palette.add(makeTypeChoiceComponent());

        // Subclasses could redefine to add color choice, as below.
        //      palette.addSeparator();
        //      palette.add(new JLabel(" Color: "));
        //      palette.add(new ColorChoiceDDMenu(ColorChoiceDDMenu.WHITE));
    }

    /** Makes the grid object type choice combo box.
     **/
    protected Component makeTypeChoiceComponent()
    {
        objComboBox = new JComboBox<GridObjectChoice>();
        addChoicesFromFactory(objComboBox);
        objComboBox.setRenderer(new TextAndIconRenderer(objComboBox));
        objComboBox.setAlignmentX(LEFT_ALIGNMENT);
        objComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
                { chooseGridObjType(); }});
        return objComboBox;
    }

    /** Builds up the list of grid object type choices for the editing
     *  palette.
     **/
    protected void addChoicesFromFactory(JComboBox<GridObjectChoice> cb)
    {
        for ( Class gridObjClass : GridPkgFactory.gridObjectClasses() )
            cb.addItem(new GridObjectChoice(gridObjClass));
    }

    /** Nested class used to hold the per-item information for the 
     *  entries in the combo box of grid object choices.
     *  Each item represents a choice which is a GridObject class.
     *  (Uses GridPkgFactory; override to use a different factory.)
     */
    protected class GridObjectChoice extends JLabel
    {
        private Class cls;
      
        public GridObjectChoice(Class c)
            { super(c.getName(),
                    new GridObjectIcon(c, 16, 16), SwingConstants.LEFT);
              cls = c;
            }

        public Class getObjectClass() { return cls; }
        public String toString() { return cls.getName(); }
    }

    /** Nested class used to draw the icons used for
     *  GridObject entries in the grid object combo box.
     *  We construct a GridObject object and then hand it off to
     *  its display object to draw.
     */
    protected class GridObjectIcon implements Icon
    {
        private GridObject gridObj;
        private Class cls;
        private Color color = Color.gray;  // default color
        private int width, height;

        public GridObjectIcon(Class cl, int w, int h) 
            {   cls = cl; width = w; height = h; }

        public GridObjectIcon(Class cl, Color c, int w, int h) 
            {   cls = cl; color = c; width = w; height = h; }

        public int getIconWidth() { return width; }
        public int getIconHeight() { return height; }
        public void paintIcon(Component comp, Graphics g, int x, int y) 
        {
            if ( gridObj == null )
            {
                Grid grid = new BoundedGrid(1, 1);
                Location loc = new Location(0, 0);
                makeObject(cls, grid, loc, color);
                gridObj = grid.objectAt(loc);
            }
            Graphics2D g2 = (Graphics2D)g;
            AffineTransform savedTransform = g2.getTransform(); // save current
            GridObjectDisplay displayObj = DisplayMap.findDisplayFor(gridObj);
            displayObj.draw(gridObj, comp, g2, new Rectangle(x, y, 
                            getIconWidth(), getIconHeight()));
            g2.setTransform(savedTransform); // restore coordinate system
        }
    }


  // methods for handling user events

    /** Follows up when the user picks a new choice from the
     *  grid object combo box (specified listener action).
     **/
    protected void chooseGridObjType()
    {
        // Could do something like enabling the color menu iff the
        // chosen class has a constructor that takes a color.
    }

    /** Handles a mouse press over the grid display, editing the
     *  contents of the grid at the specified location.  If the
     *  location is empty, a new object of the currently specified
     *  grid object class will be added.  If the location is not
     *  empty, the object at the location will be removed.
     *    @see #currentGridObjectClass()
     **/
    protected void onMousePressOverDisplay(Location loc)
    {
        if ( loc != null )
        {
            GridObject obj = getGrid().objectAt(loc);
            Class selectedClass = currentGridObjectClass();

            // If there's an object there, remove it.  Otherwise,
            // add one.
            if (obj == null )
            {
                makeObject(selectedClass, getGrid(), loc);
            }
            else
            {
                getGrid().remove(obj);
            }
            getDisplay().repaint();
        }
    }

    /** Returns the currently selected grid object class.
     **/
    protected Class currentGridObjectClass()
    {
        return ((GridObjectChoice)objComboBox.getSelectedItem()).getObjectClass();
    }

    /** Constructs the specified type of object.  In subclasses that use
     *  the color combo box, this method could be redefined to call the
     *  four-parameter <code>makeObject</code> method, passing it the
     *  current color from the combo box.
     *    @param cls   the type of object to create
     *    @param grid  the grid in which to create the object
     *    @param loc   the location at which to create the object
     **/
    protected void makeObject(Class cls, Grid grid, Location loc)
    {
        try
        {   GridPkgFactory.constructGridObject(cls, grid, loc);   }
        catch (Exception e)
        { reportConstructionError(cls, e, "Grid and Location"); }
    }

    /** Constructs the specified type of object.
     *    @param cls   the type of object to create
     *    @param grid  the grid in which to create the object
     *    @param loc   the location at which to create the object
     *    @param color the color of the new object (some objects may
     *                 ignore this)
     **/
    protected void makeObject(Class cls, Grid grid, Location loc,
                              Color color)
    {
        try
        {
            Class[] paramTypes = {Grid.class, Location.class, 
                                  Color.class};
            Object[] params = {grid, loc, color};
            GridPkgFactory.constructObject(cls, paramTypes, params);
        }
        catch (Exception e)
        {
            try
            {   GridPkgFactory.constructGridObject(cls, grid, loc);   }
            catch (Exception e2)
            { reportConstructionError(cls, e2, 
                "Grid and Location, with or without Color"); }
        }
    }

    /** Reports an error from attempting to construct an object.
     *    @param cls     the class of the failed object
     *    @param e       the exception thrown by the attempt
     *    @param string  string describing parameter types used in attempt
     **/
    protected void reportConstructionError(Class cls, Exception e,
                                           String string)
    {
        JOptionPane.showMessageDialog(parentFrame, "Cannot construct " +
                cls.getName() +
                " with " + string + " parameters.\n"
                + "Reason: " + e,
                "Error constructing grid object",
                JOptionPane.ERROR_MESSAGE);
    }

    /** Leaves the editor, returning to the parent frame. 
     **/
    protected void done()
    {
        // Although the parent already has a reference to the grid
        // being edited, set it anyway so that it updates other aspects
        // of the gui such as buttons that need to be enabled, etc.
        parentFrame.setGrid(getGrid());
        parentFrame.repaint();
        dispose();
    }

}
