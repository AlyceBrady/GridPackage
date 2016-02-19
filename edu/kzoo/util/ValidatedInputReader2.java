// Class: ValidatedInputReader
//
// Author: Alyce Brady
//
// Created on Mar 19, 2004
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

package edu.kzoo.util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *  Kalamazoo College Utility Classes:<br>
 *
 *  This class prompts for input from the user and validates responses.
 * 
 *  IT DOESN"T REALLY MAKE SENSE YET !!!
 *
 *  This class is incomplete, currently handling only integer input.
 *  Furthermore, it is undoubtedly reinventing the wheel, but I haven't
 *  had the time to do a search for a class that provides appropriate
 *  validation.
 *
 *  @author Alyce Brady
 *  @version Mar 19, 2004
 **/
public class ValidatedInputReader2
{
    private String suggestedVal; // string-ized suggested (and default) value
    private int min, max;        // begin and end of range of valid values
    private String errorMsg;     // error message when validation fails
    private JDialog dialog;
    private JTextField responseField;
    private boolean userClickedOK = false;
    private int intUserEntered;

    /** Puts up a dialog box displaying the <code>initialPrompt</code>,
     *  waits for the user's response, and validates it (the response must
     *  be a valid integer).
     *  If the user's response is invalid, it prompts the user again
     *  using the <code>clarificationPrompt</code> until the user enters
     *  a valid response or selects Cancel.  If the user selects Cancel,
     *  <code>getInteger</code> returns the <code>suggestedValue</code>.
     *    @param initialPrompt  the initial prompt to the user
     *    @param suggestedValue a suggested, valid value that is displayed
     *                          to the user, and is also used as the default
     *                          value if the user selects Cancel
     *    @param clarificationPrompt a follow-up prompt for input after
     *                               the user has input invalid data
     **/
    public int getInteger(String initialPrompt, int suggestedValue,
                                 String clarificationPrompt) 
    {
        return getInteger(initialPrompt, Integer.MIN_VALUE, Integer.MAX_VALUE,
                          suggestedValue, clarificationPrompt);
    }

    /** Puts up a dialog box displaying the <code>initialPrompt</code>,
     *  waits for the user's response, and validates it (the response must
     *  be a valid integer).
     *  If the user's response is invalid, it prompts the user again
     *  using the <code>clarificationPrompt</code> until the user enters
     *  a valid response or selects Cancel.  If the user selects Cancel,
     *  <code>getInteger</code> returns the <code>suggestedValue</code>.
     *    @param parentFrame    frame of application asking for input
     *    @param initialPrompt  the initial prompt to the user
     *    @param suggestedValue a suggested, valid value that is displayed
     *                          to the user, and is also used as the default
     *                          value if the user selects Cancel
     *    @param clarificationPrompt a follow-up prompt for input after
     *                               the user has input invalid data
     **/
    public int getInteger(JFrame parentFrame,
                                 String initialPrompt, int suggestedValue,
                                 String clarificationPrompt) 
    {
        return getInteger(parentFrame,
                          initialPrompt, Integer.MIN_VALUE, Integer.MAX_VALUE,
                          suggestedValue, clarificationPrompt);
    }

    /** Puts up a dialog box displaying the <code>initialPrompt</code>,
     *  waits for the user's response, and validates it (the response must
     *  be a valid integer).
     *  If the user's response is invalid, it prompts the user again
     *  using the <code>clarificationPrompt</code> until the user enters
     *  a valid response or selects Cancel.  If the user selects Cancel,
     *  <code>getInteger</code> returns the <code>suggestedValue</code>.
     *    @param initialPrompt  the initial prompt to the user
     *    @param startOfRange   the smallest value in the range of valid values
     *    @param endOfRange     the largest value in the range of valid values
     *    @param suggestedValue a suggested, valid value that is displayed
     *                          to the user, and is also used as the default
     *                          value if the user selects Cancel
     *    @param clarificationPrompt a follow-up prompt for input after
     *                               the user has input invalid data
     **/
    public int getInteger(String initialPrompt,
                                 int startOfRange, int endOfRange,
                                 int suggestedValue,
                                 String clarificationPrompt) 
    {
        return getInteger(null, initialPrompt, startOfRange, endOfRange,
                          suggestedValue, clarificationPrompt);
    }

