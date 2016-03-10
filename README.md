# GridPackage
Framework for creating Java learning exercises based on 2-D grids.

There are many introductory programming assignments that involve objects in a 
two-dimensional data structure.  They include games, like tic-tac-toe,
checkers, and chess; maze programs; simulations, like 
[Conway's Game of
Life](https://en.wikipedia.org/wiki/Conway's_Game_of_Life "Wikipedia article")
or the [AP® Marine Biology Simulation (MBS) case
study](http://www.cs.kzoo.edu/AP/MBS/ "MBS home page");
and simpler programs that use a grid as graph paper for
drawing histograms or bit-mapped drawings.  These projects lend themselves to
graphical representations, but the overhead involved in implementing graphical
user interfaces for such assignments, especially interfaces that support user
interaction, is non-trivial.  The Grid Package provides a set of simple Java
classes for modeling objects in a two-dimensional grid, and provides a library
of other classes that make it easy to create graphical user interfaces to
display and interact with such models.

The Grid Package was inspired by, and evolved from, the AP® Marine Biology 
Simulation (MBS) case study.  The MBS case study introduced a two-dimensional 
data structure called an Environment, which represents the marine environment
(lake, bay, or pond) for fish in a simulation.  The Grid Package introduces a
similar Grid data structure that models a two-dimensional grid made up of rows
and columns and a GridObject class that represents objects in a grid.  Each
cell in a grid may be empty or may contain one GridObject object. A GridObject
object keeps track of its own row/column location in the grid and provides
methods for checking and changing an object's location.  Subclasses of the
GridObject class represent different kinds of objects that can be placed in a
grid, each of which may have different behavior.

The example below creates a 
simple grid with two objects (subclasses of GridObject) in it.

    Grid grid = new BoundedGrid(3, 3);
    grid.add(new TextCell("A"), new Location(0, 0));
    grid.add(new ColorBlock(Color.RED), new Location(2, 2));

The Grid Package supports five basic types of graphical user interfaces for 
applications that use grid objects.  An application might have:

- a simple GUI containing a display of the contents of a grid (Figure 2a in the DraftOverview document),
- a simple GUI containing an animated display of the changing contents of a 
grid, with a slider bar to control the speed of the animation (Figure 2b in the DraftOverview),
- an interactive GUI that allows users to control the behavior of the 
application by clicking on control buttons (Figure 2c in the
DraftOverview),
- a specialized interactive GUI that operates on "stepped" applications 
(applications such as simulations that run in discrete steps), with control 
buttons such as Start/Restart, Step, or Run (Figure 2d in the
DraftOverview), or
- an interactive GUI that allows users to control the behavior of the 
application by clicking on grid cells (the appearance of this kind of GUI is
similar to those above).

The heart of any graphical user interface for a grid-based application is the 
display of the grid contents.  The Grid Package provides a
ScrollableGridDisplay class that knows how to display a grid.  The application,
though, needs to specify how to display the individual objects in the grid.
Several display classes, such as ColorBlockDisplay,
TextCellDisplay, and DefaultDisplay (displays a question mark), are provided in
the Grid Package.  Furthermore, there are several classes for displaying
images, such as ScaledImageDisplay, that provide an easy way to display other
kinds of grid objects without writing graphics code.

The DraftOverview.html document is a draft overview of the Grid Package.
There are sections that are incomplete (and possibly sections that are
somewhat out-of-date), but it still provides a detailed introduction to
the Model, View, and Controller components of the package, and provides
examples and instructions for using the package to create passive grid
applications (e.g., a histogram), animated grid applications (e.g.,
moving a queen on a chessboard), grid applications driven by control buttons
(e.g., New Grid and Reset buttons), generating buttons automatically
from the methods of a class, creating grid applications driven by
Step/Run/Stop control buttons, and creating grid applications driven by
mouse clicks in grid cells.

