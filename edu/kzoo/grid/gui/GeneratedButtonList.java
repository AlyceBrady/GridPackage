// Class: GeneratedButtonList
//
// Author: Alyce Brady
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

import javax.swing.JButton;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *  Grid GUI Support Package:<br>
 *
 *    A <code>GeneratedButtonList</code> object creates a group of
 *    control buttons based on the methods of another class.  When the
 *    <code>GeneratedButtonList</code> object is constructed, the
 *    client code must specify a target object (the object whose methods
 *    are to be associated with control buttons) and a set of arguments
 *    to be passed to those methods.  The <code>GeneratedButtonList</code>
 *    object will generate control buttons for all of the target object's
 *    methods that could be passed the given set of arguments and have a
 *    <code>void</code> return type.
 *
 *  @author Alyce Brady
 *  @version 29 July 2004
 **/
public class GeneratedButtonList extends ArrayList
{
    // Define pattern for on...ButtonClick methods.
    public static final String prefix = "on";
    public static final String suffix = "ButtonClick";
    public static final String methNameRegExpr = prefix + ".+" + suffix;
    public static final Pattern methNamePattern =
                                        Pattern.compile(methNameRegExpr);

    // Instance Variables: Encapsulated data for each GeneratedButtonList object
    protected GridAppFrame gui;
    protected Object targetObj;
    protected boolean includeOnButtonClickMethodsOnly;
    protected List targetMethods;
    protected Object[] methodArguments;
    protected Map buttonLabelToMethodObjMap = new HashMap();
    protected boolean displayGridAfterButtonActions;

  // constructors and their helper methods

    /** Constructs a list of control buttons based on methods associated
     *  with the given target object.  Will only include methods that take
     *  0 arguments and have a <code>void</code> return type.  The text on
     *  each control button will be the associated method name or, if the
     *  name matches the on...ButtonClick format, the middle part of the
     *  method name.  For example, the control button for a doX method
     *  would have the label "doX", while the control button for an
     *  onDoYButtonClick method would have the label "DoY".  When each
     *  resulting control button is pressed, it will send the associated
     *  message to the target object.  It may also display the contents
     *  of the grid afterward, depending on the value of
     *  <code>displayAfterButtonPresses</code>.
     *    @param gui       graphical user interface that will contain these
     *                     buttons
     *    @param targetObj an object of the class whose methods should become
     *                     control buttons; the buttons will forward
     *                     messages to targetObj in response to button presses
     *    @param displayAfterButtonPresses true if the user interface should
     *                     display the contents of the grid after executing
     *                     the behaviors associated with control button
     *                     presses; false otherwise
     **/
    public GeneratedButtonList(GridAppFrame gui, Object targetObj,
                               boolean displayAfterButtonPresses)
    {
        this(gui, targetObj, null, false, displayAfterButtonPresses);
    }

    /** Constructs a list of control buttons based on methods associated
     *  with the given target object.  Will only include methods that
     *  take 0 arguments, have a <code>void</code> return type, and, if
     *  <code>onButtonClickMethodsOnly</code> is true, whose names match
     *  the on...ButtonClick format (e.g., onXYZButtonClick).  The text on
     *  each control button will be the associated method name or, if the
     *  name matches the on...ButtonClick format, the middle part of the
     *  method name.  For example, the control button for a doX method
     *  would have the label "doX", while the control button for an
     *  onDoYButtonClick method would have the label "DoY". When each
     *  resulting control button is pressed, it will send the associated
     *  message to the target object.  It may also display the contents
     *  of the grid afterward, depending on the value of
     *  <code>displayAfterButtonPresses</code>.
     *    @param gui       graphical user interface that will contain these
     *                     buttons
     *    @param targetObj an object of the class whose methods should become
     *                     control buttons; the buttons will forward
     *                     messages to targetObj in response to button presses
     *    @param onButtonClickMethodsOnly true if only on...ButtonClick
     *                     methods should be included; false otherwise
     *    @param displayAfterButtonPresses true if the user interface should
     *                     display the contents of the grid after executing
     *                     the behaviors associated with control button
     *                     presses; false otherwise
     **/
    public GeneratedButtonList(GridAppFrame gui, Object targetObj,
                               boolean onButtonClickMethodsOnly,
                               boolean displayAfterButtonPresses)
    {
        this(gui, targetObj, null, onButtonClickMethodsOnly,
             displayAfterButtonPresses);
    }

