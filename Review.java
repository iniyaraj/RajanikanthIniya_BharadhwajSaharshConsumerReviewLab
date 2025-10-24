import java.util.Scanner;
import java.io.File;
import java.util.HashMap;
import java.util.ArrayList;

/**
 * Class that contains helper methods for the Review Lab
 **/
public class Review {
  
  private static HashMap<String, Double> sentiment = new HashMap<String, Double>();
  private static ArrayList<String> posAdjectives = new ArrayList<String>();
  private static ArrayList<String> negAdjectives = new ArrayList<String>();
  
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

  public static String removePunctuation(String word) {
    while (word.length() > 0 && !Character.isAlphabetic(word.charAt(0))) {
      word = word.substring(1);
    }
    while (word.length() > 0 && !Character.isAlphabetic(word.charAt(word.length() - 1))) {
      word = word.substring(0, word.length() - 1);
    }
    return word;
  }
 
  public static String randomPositiveAdj() {
    int index = (int)(Math.random() * posAdjectives.size());
    return posAdjectives.get(index);
  }
  
  public static String randomNegativeAdj() {
    int index = (int)(Math.random() * negAdjectives.size());
    return negAdjectives.get(index);
  }
  
  public static String randomAdjective() {
    boolean positive = Math.random() < .5;
    if (positive) {
      return randomPositiveAdj();
    } else {
      return randomNegativeAdj();
    }
  }

  public static double averageDisneylandRating() {
    double total = 0;
    int count = 0;
    try {
      Scanner input = new Scanner(new File("DisneylandReviewsList.csv"));
      if (input.hasNextLine()) input.nextLine();
      while (input.hasNextLine()) {
        String[] parts = input.nextLine().split(",");
        if (parts.length >= 1 && !parts[0].isEmpty()) {
          total += Double.parseDouble(parts[0]);
          count++;
        }
      }
      input.close();
    } catch (Exception e) {
      System.out.println("Error reading or parsing DisneylandReviewsList.csv");
    }
    return count > 0 ? Math.round((total / count) * 100.0) / 100.0 : 0;
  }

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

  public static double averageDisneylandSentiment() {
    double totalSentiment = 0;
    int count = 0;
    try {
      Scanner input = new Scanner(new File("DisneylandReviewsList.csv"));
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
      System.out.println("Error calculating Disneyland sentiment");
    }
    return count > 0 ? Math.round((totalSentiment / count) * 100.0) / 100.0 : 0;
  }

  public static double averageUniversalSentiment() {
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
