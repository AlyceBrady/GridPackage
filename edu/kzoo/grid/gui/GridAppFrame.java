// Class GridAppFrame
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

package edu.kzoo.grid.gui;

import edu.kzoo.grid.Grid;
import edu.kzoo.grid.Location;

import edu.kzoo.grid.display.GridDisplay;
import edu.kzoo.grid.display.PseudoInfiniteViewport;
import edu.kzoo.grid.display.ScrollableGridDisplay;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
 *  Grid GUI Support Package:<br>
 *
 *  The <code>GridAppFrame</code> class provides a window in which
 *  to display a grid and its contents.  Options include a speed slider
 *  bar and a control panel containing a Start/Restart/Reset button.
 *
 *  @author Alyce Brady (based on code by Julie Zelenski)
 *  @version 29 February 2004
 **/
public class GridAppFrame extends JFrame implements GridDisplay
{
  // constants used to initialize slider
    public static final int DEFAULT_MIN_DELAY_MSECS = 10;
    public static final int DEFAULT_MAX_DELAY_MSECS = 1000;
    public static final int DEFAULT_INITIAL_DELAY =
            defaultInitialDelay(DEFAULT_MAX_DELAY_MSECS,
                                DEFAULT_MIN_DELAY_MSECS);

  // instance variables
    private Grid grid = null;
    private Collection gridChangeListeners = new HashSet();
    private ScrollableGridDisplay display = null;
    private JPanel controlButtonsAtTopOfPanel = null;
    private JButton setupButton = null;
    protected boolean displayAfterSetUp;

    private int min_delay_msecs = DEFAULT_MIN_DELAY_MSECS, 
                max_delay_msecs = DEFAULT_MAX_DELAY_MSECS,
                initial_delay = DEFAULT_INITIAL_DELAY;
    private int delay = 0;                   // time to view the display
    private JSlider speedSlider = null;


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

    /** Constructs an empty GridAppFrame window object that will
     *  display a grid.
     *  Use methods such as <code>includeStartRestart</code> and
     *  <code>includeSpeedSlider</code> to include components on the
     *  window other than the basic grid display.
     *  Use the constructWindowContents method to set the properties of the
     *  window and make it visible.
     **/
    public GridAppFrame()
    {
        // Reset exception handler to notify user of exception and
        // generate trace information.
        System.setProperty("sun.awt.exception.handler",
                           GUIExceptionHandler.class.getName());
    }

    /** Includes the specified menu.
     *  If some menu items are enabled/disabled or visible/invisible
     *  based on the existence or identity of the grid, then the menu
     *  should be a <code>JMenu</code> subclass, should implement the
     *  <code>GridChangeListener</code> interface, and should register
     *  itself as a grid change listener.
     *  This method will have no effect unless it is
     *  called before the constructWindowContents method.
     *     @param menu the menu to include
     *  @see edu.kzoo.kgui.KHelpMenu
     *  @see BasicGridFileMenu
     *  @see #addGridChangeListener
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

    /** Includes a setup or reset button with the specified label.
     *  The grid will not automatically be displayed after the button
     *  is pressed.
     *  <p>
     *  The setup behavior performs within the same thread as the display
     *  and speed adjustment slider bar (if any), so this method should
     *  not be used to run or control an application; any calls to 
     *  <code>showGrid</code> and any adjustments to the speed slider
     *  bar will be delayed until this method has finished.  See the
     *  <code>ControlledGridAppFrame</code> class to create a user
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
     *  <code>showGrid</code> and any adjustments to the speed slider
     *  bar will be delayed until this method has finished.  See the
     *  <code>ControlledGridAppFrame</code> class to create a user
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

        // Create the slider and add a change listener to it.
        speedSlider = new JSlider(min_delay_msecs, max_delay_msecs,
                                          initial_delay);
        speedSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent evt) {
                delay = ((JSlider)evt.getSource()).getValue();
            }});
    }

    /** Constructs the body of a window containing a scrollable
     *  display for a grid and its contents.
     *    @param title frame title
     *    @param bgColor       color to paint background of grid
     *    @param viewingWidth  the width of the viewing area
     *    @param viewingHeight the height of the viewing area
     *    @param minCellSize   minimum grid cell side length
     **/
    public void constructWindowContents(String title, Color bgColor,
                                        int viewingWidth, int viewingHeight,
                                        int minCellSize)
    { 
        // Set characteristics for the main frame.
        setTitle(title);
        setLocation(25, 15);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Create the grid display that is the center of this
        // graphical user interface.
        display = constructDisplay(viewingWidth, viewingHeight,
                                   minCellSize, bgColor);
        display.addMouseListener(getMouseListenerForDisplay());
        gridChangeListeners.add(display);

        // Define the contents of the main frame.
        setContentPane(defineContent());

        pack();
        setVisible(true);
    }

    /** Constructs the grid display at the heart of the graphical
     *  user interface.  Should be redefined in subclasses that wish
     *  to use a subclass of <code>ScrollableGridDisplay</code> (for
     *  example, to modify the way tool tips are displayed, or some
     *  other aspect of the scrollable display).
     *    @param viewingWidth  the width of the viewing area
     *    @param viewingHeight the height of the viewing area
     *    @param minCellSize   minimum grid cell side length
     *    @param bgColor       color to paint background of grid
     *    @return a scrollable grid display
     **/
    protected ScrollableGridDisplay constructDisplay(
        int viewingWidth, int viewingHeight, int minCellSize, Color bgColor)
    {
        return new ScrollableGridDisplay(viewingWidth, viewingHeight,
                                         minCellSize, bgColor);
    }

  // methods that access this object's state, including methods
  // required by the GridDisplay interface

