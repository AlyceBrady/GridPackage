// Class GridCreationDialog
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
import edu.kzoo.grid.Grid;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *  Grid GUI Support Package:<br>
 *
 *  A <code>GridCreationDialog</code> is a dialog that allows the user
 *  to construct a new grid, choosing its type (bounded or unbounded)
 *  and, if appropriate, its dimensions.
 *
 *  @author Alyce Brady (based on code by Julie Zelenski)
 *  @version 15 December 2003
 **/
public class GridCreationDialog
{    
    private boolean promptForGridChoice;
    private JDialog dialog;
    private GridChoiceComboBox gridChooser;
    private JPanel dimPanel;
    private JLabel rowLabel, colLabel;
    private JTextField rowField, colField;
    private int numRows, numCols;
    private JButton[] optButtons;
    private boolean userClickedOK;

  // factory methods

    /** Creates a dialog that creates a <code>BoundedGrid</code> object
     *  after prompting the user for its dimensions.
     *      @param  parent    parent frame for the dialog
     *      @return a dialog that will prompt for bounded grid
     *              dimensions and then create the grid
     **/
    public static GridCreationDialog makeDimensionsDialog(JFrame parent)
    {
        GridCreationDialog d = new GridCreationDialog(parent, false);
        return d;
    }

    /** Creates a dialog that allows the user to choose the type of
     *  grid to create and, if appropriate, its dimensions.  The
     *  set of grid classes available to choose from depends on
     *  whether the application has registered grid classes with
     *  the <code>GridPkgFactory</code>.  If any bounded grid classes
     *  have been registered with the <code>GridPkgFactory</code>, then
     *  the dialog will include them in the set of options.  Otherwise,
     *  it will include <code>BoundedGrid</code> as its only bounded
     *  grid.  Similarly, if any unbounded grid classes have been
     *  registered with the <code>GridPkgFactory</code>, then the dialog
     *  will include them in the set of options.  Otherwise, it will
     *  include <code>UnboundedArrayListGrid</code> as its only unbounded
     *  grid.  <code>BoundedGrid</code> and <code>UnboundedArrayListGrid</code>
     *  must explicitly be registered with the <code>GridPkgFactory</code> to
     *  appear along with other options from the factory.
     *      @param  parent    parent frame for the dialog
     *      @return a dialog that prompts for an Grid representation
     **/
    public static GridCreationDialog makeGridChoiceDialog(JFrame parent)
    {
        GridCreationDialog d = new GridCreationDialog(parent, true);
        return d;
    }

  // constructor
    