    /** Constructs a list of control buttons based on methods associated
     *  with the given target object.  Will only include methods that
     *  could be passed the given set of arguments and that have a
     *  <code>void</code> return type.  The text on each control button
     *  will be the associated method name or, if the name matches the
     *  on...ButtonClick format, the middle part of the method name.  For
     *  example, the control button for a doX method would have the label
     *  "doX", while the control button for an onDoYButtonClick method
     *  would have the label "DoY".  When each resulting control button
     *  is pressed, it will send the associated message to the target object,
     *  passing as arguments the objects provided to this constructor.
     *  It may also display the contents of the grid afterward, depending
     *  on the value of <code>displayAfterButtonPresses</code>.
     *    @param gui       graphical user interface that will contain these
     *                     buttons
     *    @param targetObj an object of the class whose methods should become
     *                     control buttons; the buttons will forward
     *                     messages to targetObj in response to button presses
     *    @param arguments the arguments to pass to methods associated with
     *                     control buttons
     *    @param displayAfterButtonPresses true if the user interface should
     *                     display the contents of the grid after executing
     *                     the behaviors associated with control button
     *                     presses; false otherwise
     **/
    public GeneratedButtonList(GridAppFrame gui,
                               Object targetObj, Object[] arguments,
                               boolean displayAfterButtonPresses)
    {
        this(gui, targetObj, arguments, false, displayAfterButtonPresses);
    }

    /** Constructs a list of control buttons based on methods associated
     *  with the given target object.  Will only include methods that
     *  could be passed the given set of arguments, that have a
     *  <code>void</code> return type, and, if
     *  <code>onButtonClickMethodsOnly</code> is true, whose names match
     *  the on...ButtonClick format (e.g., onXYZButtonClick).  The text on
     *  each control button will be the associated method name or, if the
     *  name matches the on...ButtonClick format, the middle part of the
     *  method name.  For example, the control button for a doX method
     *  would have the label "doX", while the control button for an
     *  onDoYButtonClick method would have the label "DoY".  When each
     *  resulting control button is pressed, it will send the associated
     *  message to the target object, passing as arguments the objects
     *  provided to this constructor.  It may also display the contents
     *  of the grid afterward, depending on the value of
     *  <code>displayAfterButtonPresses</code>. 
     *    @param gui       graphical user interface that will contain these
     *                     buttons
     *    @param targetObj an object of the class whose methods should become
     *                     control buttons; the buttons will forward
     *                     messages to targetObj in response to button presses
     *    @param arguments the arguments to pass to methods associated with
     *                     control buttons
     *    @param onButtonClickMethodsOnly true if only on...ButtonClick
     *                     methods should be included; false otherwise
     *    @param displayAfterButtonPresses true if the user interface should
     *                     display the contents of the grid after executing
     *                     the behaviors associated with control button
     *                     presses; false otherwise
     **/
    public GeneratedButtonList(GridAppFrame gui,
                               Object targetObj, Object[] arguments,
                               boolean onClickButtonMethodsOnly,
                               boolean displayAfterButtonPresses)
    {
        this.gui = gui;
        this.targetObj = targetObj;
        if ( arguments == null )
            this.methodArguments = new Object[0];
        else
            this.methodArguments = arguments;
        this.includeOnButtonClickMethodsOnly = onClickButtonMethodsOnly;
        this.displayGridAfterButtonActions = displayAfterButtonPresses;

        // Identify the target object's methods that will have control buttons.
        identifyButtonMethods();

        // Create the control buttons.
        createButtons();
    }

    /** Identifies the target object's methods for which control buttons
     *  should be created.
     **/
    protected void identifyButtonMethods()
    {
        // Get all the methods from the targetObj class, look through
        // them, and save all the control button methods in a list.
        // (The control button methods are all the void methods that
        // take parameters corresponding to those provided in the
        // GeneratedButtonList constructor.)
        Class targetClass = targetObj.getClass();
        targetMethods = new ArrayList();
        Method[] allMethods = targetClass.getMethods();
        for ( int i = 0; i < allMethods.length; i++ )
        {
            Method meth = allMethods[i];
            if ( meetsControlMethodCriteria(meth, targetClass) )
            {
                targetMethods.add(meth);
                buttonLabelToMethodObjMap.put(buttonLabelFor(meth), meth);
            }
        }
    }

