// Class BasicGridFileMenu
//
// Author: Alyce Brady
//
// This class is based on code from the College Board's MBSGUIFrame class,
// as allowed by the GNU General Public License.  MBSGUIFrame
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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import edu.kzoo.kgui.MinimalFileMenu;
import edu.kzoo.kgui.ModelChangeListener;

/**
 *  Grid GUI Support Package:<br>
 *
 *  The <code>BasicGridFileMenu</code> class provides a file menu
 *  for creating, reading, and writing grids.  The menu will always
 *  include a Quit menu option.  It will include Open and Save menu
 *  options only if <code>fileHandler</code> is not <code>null</code>.
 *  By default it will not include New Grid or Edit Grid options,
 *  but <code>BasicGridFileMenu</code> subclasses may override the
 *  <code>createFileMenuActionHandler</code> method to create an
 *  action handler that supports grid editing.
 *
 *  @author Alyce Brady (based on code by Julie Zelenski)
 *  @version 29 February 2004
 *  @see FileMenuActionHandler
 **/
public class BasicGridFileMenu extends MinimalFileMenu
            implements ModelChangeListener
{
  // instance variables
    private Collection menuItemsThatNeedAGrid = new ArrayList();
    private GridAppFrame parentFrame = null;
    private FileMenuActionHandler fileMenuActionHandler = null;
    private GridDataFileHandler fileHandler = null;


  // constructor and configuration methods

    /** Creates a File menu tied to the specified frame for creating,
     *  reading, and writing grids.  This object will use a
     *  <code>FileMenuActionHandler</code> instance to handle file
     *  menu actions.  As a result, it will not include New Grid
     *  or Edit Grid menu options.  It will include Open and Save menu
     *  options only if <code>fileHandler</code> is not <code>null</code>.
     *  It will always include a Quit menu option.  
     *  (Precondition: frame is not null. )
     *  @param frame  the frame that uses this menu
     *  @param fileHandler object that can read and write a grid;
     *                     null if this menu should not support file i/o
     **/
    public BasicGridFileMenu(GridAppFrame frame,
                             GridDataFileHandler fileHandler)
    {
        this(frame, new FileMenuActionHandler(frame, fileHandler),
             fileHandler);
    }

    /** Creates a File menu tied to the specified frame for creating,
     *  reading, and writing grids.  The menu will always include a Quit
     *  menu option.  It will include New Grid and Edit Grid menu if
     *  <code>menuActionHandler</code> supports grid editing.  It will
     *  also include Open and Save menu items if
     *  <code>fileHandler</code> is not <code>null</code>.
     *  (Precondition: frame is not null. )
     *    @param frame  the frame that uses this menu
     *    @param menuActionHandler object that handles the behavior
     *                    associated with the File and Seed menus
     *    @param fileHandler object that can read and write a grid;
     *                       null if this menu should not support file i/o
     **/
    public BasicGridFileMenu(GridAppFrame frame,
                             FileMenuActionHandler menuActionHandler,
                             GridDataFileHandler fileHandler)
    {
        this.parentFrame = frame;
        frame.addModelChangeListener(this);
        this.fileMenuActionHandler = menuActionHandler;
        this.fileHandler = fileHandler;
        makeFileMenu();
    }

  // methods that allow subclasses access to instance variable

    /** Gets the frame containing the file menu associated with this handler.
     *    @return the frame with this file menu
     **/
    protected GridAppFrame getParentFrame()
    {
        return parentFrame;
    }

    /** Gets the action handler for the file menu.
     *    @return the file menu action handler
     **/
    protected FileMenuActionHandler getFileMenuActionHandler()
    {
        return fileMenuActionHandler;
    }

    /** Gets the object that handles file i/o.
     *    @return file i/o handler
     **/
    protected GridDataFileHandler getFileHandler()
    {
        return fileHandler;
    }


  // methods that deal with constructing menus

    /** Creates the File drop-down menu on the frame.
     *  (Precondition: fileMenuActionHandler is not null.)
     *    @return the file menu
     **/
    protected void makeFileMenu()
    {
        int menuMask = getToolkit().getMenuShortcutKeyMask();

        JMenuItem mItem;
        
        if ( getFileMenuActionHandler().supportsGridEditing() )
        {
            add(mItem = new JMenuItem("New grid..."));
            mItem.addActionListener(
                new ActionListener()
                {   public void actionPerformed(ActionEvent e)
                    {   getFileMenuActionHandler().createNewGrid();  }
                });
            mItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, menuMask));
        }

        if ( getFileMenuActionHandler().supportsFileIO() )
        {
            add(mItem = new JMenuItem("Open grid file..."));
            mItem.addActionListener(
                new ActionListener()
                {   public void actionPerformed(ActionEvent e)
                    {   getFileMenuActionHandler().openFile();  }
                });
           mItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, menuMask));
        }
 
        if ( getFileMenuActionHandler().supportsGridEditing() )
        {
            add(mItem = new JMenuItem("Edit grid..."));
            mItem.addActionListener(
                new ActionListener()
                {   public void actionPerformed(ActionEvent e)
                    {   getFileMenuActionHandler().editGrid();  }
                });
            mItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, menuMask));
            itemNeedsDefinedGrid(mItem);
        }

        if ( getFileMenuActionHandler().supportsFileIO() )
        {
            add(mItem  = new JMenuItem("Save grid as..."));
            mItem.addActionListener(
                new ActionListener()
                {   public void actionPerformed(ActionEvent e)
                    {   getFileMenuActionHandler().saveFile();  }
                });
            mItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, menuMask));
            itemNeedsDefinedGrid(mItem);
        }

        addQuitMenuItem();
    }

    /** Adds the specified menu item to the list of ones that
     *  should only be enabled when a grid is defined in the
     *  graphical user interface.
     *    @param item item that should only be enabled when
     *                there is a defined grid
     **/
    protected void itemNeedsDefinedGrid(JMenuItem item)
    {
        menuItemsThatNeedAGrid.add(item);
    }

    /** Sets the enabled status of those GUI items that need a
     *  grid to be valid.
     **/
    public void reactToNewModel(Object newModel)
    {
        Iterator iter = menuItemsThatNeedAGrid.iterator();
        while (iter.hasNext())
            ((JMenuItem)iter.next()).setEnabled(newModel != null);
    }

}


