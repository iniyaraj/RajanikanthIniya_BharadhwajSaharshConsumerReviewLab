class ReviewRunner {
  public static void main(String[] args) // main method
  {;
    double disneylandAverage = Review.averageDisneylandRating(); // Method Call #1
    System.out.println("Average Disneyland Rating: " + disneylandAverage);

    double universalAverage = Review.averageUniversalStudiosRating(); // Method Call # 2
    System.out.println("Average Universal Studios Rating: " + universalAverage);

    double disneylandSentiment = Review.averageDisneylandSentiment(); // Method Call #3
    System.out.println("Average Disneyland Sentiment Value: " + disneylandSentiment);

    double universalSentiment = Review.averageUniversalSentiment(); // Method Call #4
    System.out.println("Average Universal Studios Sentiment Value: " + universalSentiment);

   try {
            Thread.sleep(2000); // Pause for 2 seconds 
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    System.out.println("----------------------------------");
    System.out.println("End of Review Analysis: People seem to like Universal Studios slightly more based on the average review rating and sentiment analysis.");
  }
}




  


