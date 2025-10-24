import java.util.Scanner;
import java.io.File;
import java.util.HashMap;
import java.util.ArrayList;

public class Review {
  
  private static HashMap<String, Double> sentiment = new HashMap<String, Double>();
  private static ArrayList<String> posAdjectives = new ArrayList<String>();
  private static ArrayList<String> negAdjectives = new ArrayList<String>();
  
  //static block to read in the sentiment values and positive and negative adjectives in their respective ArrayLists
  static {
    try {
      Scanner input = new Scanner(new File("cleanSentiment.csv"));
      while (input.hasNextLine()) {
        String[] temp = input.nextLine().split(",");
        sentiment.put(temp[0], Double.parseDouble(temp[1]));
      }
      input.close();
    } catch (Exception e) {
      System.out.println("Error reading or parsing cleanSentiment.csv");
    }
  
    try {
      Scanner input = new Scanner(new File("positiveAdjectives.txt"));
      while (input.hasNextLine()) {
        posAdjectives.add(input.nextLine().trim());
      }
      input.close();
    } catch (Exception e) {
      System.out.println("Error reading or parsing postitiveAdjectives.txt\n" + e);
    }   
 
    try {
      Scanner input = new Scanner(new File("negativeAdjectives.txt"));
      while (input.hasNextLine()) {
        negAdjectives.add(input.nextLine().trim());
      }
      input.close();
    } catch (Exception e) {
      System.out.println("Error reading or parsing negativeAdjectives.txt");
    }   
  }
  // returns the entire text of a file as a String
  public static String textToString(String fileName) {  
    String temp = "";
    try {
      Scanner input = new Scanner(new File(fileName));
      while (input.hasNext()) {
        temp = temp + input.next() + " ";
      }
      input.close();
    } catch (Exception e) {
      System.out.println("Unable to locate " + fileName);
    }
    return temp.trim();
  }
  // returns the sentiment value of a word as a double
  public static double sentimentVal(String word) {
    word = word.toLowerCase();
    if (sentiment.containsKey(word)) {
      return sentiment.get(word);
    } else if (posAdjectives.contains(word)) {
      return 1.0;
    } else if (negAdjectives.contains(word)) {
      return -1.0;
    } else {
      return 0.0;
    }
  }
  // returns the ending punctuation of a word, or the empty string if there is none
  public static String getPunctuation(String word) { 
    String punc = "";
    for (int i = word.length() - 1; i >= 0; i--) {
      if (!Character.isLetterOrDigit(word.charAt(i))) {
        punc = punc + word.charAt(i);
      } else {
        return punc;
      }
    }
    return punc;
  }
// removes punctuation from the beginning and end of a word
  public static String removePunctuation(String word) {
    while (word.length() > 0 && !Character.isAlphabetic(word.charAt(0))) { // Compound Boolean
      word = word.substring(1);
    }
    while (word.length() > 0 && !Character.isAlphabetic(word.charAt(word.length() - 1))) {
      word = word.substring(0, word.length() - 1);
    }
    return word;
  }
 // returns a random positive adjective from the positiveAdjectives.txt file
  public static String randomPositiveAdj() {
    int index = (int)(Math.random() * posAdjectives.size());
    return posAdjectives.get(index);
  }
  // returns a random negative adjective from the negativeAdjectives.txt file
  public static String randomNegativeAdj() {
    int index = (int)(Math.random() * negAdjectives.size());
    return negAdjectives.get(index);
  }
  // returns a random adjective, positive or negative
  public static String randomAdjective() {
    boolean positive = Math.random() < .5;
    if (positive) {
      return randomPositiveAdj();
    } else {
      return randomNegativeAdj();
    }
  }
// calculates the average rating for Disneyland reviews
  public static double averageDisneylandRating() {
    double total = 0;
    int count = 0;
    try {
      Scanner input = new Scanner(new File("DisneylandReviewsList.csv"));
      if (input.hasNextLine()) input.nextLine();
      while (input.hasNextLine()) {
        String[] parts = input.nextLine().split(","); // Assumes rating is the first element
        if (parts.length >= 1 && !parts[0].isEmpty()) { // Added check for empty rating
          total += Double.parseDouble(parts[0]); // Parses rating
          count++; // Increment count only if a valid rating is found
        }
      }
      input.close();
    } catch (Exception e) { // catches both FileNotFoundException and NumberFormatException
      System.out.println("Error reading or parsing DisneylandReviewsList.csv"); // Added comment for clarity
    }
    return count > 0 ? Math.round((total / count) * 100.0) / 100.0 : 0;
  }
// calculates the average rating for Universal Studios reviews
  public static double averageUniversalStudiosRating() {
    double total = 0;
    int count = 0;
    try {
      Scanner input = new Scanner(new File("UniversalStudiosReviewsList.csv"));
      if (input.hasNextLine()) input.nextLine();
      while (input.hasNextLine()) {
        String line = input.nextLine().trim();
        if (line.isEmpty()) continue;
        String[] parts = line.split(",");
        if (parts.length >= 1 && !parts[0].isEmpty()) {
          String ratingStr = parts[0].replaceAll("[^0-9.]", "");
          if (!ratingStr.isEmpty()) {
            total += Double.parseDouble(ratingStr); 
            count++;
          }
        }
      }
      input.close();
    } catch (Exception e) {
      System.out.println("Error reading or parsing UniversalStudiosReviewsList.csv");
    }
    return count > 0 ? Math.round((total / count) * 100.0) / 100.0 : 0;
  }
// calculates the average sentiment for Disneyland reviews
  public static double averageDisneylandSentiment() { // String Method #1
    double totalSentiment = 0;
    int count = 0;
    try {
      Scanner input = new Scanner(new File("DisneylandReviewsList.csv"));
      if (input.hasNextLine()) input.nextLine(); // skip header
      while (input.hasNextLine()) { // process each review
        String[] parts = input.nextLine().split(",");
        if (parts.length >= 2) {
          String[] words = parts[1].split(" ");
          for (String w : words) { // process each word
            double val = sentimentVal(removePunctuation(w));
            if (val != 0) { 
              totalSentiment += val; 
              count++; // increment count only for words with sentiment values
            }
          }
        }
      }
      input.close();
    } catch (Exception e) {
      System.out.println("Error calculating Disneyland sentiment");
    }
    return count > 0 ? Math.round((totalSentiment / count) * 100.0) / 100.0 : 0;
  }

  public static double averageUniversalSentiment() { // String Method #2
    double totalSentiment = 0;
    int count = 0;
    try {
      Scanner input = new Scanner(new File("UniversalStudiosReviewsList.csv"));
      if (input.hasNextLine()) input.nextLine();
      while (input.hasNextLine()) {
        String[] parts = input.nextLine().split(",");
        if (parts.length >= 2) {
          String[] words = parts[1].split(" ");
          for (String w : words) {
            double val = sentimentVal(removePunctuation(w));
            if (val != 0) {
              totalSentiment += val;
              count++;
            }
          }
        }
      }
      input.close();
    } catch (Exception e) {
      System.out.println("Error calculating Universal Studios sentiment");
    }
    return count > 0 ? Math.round((totalSentiment / count) * 100.0) / 100.0 : 0;
  }
}
