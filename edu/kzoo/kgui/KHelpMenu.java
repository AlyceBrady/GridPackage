// Class: KHelpMenu
//
// Author: Alyce Brady
//
// This class is based on the College Board's MBSGUIFrame class,
// as allowed by the GNU General Public License.  MBSGUIFrame
// is a black-box class within the AP(r) CS Marine Biology Simulation
// case study.
// (See www.collegeboard.com/student/testing/ap/compsci_a/case.html.)
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

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

/**
 *  K College GUI Package:<br>
 *
 *  A <code>KHelpMenu</code> object represents a Help menu with
 *  two standard entries: "About Application_Name..." and "Help...",
 *  where "Application_Name" is the name of the application provided
 *  in a parameter to the constructor.  Selecting the "About" menu
 *  item brings up an informational dialog box with the name of the
 *  application, and, for example, its authors, and a date or version
 *  number.  Selecting the "Help" menu item displays the contents of
 *  a document in a scrolling dialog box.
 *
 *  @author Alyce Brady (based on code by Julie Zelenski)
 *  @version 31 March 2004
 **/
public class KHelpMenu extends JMenu
{
 // instance variables
    private JFrame parentFrame = null;
    private String applicationName;
    private String aboutMessage;
    private URL helpDocument;

  // static method useful in constructing KHelpMenu objects

    protected static String makeAboutMessage(String authors,
                                             String acknowledgements,
                                             String versionInfo)
    {
        String msg = "";
        if ( authors != null )
            msg += "<p>Author: " + authors + "</p><p></p>";
        if ( acknowledgements != null )
            msg += "<p>Acknowledgements: " + acknowledgements + "</p><p></p>";
        if ( versionInfo != null )
            msg += "<p><font size=-1>Version: " + versionInfo + "</font></p>";
        return msg;
    }

  // constructors

    /** Constructs an empty Help menu. **/
    public KHelpMenu()
    {
        super("Help");
    }

    /** Constructs an empty menu with the specifed name.
     *    @param name the label for this menu in the menu bar 
     **/
    public KHelpMenu(String name)
    {
        super(name);
    }

    /** Constructs a Help menu with two standard entries: 
     *  "About This_Application..." and "Help...", where
     *  This_Application is replaced with <code>applName</code>.
     *  Selecting the About option brings up a dialog box that displays
     *  the application name and the specified additional information.
     *  Selecting the "Help" menu item displays the specified help
     *  document in a scrolling dialog window.  The name of the help
     *  document should be a well-formed URL such as "file:helpFile.html"
     *  or "http://aWebSite/helpFile.html".
     *  The Help menu may have only one of the two standard entries
     *  if any of the parameters are <code>null</code> or if the name
     *  of the <code>helpDocumentURL</code> is a malformed URL.
     *     @param applName the name of this application, to be used in the
     *                  "About This_Application..." menu item;
     *                  <code>null</code> if there should not be an
     *                  "About This_Application..." menu item
     *     @param aboutMessage additional information to be displayed
     *                  in the "About This_Application..." dialog box;
     *                  <code>null</code> if there should not be an
     *                  "About This_Application..." menu item
     *     @param helpDocumentURL the URL for the document to be displayed in
     *                  a new window when the "Help..." menu item is selected;
     *                  <code>null</code> if there should not be a menu
     *                  item for a help document
     **/
    public KHelpMenu(String applName, String aboutMessage,
                         String helpDocumentURL)
    {
        super("Help");
        addAboutMenuItem(applName, aboutMessage);
        addHelpDocMenuItem(helpDocumentURL);
    }

