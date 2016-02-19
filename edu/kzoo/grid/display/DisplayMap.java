// Class: DisplayMap
//
// This is a modified version of the College Board's DisplayMap class,
// as allowed by the GNU General Public License.  DisplayMap is a
// black-box GUI class within the AP(r) CS Marine Biology Simulation
// case study (see www.collegeboard.com/ap/students/compsci).
//
// The modifications were to make DisplayMap work with GridObject and
// GridObjectDisplay objects.
//
// The original copyright and license information for DisplayMap is:
//
// AP(r) Computer Science Marine Biology Simulation:
// The DisplayMap class is copyright(c) 2002 College Entrance
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

import edu.kzoo.grid.GridObject;

import java.util.HashMap;

/**
 *  Grid Display Package:<br>
 *
 *  <code>DisplayMap</code> is a collection that maps grid
 *  object classes to objects that know how to display them.
 *
 *  <p>
 *  The <code>DisplayMap</code> class is based on the
 *  College Board's <code>DisplayMap</code> class,
 *  as allowed by the GNU General Public License.
 *
 *  @author Alyce Brady
 *  @author Jeff Raab, Northeastern University
 *  @version 13 December 2003
 **/
public class DisplayMap
{
    // Class Variables: Not tied to any one DisplayMap object
    private static HashMap map = new HashMap();  // the collection
    private static GridObjectDisplay defaultDisplay = new DefaultDisplay();

    /** Associates a display object with a grid object class. If
     *  no class can be found for that name, an error message is printed.
     *  @param gridObjClassname the name of a class whose objects might be
     *                      in an <code>Grid</code>
     *  @param displayObj object that knows how to display
     *                      objects of class <code>gridObjClassname</code>
     **/
    public static void associate(String gridObjClassname,
                                 GridObjectDisplay displayObj)
    {
        try 
        {
            // Store the actual class rather than the classname in the map.
            Class gridObjClass = Class.forName(gridObjClassname);
            map.put(gridObjClass, displayObj);
        } 
        catch (ClassNotFoundException e) 
        {
             System.err.println("DisplayMap was unable to find class named " + 
                                    gridObjClassname + 
                                    " to associate with display object.\n" +
                                    "Will use default display instead." );
        } 
    }

    /** Finds a display class that knows how to display the given object.
     *  @param obj  the object to display
     **/
    public static GridObjectDisplay findDisplayFor(GridObject obj)
    {
        // Go up through the class hierarchy for obj and see
        // if there is a display for its class or superclasses.
        for ( Class c = obj.getClass(); c != Object.class; 
              c = c.getSuperclass() )
        {
            GridObjectDisplay display = (GridObjectDisplay) map.get(c);
            if ( display != null )
                return display;
        }

        // No specific display found; use default display for generic
        // GridObject instance.
        return defaultDisplay;
    }
}
