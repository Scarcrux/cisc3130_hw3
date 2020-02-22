/* Class: CISC 3130
 * Section: TY9
 * EmplId: 23975673
 * Name: Jonathan Scarpelli
 */

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

  class MovieBST {
    private MovieNode root;

    // gets root movie
    public MovieNode getRoot() {
      return root;
    }

    // inserts element into tree
    public void put(String key, int value) {
      if (root == null) {
        root = new MovieNode( key, value );
      } else {
        root.put( key, value );
      }
    }

    // finds key and returns value if it exists
    public String get(String key) {
      return root == null ? null : root.get(key);
    }

    // Selects movie titles that fall alphabetically between start and end
    public void Print(MovieNode node, String title1, String title2) {
      /* base case */
      if (node == null) {
        return;
      }

      // If root is greater than title1, then check left subtree
      if (title1.compareTo(node.getTitle()) < 0) {
          Print(node.getLeft(), title1, title2);
      }

      /* If root is within range, then print the title */
      if ((title1.compareTo(node.getTitle()) < 0
        || title1.compareTo(node.getTitle()) == 0)
        && (title2.compareTo(node.getTitle()) > 0
        || title2.compareTo(node.getTitle()) == 0)) {
        System.out.print(node.getTitle() + " ");
      }

      /* If root is smaller than title2, then check right subtree */
      if (title2.compareTo(node.getTitle()) > 0) {
        Print(node.getRight(), title1, title2);
      }
    }
}

  class MovieNode {
  private String title;
  private int releaseYear;
  private MovieNode left, right;

  public MovieNode(String title, int releaseYear) {
    this.title = title;
    this.releaseYear = releaseYear;
  }

  //if key not found in MovieBST then it is added. if key already exists then that node's value
  //is updated.
  public void put( String key, int releaseYear) {
    if ( key.compareTo( this.title ) < 0 ) {
      if ( left != null ) {
        left.put( key, releaseYear);
      } else {
        left = new MovieNode( key, releaseYear);
      }
    } else if ( key.compareTo( this.title ) > 0 ) {
      if ( right != null ) {
        right.put( key, releaseYear );
      } else {
        right = new MovieNode( key, releaseYear );
      }
    } else {
      this.releaseYear = releaseYear;
    }
  }

  // returns movie title
  public String getTitle() {
    return title;
  }

  // returns left node
  public MovieNode getLeft() {
    return left;
  }

  // returns right node
  public MovieNode getRight() {
    return right;
  }

  // finds Node with given key and return its value
  public String get(String key) {
    if (this.title.equals(key)) {
      return title + " "+ releaseYear;
    }

    if (key.compareTo(this.title) < 0) {
      return left == null ? null : left.get(key);
    } else {
      return right == null ? null : right.get( key );
    }
  }
}

/* Test class */
public class Main {
  public static void main(String[] args) {
    // Creates an array in which we will store the names of files and directories
    String[] myFiles;

    // Creates a new File instance by converting the given pathname string
    // into an aMovieBSTract pathname
    String path = "../data/";
    File f = new File(path);

      // This filter will only include files ending with .py
    FilenameFilter filter = new FilenameFilter() {
      @Override
      public boolean accept(File f, String name) {
        return name.endsWith(".csv");
      }
    };

   myFiles = f.list(filter);

    // appends the path to the filename
    for (int i = 0; i < myFiles.length; i++) {
      myFiles[i] = path + myFiles[i];
    }

    for (String element: myFiles) {
      System.out.println(element);
    }

    MovieBST tree = new MovieBST();

    String csvFile = myFiles[0];
    BufferedReader br = null;
    String line = "";
    String cvsSplitBy = ",";

    try {
      br = new BufferedReader(new FileReader(csvFile));
      while ((line = br.readLine()) != null) {
        // Uses comma as a delimiter
        String[] country = line.split(cvsSplitBy);

        String temp = country[1];
        String regex = "^([^()]*)\\((()]*)\\)(.*)$";
        Pattern p = Pattern.compile("\\((\\d{4}?)\\)$");
        Pattern p2 = Pattern.compile("^([^()]*)");
        Matcher m = p.matcher(temp);
        Matcher m2 = p2.matcher(temp);

        // if regex matches found then put movie in tree
        while(m.find() && m2.find()) {
          if (country[1].equals("title")) {

          } else if (country[1] == null) {
          } else {
              tree.put(m2.group(1).trim(), Integer.parseInt(m.group(1).trim()));

          }
          System.out.println(m2.group(1) + "" + m.group(1));
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
    // test output
    System.out.println(tree.get("Flint"));
    tree.Print(tree.getRoot(), "Flint", "Harry Potter");
    System.out.println("");
    tree.Print(tree.getRoot(), "Superman", "Xeno");
  }
}
