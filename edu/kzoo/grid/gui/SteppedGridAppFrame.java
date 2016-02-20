// Class SteppedGridAppFrame
//
// Author: Alyce Brady
//
// This class is based on the College Board's MBSGUIFrame class,
// as allowed by the GNU General Public License.  MBSGUIFrame
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

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *  Grid GUI Support Package:<br>
 *
 *  The <code>SteppedGridAppFrame</code> class provides a window in which
 *  to run and display a grid application controlled by the user via
 *  a combination of Step, NSteps, Run, and Stop buttons.  Other options
 *  provided by the <code>GridAppFrame</code> superclass include a
 *  speed slider bar and a Start/Restart button.
 *
 *  @author Alyce Brady (based on code by Julie Zelenski)
 *  @version 1 September 2004
 **/
public class SteppedGridAppFrame extends GridAppFrame
{
    protected SteppedGridAppController appController;
    protected Timer   timer;
    protected boolean displayAfterEachStep;
    protected boolean runningNSteps;
    protected int     numStepsToRun, numStepsSoFar;


  // constructors and methods that specify which components to include
  //   in the window

    /** Constructs an empty SteppedGridAppFrame window object that will
     *  display a grid controlled by a combination of Step, NSteps, Run, and
     *  Stop buttons.  Use methods such as includeStepOnceButton,
     *  includeRunButton, and includeStopButton, as well as the
     *  includeSetUpButton and includeSpeedSlider methods from GridAppFrame,
     *  to include components on the frame.  Then use the
     *  constructWindowContents method to set the properties of the window
     *  and make it visible.  The displayAfterEachStep parameter specifies
     *  whether the user interface should display the contents of the grid
     *  after each individual step (once each time the Step button is pressed,
     *  repeatedly when the NSteps or Run buttons are pressed). 
     *  (Precondition: control is not <code>null</code>.)
     *    @param control            the object that controls the running of
     *                              the grid application through step
     *                              and/or run methods
     *    @param displayAfterEachStep <code>true</code> if the user interface
     *                              should display the contents of the grid
     *                              after each individual step;
     *                              <code>false</code> otherwise
     **/
    public SteppedGridAppFrame(SteppedGridAppController control,
                               boolean displayAfterEachStep)
    {
        appController = control;
        this.displayAfterEachStep = displayAfterEachStep;
    }

    /** Includes a set/initialize/reset button with the specified label.
     *  This method will have no effect unless it is
     *  called before the constructWindowContents method.
     *     @param label the button label (examples: "Initialize", "Start",
     *                                              "Restart", "Reset") 
     *     @param enableDisableIndicator indicates when the set/reset button
     *                                   should be enabled or disabled
     *     @param displayAfterSetReset true if grid should be displayed after
     *                    set/reset is complete; false otherwise
     **/
    public void includeSetResetButton(String label,
                                      int enableDisableIndicator,
                                      boolean displayAfterSetReset)
    {
        // Create the button and add to control panel.
        JButton startButton = 
            new ThreadedControlButton(this, label, displayAfterSetReset)
                { public void act() { initialize(); }};
        includeControlComponent(startButton, enableDisableIndicator);
    }

    /** Includes a set/initialize/reset button with the specified label.
     *  The button starts out enabled (visible and usable)
     *  or disabled according to the <code>initiallyEnabled</code> parameter;
     *  it later switches to enabled or disabled under the conditions
     *  indicated by the <code>enableDisableIndicator</code> parameter.
     *  Note, though, that it will only switch if there is a state change
     *  that would normally cause it to switch.  It will never switch,
     *  for example, if the <code>enableDisableIndicator</code> parameter
     *  is ALWAYS_ENABLED or ALWAYS_DISABLED, because there is no defined
     *  state change that would cause such components to switch.  Thus,
     *  a component that is specified as ALWAYS_ENABLED but initially
     *  disabled will remain disabled throughout the application.
     *  <p>This method will have no effect unless it is
     *  called before the constructWindowContents method.
     *     @param label the button label (examples: "Initialize", "Start",
     *                                              "Restart", "Reset") 
     *     @param enableDisableIndicator indicates when the set/reset button
     *                                   should be enabled or disabled
     *     @param initiallyEnabled true if button should initially be enabled;
     *                    false if button should initially be disabled
     *     @param displayAfterSetReset true if grid should be displayed after
     *                    set/reset is complete; false otherwise
     **/
    public void includeSetResetButton(String label,
                                      int enableDisableIndicator,
                                      boolean initiallyEnabled,
                                      boolean displayAfterSetReset)
    {
        // Create the button and add to control panel.
        JButton startButton = 
            new ThreadedControlButton(this, label, displayAfterSetReset)
                { public void act() { initialize(); }};
        includeControlComponent(startButton, enableDisableIndicator);
        startButton.setEnabled(initiallyEnabled);
    }

    /** Includes the Step Once button in the control panel.
     *  This method will have no effect unless it is
     *  called before the constructWindowContents method.
     **/
    public void includeStepOnceButton()
    {
        // Create the button and add to control panel.
        JButton stepButton =
            new ControlButton(this, "Step Once", displayAfterEachStep)
                {  public void act() { step(); } };
        includeControlComponent(stepButton,
                           EnabledDisabledStates.NEEDS_GRID_AND_APP_WAITING);
    }

