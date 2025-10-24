class ReviewRunner {
  public static void main(String[] args) // main method
  {;
    double disneylandAverage = Review.averageDisneylandRating();
    System.out.println("Average Disneyland Rating: " + disneylandAverage);

    double universalAverage = Review.averageUniversalStudiosRating();
    System.out.println("Average Universal Studios Rating: " + universalAverage);

    double disneylandSentiment = Review.averageDisneylandSentiment();
    System.out.println("Average Disneyland Sentiment Value: " + disneylandSentiment);

    double universalSentiment = Review.averageUniversalSentiment();
    System.out.println("Average Universal Studios Sentiment Value: " + universalSentiment);

   try {
            Thread.sleep(2000); // Pause for 2 seconds 
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    System.out.println("------");
    System.out.println("End of Review Analysis: People seem to like Universal Studios slightly more based on the average review rating and sentiment analysis.");
  }
}




  


