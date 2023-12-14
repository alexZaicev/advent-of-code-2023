package com.alza.adventofcode;

import java.util.Arrays;

public class A {

  public static void main(String[] args) {
    String input = "ABC#DEF#GHI";
    String delimiter = "#";

    String[] parts = splitWithEmptyStrings(input, delimiter);

    System.out.println("Input: " + input);
    System.out.println("Split with Empty Strings: " + Arrays.toString(parts));
  }

  private static String[] splitWithEmptyStrings(String input, String delimiter) {
    // Replace the delimiter with a unique sequence
    String uniqueSequence = "###";
    String replacedInput = input.replaceAll(delimiter, uniqueSequence);

    // Split the string using the modified delimiter
    String[] parts = replacedInput.split(uniqueSequence, -1);

    // Replace the unique sequence back to the original characters
    for (int i = 0; i < parts.length; i++) {
      parts[i] = parts[i].replace(uniqueSequence, delimiter);
    }

    return parts;
  }

}
