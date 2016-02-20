// Class MinimalFileMenu
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

package edu.kzoo.kgui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

/**
 *  K College GUI Package:<br>
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

    /** Creates an empty File menu.  
     **/
    public MinimalFileMenu()
    {
        super("File");
    }

    /** Creates an empty menu with the specifed name.
     *    @param name the label for this menu in the menu bar 
     **/
    public MinimalFileMenu(String name)
    {
        super(name);
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


