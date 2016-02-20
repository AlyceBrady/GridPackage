// Class GridFileChooser
//
// Author: Alyce Brady
//
// This class is based on the College Board's EnvFileChooser class, as
// allowed by the GNU General Public License.  The MBS EnvFileChooser class
// is a black-box class within the AP(r) CS Marine Biology Simulation
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

package edu.kzoo.grid.gui;

import edu.kzoo.grid.BoundedGrid;
import edu.kzoo.grid.ArrayListGrid;

import java.awt.Component;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.BorderFactory;
import javax.swing.filechooser.FileFilter;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.io.File;

import java.util.Set;

/**
 *  Grid GUI Support Package:<br>
 *
 *  An <code>GridFileChooser</code> is a <code>JFileChooser</code>
 *  subclass that adds
 *  some specialized behavior for opening and saving grid
 *  data files.  The additional features allow for filtering to
 *  just ".dat" files, an alert that confirms before overwriting
 *  an existing file, and an accessory panel on the open dialog
 *  for choosing which bounded/unbounded class to use for
 *  the new grid.
 *
 *  <p>
 *  This class is a slightly modified version of the College Board's
 *  Marine Biology Simulation <code>EnvFileChooser</code> class
 *  (see www.collegeboard.com/ap/students/compsci/).  Like the MBS
 *  version, this version will look in the <code>GridPkgFactory</code>
 *  for lists of the available bounded and unbounded representations.
 *  This version, however, does not require applications to use
 *  <code>GridPkgFactory</code> if the only grid representations
 *  available are <code>BoundedGrid</code> and
 *  <code>UnboundedArrayListGrid</code>.
 *
 *  @author Alyce Brady (based on code by Julie Zelenski)
 *  @version 15 December 2003
 **/

public class GridFileChooser extends JFileChooser
{
    protected static final String DATA_FILE_EXT = ".dat";
    protected JPanel openAccessory;
    protected GridChoiceComboBox boundedChooser, unboundedChooser;
    protected Class defaultBounded, defaultUnbounded;

    
    /** Creates a new GridFileChooser. The default starting directory will be the "DataFiles"
     *  subdirectory on the current working directory.
     **/
    public GridFileChooser()
    {
        super(new File(System.getProperty("user.dir") + File.separator + "DataFiles"));
        setFileFilter(new FileFilter() {
            public boolean accept(File f) {
               return (f.getName().toLowerCase().endsWith(DATA_FILE_EXT) || f.isDirectory());
            }
            public String getDescription() {
                return("Grid data files (*" + DATA_FILE_EXT + ")");
        }});
        setFileSelectionMode(JFileChooser.FILES_ONLY);
        makeOpenAccessory();
    }
    
    /** Builds the open accessory with combo boxes for choosing the bounded
     *  and unbounded grid classes.
     **/
    protected void makeOpenAccessory()
    {
        openAccessory = new JPanel();
        openAccessory.setLayout(new BoxLayout(openAccessory, BoxLayout.Y_AXIS));
        openAccessory.setBorder(BorderFactory.createEmptyBorder(20, 5, 5, 0));

        // Get the list of possible bounded grids.  If factory doesn't
        // have any, use BoundedGrid.  If factory has just one, use it.  Otherwise,
        // build a combo box for user to choose a grid representations.
        Set set = GridPkgFactory.boundedGridClasses();
        if (set.size() == 0)
            defaultBounded = BoundedGrid.class;
        else if (set.size() == 1)
            defaultBounded = (Class)set.iterator().next();
        else if (set.size() > 1) 
        {
            openAccessory.add(new JLabel("Class to use if bounded:"));
            boundedChooser = new GridChoiceComboBox();
            boundedChooser.addBoundedGrids();
            boundedChooser.setAlignmentX(Component.LEFT_ALIGNMENT);
            openAccessory.add(boundedChooser);
            openAccessory.add(Box.createRigidArea(new java.awt.Dimension(5, 5)));
        }

        // Do the same thing for unbounded grids.
        set = GridPkgFactory.unboundedGridClasses();
        if (set.size() == 0)
            defaultUnbounded = ArrayListGrid.Unbounded.class;
        else if (set.size() == 1)
            defaultUnbounded = (Class)set.iterator().next();
        else if (set.size() > 1) 
        {
            openAccessory.add(new JLabel("Class to use if unbounded:"));
            unboundedChooser = new GridChoiceComboBox();
            unboundedChooser.addUnboundedGrids();
            unboundedChooser.setAlignmentX(Component.LEFT_ALIGNMENT);
            openAccessory.add(unboundedChooser);
        }
    }

    /** Brings up a modal file chooser dialog allowing the user to choose
     *  the grid file. 
     *  @param parent the parent of the dialog
     *  @return the return state of the dialog once finished
     **/
    public int showOpenDialog(Component parent)
    {
        setDialogTitle("Open grid file");
        setAccessory(openAccessory);
        rescanCurrentDirectory();
        return super.showOpenDialog(parent);
    }
    
    /** Returns the bounded grid class selected on the open accessory.
     *  @return the selected bounded grid class
     **/
    public Class boundedClass()
    {
        if (boundedChooser == null)
            return defaultBounded;
        else
            return boundedChooser.getSelectedClass();
    }
    
    /** Returns the unbounded grid class selected on the open accessory.
     *  @return the selected grid class
     */
    public Class unboundedClass()
    {
        if (unboundedChooser == null)
            return defaultUnbounded;
        else
            return unboundedChooser.getSelectedClass();
    }

    /** Brings up a modal file chooser dialog allowing the user to save
     *  to a grid file.
     *  @param parent the parent of the dialog
     *  @return the return state of the dialog once finished
    **/
    public int showSaveDialog(Component parent)
    {
        setDialogTitle("Save grid file");
        setAccessory(null);
        rescanCurrentDirectory();
        return super.showSaveDialog(parent);
    }

    /** Called when the user hits the approve button (Save/Open) to 
     *  confirm the selected file is acceptable. Overrides the
     *  JFileChooser to confirm overwrite if choosing a file
     *  that already exists for a save action.
     **/
    public void approveSelection() 
    {
        if (getDialogType() == SAVE_DIALOG)
        {
            File file = getSelectedFile();
            if (!file.getName().endsWith(DATA_FILE_EXT))	// add extension if missing
                setSelectedFile(file = new File(file.getAbsolutePath() + DATA_FILE_EXT));
            if (file.exists()
                && JOptionPane.showConfirmDialog(this,"File " + file.getName() + 
                            " exists. Overwrite?", "Confirm overwrite",
                             JOptionPane.OK_CANCEL_OPTION) != JOptionPane.OK_OPTION)
                return;
        }
        super.approveSelection(); //if we get here, this will dismiss the dialog
    }
    
}