    /** Returns <code>true</code> if the given method was declared in
     *  the specified class (not in one of its superclasses) and has
     *  the right return type and parameters to be turned into a
     *  control button; <code>false</code> otherwise.
     **/
    protected boolean meetsControlMethodCriteria(Method methodToCheck,
                                                 Class targetClass)
    {
        Class retType = methodToCheck.getReturnType();
        Class[] paramTypes = methodToCheck.getParameterTypes();

        // Check whether method was declared in the target class.
        if ( ! (methodToCheck.getDeclaringClass().equals(targetClass)) )
            return false;

        // Check whether method has the right return type & number of
        // parameters.
        if ( ! ( retType.equals(void.class) &&
                 paramTypes.length == methodArguments.length ) )
            return false;

        // Check whether all its parameters are of the right type.
        for ( int j = 0; j < paramTypes.length; j++ )
        {
            if ( ! paramTypes[j].isInstance(methodArguments[j]) )
            {
                // This parameter type is not as expected.
                return false;
            }
        }

        // Check whether the button name has the right format.
        if ( includeOnButtonClickMethodsOnly )
            return meetsMethodNameFormatCriteria(methodToCheck.getName());

        // This method meets all the criteria for a target method.
        return true;
    }

    /** Returns <code>true</code> if the given method was declared in
     *  the specified class (not in one of its superclasses) and has
     *  the right return type and parameters to be turned into a
     *  control button; <code>false</code> otherwise.
     **/
    protected boolean meetsMethodNameFormatCriteria(String methodName)
    {
        Matcher patternMatcher = methNamePattern.matcher(methodName);
        return patternMatcher.matches();
    }

    /** Returns the appropriate button label for the given method. **/
    protected String buttonLabelFor(Method method)
    {
        String methodName = method.getName();
        if ( meetsMethodNameFormatCriteria(methodName) )
        {
            int endIndex = methodName.length() - suffix.length();
            return methodName.substring(prefix.length(), endIndex);
        }
        else
            return methodName;
    }

    /** Creates control buttons that, when pressed, will pass a message to
     *  the appropriate method in the target object.
     *    @param targetMethods the methods for which to create control buttons
     **/
    protected void createButtons()
    {
        Iterator iter = targetMethods.iterator();
        while ( iter.hasNext() )
        {
            Method meth = (Method) iter.next();
            add(new GeneratedThreadedControlButton(gui, buttonLabelFor(meth),
                                                    true));
        }
    }


  // public method to customize button labels

    /** Resets the specified button label associated with a method in
     *  the target object.  The <code>prevButtonLabel</code> parameter
     *  should be the previous button label.  (The default  button label
     *  is the name of the associated method or, if the method name
     *  matches the onXYZButtonClick format, is the name of the method
     *  without the on...ButtonClick prefix and suffix, e.g., XYZ).   This
     *  method should be called before the <code>constructWindowContents</code>
     *  method of the graphical user interface.  This method does nothing
     *  if the list of generated control buttons does not include a button
     *  whose current label is <code>prevButtonLabel</code>. 
     *    @param prevButtonLabel  previous button label
     *    @param buttonLabel label to place on button
     **/
    public void resetButtonLabel(String prevButtonLabel, String buttonLabel)
    {
        // Change the label on the button.
        Iterator iter = iterator();
        while ( iter.hasNext() )
        {
            JButton button = (JButton) iter.next();
            if ( button.getText().equals(prevButtonLabel) )
            {
                button.setText(buttonLabel);

                // Update the button label to method object map.
                Method meth =
                        (Method) buttonLabelToMethodObjMap.get(prevButtonLabel);
                buttonLabelToMethodObjMap.remove(prevButtonLabel);
                buttonLabelToMethodObjMap.put(buttonLabel, meth);
            }
        }
    }


  // Nested class for generated ThreadedControlButton objects

    /** GeneratedThreadedControlButton objects represent buttons whose
     *  button action is to reflectively invoke the appropriate method
     *  in the target object.
     **/
    protected class GeneratedThreadedControlButton
                    extends ThreadedControlButton
    {
        /** Constructs a button that will run in its own thread.
         *    @param gui    graphical user interface containing this button
         *    @param label  label to place on button
         *    @param displayAtEnd true if grid should be displayed when
         *                    button behavior is complete; false otherwise
         **/
        protected GeneratedThreadedControlButton(GridAppFrame gui,
                                                 String label,
                                                 boolean displayAtEnd)
        {
            super(gui, label, displayAtEnd);
        }

        /** Performs the button action associated with this button;
         *  actually delegates the button action to the method in the
         *  target object associated with this button.
         **/
        public void act()
        {
            // Get method associated with button label and try to invoke it.
            Method meth = (Method) buttonLabelToMethodObjMap.get(getText());
            try
            {   meth.invoke(targetObj, methodArguments);  }
            catch (InvocationTargetException e)
            {   throw new IllegalArgumentException("Can't invoke " +
                    meth.getName() + ": " + e);
            }
            catch (IllegalAccessException e)
            {   throw new IllegalArgumentException("Can't invoke " +
                    meth.getName() + ": " + e);
            }
        }
    } 

}
