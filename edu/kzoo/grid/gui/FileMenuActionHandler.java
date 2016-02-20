// Class FileMenuActionHandler
//
// Author: Alyce Brady
//
// This class is based on the College Board's EnvironmentController class,
// as allowed by the GNU General Public License.  EnvironmentController
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

import edu.kzoo.grid.Grid;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *  Grid GUI Support Package:<br>
 *
 *    The <code>FileMenuActionHandler</code> class implements the methods
 *    used by the File menu defined in <code>GridAppFrame</code>.
 *
 *  @author Alyce Brady (based on code by Julie Zelenski)
 *  @version 15 December 2003
 *  @see GridAppFrame
 **/
public class FileMenuActionHandler
{
  // instance variables

    private GridAppFrame parentFrame = null;
    private GridCreationDialog gridCreationDialog = null;
    private GridFileChooser fileChooser;
    private GridDataFileHandler fileHandler = null;


  // constructor

    /** Creates a new file menu action handler tied to the specified frame.
     *  (Precondition: frame is not null. )
     *  @param frame  the frame that uses this action handler
     *  @param fileHandler object that can read and write a grid;
     *                     null if this handler should not support file i/o
     **/
    public FileMenuActionHandler(GridAppFrame frame,
                                 GridDataFileHandler fileHandler)
    {
        parentFrame = frame;
        this.fileHandler = fileHandler;
    }


  // methods that allow subclasses access to instance variables

    /** Gets the frame containing the file menu associated with this handler.
     *    @return the frame with this file menu
     **/
    protected GridAppFrame getParentFrame()
    {
        return parentFrame;
    }

    /** Gets the dialog used previously (if any) for grid creation.
     *    @return the dialog that prompts for grid creation parameters
     **/
    protected GridCreationDialog getGridCreationDialog()
    {
        if (gridCreationDialog == null)
            gridCreationDialog = createGridCreationDialog();
        return gridCreationDialog;
    }

    /** Creates a grid creation dialog.
     *  @return a grid creation dialog
     *
     **/
    protected GridCreationDialog createGridCreationDialog()
    {
        return GridCreationDialog.makeGridChoiceDialog(parentFrame);
    }

    /** Gets the file chooser dialog used previously (if any) or creates
     *  a new one using the <code>createFileChooser</code> method.
     *    @return the file chooser for finding files to read to or write from
     **/
    protected GridFileChooser getFileChooser()
    {
        if (fileChooser == null) fileChooser = createFileChooser();
        return fileChooser;
    }

    /** Creates the file chooser.
     *  @return the file to open
     *
     **/
    protected GridFileChooser createFileChooser()
    {
        return new GridFileChooser();
    }

    /** Gets the object that handles file i/o.
     *    @return file i/o handler
     **/
    protected GridDataFileHandler getFileHandler()
    {
        return fileHandler;
    }


  // methods that support the actions of a typical, basic file menu

    /** Indicates whether this action handler supports file i/o.
     *  @return <code>true</code> if this action handler was constructed
     *          with a non-null grid data file handler
     **/
    public boolean supportsFileIO()
    {
        return fileHandler != null; 
    }

    /** Indicates whether this action handler supports grid editing.
     *  Should only be redefined in subclasses that also redefine the
     *  <code>invokeEditor</code> method.
     **/
    public boolean supportsGridEditing()
    {
        return false; 
    }

    /** Creates a new empty grid and invokes the grid editor.
     *  (Precondition: <code>supportsGridEditing() == true</code>
     **/
    public void createNewGrid()
    {
        GridCreationDialog dialog = getGridCreationDialog();
        Grid newGrid = dialog.showDialog();
        if ( newGrid != null )
        {
            parentFrame.setGrid(newGrid);
            invokeEditor();
        }
    }

    /** Invokes the appropriate grid editor to edit the grid from the
     *  parent frame.  Should be redefined in subclasses that support
     *  a grid editor, to construct and run the editor.
     **/
    protected void invokeEditor()
    {
        throw new java.lang.UnsupportedOperationException(); 

        // Would be something like:
        // GridEditor editor = new GridEditor(getParentFrame());
        // editor.constructWindowContents();

    }
    
    /** Enables editing of the existing grid from the parent frame.
     *  Subclasses that support grid editing should ensure that the
     *  application is not actively running and modifying the grid
     *  while the grid is being edited.  For example, if the parent
     *  frame is a <code>SteppedGridAppFrame</code>, the subclass
     *  should call the frame's <code>stop</code> method.
     **/
    public void editGrid()
    {
        if ( parentFrame.getGrid() != null )
            invokeEditor();
    }

    /** Reads a new grid from a saved data file. Gets file via file
     *  chooser dialog, reads contents using the specified file handler.
     *  On error, brings up dialog and continues using previous grid.
     **/
    public void openFile()
    {
        GridFileChooser chooser = getFileChooser();
        if (chooser.showOpenDialog(parentFrame) != JFileChooser.APPROVE_OPTION)
            return;
            
        Grid newGrid;
        try 
        {
            newGrid = fileHandler.readGrid(chooser.getSelectedFile());
        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(parentFrame, "Unable to read grid from file.\n"
                        + "Reason: " + ex, "Error reading file", JOptionPane.ERROR_MESSAGE);
            return;
        }
        parentFrame.setGrid(newGrid);
    }

    /** Saves the grid from the parent frame to a data file.
     **/
    public void saveFile()
    {
        Grid grid = parentFrame.getGrid();
        if (fileChooser == null) fileChooser = new GridFileChooser();
        if (fileChooser.showSaveDialog(parentFrame) != JFileChooser.APPROVE_OPTION)
            return;
            
        try 
        {
            fileHandler.writeGrid(grid, fileChooser.getSelectedFile());
        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(parentFrame, "Unable to save grid to file.\n"
                        + "Reason: " + ex, "Error saving file", JOptionPane.ERROR_MESSAGE);
        }
    }

}
