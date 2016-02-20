// Class: ComponentModelView
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


import javax.swing.JComponent;

/**
 *  K College GUI Package:<br>
 *
 *  A <code>ComponentModelView</code> is a panel containing a
 *  scrollable graphical view of a swing component.  
 *
 *  @author Alyce Brady
 *  @version 4 April 2004
 **/
public class ComponentModelView extends ScrollablePanel
{
    /** Constructs a new ScrollablePanel object with an empty model
     *  and the specified initial dimensions.
     *      @param width  the width of the viewing area
     *      @param height the height of the viewing area
     **/
    public ComponentModelView(int width, int height)
    {
        this(null, width, height);
    }

    /** Constructs a new ScrollablePanel object with the specified model.
     *      @param model the model this component views
     **/
    public ComponentModelView(JComponent model)
    {
        super(model);
        if ( model != null )
            add(model);         // add the model JComponent to this panel
    }

    /** Constructs a new ScrollablePanel object with the specified model
     *  and initial dimensions.
     *      @param model the model this component views
     *      @param width  the width of the viewing area
     *      @param height the height of the viewing area
     **/
    public ComponentModelView(JComponent model, int width, int height)
    {
        super(model, width, height);
        if ( model != null )
            add(model);
    }

}
