// Class: ScrollablePanel
//
// Author: Alyce Brady
//
// This class is based on the College Board's MBSDisplay class, as
// allowed by the GNU General Public License.  MBSDisplay is a
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

package edu.kzoo.kgui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.JPanel;
import javax.swing.JViewport;
import javax.swing.Scrollable;
import javax.swing.SwingConstants;

/**
 *  K College GUI Package:<br>
 *
 *  A <code>ScrollablePanel</code> is a panel containing a
 *  scrollable graphical view of a model.  Subclasses should
 *  add components to the panel or should override the
 *  <code>paintComponent</code> method to actually display the
 *  model. 
 *
 *  @author Alyce Brady (based on MBSDisplay by Julie Zelenski)
 *  @version 31 March 2004
 **/
public class ScrollablePanel extends JPanel
    implements GraphicalModelView, Scrollable
{
    // Encapsulated data used to monitor/display the grid
    protected Object theModel;
    protected Dimension preferredVPSize;   // preferred dimensions of viewing area

    /** Constructs a new ScrollablePanel object with an empty model
     *  and the specified initial dimensions.
     *      @param width  the width of the viewing area
     *      @param height the height of the viewing area
     **/
    public ScrollablePanel(int width, int height)
    {
        this(null, width, height);
    }

    /** Constructs a new ScrollablePanel object with the specified model.
     *      @param model the model this component views
     **/
    protected ScrollablePanel(Object model)
    {
        theModel = model;
    }

    /** Constructs a new ScrollablePanel object with the specified model
     *  and initial dimensions.
     *      @param model the model this component views
     *      @param width  the width of the viewing area
     *      @param height the height of the viewing area
     **/
    protected ScrollablePanel(Object model, int width, int height)
    {
        theModel = model;
        setPreferredViewingSize(new Dimension(width + extraWidth(),
                                              height + extraHeight()));
    }

    /** Gets the model being displayed. **/
    public Object model()
    {
        return theModel;
    }

    /** Shows the model.
     *  Invoking the <code>repaint</code> method is the standard way to ask a
     *  Swing component to redraw itself. This eventually turns into a call
     *  back to our version of the standard <code>paintComponent</code>
     *  method where we do the actual drawing.
     **/
    public void showModel()
    {
        repaint();
    }

    /** Sets or changes the preferred viewing size.
     *    @param preferredSize the preferred size for the viewport view
     **/
    protected void setPreferredViewingSize(Dimension preferredSize)
    {
        preferredVPSize = preferredSize;
    }

    /** Gets the viewing size of this component.  The viewing size may
     *  be the complete size of this component, or it may be the size of
     *  the portion of this component viewable through a viewport.
     *    @return the viewable size of this component
     **/
    public Dimension getViewingSize()
    {
        JViewport vp = getEnclosingViewport();
        return (vp != null) ? vp.getSize() : getSize();
    }

    /** Gets our parent viewport, if we are in one.
     **/
    public JViewport getEnclosingViewport()
    {
        Component parent = getParent();
        return (parent instanceof JViewport) ? (JViewport)parent : null;
    }

    /* (non-Javadoc)
     * @see javax.swing.Scrollable#getScrollableUnitIncrement(java.awt.Rectangle, int, int)
     */
    public int getScrollableUnitIncrement(Rectangle visibleRect,
                                          int orientation, int direction) 
    { 
        if (orientation == SwingConstants.VERTICAL)
            return (int)(visibleRect.height * .1);
        else
            return (int)(visibleRect.width * .1);
    }

    /* (non-Javadoc)
     * @see javax.swing.Scrollable#getScrollableBlockIncrement(java.awt.Rectangle, int, int)
     */
    public int getScrollableBlockIncrement(Rectangle visibleRect,
                                           int orientation, int direction) 
    { 
        if (orientation == SwingConstants.VERTICAL)
            return (int)(visibleRect.height * .9);
        else
            return (int)(visibleRect.width * .9);
    }

    /* (non-Javadoc)
     * @see javax.swing.Scrollable#getScrollableTracksViewportWidth()
     */
    public boolean getScrollableTracksViewportWidth() 
    {  
        return false;
    }

    /* (non-Javadoc)
     * @see javax.swing.Scrollable#getScrollableTracksViewportHeight()
     */
    public boolean getScrollableTracksViewportHeight() 
    { 
         return false;
    }

    /* (non-Javadoc)
     * @see javax.swing.Scrollable#getPreferredScrollableViewportSize()
     */
    public Dimension getPreferredScrollableViewportSize() 
    {
        if ( preferredVPSize != null )
            return preferredVPSize;
        else
            return getPreferredSize();
    }

    // protected helpers to caculate extra width/height needs for borders/insets.
    protected int extraWidth() 
    { 
        return getInsets().left + getInsets().right;
    }

    protected int extraHeight() 
    { 
        return getInsets().top + getInsets().bottom; 
    }

}
