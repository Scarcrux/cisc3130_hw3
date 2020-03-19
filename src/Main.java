/* Class: CISC 3130
 * Section: TY9
 * EmplId: 23975673
 * Name: Jonathan Scarpelli
 */

import java.io.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/* Binary Search Tree for Movies */
class MovieBST {
  private MovieNode root;
  private StringBuilder sb = new StringBuilder();

  /* Gets root movie */
  public MovieNode getRoot() {
    return root;
  }

  /* Inserts movie into tree */
  public void put(String key, int value) {
    if (root == null) {
      root = new MovieNode(key, value);
    } else {
      root.put(key, value);
    }
  }

  /* Searches for key and attempts to return a value */
  public String get(String key) {
    return root == null ? null : root.get(key);
  }

  /* Clears the StringBuilder used for CSV output */
  public void PrintClear() {
    sb.setLength(0);
  }
  /* Selects movie titles that fall alphabetically between start and end */
  public void Print(MovieNode node, String title1, String title2) {
    // Base Case: null node
    if (node == null) {
      // Writes the playlist to CSV
      File csvOutputFile = new File("../data/reports/" + title1 + "_" + title2 + ".csv");
      try (PrintWriter  writer= new PrintWriter(csvOutputFile)) {
        writer.write(sb.toString());
      } catch (FileNotFoundException error) {
        System.out.println(error);
      }
      return;
    }

    // If root is greater than title1, then check left subtree
    if (title1.compareTo(node.getTitle()) < 0) {
        Print(node.getLeft(), title1, title2);
    }

    // If root is within range, then print the title
    if ((title1.compareTo(node.getTitle()) < 0
      || title1.compareTo(node.getTitle()) == 0)
      && (title2.compareTo(node.getTitle()) > 0
      || title2.compareTo(node.getTitle()) == 0)) {
      sb.append(node.getTitle());
      sb.append('\n');
      System.out.print(node.getTitle() + " ");
    }

    // If root is smaller than title2, then check right subtree
    if (title2.compareTo(node.getTitle()) > 0) {
      Print(node.getRight(), title1, title2);
    }
  }
}

/* Movie nodes for the BST */
class MovieNode {
  private String title;
  private int releaseYear;
  private MovieNode left, right;

  /* Constructor for a movie with title and release year */
  public MovieNode(String title, int releaseYear) {
    this.title = title;
    this.releaseYear = releaseYear;
  }

  /* Adds a new movie node or updates an existing one */
  public void put(String key, int releaseYear) {
    // Checks whether the key is alphabetically less than
    if (key.compareTo(this.title) < 0 ) {
      if (left != null) {
        left.put(key, releaseYear);
      } else {
        left = new MovieNode(key, releaseYear);
      }
    } // Checks whether the key is alphabetically greater
    else if (key.compareTo(this.title) > 0) {
      if ( right != null ) {
        right.put(key, releaseYear);
      } else {
        right = new MovieNode(key, releaseYear);
      }
    } // Updates the movie because it's the same
    else {
      this.releaseYear = releaseYear;
    }
  }

  /* Gets movie title */
  public String getTitle() {
    return title;
  }

  /* Gets left node */
  public MovieNode getLeft() {
    return left;
  }

  /* Gets right node */
  public MovieNode getRight() {
    return right;
  }

  /* Searches for movie and attempts to return a value */
  public String get(String key) {
    if (this.title.equals(key)) {
      return title + " "+ releaseYear;
    }
    if (key.compareTo(this.title) < 0) {
      return left == null ? null : left.get(key);
    } else {
      return right == null ? null : right.get(key);
    }
  }
}

/* Test class */
public class Main {
  public static void main(String[] args) {
    // Creates an array to store the names of files and directories
    String[] myFiles;

    // Creates a new File instance by converting the given pathname String
    // into an abstract pathname
    String path = "../data/";
    File f = new File(path);

   // Filters to only include CSV files
    FilenameFilter filter = new FilenameFilter() {
      @Override
      public boolean accept(File f, String name) {
        return name.endsWith(".csv");
      }
    };
    myFiles = f.list(filter);

    // Appends the path to the filename
    for (int i = 0; i < myFiles.length; i++) {
      myFiles[i] = path + myFiles[i];
    }

    // Creates the BST and populates it with data
    MovieBST tree = new MovieBST();
    String csvFile = myFiles[0];
    BufferedReader br = null;
    String line = "";
    String cvsSplitBy = ",";

    try {
      br = new BufferedReader(new FileReader(csvFile));
      while ((line = br.readLine()) != null) {
        // Uses comma as a delimiter
        String[] movies = line.split(cvsSplitBy);

        String temp = movies[1];
        Pattern p = Pattern.compile("\\((\\d{4}?)\\)$");
        Pattern p2 = Pattern.compile("^([^()]*)");
        Matcher m = p.matcher(temp);
        Matcher m2 = p2.matcher(temp);

        // If regex matches found, then inserts movie into tree
        while(m.find() && m2.find()) {
          if (movies[1].equals("title")) {

          } else if (movies[1] == null) {
          } else {
              tree.put(m2.group(1).trim(), Integer.parseInt(m.group(1).trim()));

          }
        }
      }
    } catch (FileNotFoundException error) {
      error.printStackTrace();
    } catch (IOException error) {
      error.printStackTrace();
    } finally {
      if (br != null) {
        try {
          br.close();
        } catch (IOException error) {
          error.printStackTrace();
        }
      }
    }

    // Test Output
    System.out.println(tree.get("Flint"));
    tree.Print(tree.getRoot(), "Superman", "Zeus");
    tree.PrintClear();
    System.out.println("\n");
    tree.Print(tree.getRoot(), "Harry Potter", "John");
  }
}