    /** Puts up a dialog box displaying the <code>initialPrompt</code>,
     *  waits for the user's response, and validates it (the response must
     *  be a valid integer).
     *  If the user's response is invalid, it prompts the user again
     *  using the <code>clarificationPrompt</code> until the user enters
     *  a valid response or selects Cancel.  If the user selects Cancel,
     *  <code>getInteger</code> returns the <code>suggestedValue</code>.
     *    @param parentFrame    frame of application asking for input
     *    @param initialPrompt  the initial prompt to the user
     *    @param startOfRange   the smallest value in the range of valid values
     *    @param endOfRange     the largest value in the range of valid values
     *    @param suggestedValue a suggested, valid value that is displayed
     *                          to the user, and is also used as the default
     *                          value if the user selects Cancel
     *    @param clarificationPrompt a follow-up prompt for input after
     *                               the user has input invalid data
     **/
    public int getInteger(JFrame parentFrame, String initialPrompt,
                                 int startOfRange, int endOfRange,
                                 int suggestedValue,
                                 String clarificationPrompt) 
    {
        makeDialog(parentFrame, initialPrompt, startOfRange,
                            endOfRange, suggestedValue, clarificationPrompt);
        userClickedOK = false;

        // Show the dialog box; will block until setVisible(false), see ok/cancel methods/
        // Dialog box is shown in middle of parent frame.
        responseField.requestFocus();
        if ( parentFrame != null )
            dialog.setLocation(parentFrame.getX() + parentFrame.getWidth()/2 - dialog.getSize().width/2,
                               parentFrame.getY() + parentFrame.getHeight()/2 - dialog.getSize().height/2);
        dialog.show();   // Modal dialog will block until user clicks ok/cancel

        // User selected grid and/or dimensions, so create grid.
        if ( ! userClickedOK )  // if user cancelled or closed dialog
            return suggestedValue;    // return null
        else
            return intUserEntered;
    }

    public void makeDialog(JFrame parent, String initialPrompt,
                                int startOfRange, int endOfRange,
                                int suggestedValue,
                                String clarificationPrompt) 
    {
        suggestedVal = "" + suggestedValue;
        String response =
                (String)JOptionPane.showInputDialog(null, initialPrompt,
                    "Input", JOptionPane.QUESTION_MESSAGE, null, null,
                    suggestedVal);
        dialog = new JDialog(parent, "Input", true);

        JPanel myControls = new JPanel();
        myControls.setLayout(new BoxLayout(myControls, BoxLayout.Y_AXIS));

        myControls.add(new JLabel(initialPrompt));
        JOptionPane optPane = new JOptionPane(makeRequestPanel(suggestedVal),
                                              JOptionPane.QUESTION_MESSAGE);

        JButton[] optButtons =
                new JButton[] { new JButton("OK"), new JButton("Cancel")};
        optPane.setOptions(optButtons);
        optPane.setInitialValue(optButtons[0]);
        optButtons[0].addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent e) { okClicked(); }});
        optButtons[1].addActionListener( new ActionListener() { 
            public void actionPerformed(ActionEvent e) { cancelClicked(); }});
        optButtons[0].requestFocus();


        dialog.setContentPane(optPane);
        dialog.pack();
        dialog.setResizable(false);
    }

    /** Creates a panel to prompt for input.
     *      @param suggestedValue suggested value to put in input field
     **/
    private JPanel makeRequestPanel(String suggestedValue)
    {
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.X_AXIS));
        inputPanel.add(new JLabel("Input: "));
        inputPanel.add(responseField = new JTextField(suggestedValue));
        return inputPanel;
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
            intUserEntered = Integer.parseInt(responseField.getText().trim());
            if ( intUserEntered <= min || intUserEntered > max )
                throw new NumberFormatException();
            dialog.setVisible(false);
        }
        catch (NumberFormatException ex)
        {
            JOptionPane.showMessageDialog(dialog, errorMsg, "Input Error", 
                                          JOptionPane.ERROR_MESSAGE);
            // we don't dismiss the dialog in this case, leave up for another try
        }
    }

}
