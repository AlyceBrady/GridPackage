// Class JComponentModelWindow
//
// Author: Alyce Brady
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

import javax.swing.JComponent;

/**
 *  K College GUI Package:<br>
 *
 *  The <code>ModelWindow</code> class provides a window in which
 *  to graphically view a model and its contents.  Optional
 *  window components include a menu bar, a speed slider to
 *  control how long the graphical user interface should pause
 *  after displaying the model (useful for animations or other
 *  repeated displays of a changing model), and a control
 *  panel containing a Start/Restart/Reset button.
 *
 *  @author Alyce Brady (based on code by Julie Zelenski)
 *  @version 31 March 2004
 **/
public class JComponentModelWindow extends ModelWindow
{

    /** Constructs the model viewer at the heart of the graphical
     *  user interface which, in this case, provides a view of a
     *  simple JComponent.
     *    @param viewingWidth  the width of the viewing area
     *    @param viewingHeight the height of the viewing area
     *    @return a component for viewing the model
     **/
//    protected ScrollablePanel constructViewer(Object model,
//                                                 int viewingWidth,
//                                                 int viewingHeight)
//    {
//        return new ComponentModelView((JComponent)model,
//                                               viewingWidth, viewingHeight);
//    }

    /** Sets the model being displayed.
     *    @param model the model to graphically view
     **/
    public void setViewerContents(JComponent model)
    {
        super.setModel(model, new ComponentModelView(model));
    }

}


