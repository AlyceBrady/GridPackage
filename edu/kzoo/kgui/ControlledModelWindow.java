// Class ControlledModelWindow
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

package edu.kzoo.kgui;

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
 *  K College GUI Package:<br>
 *
 *  The <code>ControlledModelWindow</code> class provides a window in which
 *  to graphically view a model and its contents, and which allows the
 *  user to control the running of the application via a combination of
 *  Step, NSteps, Run, and Stop buttons.  Other optional window components
 *  supported by the <code>ModelWindow</code> superclass include a menu
 *  bar, a speed slider, and a Start/Restart button.
 *
 *  @author Alyce Brady (based on code by Julie Zelenski)
 *  @version 31 March 2004
 **/
public class ControlledModelWindow extends ModelWindow
{
    protected SteppingModelController controller;
    protected Timer   timer;
    protected boolean putRestartFirst;
    protected JButton stepButton, nStepsButton,
                      runButton, stopButton;
    protected boolean running, runningNSteps;
    private boolean   displayAfterEachStep;
    protected int     numStepsToRun, numStepsSoFar;


  // constructors and initialization methods

    /** Constructs an empty ControlledModelWindow window object that will
     *  display a grid controlled by a combination of Step, NSteps, Run,
     *  and Stop buttons.  Use methods such as includeStepOnceButton,
     *  includeRunButton, and includeStopButton, as well as the
     *  includeSetUpButton and includeSpeedSlider methods from ModelWindow,
     *  to include components on the frame.  Then use the
     *  constructWindowContents method to set the properties of the window
     *  and make it visible.  The displayAfterEachStep parameter specifies
     *  whether the user interface should display the contents of the grid
     *  after each individual step (once each time the Step button is pressed,
     *  repeatedly when the NSteps or Run buttons are pressed). 
     *  (Precondition: control is not <code>null</code>.)
     *    @param control         the object that controls the running of the
     *                           application through step and/or run methods.
     *    @param displayAfterEachStep <code>true</code> if the user interface
     *                           should display the contents of the grid after
     *                           each individual step;
     *                           <code>false</code> otherwise
     **/
    public ControlledModelWindow(SteppingModelController control,
                                 boolean displayAfterEachStep)
    {
        controller = control;
        this.displayAfterEachStep = displayAfterEachStep;

        // Construct the buttons whether they will be included in the
        // control panel or not, so that the enable/disable code does not
        // have to constantly check for each one. 
        stepButton = new JButton("Step Once");
        stepButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
                { stepAndDisplay(); }});

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
    }

    /** Includes the Step Once button in the control panel.
     *  This method will have no effect unless it is
     *  called before the constructWindowContents method.
     **/
    public void includeStepOnceButton()
    {
        includeControlButton(stepButton);
    }

    /** Includes the Step N Times button in the control panel.
     *  This method will have no effect unless it is
     *  called before the constructWindowContents method.
     **/
    public void includeStepNTimesButton()
    {
        includeControlButton(nStepsButton);
    }

    /** Includes the Run button in the control panel.
     *  This method will have no effect unless it is
     *  called before the constructWindowContents method.
     **/
    public void includeRunButton()
    {
        includeControlButton(runButton);
    }

    /** Includes the Stop button in the control panel.
     *  This method will have no effect unless it is
     *  called before the constructWindowContents method.
     **/
    public void includeStopButton()
    {
        includeControlButton(stopButton);
    }


  // methods that access this object's state

    /** Returns the controller used to drive the application. **/
    public SteppingModelController getController()
    {
        return controller;
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

    /** Returns <code>true</code> if the application should redisplay
     *  after each step; returns <code>false</code> otherwise.
     **/
    protected boolean shouldShowDisplayAfterEachStep()
    {
        return displayAfterEachStep;
    }


  // redefinition of methods from the GraphicalModelView interface

    /** Sets the model being displayed and the graphical viewer used to
     *  view it.  This method should only be called by subclass methods
     *  whose parameters are of the appropriate types for the actual model
     *  and viewer used by a particular application.
     *    @param model the model to graphically view
     *    @param viewer the graphical viewer to view <code>model</code>
     **/
    protected void setModel(Object newModel, ScrollablePanel viewer)
    {
        if ( running )
            stop();
        super.setModel(newModel, viewer);

        // Set the application controller's grid to match this one.
        controller.setModel(newModel);

        // Enable and disable buttons as appropriate.
        if ( newModel != null )
            enterNotRunningMode();
    }

    /* (non-Javadoc)
     * @see edu.kzoo.kgui.GraphicalModelView#showModel()
     */
    public void showModel()
    {
        // Does not include a delay, because that is built in to the
        // n steps and run methods.
        getGraphicalModelViewer().showModel();
    }


  // methods that implement button actions

    /** Sets up (initializes) or resets the application.
     *  Should be redefined if the application wants to show
     *  the grid after the initialization or reset.
     **/
    public void setup()
    {
        controller.restart();
        enterNotRunningMode();
    }

    /** Advances the application one step and displays the grid if
     *  appropriate.  Uses the Template Method pattern to make it easier
     *  to redefine the step behavior and still display only after the
     *  step has been completed.
     **/
    public void stepAndDisplay()
    {
        step();

        if ( shouldShowDisplayAfterEachStep() )
            showModel();
    }

    /** Advances the application one step.  Should be redefined if the
     *  application wants to show the model after each step.  For example:
     *  <pre>
     *     super.step();
     *     showModel();
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

    /** Enables and disables GUI components as necessary while the application
     *  is running.
     **/
    protected void enterRunningMode()
    {
        running = true;

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