    /** Constructs a Help menu with two standard entries: 
     *  "About This_Application..." and "Help...", where
     *  This_Application is replaced with <code>applName</code>.
     *  Selecting the About option brings up a dialog box that
     *  displays the application name, and the specified author(s),
     *  acknowledgements, and version information, each preceded
     *  with an appropriate label.  If any of those parameters is
     *  <code>null</code>, the associated label will not be included.
     *  Selecting the "Help" menu item displays the specified help
     *  document in a scrolling dialog window.  The name of the help
     *  document should be a well-formed URL such as "file:helpFile.html"
     *  or "http://aWebSite/helpFile.html".
     *  The Help menu may have only one of the two standard entries
     *  if <code>applName</code> or <code>helpDocumentURL</code> is
     *  <code>null</code> or if the name of the <code>helpDocumentURL</code>
     *  is a malformed URL.
     *     @param applName the name of this application, to be used in the
     *                  "About This_Application..." menu item;
     *                  <code>null</code> if there should not be an
     *                  "About This_Application..." menu item
     *     @param authors the name(s) of the author(s) to be displayed in
     *                  the "About This_Application..." dialog box;
     *                  <code>null</code> if the "About" information should
     *                  not include author information
     *     @param acknowledgements acknowledgement information to be included
     *                  in the "About This_Application..." dialog box;
     *                  <code>null</code> if the "About" information should
     *                  not include acknowledgements
     *     @param versionInfo version information (for example, date or
     *                  version number) to be included in the
     *                  "About This_Application..." dialog box;
     *                  <code>null</code> if the "About" information should
     *                  not include version information
     *     @param helpDocumentURL the URL for the document to be displayed in
     *                  a new window when the "Help..." menu item is selected;
     *                  <code>null</code> if there should not be a menu
     *                  item for a help document
     **/
    public KHelpMenu(String applName, String authors, 
                         String acknowledgements, String versionInfo,
                         String helpDocumentURL)
    {
        super("Help");
        addAboutMenuItem(applName, makeAboutMessage(authors, 
                                                    acknowledgements,
                                                    versionInfo));
        addHelpDocMenuItem(helpDocumentURL);
    }

    /** Sets the frame to which this menu bar is attached.  The menu bar
     *  does not need this information but will use it, if provided, to
     *  locate the window that displays help information.
     **/
    public void setFrame(JFrame frame)
    {
        this.parentFrame = frame;
    }

    /** Adds an "About This_Application" menu option to this menu,
     *  where This_Application is replaced with <code>applName</code>.
     *  Selecting this option brings up a dialog box that displays
     *  the application name (<code>applName</code>) and the specified
     *  additional information.
     *     @param applName the name of this application to be used in the
     *                  menu item and as a title for the information in
     *                  the dialog box
     *     @param additionalInfo additional information to be displayed
     *                  in the dialog box
     **/
    public void addAboutMenuItem(String applName, String additionalInfo)
    {
        if ( applName == null || additionalInfo == null )
            return;

        this.applicationName = applName;
        this.aboutMessage = "<html><h2>" + applName + "</h2>" +
                            additionalInfo + "</html>";
 
        JMenuItem mItem;
        add(mItem = new JMenuItem("About " + applName + "..."));
        mItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
                { showAboutPanel(); }});
    }

    /** Adds a "Help..." menu option to this menu that, when selected,
     *  displays the specified help document in a scrolling dialog window.
     *     @param documentURL the URL for the document to be displayed in
     *              a new window when the "Help..." menu option is selected
     **/
    public void addHelpDocMenuItem(String documentURL)
    {
        if ( documentURL == null || documentURL.equals("") )
            return;

        this.helpDocument = null;
        try {
            helpDocument = new URL(documentURL);
        }
        catch (MalformedURLException e) { }
 
        int menuMask = getToolkit().getMenuShortcutKeyMask();

        JMenuItem mItem;
        add(mItem = new JMenuItem("Help..."));
        mItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
                { showHelp(); }});
        mItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_HELP,
                                                    menuMask));
    }

    /** Brings up a simple dialog with some general information about
     *  the application.
     **/
     private void showAboutPanel()
     {
         String html = "<html><h2>" + applicationName + "</h2>" +
                       aboutMessage + "</html>";
         JOptionPane.showMessageDialog(this, new JLabel(aboutMessage),
                                       "About " + applicationName, 
                                       JOptionPane.INFORMATION_MESSAGE);
     }
    
    /** Brings up a window with a scrolling text pane that display
     *  the help information for the simulation.
     **/
    private void showHelp()
    {
        JDialog dialog = new JDialog(parentFrame, applicationName + " Help");
        final JEditorPane helpText = new JEditorPane();
        try 
        {
            helpText.setPage(helpDocument);
        } 
        catch (Exception e)
        {
            helpText.setText("Couldn't load help file.");
        }
        helpText.setEditable(false);
        helpText.addHyperlinkListener(new HyperlinkListener() {
            public void hyperlinkUpdate(HyperlinkEvent ev) {
                if (ev.getEventType() == HyperlinkEvent.EventType.ACTIVATED)
                    try 
                    {
                        helpText.setPage(ev.getURL());
                    }
                    catch (Exception ex) {}
            }});
        JScrollPane sp = new JScrollPane(helpText);
        sp.setPreferredSize(new Dimension(650, 500));
        dialog.getContentPane().add(sp);
        dialog.setLocation(getX() + getWidth() - 200, getY() + 50);
        dialog.pack();
        dialog.setVisible(true);
    }

}
