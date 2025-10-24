class ReviewRunner {
  public static void main(String[] args) 
  {;
    double disneylandAverage = Review.averageDisneylandRating();
    System.out.println("Average Disneyland Rating: " + disneylandAverage);

    double universalAverage = Review.averageUniversalStudiosRating();
    System.out.println("Average Universal Studios Rating: " + universalAverage);

    double disneylandSentiment = Review.averageDisneylandSentiment();
    System.out.println("Average Disneyland Sentiment: " + disneylandSentiment);

    double universalSentiment = Review.averageUniversalSentiment();
    System.out.println("Average Universal Studios Sentiment: " + universalSentiment);

    System.out.println("End of Review Analysis: People seem to like Universal Studios slightly more based on the average review rating and sentiment analysis.");



  }
}


