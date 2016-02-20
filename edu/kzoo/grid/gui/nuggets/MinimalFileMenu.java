// Class MinimalFileMenu
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

package edu.kzoo.grid.gui.nuggets;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

/**
 *  Grid GUI Nuggets Package (Handy Grid GUI Components):<br>
 *
 *  The <code>MinimalFileMenu</code> class provides a file menu
 *  that initially has no menu items, although the class also provides
 *  an <code>addQuitMenuItem</code> method that adds a Quit button for
 *  quitting an application.
 *
 *  @author Alyce Brady
 *  @version 31 March 2004
 **/
public class MinimalFileMenu extends JMenu
{

    /** Creates a minimal File menu containing only a Quit option.  
     **/
    public MinimalFileMenu()
    {
        this("File", true);
    }

    /** Creates a File menu that may be empty or may include a Quit option.
     *    @param includeQuitOption  <code>true</code> if the File menu
     *                              should include a Quit option;
     *                              <code>false</code>  otherwise
     **/
    public MinimalFileMenu(boolean includeQuitOption)
    {
        this("File", includeQuitOption);
    }

    /** Creates a minimal File menu with the specifed name containing
     *  only a Quit option.
     *    @param name the label for this menu in the menu bar 
     **/
    public MinimalFileMenu(String name)
    {
        this(name, true);
    }

    /** Creates a File menu with the specifed name that may be empty
     *  or may include a Quit option.
     *    @param name the label for this menu in the menu bar 
     *    @param includeQuitOption  <code>true</code> if the File menu
     *                              should include a Quit option;
     *                              <code>false</code>  otherwise
     **/
    public MinimalFileMenu(String name, boolean includeQuitOption)
    {
        super(name);
        if ( includeQuitOption )
            addQuitMenuItem();
    }

    /** Adds a Quit menu option to this menu.
     **/
    public void addQuitMenuItem()
    {
        int menuMask = getToolkit().getMenuShortcutKeyMask();

        JMenuItem mItem;
        add(mItem = new JMenuItem("Quit"));
        mItem.addActionListener(
            new ActionListener()
            {   public void actionPerformed(ActionEvent e)
                {   System.exit(0);  }
            });
        mItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, menuMask));
    }

}


