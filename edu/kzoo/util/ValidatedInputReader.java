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

import javax.swing.JOptionPane;

/**
 *  Kalamazoo College Utility Classes:<br>
 *
 *  This class prompts for input from the user and validates responses.
 *
 *  This class is incomplete, currently handling only integer input.
 *  Furthermore, it is undoubtedly reinventing the wheel, but I haven't
 *  had the time to do a search for a class that provides appropriate
 *  validation.
 *
 *  @author Alyce Brady
 *  @version Mar 19, 2004
 **/
public class ValidatedInputReader
{
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
    public static int getInteger(String initialPrompt, int suggestedValue,
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
     *    @param initialPrompt  the initial prompt to the user
     *    @param startOfRange   the smallest value in the range of valid values
     *    @param endOfRange     the largest value in the range of valid values
     *    @param suggestedValue a suggested, valid value that is displayed
     *                          to the user, and is also used as the default
     *                          value if the user selects Cancel
     *    @param clarificationPrompt a follow-up prompt for input after
     *                               the user has input invalid data
     **/
    public static int getInteger(String initialPrompt,
                                 int startOfRange, int endOfRange,
                                 int suggestedValue,
                                 String clarificationPrompt) 
    {
        String suggested = "" + suggestedValue;
        String response = getResponse(initialPrompt, suggested);
        while (response != null)
            try
            {
                int userEntered = Integer.parseInt(response.trim());
                if ( userEntered <= startOfRange || userEntered > endOfRange )
                    throw new NumberFormatException();
                return userEntered;
            } 
            catch (NumberFormatException ex)
            {   response = getResponse(clarificationPrompt, suggested);   }
        return suggestedValue;
    }

    /** Displays the appropriate JOptionPane and gets the result. **/
    protected static String getResponse(String prompt,
                                        String suggestedValue)
    {
        return (String)JOptionPane.showInputDialog(null, prompt,
                        "Input", JOptionPane.QUESTION_MESSAGE, null, null,
                        suggestedValue);
    }

}