    /** Creates a dialog that allows the user to choose the type of
     *  grid to create and/or its dimensions.
     *      @param  parent    parent frame for the dialog
     *      @param  promptForGridChoice <code>true</code> if dialog should
     *                    prompt for choice of grid representation;
     *                    <code>false</code> if it should always create
     *                    a <code>BoundedGrid</code> grid
     **/
    protected GridCreationDialog(JFrame parent, boolean promptForGridChoice)
    {
        this.promptForGridChoice = promptForGridChoice;
        dialog = new JDialog(parent, "Create new grid", true);

        JPanel myControls = new JPanel();
        myControls.setLayout(new BoxLayout(myControls, BoxLayout.Y_AXIS));

        // Add grid choice drop-down menu, if appropriate.
        if ( promptForGridChoice )
            makeGridChoiceMenu(myControls);

        // Make dimensions choice panel.
        makeDimensionsFields(myControls);

        // Add Create/Cancel buttons.
        JOptionPane optPane = new JOptionPane(myControls, JOptionPane.QUESTION_MESSAGE);
        optButtons = new JButton[] { new JButton("Create"), new JButton("Cancel")};
        optPane.setOptions(optButtons);
        optPane.setInitialValue(optButtons[0]);
        optButtons[0].addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent e) { okClicked(); }});
        optButtons[1].addActionListener( new ActionListener() { 
            public void actionPerformed(ActionEvent e) { cancelClicked(); }});

        dialog.setContentPane(optPane);
        dialog.pack();
        dialog.setResizable(false);
    }

    /** Creates a drop-down menu for choosing a grid.
     *      @param panel  panel to which to add the drop-down menu
     **/
    private void makeGridChoiceMenu(JPanel panel)
    {
        panel.setBorder(BorderFactory.createEmptyBorder(5, 0, 10, 0));
        JLabel lab = new JLabel("Choose grid type: ");
        panel.add(lab);
        lab.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // add all bounded/unbounded grid classes to the combo box
 	    gridChooser = new GridChoiceComboBox();
 	    gridChooser.addBoundedGrids();
 	    gridChooser.addUnboundedGrids();
   	    gridChooser.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) { gridChosen(); }});
        panel.add(gridChooser);
    }
    
    /** callback when user selects new grid class choice from combo box */
    private void gridChosen()
    {
        // only show the num rows/cols field for bounded grids
        dimPanel.setVisible(gridChooser.selectedClassIsBounded());
        optButtons[0].requestFocus();
    }

    /** Creates a panel to prompt for bounded grid dimensions.
     *      @param panel  panel to which to add the drop-down menu
     **/
    private void makeDimensionsFields(JPanel panel)
    {
        dimPanel = new JPanel();
        dimPanel.setLayout(new BoxLayout(dimPanel, BoxLayout.X_AXIS));
        dimPanel.add(rowLabel = new JLabel("rows: "));
        dimPanel.add(rowField = new JTextField("10"));
        dimPanel.add(colLabel = new JLabel("  cols: "));
        dimPanel.add(colField = new JTextField("10"));
        panel.add(dimPanel);
    }
    
    /** callback when user clicks cancel button */
    private void cancelClicked()
    {
        userClickedOK = false;
        dialog.setVisible(false);
    }

   /** callback when user clicks ok button */
    private void okClicked()
    {
        userClickedOK = true;
        try 
        {
            // If creating a bounded grid, get grid dimensions
            if ( ! promptForGridChoice || gridChooser.selectedClassIsBounded() )
            {
                rowField.requestFocus();
                numRows = Integer.parseInt(rowField.getText().trim());
                colField.requestFocus();
                numCols = Integer.parseInt(colField.getText().trim());
                if (numRows <= 0 || numCols <= 0) throw new NumberFormatException();
            }
            dialog.setVisible(false);
        }
        catch (NumberFormatException ex)
        {
            JOptionPane.showMessageDialog(dialog, 
            "Grid dimensions must be positive integers!", 
            "Error creating grid", JOptionPane.ERROR_MESSAGE);
            // we don't dismiss the dialog in this case, leave up for another try
        }
    }

    /** Shows the modal dialog that allows the user to create a new
     *  grid.  If the dialog is dismissed by clicking the "OK"
     *  button, a new grid is created to the user's specification 
     *  and returned. If "Cancel" is chosen or there is an error
     *  constructing the grid, <code>null</code> is returned.
     *  @return the newly created grid or <code>null</code>
     **/
    public Grid showDialog()
    {
        userClickedOK = false;

        // Show the dialog box; will block until setVisible(false), see ok/cancel methods/
        // Dialog box is shown in middle of parent frame.
        rowField.requestFocus();
        Component parent = dialog.getParent();
        dialog.setLocation(parent.getX() + parent.getWidth()/2 - dialog.getSize().width/2,
                           parent.getY() + parent.getHeight()/2 - dialog.getSize().height/2);
        dialog.show();   // Modal dialog will block until user clicks ok/cancel

        // User selected grid and/or dimensions, so create grid.
        if ( ! userClickedOK ) 	// if user cancelled or closed dialog
            return null;	// return null
        try 
        {
            if ( promptForGridChoice )
            {
                Class selectedClass = gridChooser.getSelectedClass();
                if ( gridChooser.selectedClassIsBounded() )
                    return GridPkgFactory.constructGrid(selectedClass,
                                                   numRows, numCols);
                else
                    return GridPkgFactory.constructGrid(selectedClass);
            }
            else
                return GridPkgFactory.constructGrid(BoundedGrid.class,
                                               numRows, numCols);
        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(dialog.getParent(),
                        "Unable to create new grid.\n"
                        + "Reason: " + ex, "Error creating grid",
                        JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

}

