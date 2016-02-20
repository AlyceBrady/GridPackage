// Class: ScrollableGridDisplay
//
// Author: Alyce Brady
//
// This class is based on the College Board's MBSDisplay class, as
// allowed by the GNU General Public License.  MBSDisplay is a
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

import edu.kzoo.grid.Grid;
import edu.kzoo.grid.GridObject;
import edu.kzoo.grid.Location;

import edu.kzoo.grid.gui.GridChangeListener;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.JPanel;
import javax.swing.JViewport;
import javax.swing.Scrollable;
import javax.swing.SwingConstants;
import javax.swing.ToolTipManager;

import java.awt.event.MouseEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.AffineTransform;

/**
 *  Grid Display Package:<br>
 *
 *  A <code>ScrollableGridDisplay</code> is a panel containing a
 *  scrollable graphical display of a grid.
 *
 *  @author Alyce Brady (based on MBSDisplay by Julie Zelenski)
 *  @version 13 February 2004
 **/
public class ScrollableGridDisplay extends JPanel
    implements GridDisplay, GridBackgroundDisplay, GridChangeListener,
    Scrollable, PseudoInfiniteViewport.Pannable
{
    // Class constants
    public static final int DEFAULT_MIN_CELL_SIZE = 8, 
                            DEFAULT_VIEWABLE_SIZE = 420;
    public static final Color OCEAN_BLUE = new Color(75, 75, 255);
    protected static final int LOCATION_TOOL_TIPS = 0,
                               OBJECT_STRING_TOOL_TIPS = 1;

    // Encapsulated data used to monitor/display the grid
    protected Grid theGrid;
    protected Dimension preferredVPSize;   // preferred dimensions of viewing area
    protected int gridLineWidth,           // width of each grid line
                  minCellSize,             // minimum cell size, not including width of grid lines
                  outerCellSize,           // actual cell size, including width of grid lines
                  numRows, numCols,        // number of rows & cols to display in grid
                  originRow, originCol;    // row and column representing origin
    protected GridBackgroundDisplay backgroundDisplay;
    protected Color bgColor;
    protected boolean toolTipsEnabledFlag;
    protected int toolTipsType = OBJECT_STRING_TOOL_TIPS;


    /** Constructs a new ScrollableGridDisplay object with no grid
     *  and an empty view.
     **/
    public ScrollableGridDisplay()
    {
        this(DEFAULT_VIEWABLE_SIZE, DEFAULT_VIEWABLE_SIZE,
             DEFAULT_MIN_CELL_SIZE, OCEAN_BLUE);
    }

    /** Constructs a new ScrollableGridDisplay object with no grid
     *  and an empty view.
     *  (Precondition: <code>width</code> and <code>height</code> must be
     *  at least as large as the default minimum cell size.)
     *  @param width  the width of the viewing area
     *  @param height the height of the viewing area
     **/
    public ScrollableGridDisplay(int width, int height)
    {
        this(width, height, DEFAULT_MIN_CELL_SIZE, OCEAN_BLUE);
    }

    /** Constructs a new ScrollableGridDisplay object with no grid
     *  and an empty view.
     *  @param minimumCellSize minimum cell side length
     **/
    public ScrollableGridDisplay(int minimumCellSize)
    {
        this(DEFAULT_VIEWABLE_SIZE, DEFAULT_VIEWABLE_SIZE,
             minimumCellSize, OCEAN_BLUE);
    }

    /** Constructs a new ScrollableGridDisplay object with no grid
     *  and an empty view.
     *  (Precondition: <code>width</code> and <code>height</code> must be
     *  at least as large as <code>minimumCellSize</code>.)
     *  @param width  the width of the viewing area
     *  @param height the height of the viewing area
     *  @param minimumCellSize minimum cell side length
     **/
    public ScrollableGridDisplay(int width, int height, int minimumCellSize)
    {
        this(width, height, minimumCellSize, OCEAN_BLUE);
    }

    /** Constructs a new ScrollableGridDisplay object with no grid
     *  and an empty view.
     *  @param backgroundColor color to paint background of grid
     **/
    public ScrollableGridDisplay(Color backgroundColor)
    {
        this(DEFAULT_VIEWABLE_SIZE, DEFAULT_VIEWABLE_SIZE,
             DEFAULT_MIN_CELL_SIZE, backgroundColor);
    }

    /** Constructs a new ScrollableGridDisplay object with no grid
     *  and an empty view.
     *  (Precondition: <code>width</code> and <code>height</code> must be
     *  at least as large as the default minimum cell size.)
     *  @param width  the width of the viewing area
     *  @param height the height of the viewing area
     *  @param backgroundColor color to paint background of grid
     **/
    public ScrollableGridDisplay(int width, int height, Color backgroundColor)
    {
        this(width, height, DEFAULT_MIN_CELL_SIZE, backgroundColor);
    }

    /** Constructs a new ScrollableGridDisplay object with no grid
     *  and an empty view.
     *  @param minimumCellSize minimum cell side length
     *  @param backgroundColor color to paint background of grid
     **/
    public ScrollableGridDisplay(int minimumCellSize, Color backgroundColor)
    {
        this(DEFAULT_VIEWABLE_SIZE, DEFAULT_VIEWABLE_SIZE,
             minimumCellSize, backgroundColor);
    }

    /** Constructs a new ScrollableGridDisplay object with no grid
     *  and an empty view.
     *  (Precondition: <code>width</code> and <code>height</code> must be
     *  at least as large as <code>minimumCellSize</code>.)
     *  @param viewingWidth  the width of the viewing area
     *  @param viewingHeight the height of the viewing area
     *  @param minimumCellSize minimum cell side length
     *  @param backgroundColor color to paint background of grid
     **/
    public ScrollableGridDisplay(int viewingWidth, int viewingHeight,
                                int minimumCellSize, Color backgroundColor)
    {
        theGrid = null;

        if ( minimumCellSize > 0 )
            minCellSize = minimumCellSize;
        else
            minCellSize = DEFAULT_MIN_CELL_SIZE;

        int origViewingWidth, origViewingHeight;
        if ( viewingWidth > minCellSize && viewingHeight > minCellSize )
        {
            origViewingWidth = viewingWidth;
            origViewingHeight = viewingHeight;
        }
        else
        {
            origViewingWidth = Math.max(DEFAULT_VIEWABLE_SIZE, minCellSize);
            origViewingHeight = Math.max(DEFAULT_VIEWABLE_SIZE, minCellSize);
        }
        preferredVPSize = new Dimension(origViewingWidth + extraWidth(),
                                        origViewingHeight + extraHeight());

        backgroundDisplay = this;
        if ( backgroundColor != null )
            bgColor = backgroundColor;
        else
            bgColor = OCEAN_BLUE;

        gridLineWidth = calculateGridLineWidth();
        numRows = numCols = 0;
        originRow = originCol = 0;
        setToolTipsEnabled(false);
        addComponentListener(new ComponentAdapter()
            {   public void componentResized(ComponentEvent e)
                    { JViewport vp = getEnclosingViewport();
                      if (vp == null)
                      {
                          recalculateCellSize();
                      }
                    }
            });
    }

    /** Sets the Grid being displayed.  Sets the cell size to
     *  be the largest that fits the entire grid in the current 
     *  viewing area (uses a default minimum if grid is too large).
     *  @param grid the Grid to display
     **/
    public void setGrid(Grid grid)
    {
        JViewport vp = getEnclosingViewport();	// before changing, reset scroll/pan position
        if (vp != null)
            vp.setViewPosition(new Point(0, 0));

        theGrid = grid;
        setToolTipsEnabled(grid != null);

        if ( grid == null )
            return;

        outerCellSize = 0;
        originRow = originCol = 0;

        if ( theGrid.numRows() == -1 )
            numRows = 2000; // This determines the "virtual" size of the pan world
        else
            numRows = theGrid.numRows();
        
        if ( theGrid.numCols() == -1 )
            numCols = 2000; // This determines the "virtual" size of the pan world
        else
            numCols = theGrid.numCols();

        recalculateCellSize();
    }

    /* (non-Javadoc)
     * @see edu.kzoo.grid.gui.GridChangeListener#reactToNewGrid(edu.kzoo.grid.Grid)
     */
    public void reactToNewGrid(Grid newGrid)
    {
        setGrid(newGrid);
    }

    /** Gets our parent viewport, if we are in one.
     **/
    public JViewport getEnclosingViewport()
    {
        Component parent = getParent();
        return (parent instanceof JViewport) ? (JViewport)parent : null;
    }

    /** Calculates the cell size to use given the current viewable region and
     *  the number of rows and columns in the grid.  We use the largest
     *  cellSize that will fit in the viewable region, bounded to be at least
     *  the parameter minSize.
     *  @param vp        the view port that represents the viewable region
     **/   
    protected void recalculateCellSize()
    {
        if (numRows == 0 || numCols == 0) 
        {
            outerCellSize = 0;
        } 
        else 
        {
            JViewport vp = getEnclosingViewport();
            Dimension windowSize = (vp != null) ? vp.getSize() : getSize();
            int viewingHeight = windowSize.height - extraHeight();
            int viewingWidth = windowSize.width - extraWidth();
            outerCellSize = Math.min(viewingHeight/numRows,
                                     viewingWidth/numCols);
            int innerCellSize = outerCellSize - gridLineWidth;
            innerCellSize = Math.max(innerCellSize, minCellSize);
            outerCellSize = innerCellSize + gridLineWidth;
        }
        revalidate();
    }

    /** Returns the width of each grid line.
     **/
    protected int calculateGridLineWidth()
    {
        return (int)Math.round(Math.ceil((new BasicStroke()).getLineWidth()));
    }

    /** Returns the size of each cell, not including the width of a grid line.
     **/
    public int innerCellSize()
    {
        return outerCellSize - gridLineWidth;
    }

    /** Gets the grid. **/
    public Grid grid()
    {
        return theGrid;
    }

    /** Gets the minimum cell size. **/
    public int minimumCellSize()
    {
        return minCellSize;
    }

    /** Sets the object used to draw the background. **/
    public void setBackgroundDisplay(GridBackgroundDisplay bgDisplay)
    {
        backgroundDisplay = bgDisplay;
    }

    /** Gets the background color for displaying the grid. **/
    public Color backgroundColor()
    {
        return bgColor;
    }

    /** Sets the background color for displaying the grid. **/
    public void setBackgroundColor(Color newBackgroundColor)
    {
        bgColor = newBackgroundColor;
    }

    /** Makes the gridlines visible or invisible, depending on the value
     *  of the <code>visible</code> parameter.  The gridlines are visible
     *  by default.
     *  @param visible whether to make the gridlines visible (<code>true</code>)
     *                 or invisible (<code>false</code>)
     **/
    public void makeGridLinesVisible(boolean visible)
    {
        gridLineWidth = visible ? calculateGridLineWidth() : 0;
    }

    /** Returns <code>true</code> if the grid lines are visible,
     *  <code>false</code> otherwise.
     **/
    public boolean gridLinesAreVisible()
    {
        return gridLineWidth > 0;
    }

    /** Shows the grid.
     *  Invoking the <code>repaint</code> method is the standard way to ask a
     *  Swing component to redraw itself. This eventually turns into a call
     *  back to our version of the standard <code>paintComponent</code>
     *  method where we do the actual drawing.
     **/
    public void showGrid()
    {
        repaint();
    }

    /** Updates the display of just a single location on the grid.
     *  Does not redraw the gridlines.
     **/
    public void updateLocation(Location loc)
    {
        // Find the object to redraw.
        if (grid() == null)
            return;

        // Get the screen location for this grid location.
        Rectangle cellOnScreen =
            new Rectangle(colToXCoord(loc.col()), 
                          rowToYCoord(loc.row()), 
                          innerCellSize(), innerCellSize());

        Graphics2D g2 = (Graphics2D) getGraphics();
        AffineTransform savedTransform = g2.getTransform(); // save
            
        // Redraw the background for just this one location.
        g2.setColor(bgColor);  // fill background
        g2.fillRect(cellOnScreen.x, cellOnScreen.y, 
                    cellOnScreen.width, cellOnScreen.height); 

        // Draw the object.
        GridObject obj = grid().objectAt(loc);
        if ( obj != null )
        {           
            // Get the drawing object to display this grid object.
            GridObjectDisplay displayObj = DisplayMap.findDisplayFor(obj);
            displayObj.draw(obj, this, g2, cellOnScreen);
        }
            
        g2.setTransform(savedTransform); // restore coordinate system

    }

    /** Paints this component.
     *  @param g the Graphics object to use to render this component 
     **/
    public void paintComponent(Graphics g)
    {
        Graphics2D g2 = (Graphics2D)g;
        
        super.paintComponent(g2);
        if (grid() == null)
            return;
        
        backgroundDisplay.drawBackground(g2);

        GridObject[] allGridObjects = grid().allObjects();
        for (int k = 0; k < allGridObjects.length; k++) 
            drawGridObject(g2, allGridObjects[k]);

        if ( gridLinesAreVisible() )
            drawGridlines(g2);
    }

    /** Draws the grid background.
     *    @param g2 the Graphics2 object to use to render 
     **/
    public void drawBackground(Graphics2D g2)
    {
        fillBackground(g2, bgColor);
    }

    /** Fills the grid background with the specified color.  At the
     *  end of this method, the graphics context is set to draw
     *  whatever color it was set to draw when the method was called,
     *  not the fill color.
     *    @param g2         the Graphics2 object to use to render
     *    @param fillColor  the color with which to fill the background 
     **/
    public void fillBackground(Graphics2D g2, Color fillColor)
    {
        Color oldColor = g2.getColor();
        Insets insets = getInsets();
        g2.setColor(fillColor);
        g2.fillRect(insets.left, insets.top, 
                    numCols*outerCellSize + gridLineWidth,
                    numRows*outerCellSize + gridLineWidth);
        g2.setColor(oldColor);
    }

    /** Draws the gridlines for the grid.  We only draw the portion
     *  of the lines that intersect the current clipping bounds.
     *  @param g2 the Graphics2 object to use to render 
     **/
    protected void drawGridlines(Graphics2D g2)
    {
        Rectangle curClip = g2.getClip().getBounds();
        int top = getInsets().top, left = getInsets().left;

        int cellSize = outerCellSize;
        int miny = Math.max(0, (curClip.y - top)/cellSize)*cellSize + top;
        int minx = Math.max(0, (curClip.x - left)/cellSize)*cellSize + left;
        int maxy = Math.min(numRows,
            (curClip.y + curClip.height - top + cellSize - 1)/cellSize)*cellSize + top;
        int maxx = Math.min(numCols,
            (curClip.x + curClip.width - left + cellSize - 1)/cellSize)*cellSize + left;

        g2.setColor(Color.black);
        g2.setStroke(new BasicStroke());
        for (int y = miny; y <= maxy; y += cellSize) // draw horizontal lines
            g2.drawLine(minx, y, maxx, y);
    
        for (int x = minx; x <= maxx; x += cellSize) // draw vertical lines
            g2.drawLine(x, miny, x, maxy);
    }

    /** Draws one GridObject instance.  First verifies that the object
     *  is actually visible before any drawing, sets up the clip
     *  appropriately and uses the DisplayMap to determine
     *  which object to call upon to render this particular GridObject.
     *  Note that we save and restore the graphics transform to
     *  restore back to normalcy no matter what the renderer did to
     *  to the coordinate system. 
     *  @param g2 the Graphics2D object to use to render 
     *  @param obj the GridObject object to draw
     **/
    protected void drawGridObject(Graphics2D g2, GridObject obj)
    {
        // Make sure that obj hasn't been removed from the grid
        // since it was placed in the list of objects to draw
        if ( obj.grid() != grid() )
            return;

        Location objLoc = obj.location();
        Rectangle cellToDraw =
            new Rectangle(colToXCoord(objLoc.col()), 
                          rowToYCoord(objLoc.row()), 
                          innerCellSize(), innerCellSize());

        // Only draw if the object is visible within the current clipping region.
        if (cellToDraw.intersects(g2.getClip().getBounds())) 
        {	
            AffineTransform savedTransform = g2.getTransform(); // save
            
            // Get the drawing object to display this grid object.
            GridObjectDisplay displayObj = DisplayMap.findDisplayFor(obj);
            displayObj.draw(obj, this, g2, cellToDraw);
            
            g2.setTransform(savedTransform); // restore coordinate system
        }
    }


  // The following methods implement the Scrollable interface to get nicer
  // behavior in a JScrollPane.

    /* (non-Javadoc)
     * @see javax.swing.Scrollable#getScrollableUnitIncrement(java.awt.Rectangle, int, int)
     */
    public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) 
    { 
        return outerCellSize;
    }

    /* (non-Javadoc)
     * @see javax.swing.Scrollable#getScrollableBlockIncrement(java.awt.Rectangle, int, int)
     */
    public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) 
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
        return preferredVPSize;
    }


  // The following methods provide zooming and panning behavior.

    /** Zooms in the display by doubling the current cell size.
     **/
    public void zoomIn()
    {
        outerCellSize *= 2;
        revalidate();
    }

    /** Zooms out the display by halving the current cell size.
     **/
    public void zoomOut()
    {
        outerCellSize = Math.max(outerCellSize/2, minCellSize);
        revalidate();
    }

    /** Pans the display back to the origin, so that 0, 0 is at the
     *  the upper left of the visible viewport.
     **/
    public void recenterOnOrigin()
    {
        originRow = 0;
        originCol = 0;
        repaint();
        JViewport vp = getEnclosingViewport();
        if (vp != null)
        {
            if (!isPannableUnbounded() || !(vp instanceof PseudoInfiniteViewport))
                vp.setViewPosition(new Point(0, 0));
            else
                ((PseudoInfiniteViewport)vp).showOriginTip();
        }
    }

  // The following methods implement the PseudoInfiniteViewport.Pannable
  // interface to get nicer pan behavior for viewing an unbounded grid.

    public void panBy(int hDelta, int vDelta)
    {
        originCol += hDelta/outerCellSize;
        originRow += vDelta/outerCellSize;
        repaint();
    }

    public boolean isPannableUnbounded() 
    { 
        return (theGrid != null && theGrid.numRows() == -1); 
    }

    public String getPannableTipText()
    {
        Point upperLeft = new Point(0, 0);
        JViewport vp = getEnclosingViewport();
        if (!isPannableUnbounded() && vp != null) upperLeft = vp.getViewPosition();
        Location loc = locationForPoint(upperLeft);
        return (loc != null ? loc.toString() : null);
    }


  // The following methods support tool tips.
        
    /** Enables/disables showing of tooltip giving information about
     *  the grid object beneath the mouse.
     *  @param flag whether to enable/disable tool tips
     **/
    public void setToolTipsEnabled(boolean flag)
    {
        if (flag)
            ToolTipManager.sharedInstance().registerComponent(this);  
        else
            ToolTipManager.sharedInstance().unregisterComponent(this);
        toolTipsEnabledFlag = flag;
    }

    /** Indicates whether tool tips have been enabled.
     *  @return <code>true</code> if tool tips are enabled; 
     *          <code>false</code> otherwise
     **/
    public boolean toolTipsEnabled()
    {
        return toolTipsEnabledFlag;
    }

    /** Sets tool tips to provide information about the locations
     *  of cells in the grid.
     **/
    public void makeToolTipsReportLocation()
    {
        setToolTipsEnabled(true);
        toolTipsType = LOCATION_TOOL_TIPS;
    }

    /** Sets tool tips to provide information about the contents
     *  of cells in the grid.
     **/
    public void makeToolTipsReportObject()
    {
        setToolTipsEnabled(true);
        toolTipsType = OBJECT_STRING_TOOL_TIPS;
    }

    /** Given a MouseEvent, determines what text to place in the floating
     *  tool tip when the the mouse hovers over this location.  If the mouse
     *  is over a valid cell in the grid, we provide some information about
     *  the cell and its contents.  This method is  automatically called
     *  on mouse-moved events if we register for tool tips.
     *  @param evt the MouseEvent in question 
     *  @return the tool tip string for this location
     **/   
    public String getToolTipText(MouseEvent evt)
    {
        Location loc = locationForPoint(evt.getPoint());
        if (!toolTipsEnabled() || loc == null)
            return null;
        if ( toolTipsType == OBJECT_STRING_TOOL_TIPS )
        {
            GridObject obj = grid().objectAt(loc);
            if ( obj != null )
                return obj.toString();
            else
                return loc + " is empty";
        }
        else
            return loc.toString();
    }

    /** Given a Point, determines which grid location (if any)
     *  is under the mouse.  This method is used by the GUI when
     *  generating text tips.
     *  @param p the Point in question (in display's coordinate system)
     *  @return the grid Location beneath the event, or null if
     *               no cell is beneath the mouse
     **/
    public Location locationForPoint(Point p)
    {
        if ( theGrid == null )
            return null;
        Location loc = new Location(yCoordToRow(p.y), xCoordToCol(p.x));
        return (theGrid.isValid(loc)) ? loc : null;
    }

    // protected helpers to convert between (x,y) and (row,col)
    /** Returns column corresponding to given X-coordinate. **/
    protected int xCoordToCol(int xCoord)
    {
        return (xCoord - getInsets().left - gridLineWidth)/outerCellSize + originCol;
    }

    /** Returns row corresponding to given Y-coordinate. **/
    protected int yCoordToRow(int yCoord)
    {
        return (yCoord - getInsets().top - gridLineWidth)/outerCellSize + originRow;
    }

    /** Returns X-coordinate of left side of given column. **/
    public int colToXCoord(int col)
    {
        return (col - originCol)*outerCellSize + getInsets().left + gridLineWidth;
    }

    /** Returns Y-coordinate of top of given row. **/
    public int rowToYCoord(int row)
    {
        return (row - originRow)*outerCellSize + getInsets().top + gridLineWidth;
    }


  // The following methods are used by the Java GUI classes.

    /** Returns the desired size of the display, for use by layout manager.
     *  @return preferred size
     **/
    public Dimension getPreferredSize()
    {
       return new Dimension(numCols*outerCellSize + extraWidth(), 
                            numRows*outerCellSize + extraHeight());
    }

    /** Returns the minimum size of the display, for use by layout manager.
     *  @return minimum size
     **/
    public Dimension getMinimumSize()
    {
        return new Dimension(numCols*minCellSize + extraWidth(), 
                             numRows*minCellSize + extraHeight());
    }

    // protected helpers to caculate extra width/height needs for borders/insets.
    protected int extraWidth() 
    { 
        return getInsets().left + getInsets().right + gridLineWidth;
    }

    protected int extraHeight() 
    { 
        return getInsets().top + getInsets().bottom + gridLineWidth; 
    }

}
