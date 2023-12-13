package com.alza.adventofcode.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ListUtils {

  /*
   * Function: transpose
   *
   * Transforms a grid of rows-column into grid of column-rows.
   */
  public static List<String> transpose(List<String> grid) {
    List<String> transposedGrid = new ArrayList<>();

    int numRows = grid.size();
    int numCols = grid.get(0).length();

    for (int c = 0; c < numCols; c++) {
      StringBuilder column = new StringBuilder();
      for (int r = 0; r < numRows; r++) {
        column.append(grid.get(r).charAt(c));
      }
      transposedGrid.add(column.toString());
    }

    return transposedGrid;
  }

  /*
   * Function: getReversedSublist
   *
   * Slices the list based on the start and end range, and returns a reversed
   * version the list.
   */
  public static List<String> getReversedSublist(List<String> list, int start, int end) {
    if (start < 0 || end > list.size() || start > end) {
      throw new IllegalArgumentException("Invalid start or end indices");
    }

    List<String> sublist = new ArrayList<>(list.subList(start, end));
    Collections.reverse(sublist);
    return sublist;
  }
}
