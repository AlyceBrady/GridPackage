// Class ControlledGridAppFrame
//
// Author: Alyce Brady
//
// This class is based on the College Board's MBSGUIFrame class,
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

import edu.kzoo.grid.Grid;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *  Grid GUI Support Package:<br>
 *
 *  The <code>ControlledGridAppFrame</code> class provides a window in which
 *  to run and display a grid application controlled by the user via
 *  a combination of Step, NSteps, Run, and Stop buttons.  Other options
 *  include a speed slider bar and a Start/Restart button.
 *
 *  @author Alyce Brady (based on code by Julie Zelenski)
 *  @version 29 February 2004
 **/
public class ControlledGridAppFrame extends GridAppFrame
{
    protected GridAppController controller;
    protected Timer   timer;
    protected boolean putRestartFirst;
    protected JButton stepButton, nStepsButton,
                      runButton, stopButton;
    protected boolean includeStep, includeNSteps, includeRun, includeStop;
    protected boolean running, runningNSteps;
    protected int     numStepsToRun, numStepsSoFar;


  // constructors and initialization methods

    /** Constructs an empty ControlledGridAppFrame window object
     *  that will display a model controlled by a combination of
     *  Step, NSteps, Run, and Stop buttons. A ControlledGridAppFrame
     *  constructed using this constructor will have no control
     *  buttons unless the appropriate <code>include</code>Button
     *  messages are sent before the <code>constructWindowContents</code>
     *  message.  Use the <code>includeStartRestart</code> and
     *  <code>includeSpeedSlider</code> to include components other
     *  than the basic model display and the specified run buttons.
     *  Finally, use the constructWindowContents method to set the
     *  properties of the window and make it visible.
     *  (Precondition: control is not <code>null</code>.)
     *    @param control         the object that controls the running of the
     *                           application through step and/or run methods.
     **/
    public ControlledGridAppFrame(GridAppController control)
    {
        controller = control;

        this.includeStep = false;
        this.includeNSteps = false;
        this.includeRun = false;
        this.includeStop = false;

        // Construct the buttons whether they will be included in the
        // control panel or not, so that the enable/disable code does not
        // have to constantly check for each one. 
        stepButton = new JButton("Step Once");
        stepButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
                { step(); }});

        nStepsButton = new JButton("Step N Times");
        nStepsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
                { nSteps(); }});

        runButton = new JButton("Run...");
        runButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
                { run(); }});

        stopButton = new JButton("Stop");
        stopButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
                { stop(); }});

        stepButton.setEnabled(false);
        nStepsButton.setEnabled(false);
        runButton.setEnabled(false);
        stopButton.setEnabled(false);

        // Define timer used by NSteps and Run buttons.
        timer = new Timer(getDelay(),
                          new ActionListener()
                          {   public void actionPerformed(ActionEvent evt)
                              {   step();   }
                          });


        // Timer needs to listen for changes to the speed slider.
        if ( getSpeedSlider() != null )
        {
            getSpeedSlider().addChangeListener(new ChangeListener() {
                public void stateChanged(ChangeEvent evt) {
                    timer.setDelay(((JSlider)evt.getSource()).getValue());
                }});
        }
    }

    /** Constructs an empty ControlledGridAppFrame window object that will
     *  display a model controlled by a combination of Step, NSteps, Run, and
     *  Stop buttons.  The boolean parameters determine which buttons
     *  will be included in the control panel of the graphical user
     *  interface.  Use methods such as <code>includeStartRestart</code>
     *  and <code>includeSpeedSlider</code> to include components on the
     *  other than the basic model display and the specified run buttons.
     *  Then use the constructWindowContents method to set the properties
     *  of the window and make it visible.
     *  (Precondition: control is not <code>null</code>.)
     *    @param control         the object that controls the running of the
     *                           application through step and/or run methods.
     *    @param includeStep     <code>true</code> if GUI should include
     *                           a One Step button
     *    @param includeNSteps   <code>true</code> if GUI should include
     *                           an N Steps button
     *    @param includeRun      <code>true</code> if GUI should include
     *                           a Run... button
     *    @param includeStop     <code>true</code> if GUI should include
     *                           a Stop button
     **/
    public ControlledGridAppFrame(GridAppController control,
                           boolean includeStep, boolean includeNSteps,
                           boolean includeRun, boolean includeStop)
    {
        this(control);

        if ( includeStep )
            includeStepOnceButton();

        if ( includeNSteps )
            includeStepNTimesButton();

        if ( includeRun )
            includeRunButton();

        if ( includeStop )
            includeStopButton();
    }

    /** Includes the Step Once button in the control panel.
     *  This method will have no effect unless it is
     *  called before the constructWindowContents method.
     **/
    public void includeStepOnceButton()
    {
        this.includeStep = true;
        includeControlButton(stepButton);
    }

    /** Includes the Step N Times button in the control panel.
     *  This method will have no effect unless it is
     *  called before the constructWindowContents method.
     **/
    public void includeStepNTimesButton()
    {
        this.includeNSteps = true;
        includeControlButton(nStepsButton);
    }

    /** Includes the Run button in the control panel.
     *  This method will have no effect unless it is
     *  called before the constructWindowContents method.
     **/
    public void includeRunButton()
    {
        this.includeRun = true;
        includeControlButton(runButton);
    }

    /** Includes the Stop button in the control panel.
     *  This method will have no effect unless it is
     *  called before the constructWindowContents method.
     **/
    public void includeStopButton()
    {
        this.includeStop = true;
        includeControlButton(stopButton);
    }


  // accessor methods

    /** Returns the controller used to drive the application. **/
    public GridAppController getController()
    {
        return controller;
    }


  // redefinition of methods from the GridDisplay interface

    /** Sets the Grid being displayed.
     *    @param grid the Grid to display
     **/
    public void setGrid(Grid grid)
    {
        if ( running )
            stop();
        super.setGrid(grid);

        // Set the application controller's grid to match this one.
        controller.setGrid(grid);

        // Enable and disable buttons as appropriate.
        if ( grid != null )
            enterNotRunningMode();
    }


  // methods that implement button actions

    /** Advances the application one step.  Should be redefined if the
     *  application wants to show the grid after each step.  For example:
     *  <pre>
     *     super.step();
     *     showGrid();
     *  </pre>
     **/
    public void step()
    {
        controller.step();

        if ( runningNSteps )
            numStepsSoFar++;

        if ( running && shouldStop() )
            stop();
    }

    /** Determines whether a running application has reached
     *  a desired stopping state.
     *    @return <code>true</code> if the application should
     *            stop 
     **/
    public boolean shouldStop()
    { 
        return ( ( runningNSteps && numStepsSoFar == numStepsToRun ) ||
            controller.hasReachedStoppingState() );
    }

    /** Advances the application multiple (N) steps (where N is provided
     *  by the user in a dialog box).  It may stop before the N steps
     *  have completed if the user clicks on the stop button (if one is
     *  provided) or if the application controller indicates that the
     *  application has reached a stopping state.
     **/
    public void nSteps()
    { 
        if ( promptUserForStepCount() )
        {
            runningNSteps = true;
            numStepsSoFar = 0;
            enterRunningMode();
            timer.start();
        }
    }

    /** Runs a dialog asking the user to input a number of steps
     *  for the run behavior. If successful, updates numStepsToRun.
     *  and returns true, else returns false.
     **/
    private boolean promptUserForStepCount() 
    {
        int suggested = numStepsToRun > 0 ? numStepsToRun : 10;
        String response = getInitialResponse(suggested);
        while (response != null)
            try
            {
                int userEntered = Integer.parseInt(response.trim());
                if (userEntered <= 0)
                    throw new NumberFormatException();
                numStepsToRun = userEntered;
                return true;
            } 
            catch (NumberFormatException ex)
            {   response = getClarificationResponse(suggested);   }
            catch (Exception ex)
            {   Toolkit.getDefaultToolkit().beep();   }
        return false;
    }

    /** Provides inital prompt for number of steps and gets result. **/
    protected String getInitialResponse(int suggested)
    {
        return (String)JOptionPane.showInputDialog(this, 
                        "Enter number of steps:",
                        "Input", JOptionPane.QUESTION_MESSAGE, null, null,
                        "" + suggested);
    }

    /** Provides a follow-up prompt for number of steps if initial response
     *  failed validation, and gets result.
     **/
    protected String getClarificationResponse(int suggested)
    {
        return (String)JOptionPane.showInputDialog(this, 
                        "Number of steps must be a valid, positive integer:",
                         "Input", JOptionPane.QUESTION_MESSAGE, null, null,
                         "" + suggested);
    }

    /** Starts a timer to repeatedly step the application at
     *  the speed currently indicated by the speed slider.
     *  It will stop when the user clicks on the stop button or
     *  when the application controller indicates that the application
     *  has reached a stopping state.
     **/
    public void run() 
    {
        runningNSteps = false;
        enterRunningMode();
        timer.start(); 
    }

    /** Stops any existing timer currently stepping the application.
     **/
    public void stop()
    {
        timer.stop();
        enterNotRunningMode();
    }

    /** Restarts the application.  Should be redefined if the
     *  application wants to show the grid after the restart.
     **/
    public void setup()
    {
        controller.restart();
        enterNotRunningMode();
    }

    /** Enables and disables GUI components as necessary while the application
     *  is running.
     **/
    protected void enterRunningMode()
    {
        running = true;
        // hide tool tips while running
        if ( getGridDisplay() != null )
            getGridDisplay().setToolTipsEnabled(false);

        stopButton.setEnabled(true);

        stepButton.setEnabled(false);
        nStepsButton.setEnabled(false);
        runButton.setEnabled(false);
        if ( getSetupButton() != null )
            getSetupButton().setEnabled(false);

        if ( getJMenuBar() != null )
            getJMenuBar().setEnabled(false);
    }

    /** Enables and disables GUI components as necessary when an application
     *  is not running.
     **/
    protected void enterNotRunningMode()
    {
        running = runningNSteps = false;
        if ( getGridDisplay() != null )
            getGridDisplay().setToolTipsEnabled(true);

        stopButton.setEnabled(false);

        stepButton.setEnabled(true); 
        nStepsButton.setEnabled(true);
        runButton.setEnabled(true); 
        if ( getSetupButton() != null )
            getSetupButton().setEnabled(true);

        if ( getJMenuBar() != null )
            getJMenuBar().setEnabled(true);
    }

}