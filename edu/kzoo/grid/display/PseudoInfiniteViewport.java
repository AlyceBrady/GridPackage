// AP(r) Computer Science Marine Biology Simulation:
// The PseudoInfiniteViewport class is copyright(c) 2002 College Entrance
// Examination Board (www.collegeboard.com).
//
// This class is free software; you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation.
//
// This class is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.

package edu.kzoo.grid.display;

import java.awt.Point;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolTip;
import javax.swing.JViewport;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

/**
 *  AP&reg; Computer Science Marine Biology Simulation:<br>
 *  A <code>PseudoInfiniteViewport</code> is a
 *  <code>JViewport</code> subclass that
 *  translates scroll actions into pan actions across an
 *  unbounded view. 
 *
 *  <p>
 *  The <code>PseudoInfiniteViewport</code> class is
 *  copyright&copy; 2002 College Entrance Examination Board
 *  (www.collegeboard.com).
 *
 *  @author Julie Zelenski
 *  @version 1 August 2002
 **/

public class PseudoInfiniteViewport extends JViewport
{
    /** The Pannable interface contains those methods the view
     *  installed in a PseudoInfiniteViewport needs to support
     *  to enable panning behavior along with scrolling.
     **/
    public interface Pannable 
    {  
        void panBy(int hDelta, int vDelta);
        boolean isPannableUnbounded();
        String getPannableTipText(); //  return null if no tip desired
   }

    private static final int ORIGIN_TIP_DELAY = 1000;

    private JScrollPane scrollParent;
    private JPanel glassPane;
    private JToolTip originTip;
    private Timer originTipTimer;
    private Point panPoint = new Point(0,0);
    
    /** Construct a new PseudoInfiniteViewport object for the given scrollpane.
     *  @param parent the JScrollPane for which this will be the viewport
     **/
    public PseudoInfiniteViewport(JScrollPane parent)
    {
        scrollParent = parent;
        setBackground(Color.lightGray);
    }
    
    /** Sets the view position (upper left) to a new point. Overridden from
     *  JViewport to do a pan, instead of scroll, on an unbounded view.
     *  @param pt the Point to become the upper left
     **/
    public void setViewPosition(Point pt)
    {
        boolean isAdjusting = scrollParent.getVerticalScrollBar().getValueIsAdjusting()
                    || scrollParent.getHorizontalScrollBar().getValueIsAdjusting();
        boolean changed = true;
        
        if (viewIsUnbounded()) 
        {
            int hDelta = pt.x - panPoint.x;
            int vDelta = pt.y - panPoint.y;
            if (hDelta != 0 && vDelta == 0)
                getPannableView().panBy(hDelta, vDelta);
            else if (vDelta != 0 && hDelta == 0)
                getPannableView().panBy(hDelta, vDelta);
            else
                changed = false; // no pan action was taken
            panPoint = pt;
            if (!panPoint.equals(getPanCenterPoint()) && !isAdjusting) 
            {                            // needs recentering
                panPoint = getPanCenterPoint();
                fireStateChanged();	// update scrollbars to match
            }
        } 
        else 	// ordinary scroll behavior
        {
            changed = !getViewPosition().equals(pt);
            super.setViewPosition(pt);
        }
        if (changed || isAdjusting) showOriginTip(); // briefly show tip
    }
    
    
    /** Returns current view position (upper left). Overridden from
     *  JViewport to use pan center point for unbounded view.
     **/
    public Point getViewPosition()
    {
        return (viewIsUnbounded() ? getPanCenterPoint() : super.getViewPosition());
    }
    
    /** Returns current view size. Overridden from
     *  JViewport to use preferred virtual size for unbounded view.
     **/
    public Dimension getViewSize()
    {
        return (viewIsUnbounded() ? getView().getPreferredSize() : super.getViewSize());
    }
            
    /** Shows a tool tip over the upper left corner of the viewport 
     *  with the contents of the pannable view's pannable tip text
     *  (typically a string identifiying the corner point). Tip is
     *  removed after a short delay.
     **/
    public void showOriginTip()
    {
        if (getRootPane() == null) return;
        // draw in glass pane to appear on top of other components
        if (glassPane == null) 
        {
            getRootPane().setGlassPane(glassPane = new JPanel());
            glassPane.setOpaque(false);
            glassPane.setLayout(null);	// will control layout manually
            glassPane.add(originTip = new JToolTip());
            originTipTimer = new Timer(ORIGIN_TIP_DELAY, new ActionListener() {
                public void actionPerformed(ActionEvent evt) { glassPane.setVisible(false); }});
            originTipTimer.setRepeats(false);
        }
        String tipText = getPannableView().getPannableTipText();
        if (tipText == null) return;
        
        // set tip text to identify current origin of pannable view
        originTip.setTipText(tipText);
        
        // position tip to appear at upper left corner of viewport
        originTip.setLocation(SwingUtilities.convertPoint(this, getLocation(), glassPane));
        originTip.setSize(originTip.getPreferredSize());
        
        // show glass pane (it contains tip)
        glassPane.setVisible(true);
        
        // this timer will hide the glass pane after a short delay
        originTipTimer.restart();
    }
    
    
    // some simple private helpers 
    
    private Pannable getPannableView()
    {
        return (Pannable)getView();
    }
    
    private boolean viewIsUnbounded()
    {
        Pannable p = getPannableView();
        return (p != null && p.isPannableUnbounded());
    }
    
    private Point getPanCenterPoint()
    {
        Dimension size = getViewSize();
        return new Point(size.width/2, size.height/2);
    }


}