    /** Includes the Step N Times button in the control panel.
     *  This method will have no effect unless it is
     *  called before the constructWindowContents method.
     **/
    public void includeStepNTimesButton()
    {
        // Create the button and add to control panel.
        JButton nStepsButton = 
            new ControlButton(this, "Step N Times", false)
                {  public void act() { nSteps(); } };
        includeControlComponent(nStepsButton,
                            EnabledDisabledStates.NEEDS_GRID_AND_APP_WAITING);
    }

    /** Includes the Run button in the control panel.
     *  This method will have no effect unless it is
     *  called before the constructWindowContents method.
     **/
    public void includeRunButton()
    {
        // Create the button and add to control panel.
        JButton runButton =
            new ControlButton(this, "Run...", false)
                {  public void act() { run(); } };
        includeControlComponent(runButton,
                            EnabledDisabledStates.NEEDS_GRID_AND_APP_WAITING);
    }

    /** Includes the Stop button in the control panel.
     *  This method will have no effect unless it is
     *  called before the constructWindowContents method.
     *     @param displayAfterStopping true if grid should be displayed after
     *                    stop is complete; false otherwise
     **/
    public void includeStopButton(boolean displayAfterStopping)
    {
        // Create the button and add to control panel.
        JButton stopButton =
            new ControlButton(this, "Stop", displayAfterStopping)
                {  public void act() { stop(); } };
        includeControlComponent(stopButton,
                                EnabledDisabledStates.NEEDS_APP_RUNNING);
    }


  // methods that access this object's state

    /** Returns the controller used to drive the application. **/
    public SteppedGridAppController getController()
    {
        return appController;
    }

    /** Specifies whether or not to display the contents of the grid
     *  after each step.
     *    @param whetherToDisplay <code>true</code> if the application
     *                      should redisplay after each step;
     *                      <code>false</code> otherwise
     **/
    public void showDisplayAfterEachStep(boolean whetherToDisplay)
    {
        displayAfterEachStep = whetherToDisplay;
    }


  // methods and nested classes for building the control panel

    /** Creates the control panel.
     *    @param title description for this set of control components
     *    @return a panel containing the control components
     **/
    protected JPanel makeControlPanel(String title)
    {
        // Create a panel for the control components.
        if ( title == null )
            title = "Control Buttons";
        JPanel controlPanel = super.makeControlPanel(title);

        // Define timer used by NSteps and Run buttons.  Define it here
        // rather than in the constructor because it is critical that
        // the speed slider (if there's going to be one) be created first.
        timer = new Timer(getDelay(),
                          new ActionListener()
                          {   public void actionPerformed(ActionEvent evt)
                              {   stepAndDisplay();   }
                          });


        // Timer needs to listen for changes to the speed slider.
        if ( getSpeedSlider() != null )
        {
            getSpeedSlider().addChangeListener(new ChangeListener() {
                public void stateChanged(ChangeEvent evt) {
                    timer.setDelay(((JSlider)evt.getSource()).getValue());
                }});
        }

        return controlPanel;
    }


  // redefinition of methods from the GridDisplay interface

    /** Sets the Grid being displayed.  If the application is actively
     *  running, setGrid stops it.
     *    @param grid the Grid to display
     **/
    public void setGrid(Grid grid)
    {
        if ( isInRunningMode() )
            stop();
        super.setGrid(grid);

        // Set the application controller's grid to match this one.
        appController.setGrid(grid);

        // Enable and disable buttons as appropriate.
        if ( grid != null )
            enterNotRunningMode();
    }

    /** Shows the grid.
     **/
    public void showGrid()
    {
        getDisplay().showGrid();
    }


  // methods that implement button actions

    /** Sets up (initializes) or resets the application. **/
    public void initialize()
    {
        appController.init();
    }

    /** Advances the application one step and displays the grid if
     *  appropriate.  Uses the Template Method pattern to make it easier
     *  to redefine the step behavior and still display only after the
     *  step has been completed.
     **/
    public void stepAndDisplay()
    {
        step();

        if ( displayAfterEachStep )
            showGrid();
    }

    /** Advances the application one step. **/
    public void step()
    {
        appController.step();

        if ( runningNSteps )
            numStepsSoFar++;

//        if ( isInRunningMode() && shouldStop() )
        if ( shouldStop() )
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
                 appController.hasReachedStoppingState() );
    }

    /** Advances the application multiple (N) steps (where N is provided
     *  by the user in a dialog box) in a separate thread.  It may stop
     *  before the N steps have completed if the user clicks on the stop
     *  button (if one is provided) or if the application controller
     *  indicates that the application has reached a stopping state.
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
     *  has reached a stopping state.  The step action happens in
     *  a separate thread from the graphical user interface.
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

    /** Enables and disables GUI components as necessary when an application
     *  is not running.
     **/
    public void enterNotRunningMode()
    {
        runningNSteps = false;
        super.enterNotRunningMode();
    }

}