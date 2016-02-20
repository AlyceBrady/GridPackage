// Class: TextCellDisplay
//
// Author: Alyce Brady
//   Modified: 21 March 2004: Modified to handle any class that has text and
//                            color methods, not just TextCell objects.
//   Modified: 15 September 2004: Most functionality moved to the TextDisplay
//                                class.  TextCellDisplay now extends
//                                TextDisplay rather than DefaultDisplay.
//
// This class is based on the College Board's DefaultDisplay class,
// as allowed by the GNU General Public License.  DefaultDisplay is a
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

import edu.kzoo.grid.GridObject;

import java.awt.Color;

/**
 *  Grid Display Package:<br>
 *
 *    A <code>TextCellDisplay</code> object displays a
 *    <code>TextCell</code> object (or any object with
 *    <code>text</code> and <code>color</code> methods)
 *    in a grid.
 *
 *  @author Alyce Brady
 *  @version 15 September 2004
 **/
public class TextCellDisplay extends TextDisplay
{

    /** Gets the text string to draw.
     *  (Precondition: <code>obj</code> has a <code>text</code> method.)
     **/
    protected String getText(GridObject obj)
    {
        return (String) invokeAccessorMethod(obj, "text");
    }

    /** Gets the text color.
     *  (Precondition: <code>obj</code> has a <code>color</code> method.)
     */
    protected Color getTextColor(GridObject obj)
    {
        return (Color) invokeAccessorMethod(obj, "color");
    }

}
