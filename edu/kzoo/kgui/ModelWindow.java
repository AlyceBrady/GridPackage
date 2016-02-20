// Class ModelWindow
//
// Author: Alyce Brady
//
// This class is based on the College Board's MBSGUIFrame class,
// as allowed by the GNU General Public License.  MBSGUIFrame
// is a black-box class within the AP(r) CS Marine Biology Simulation
// case study.
// (See www.collegeboard.com/student/testing/ap/compsci_a/case.html)
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

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *  K College GUI Package:<br>
 *
 *  The <code>ModelWindow</code> class provides a window in which
 *  to graphically view a model and its contents.  Optional
 *  window components include a menu bar, a speed slider to
 *  control how long the graphical user interface should pause
 *  after displaying the model (useful for animations or other
 *  repeated displays of a changing model), and a control
 *  panel containing a Start/Restart/Reset button.
 *
 *  @author Alyce Brady (based on code by Julie Zelenski)
 *  @version 31 March 2004
 **/
public abstract class ModelWindow extends JFrame
    implements GraphicalModelView
{
  // constants used to determine when components are enabled/disabled
  // [JOEL: THESE SHOULD BE ENUMERATION CONSTANTS IN JAVA 1.5.]

    /** Indicates that a component should be enabled whenever the application
     *  is waiting for user input and disabled when the application is
     *  actively executing (responding to another button, for example).
     **/
    public static final int DISABLED_WHEN_RUNNING = 0;

    /** Indicates that a component should always be enabled. **/
    public static final int ALWAYS_ENABLED = 1;

    /** Indicates that a component should be enabled when the application is
     *  actively executing (responding to another button, for example) and
     *  disabled whenever the application is waiting for user input; useful
     *  for stop buttons, for example.
     **/
    public static final int ENABLED_ONLY_WHEN_RUNNING = 2;

    /** Indicates that a component should always be disabled. **/
    public static final int ALWAYS_DISABLED = 3;

  // constants used to initialize slider

    /** Default minimum value for speed slider bar (pause time in milliseconds). **/
    public static final int DEFAULT_MIN_DELAY_MSECS = 10;

    /** Default maximum value for speed slider bar (pause time in milliseconds). **/
    public static final int DEFAULT_MAX_DELAY_MSECS = 1000;

    /** Default initial value for speed slider bar (pause time in milliseconds). **/
    public static final int DEFAULT_INITIAL_DELAY =
            defaultInitialDelay(DEFAULT_MAX_DELAY_MSECS,
                                DEFAULT_MIN_DELAY_MSECS);

  // instance variables
    private Object model = null;
    private Collection modelChangeListeners = new HashSet();
    private ScrollablePanel graphicalView = null;
    private JScrollPane modelScrollPane = null;
    private JPanel controlButtonsAtTopOfPanel = null;
    private JButton setupButton = null;
    private boolean displayAfterSetUp;

    private int min_delay_msecs = DEFAULT_MIN_DELAY_MSECS, 
                max_delay_msecs = DEFAULT_MAX_DELAY_MSECS,
                initial_delay = DEFAULT_INITIAL_DELAY;
    private int delay = 0;                // time to view the graphical view
    private JSlider speedSlider = new JSlider();
    private boolean includeSlider = false;


  // methods not tied to any particular instance of this class

    /** Calculates the default initial delay when one is not provided
     *  by the user.
     *    @param maxDelay maximum delay value for slider
     *    @param minDelay minimum delay value for slider
     **/
    private static final int defaultInitialDelay(int maxDelay, int minDelay)
    {
        return minDelay + (maxDelay - minDelay)/2;
    }


  // constructor, methods that specify which components to include
  //   in the window, and constructWindowContents method

    /** Constructs an empty ModelWindow window object that will
     *  provide a graphical view for a model.
     *  Use methods such as <code>includeStartRestart</code> and
     *  <code>includeSpeedSlider</code> to include components on the
     *  window other than the basic graphical view of the model.
     *  Use the constructWindowContents method to set the properties of the
     *  window and make it visible.
     **/
    public ModelWindow()
    {
        // Reset exception handler to notify user of exception and
        // generate trace information.
        System.setProperty("sun.awt.exception.handler",
                           GUIExceptionHandler.class.getName());
    }

    /** Includes the specified menu.
     *  If some menu items are enabled/disabled or visible/invisible
     *  based on the existence or identity of the model, then the menu
     *  should be a <code>JMenu</code> subclass, should implement the
     *  <code>ModelChangeListener</code> interface, and should register
     *  itself as a model change listener.
     *  This method will have no effect unless it is
     *  called before the constructWindowContents method.
     *     @param menu the menu to include
     *  @see MinimalFileMenu
     *  @see KHelpMenu
     *  @see addModelChangeListener
     **/
    public void includeMenu(JMenu menu)
    {
        if ( menu != null )
        {
            if ( getJMenuBar() == null )
                setJMenuBar(new JMenuBar());
            getJMenuBar().add(menu);
        }
    }

    /** Includes the specified control button in a list of control
     *  buttons at the top of the control panel.
     *  This method will have no effect unless it is
     *  called before the constructWindowContents method.
     *     @param button the button to include
     **/
    public void includeControlButton(JButton button)
    {
        if ( button != null )
        {
            if ( controlButtonsAtTopOfPanel == null )
            {
                controlButtonsAtTopOfPanel = new JPanel();
                controlButtonsAtTopOfPanel.setLayout(new GridLayout(0, 1));
            }
            controlButtonsAtTopOfPanel.add(button);
        }
    }

    /** Includes a setup or reset button with the specified label.
     *  The grid will not automatically be displayed after the button
     *  is pressed.
     *  <p>
     *  The setup behavior performs within the same thread as the display
     *  and speed adjustment slider bar (if any), so this method should
     *  not be used to run or control an application; any calls to 
     *  <code>showModel</code> and any adjustments to the speed slider
     *  bar will be delayed until this method has finished.  See the
     *  <code>ControlledModelWindow</code> class to create a user
     *  interface with step, run, and/or stop buttons.
     *  <p>
     *  This method will have no effect unless it is
     *  called before the constructWindowContents method.
     *     @param label the button label (examples: "Initialize", "Reset") 
     *     @param enabled true if button should initially be enabled;
     *                    false if button should initially be disabled
     **/
    public void includeSetUpButton(String label, boolean enabled)
    {
        includeSetUpButton(label, enabled, false);
    }

    /** Includes a setup or reset button with the specified label.
     *  The setup behavior performs within the same thread as the display
     *  and speed adjustment slider bar (if any), so this method should
     *  not be used to run or control an application; any calls to 
     *  <code>showModel</code> and any adjustments to the speed slider
     *  bar will be delayed until this method has finished.  See the
     *  <code>ControlledModelWindow</code> class to create a user
     *  interface with step, run, and/or stop buttons.
     *  <p>
     *  This method will have no effect unless it is
     *  called before the constructWindowContents method.
     *     @param label the button label (examples: "Initialize", "Reset") 
     *     @param enabled true if button should initially be enabled;
     *                    false if button should initially be disabled
     *     @param displayAfterSetUp true if grid should be displayed when;
     *                    setup is complete; false otherwise
     **/
    public void includeSetUpButton(String label, boolean enabled,
                                   boolean displayAfterSetUp)
    {
        this.displayAfterSetUp = displayAfterSetUp;

        // Create the button with the specified properties.
        setupButton = new JButton(label);
        setupButton.setEnabled(enabled);

        // Define an action listener for the button.
        setupButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
                { setupAndDisplay(); }});
        includeControlButton(setupButton);
    }

    /** Includes a speed adjustment slider bar with default values.
     *  This method will have no effect unless it is
     *  called before the constructWindowContents method.
     *  The slider will be initialized to range from
     *  <code>DEFAULT_MAX_DELAY_MSECS</code> to
     *  <code>DEFAULT_MIN_DELAY_MSECS</code> (maximum delay is
     *  slowest; minimum dalay is fastest).
     **/
    public void includeSpeedSlider()
    {
        includeSpeedSlider(DEFAULT_MAX_DELAY_MSECS, DEFAULT_MIN_DELAY_MSECS,
                           DEFAULT_INITIAL_DELAY);
    }

    /** Includes a speed adjustment slider bar with the specified
     *  extreme values and an initial delay halfway between them.
     *  This method will have no effect unless it is
     *  called before the constructWindowContents method.
     *  The slider will be initialized to range from
     *  <code>maxDelayMsecs</code> to <code>minDelayMsecsY_MSECS</code>
     *  (maximum delay is slowest; minimum dalay is fastest).
     *  (Precondition: minDelayMsecs &lt;= &lt;= maxDelayMsecs)
     *    @param maxDelayMsecs maximum delay value for slider, in milliseconds
     *    @param minDelayMsecs minimum delay value for slider, in milliseconds
     **/
    public void includeSpeedSlider(int maxDelayMsecs, int minDelayMsecs)
    {
        includeSpeedSlider(maxDelayMsecs, minDelayMsecs,
                        defaultInitialDelay(maxDelayMsecs, minDelayMsecs));
    }

    /** Includes a speed adjustment slider bar with the specified
     *  properties.  This method will have no effect unless it is
     *  called before the constructWindowContents method.
     *  The slider will be initialized to range from
     *  <code>maxDelayMsecs</code> to <code>minDelayMsecsY_MSECS</code>
     *  (maximum delay is slowest; minimum dalay is fastest).
     *  (Precondition: minDelayMsecs &lt;= initialDelayMsecs &lt;= maxDelayMsecs)
     *    @param maxDelayMsecs maximum delay value for slider, in milliseconds
     *    @param minDelayMsecs minimum delay value for slider, in milliseconds
     *    @param initialDelayMsecs initial value for slider, in milliseconds
     **/
    public void includeSpeedSlider(int maxDelayMsecs, int minDelayMsecs,
                                   int initialDelayMsecs)
    {
        includeSlider = true;

        // Initialize the slider values.
        min_delay_msecs = minDelayMsecs;
        max_delay_msecs = maxDelayMsecs;
        if ( minDelayMsecs <= initialDelayMsecs &&
             initialDelayMsecs <= maxDelayMsecs )
        {
            initial_delay = initialDelayMsecs;
        }
        else
            initial_delay = defaultInitialDelay(maxDelayMsecs, minDelayMsecs);
        delay = initial_delay;
        speedSlider.setMinimum(min_delay_msecs);
        speedSlider.setMaximum(max_delay_msecs);
        speedSlider.setValue(initial_delay);

        // Add a change listener to the slider bar.
        speedSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent evt) {
                delay = ((JSlider)evt.getSource()).getValue();
            }});
    }

    /** Constructs the body of a window that displays a graphical view
     *  of the model.  It may have additional components as well, such
     *  as a menu bar, Start/Restart button, or speed slider, depending
     *  on whether any <code>include</code>Component messages were sent
     *  prior to this call to <code>constructWindowContents</code>.
     *    @param title  title to appear in window's title bar
     *    @param viewingWidth  the width of the viewing area
     *    @param viewingHeight the height of the viewing area
     **/
    public void constructWindowContents(String title,
                                        int viewingWidth, int viewingHeight)
    { 
        // Set characteristics for the main frame.
        setTitle(title);
        setLocation(25, 15);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Create the model graphical view that is the center of this
        // graphical user interface.
        graphicalView = constructViewer(viewingWidth, viewingHeight);

        // Define the contents of the main frame.
        setContentPane(defineContent());

        pack();
        setVisible(true);
    }

  // methods that access this object's state, including methods
  // required by the GraphicalModelView interface

    /** Sets the model being displayed and the graphical viewer used to
     *  view it.  This method should only be called by subclass methods
     *  whose parameters are of the appropriate types for the actual model
     *  and viewer used by a particular application.
     *    @param model the model to graphically view
     *    @param viewer the graphical viewer to view <code>model</code>
     **/
    protected void setModel(Object model, ScrollablePanel viewer)
    {
        this.model = model;
        graphicalView = viewer;
        modelScrollPane.setViewportView(graphicalView);
        notifyModelChangeListeners();
        showModel();
    }

    /** Returns the model at the center of this graphical user
     *  interface.
     *    @return the model
     */
    protected Object getModel()
    {
        return model;
    }

    /** Shows the model.
     *  (Precondition: must have called <code>setModel</code>.)
     **/
    public void showModel()
    {
        getGraphicalModelViewer().showModel();
        if ( getDelay() > 0 )
        {
            try 
            {
              Thread.sleep(getDelay());
            }
            catch (InterruptedException e) {}
        }
    }

    /** Constructs an empty model viewer at the heart of the graphical
     *  user interface.  Should be redefined in subclasses that use
     *  a subclass of ScrollablePanel.
     *    @param viewingWidth  the width of the viewing area
     *    @param viewingHeight the height of the viewing area
     *    @return a component for viewing the model
     **/
    protected ScrollablePanel constructViewer(int viewingWidth,
                                              int viewingHeight)
    {
        return new ScrollablePanel(viewingWidth, viewingHeight);
    }

    /** Gets the object that provides the graphical view of the object.
     *    @return the object that provides the graphical view of the object
     **/
    protected ScrollablePanel getGraphicalModelViewer()
    {
        return graphicalView;
    }

    /** Returns the start/restart/reset button (<code>null</code> if no 
     *  such button is included with this graphical user interface).
     **/
    protected JButton getSetupButton()
    {
        return setupButton;
    }

    /** Specifies whether or not to display the contents of the grid
     *  after setup or initialization.
     *    @param whetherToDisplay <code>true</code> if the application
     *                      should redisplay after setup;
     *                      <code>false</code> otherwise
     **/
    public void showDisplayAfterSetUp(boolean whetherToDisplay)
    {
        displayAfterSetUp = whetherToDisplay;
    }

    /** Returns <code>true</code> if the application should redisplay
     *  after setup; returns <code>false</code> otherwise.
     **/
    protected boolean shouldShowDisplayAfterSetUp()
    {
        return displayAfterSetUp;
    }

    /** Returns the speed slider bar (<code>null</code> if no speed slider
     *  is included with this graphical user interface).
     **/
    protected JSlider getSpeedSlider()
    {
        return speedSlider;
    }

    /** Sets the current delay value.  This method overrides the speed slider
     *  bar value without changing the appearance of the slider bar (if
     *  there is one).  Subsequent changes to the slider bar will override
     *  this value.
     *    @param delayMsecs the length of time the application should pause
     *                      after displaying the model to allow users time
     *                      to see it (in milliseconds)
     **/
    public void setDelay(int delayMsecs)
    {
        delay = delayMsecs;
    }

    /** Returns the current delay value from the speed slider or a previous
     *  call to <code>setDelay</code>; defaults to 0 if there is no speed
     *  slider included with this graphical user interface and if
     *  <code>setDelay</code> has never been called.
     *    @return the length of time the application should pause after
     *        displaying the model to allow users time to see it
     *        (in milliseconds)
     **/
    public int getDelay()
    {
        return delay;
    }


  // methods that create components of the graphical user interface

    /** Defines contents of main window panel.  Redefine this method
     *  to define panels other than a model view panel, control panel,
     *  or speed slider bar panel, or to alter the layout or border of
     *  the main contents panel.
     *   @return the panel that will serve as the window's content panel
     **/
    protected JPanel defineContent()
    {
        JPanel content = new JPanel();
        content.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        content.setLayout(new BorderLayout());

        // Create a panel to graphically view the model, an optional control
        // panel for buttons (e.g., a Reset button), and an optional speed
        // slider panel, and add them to the main panel. 
        content.add(makeDisplayPanel(), BorderLayout.CENTER);

        JComponent controlPanel = makeControlPanel();
        if ( controlPanel != null )
            content.add(controlPanel, BorderLayout.WEST);

        JComponent sliderPanel = makeSliderPanel();
        if ( sliderPanel != null )
            content.add(sliderPanel, BorderLayout.SOUTH);

        return content;
    }

    /** Creates the panel for displaying and controlling a model
     *  application.
     **/
    protected JScrollPane makeDisplayPanel()
    {
        // Create a scrollable pane with a viewport that views the
        // graphical view of the model.
        modelScrollPane = new JScrollPane(getGraphicalModelViewer());

        // There are no actions defined for the graphics scrollpane, so
        // it is always "disabled."
        modelScrollPane.setEnabled(false);

        return modelScrollPane;
    }

    /** Creates a control panel that lays out control buttons starting
     *  from the top.
     *    @return a panel containing the control components
     **/
    protected JPanel makeControlPanel()
    {
        return makeControlPanel(null);
    }

    /** Creates a control panel that lays out control buttons starting
     *  from the top.  Redefine this method to change the layout or
     *  border of the control panel.
     *    @param title  a title to put in the border of this control
     *                  panel, or <code>null</code> if no title is desired
     *    @return a panel containing the control components
     **/
    protected JPanel makeControlPanel(String title)
    {
        // Create a panel for the control components.
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BorderLayout());
        if ( title != null )
            controlPanel.setBorder(BorderFactory.createTitledBorder(title));

        // Add the control buttons to the top of the control panel.
        if ( controlButtonsAtTopOfPanel == null )
            return null;
        controlPanel.add(controlButtonsAtTopOfPanel, BorderLayout.NORTH);
        return controlPanel;
    }

    /** Creates a speed slider for controling the speed of the animation.
     *    @return a panel containing the speed slider
     **/
    protected JPanel makeSliderPanel()
    {
        if ( ! includeSlider )
        {
            // No speed slider included on this graphical user interface.
            return null;
        }

        // Define the labels and appearance of the speed slider for
        // controling the speed of the application.
        JPanel sliderPanel = new JPanel();
        sliderPanel.setBorder(BorderFactory.createTitledBorder("Adjust Speed"));
        sliderPanel.add(new JLabel("Slow"));
        speedSlider.setInverted(true);
        speedSlider.setPreferredSize(new Dimension(100, 
                                    speedSlider.getPreferredSize().height));
        speedSlider.setMaximumSize(speedSlider.getPreferredSize());
        sliderPanel.add(speedSlider);
        sliderPanel.add(new JLabel("Fast"));

        // The speed slider is always enabled.
        sliderPanel.setEnabled(true);

        return sliderPanel;
    }

  // Methods that support notifying components or other interested objects
  // about changes to the model (changes in model identity, i.e., which model
  // object is being displayed, not changes to the model contents, which the
  // GUI doesn't know about).

    /** Registers the specified object as interested in being notified
     *  of changes in the identify of the model being modeled.
     *    @param listener  the object that should be notified of
     *                     changes to the identity of the model
     **/ 
    public void addModelChangeListener(ModelChangeListener listener)
    {
        modelChangeListeners.add(listener);
    }

    /** Notifies all registered model change listeners that the model
     *  has been replaced.
     **/
    public void notifyModelChangeListeners()
    {
        Iterator it = modelChangeListeners.iterator();
        while ( it.hasNext() )
        {
            ModelChangeListener listener = (ModelChangeListener)it.next();
            listener.reactToNewModel(getModel());
        }
    }

  // Methods for handling user-initiated events

    /** Sets up (initializes) or resets the application and displays the grid
     *  if appropriate.  Uses the Template Method pattern to make it easier
     *  to redefine the setup behavior and still display only after the
     *  setup has been completed.
     **/
    public void setupAndDisplay()
    {
        setup();

        if ( shouldShowDisplayAfterSetUp() )
            showModel();
    }

    /** Sets up or resets the application.
     *  Currently does nothing, but can be redefined to provide appropriate
     *  initialization for an application.
     *  The setup behavior performs within the same thread as the display
     *  and speed adjustment slider bar (if any), so this method should
     *  not be used to run or control an application; any calls to 
     *  <code>showGrid</code> and any adjustments to the speed slider
     *  bar will be delayed until this method has finished.  See the
     *  <code>ControlledModelWindow</code> class to create a user
     *  interface with step, run, and/or stop buttons.
     **/
    protected void setup()
    {
    }

  // Nested class for handling exceptions
    
    /** Nested class that is registered as the handler for exceptions
     *  on the Swing event thread. The handler will put up an alert panel,
     *  dump the stack trace to the console, and then exit entire program 
     *  rather than persist in an inconsistent state, which would be
     *  the default behavior.
     **/
    public static class GUIExceptionHandler
    {
        public void handle(Throwable e)
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, 
                "An error occurred. The application must exit." + "\nReason: " + e, 
                "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    } 

}


