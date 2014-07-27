import java.io.*;
import java.util.*;
/**
 * This application will solve a maze supplied by a data file 
 * that conforms to specific boundary specifications.
 * @author Sean Bucholtz
 * @version Assignment 3
 */
public class Pathfinder {
  
 // file to hold the maze file
 private File f;
 // dimensions of maze
 private int height;
 private int width;
 // array to store the maze
 private char[][] maze;
 // the starting coordinates
 private int startH;
 private int startW;
 
 /**
  * Parameterized constructor takes a (maze) File object as its argument.
  * @param f the maze file
  */
 public Pathfinder(File f) {
  // instantiate new file object to maze file reference
  this.f = f;
  // sets the maze's dimensions
  getDims();
  // reads and imports the maze file
  readMazeFile();
 }
 
 /**
  * Solves the maze.
  */
 public void solve() {
  // find a path from the start of the maze to the goal
  findPath(startH,startW);
  //places an "S" at the start location
  maze[startH][startW] = 'S';
  // display maze solution
  printSolution();
  
 }

 /**
  * Displays the (completed) maze.
  */
 private void printSolution() {
  // for each row
  for (int row = 0; row < height; row ++) {
   // for each column in the row
   for (int col = 0; col < width; col ++) {
    // print value
    System.out.print(maze[row][col]);
   }
   // start new line for next row
   System.out.println();
  }
 }
 
 /**
  * The recursive method that finds (and marks) the path.
  * @param startH the starting height coordinate
  * @param startW the starting width coordinate
  * @return returns true if the path has been found and false otherwise
  */
 private boolean findPath(int h, int w) {
  // if height and width coordinate arguments are outside the maze's coordinate range
  if (h < 0 || w < 0 || h > height-1 || w > width-1) {
   return false;
  }
  // if height and width coordinate arguments concur with that of the maze's goal coordinates
  if (maze[h][w] == 'G') {
   return true;
  }
  // if height and width coordinate arguments are not equal to an empty space or the start location
  if (maze[h][w] != ' ' && maze[h][w] != 'S') {
   return false;
  }
  // height and width coordinate arguments are then valid
  // place a token in the maze at the current height and width coordinates
  maze[h][w] = '*';
  // boolean sentinel to recursively check for the next valid path
  boolean isOpen = (findPath(h,w+1) || findPath(h+1,w) || 
    findPath(h-1,w) || findPath(h,w-1));
  // if path is not valid
  if (!isOpen) {
   // remove token from maze
   maze[h][w] = ' ';
  }
 // return the valid boolean path
 return isOpen; 
 }
 
 /**
  * Creates the array and reads the maze file into it.
  */
 private void readMazeFile() {
  // instantiate new char array to store maze file
  maze = new char[height][width];
  try {
  // declare and instantiate new scanner object with maze file
  Scanner scan = new Scanner(f);
  // change scanner delimiter to capture entire line
  scan.useDelimiter("\n");
  // declare row counter and set to zero
  int row = 0;
  // scan rows
  while (scan.hasNext()) {
   // line token
   String line = scan.next();
   // char array to hold each value in the line token string
   char[] col = line.toCharArray();
   // add each column to the maze array
   for (int i = 0; i < col.length; i ++) {
    // if start location is found
    if (col[i] == 'S') {
     // set start location height
     startH = row;
     // set start location width
     startW = i;
    }
    // add map value to maze array
    maze[row][i] = col[i];
   }
   // increment row
   row += 1;
  }
  // close scanner
  scan.close();
  return;
 }
 catch (FileNotFoundException e) {
  System.out.println("File not found");
  return;
 }
 }
 
 /**
  * Sets the height and width dimensions of the maze.
  */
 private void getDims() {
  try {
  // declare and instantiate new scanner object with maze file
  Scanner scan = new Scanner(f);
  // change scanner delimiter to capture entire line
  scan.useDelimiter("\n");
  // declare row counter and set to zero
  int rows = 0;
  // declare column counter and set to zero
  int columns = 0;
  // scan rows
  while (scan.hasNext()) {
   // increment row counter
   rows += 1;
   // line token
   String row = scan.next();
   // char array to hold each value in the line token string
   char[] col = row.toCharArray();
   // if the current column value is less than the char column length
   if (col.length > columns) {
    // the current char column length becomes the new columns count
    columns = col.length;
   }
  }
  // height dimension gets the row count
  height = rows;
  // width dimension gets the max column count
  width = columns;
  // close scanner 
  scan.close();
  return;
 }
 catch (FileNotFoundException e) {
  System.out.println("File not found");
  return;
  }
 }
 
}