    /** Sets the grid being displayed.
     *    @param grid the Grid to display
     **/
    public void setGrid(Grid grid)
    {
        this.grid = grid;
        notifyGridChangeListeners();
    }

    /** Returns the grid at the center of this graphical user
     *  interface.
     *    @return the grid
     */
    protected Grid getGrid()
    {
        return grid;
    }

    /** Shows the grid.
     *  (Precondition: must have called <code>setGrid</code>.)
     **/
    public void showGrid()
    {
        display.showGrid();
        if ( getDelay() > 0 )
        {
            try 
            {
              Thread.sleep(getDelay());
            }
            catch (InterruptedException e) {}
        }
    }

    /** Gets the grid display.
     *    @return a scrollable grid display
     **/
    protected ScrollableGridDisplay getDisplay()
    {
        return display;
    }

    /** Returns a mouse adapter that responds to mouse presses over
     *  the grid display.
     *  Subclasses that wish to respond to other mouse events should
     *  redefine this method to return a subclass of DisplayMouseListener
     *  (or another MouseAdapter subclass) that handles other mouse events.
     **/
    protected MouseAdapter getMouseListenerForDisplay()
    {
        // Return an instance of an anonymous inner subclass of MouseAdapter.
        return new DisplayMouseListener();
    }

    /** Returns the setup/set/reset button (<code>null</code> if no 
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
     *                      after displaying the grid to allow users time
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
     *        displaying the grid to allow users time to see it
     *        (in milliseconds)
     **/
    public int getDelay()
    {
        return delay;
    }


  // methods that create components of the graphical user interface

    /** Defines contents of main window panel.  Redefine this method
     *  to define panels other than a grid display panel, control panel,
     *  or speed slider bar panel, or to alter the layout or border of
     *  the main contents panel.
     *   @return the panel that will serve as the window's content panel
     **/
    protected JPanel defineContent()
    {
        JPanel content = new JPanel();
        content.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        content.setLayout(new BorderLayout());

        // Create a panel to display the grid, an optional control panel for
        // buttons (e.g., a Reset button), and an optional speed slider panel,
        // and add them to the main panel. 
        content.add(makeDisplayPanel(), BorderLayout.CENTER);

        JComponent controlPanel = makeControlPanel(null);
        if ( controlPanel != null )
            content.add(controlPanel, BorderLayout.WEST);

        JComponent sliderPanel = makeSliderPanel();
        if ( sliderPanel != null )
            content.add(sliderPanel, BorderLayout.SOUTH);

        return content;
    }

    /** Creates the panel for displaying a grid application.
     **/
    protected JComponent makeDisplayPanel()
    {
        // Create a scrollable pane with a viewport that views the
        // grid display.
        JScrollPane scrollPane = new JScrollPane();
        PseudoInfiniteViewport vp = new PseudoInfiniteViewport(scrollPane);
        scrollPane.setViewport(vp);
        scrollPane.setViewportView(getDisplay());

        // There are no actions defined for the viewing scrollpane, so
        // it is always "disabled."
        scrollPane.setEnabled(false);

        return scrollPane;
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
        if ( speedSlider == null )
        {
            // No speed slider.
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
  // about changes to the grid (changes in grid identity, i.e., which grid
  // object is being displayed, not changes to the grid contents, which the
  // GUI doesn't know about).

    /** Registers the specified object as interested in being notified
     *  of changes in the identify of the grid being modeled.
     *    @param listener  the object that should be notified of
     *                     changes to the identity of the grid
     **/ 
    public void addGridChangeListener(GridChangeListener listener)
    {
        gridChangeListeners.add(listener);
    }

    /** Notifies all registered model change listeners that the model
     *  has been replaced.
     **/
    public void notifyGridChangeListeners()
    {
        Iterator it = gridChangeListeners.iterator();
        while ( it.hasNext() )
        {
            GridChangeListener listener = (GridChangeListener)it.next();
            listener.reactToNewGrid(getGrid());
        }
    }

  // Methods for handling user-initiated events

    /** Handles a mouse press over the grid display.
     *  Currently does nothing, but can be redefined in subclasses
     *  to handle actions.
     **/
    protected void onMousePressOverDisplay(Location loc)
    {
    }

    /** Sets up (initializes) or resets the application and displays the grid
     *  if appropriate.  Uses the Template Method pattern to make it easier
     *  to redefine the setup behavior and still display only after the
     *  setup has been completed.
     **/
    public void setupAndDisplay()
    {
        setup();

        if ( displayAfterSetUp )
            showGrid();
    }

    /** Sets up (initializes) or resets the application.
     *  Currently does nothing, but can be redefined to provide appropriate
     *  initialization for an application.
     *  The setup behavior performs within the same thread as the display
     *  and speed adjustment slider bar (if any), so this method should
     *  not be used to run or control an application; any calls to 
     *  <code>showGrid</code> and any adjustments to the speed slider
     *  bar will be delayed until this method has finished.  See the
     *  <code>ControlledGridAppFrame</code> class to create a user
     *  interface with step, run, and/or stop buttons.
     **/
    public void setup()
    {
    }

  // Nested class for handling mouse events over the grid display

    /** Nested class that handles simple mouse presses over the grid
     *  display.  Can be extended to handle other mouse events (mouse
     *  release, mouse click, etc.).
     **/
    public class DisplayMouseListener extends MouseAdapter
    {
        // Redefined method from MouseAdapter
        public void mousePressed(MouseEvent evt)
        {
            Location loc = getMouseLocation(evt);
            if ( loc != null )
                onMousePressOverDisplay(loc);
        }

        /** Returns the Location in the grid corresponding to the location
         *  of the mouse event.
         **/
        public Location getMouseLocation(MouseEvent evt)
        {
            return getDisplay().locationForPoint(evt.getPoint());
        }
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


