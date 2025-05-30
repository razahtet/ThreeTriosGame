package customer.fileoperation;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import customer.gamefeatures.Card;
import customer.gamefeatures.GameCard;
import customer.gamefeatures.GameGrid;
import customer.gamefeatures.IGameGrid;

/**
 * File reader class to initialize the grid using configuration file.
 */
public class ConfigurationReader {

  /**
   * Construct a GameGrid using a txt file that contains the basic
   * constructing information.
   *
   * @param filePath The path of the configuration file.
   * @return The game grid after initialization.
   */
  public IGameGrid buildGridUsingConfigFile(String filePath) {
    try {
      File configFile = new File(filePath);
      Scanner scanner = new Scanner(configFile);

      if (!scanner.hasNextInt()) {
        throw new IllegalArgumentException("File error, doesn't contains rows.");
      }
      int row = scanner.nextInt();

      if (!scanner.hasNextInt()) {
        throw new IllegalArgumentException("File error, doesn't contains column.");
      }
      int col = scanner.nextInt();
      if (row < 0 || col < 0) {
        throw new IllegalArgumentException("Row and col number should be positive.");
      }
      // jump the end sign
      if (scanner.hasNextLine()) {
        scanner.nextLine();
      }
      int cells = 0;
      Card[][] grid = new Card[row][col];
      // check the actual row number in the file
      // all lines following the last row are ignored.
      for (int i = 0; i < row; i += 1) {
        if (!scanner.hasNextLine()) {
          throw new IllegalArgumentException("Not enough line.");
        }
        String line = scanner.nextLine();
        if (line.length() != col) {
          throw new IllegalArgumentException("Wrong element number in a row.");
        }
        // check through the String line and update every hole in the grid.
        for (int j = 0; j < col; j += 1) {
          char[] thisLine = line.toCharArray();
          if (thisLine[j] == 'X') {
            grid[i][j] =
                    new GameCard("X", "GRAY", 0, 0, 0, 0);
          } else if (thisLine[j] == 'C') {
            grid[i][j] =
                    new GameCard("C", "YELLOW", 0, 0, 0, 0);
            cells += 1;
          } else {
            throw new IllegalArgumentException(
                    "Element in the grid should only be represent by char 'X' or 'C'.");
          }
        }
      }
      return new GameGrid(row, col, grid, cells);
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("File not found: " + filePath);
    }

  }
}
