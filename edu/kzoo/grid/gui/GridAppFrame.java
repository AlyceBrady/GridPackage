// Class GridAppFrame
//
// Author: Alyce Brady
//
// This class is based on the College Board's MBSGUIFrame class,
// as allowed by the GNU General Public License.  MBSGUIFrame
// is a black-box class within the AP(r) CS Marine Biology Simulation
// case study.
// (See www.collegeboard.com/student/testing/ap/compsci_a/case.html)
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

import edu.kzoo.kgui.ModelWindow;
import edu.kzoo.kgui.ScrollablePanel;

import edu.kzoo.grid.Grid;
import edu.kzoo.grid.Location;

import edu.kzoo.grid.display.GridDisplay;
import edu.kzoo.grid.display.PseudoInfiniteViewport;
import edu.kzoo.grid.display.ScrollableGridDisplay;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JMenu;
import javax.swing.JScrollPane;

/**
 *  Grid GUI Support Package:<br>
 *
 *  The <code>GridAppFrame</code> class provides a window in which
 *  to display a grid and its contents.  Options include a speed slider
 *  bar and a control panel containing a Start/Restart/Reset button.
 *
 *  @author Alyce Brady (based on code by Julie Zelenski)
 *  @version 29 February 2004
 **/
public class GridAppFrame extends ModelWindow implements GridDisplay
{
  // instance variables
    private JMenu helpMenu = null;
    private int   minCellSize;
    private Color bgColor;


  // constructor, methods that specify which components to include
  //   in the window, and constructWindowContents method

    /** Constructs an empty GridAppFrame window object that will
     *  display a grid.
     *  Use methods such as <code>includeStartRestart</code> and
     *  <code>includeSpeedSlider</code> to include components on the
     *  window other than the basic grid display.
     *  Use the constructWindowContents method to set the properties of the
     *  window and make it visible.
     **/
    public GridAppFrame()
    {
    }

    /** Constructs the body of a window containing a scrollable
     *  display for a grid and its contents.
     *    @param title frame title
     *    @param bgColor       color to paint background of grid
     *    @param viewingWidth  the width of the viewing area
     *    @param viewingHeight the height of the viewing area
     *    @param minCellSize   minimum grid cell side length
     **/
    public void constructWindowContents(String title, Color bgColor,
                                        int viewingWidth, int viewingHeight,
                                        int minCellSize)
    {
        super.constructWindowContents(title, viewingWidth, viewingHeight);
        this.minCellSize = minCellSize;
        this.bgColor = bgColor;
        getGraphicalModelViewer().addMouseListener(getMouseListenerForDisplay());
    }

    /** Constructs an empty grid viewer at the heart of the graphical
     *  user interface.  Should be redefined in subclasses that use
     *  a subclass of ScrollableGridDisplay.
     *    @param viewingWidth  the width of the viewing area
     *    @param viewingHeight the height of the viewing area
     *    @return a component for viewing the model
     **/
    protected ScrollablePanel constructViewer(int viewingWidth,
                                              int viewingHeight)
    {
        return new ScrollableGridDisplay(viewingWidth, viewingHeight,
                                         minCellSize, bgColor);
    }

  // methods that access this object's state, including methods
  // required by the GridDisplay interface

    /** Sets the grid being displayed.
     *    @param grid the Grid to display
     **/
    public void setGrid(Grid grid)
    {
        setModel(grid, getGraphicalModelViewer());
    }

    /** Returns the grid at the center of this graphical user
     *  interface.
     *    @return the grid
     */
    protected Grid getGrid()
    {
        // This is NOT type-safe !!!
        //   (They could call setModel instead of setGrid.)
        return (Grid) getModel();
    }

    /** Shows the grid.
     *  (Precondition: must have called <code>setGrid</code>.)
     **/
    public void showGrid()
    {
        showModel();
    }

    /** Gets the object that provides the graphical view of the object.
     *    @return the object that provides the graphical view of the object
     **/
    protected ScrollableGridDisplay getGridDisplay()
    {
        // This is NOT type-safe !!!
        return (ScrollableGridDisplay) getGraphicalModelViewer();
    }

    /** Creates the panel for displaying and controlling a model
     *  application.
     **/
    protected JScrollPane makeDisplayPanel()
    {
        JScrollPane scrollPane = super.makeDisplayPanel();
        PseudoInfiniteViewport vp = new PseudoInfiniteViewport(scrollPane);
        scrollPane.setViewport(vp);
        return scrollPane;
    }


    /** Returns a mouse adapter that responds to mouse presses over
     *  the grid display.
     *  Subclasses that wish to respond to other mouse events should
     *  redefine this method to return a subclass of DisplayMouseListener
     *  (or another MouseAdapter subclass) that handles other mouse events.
     **/
    protected MouseAdapter getMouseListenerForDisplay()
    {
        // Return an instance of an anonymous inner subclass of MouseAdapter.
        return new DisplayMouseListener();
    }


  // Methods for handling user-initiated events

    /** Handles a mouse press over the grid display.
     *  Currently does nothing, but can be redefined in subclasses
     *  to handle actions.
     **/
    protected void onMousePressOverDisplay(Location loc)
    {
    }

  // Nested class for handling mouse events over the grid display

    /** Nested class that handles simple mouse presses over the grid
     *  display.  Can be extended to handle other mouse events (mouse
     *  release, mouse click, etc.).
     **/
    public class DisplayMouseListener extends MouseAdapter
    {
        // Redefined method from MouseAdapter
        public void mousePressed(MouseEvent evt)
        {
            onMousePressOverDisplay(getMouseLocation(evt));
        }

        /** Returns the Location in the grid corresponding to the location
         *  of the mouse event.
         **/
        public Location getMouseLocation(MouseEvent evt)
        {
            return getGridDisplay().locationForPoint(evt.getPoint());
        }
    }

}


