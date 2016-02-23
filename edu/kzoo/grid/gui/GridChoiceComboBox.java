// Class GridChoiceComboBox
//
// Author: Alyce Brady
//
// This class is based on the College Board's CreateEnvDialog class,
// as allowed by the GNU General Public License.  CreateEnvDialog
// is a black-box class within the AP(r) CS Marine Biology Simulation
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

import edu.kzoo.grid.BoundedGrid;
import edu.kzoo.grid.ArrayListGrid;

import javax.swing.JComboBox;
import java.util.Set;

/**
 *  Grid GUI Support Package:<br>
 *
 *  A <code>GridChoiceComboBox</code> is a dialog that allows the user to
 *  choose the type of grid to be created and, if appropriate, its
 *  dimensions.
 *
 *  @author Alyce Brady (based on code by Julie Zelenski)
 *  @version 15 December 2003
 **/
public class GridChoiceComboBox extends JComboBox<GridChoiceComboBox.GridChoice>
{    
    private static final long serialVersionUID = 3532436510757444069L;

    private GridChoice gridChoice;

    /** Adds bounded grid classes to the combo box.  If the
     *  GridPkgFactory has a list of bounded grids, that list is
     *  used.  Otherwise, only the BoundedGrid class is added.
     **/
    public void addBoundedGrids()
    {
        Set<Class> gridClasses = GridPkgFactory.boundedGridClasses();
        if ( gridClasses.isEmpty() )
            addItem(new GridChoice(BoundedGrid.class, true));
        else
        {
            for ( Class gridClass : gridClasses )
                addItem(new GridChoice(gridClass, true));
        }
    }

    /** Adds unbounded grid classes to the combo box.  If the
     *  GridPkgFactory has a list of unbounded grids, that list is
     *  used.  Otherwise, only the UnboundedArrayListGrid class is added.
     **/
    public void addUnboundedGrids()
    {
        Set<Class> gridClasses = GridPkgFactory.unboundedGridClasses();
        if ( gridClasses.isEmpty() )
            addItem(new GridChoice(ArrayListGrid.Unbounded.class, false));
        else
        {
            for ( Class gridClass : gridClasses )
                addItem(new GridChoice(gridClass, false));
        }
    }

    /** Returns the class associated with the selected item in the
     *  combo box.
     **/
    public Class getSelectedClass()
    {
        return ((GridChoice)getSelectedItem()).gridClass();
    }

    /** Returns <code>true</code> if the class associated with the
     *  selected item in the combo box is a bounded grid and
     *  <code>false</code> if it is an unbounded grid.
     **/
    public boolean selectedClassIsBounded()
    {
        return ((GridChoice)getSelectedItem()).isBounded();
    }


    /** Nested class  used to hold the per-item information 
     *  for the entries in a combo box of grid
     *  choices. Each item represents a grid
     *  class and tracks whether this is a bounded or unbounded type.
     */
    static class GridChoice	
    {
        private Class cls;
        private boolean isBounded;
        
        public GridChoice(Class cl, boolean isB) { cls = cl; isBounded = isB; }
        public boolean isBounded() { return isBounded; }
        public Class gridClass() { return cls; }
        public String toString() { return cls.getName(); }
    }

}

