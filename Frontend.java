// --== CS400 File Header Information ==--
// Name: Zhi Zheng
// Email: zzheng94@wisc.edu
// Team: KD
// TA: Keren
// Lecturer: Gary
// Notes to Grader: <optional extra notes>
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.zip.DataFormatException;

/**
 * The frontend class helps users run the trivia game. Each player will finish the questions
 * randomly chosen from database. After each player finishes, the player will be asked whether to
 * see his/her ranking. After all players finish, the user can check the score and ranking by
 * inputing his/her name.
 * 
 * @author Zhi Zheng
 *
 */
public class Frontend {
  int score;
  private static Backend backend;
  private static FileReader r;
  private static File f = new File("./Questions.csv");
  // the question number in the database
  private static int questionNumber = 1;
  private static ArrayList<Question> questions = null;
  private static Scanner input;
  // the total player number in the players
  private static int playerNumber;
  // the current player number in the players
  private static int player = 1;
  // the consecutive correct answers
  private static int consecutiveCorrect = 1;
  private static String playerName;

  /**
   * The main method helps initialize fileReader to get the questions, initialize number of players
   * to decide run how many times the gameSetting and gameMode method. Also, After all players
   * finish the game, asking whether anyone want to check his/her scores and ranking.
   * 
   * @param args The string inputs
   * @throws FileNotFoundException exception thrown in backend.java
   * @throws IOException           exception thrown in backend.java
   * @throws DataFormatException   exception thrown in backend.java
   */
  public static void main(String[] args)
      throws FileNotFoundException, IOException, DataFormatException {
    try {
      r = new FileReader(f);
    } catch (FileNotFoundException fnfe) {
      fnfe.printStackTrace();
    }
    backend = new Backend(r);
    System.out.println("Please enter the number of players.");
    System.out.println("These players' score will be stored in the database.");
    input = new Scanner(System.in);
    String number = input.nextLine();
    // catch the non-format exception//ask for another input
    try {
      while (Integer.parseInt(number.replaceAll("\\s", "")) <= 0) {
        System.out.println("Please enter a valid player number (positive).");
        number = input.nextLine();
      }
    } catch (NumberFormatException e) {
      System.out.println("Please enter a valid player number (positive integer).");
      number = input.nextLine();
    }
    playerNumber = Integer.parseInt(number.replaceAll("\\s", ""));
    // start game for each player
    for (int i = 0; i < playerNumber; ++i) {
      gameSetting();
      player++;
    }
    // search ranking and scores after finishing
    System.out.println("All players finish the trivia Game! Good Job!!!!");
    System.out.println("Do you want to check your ranking?");
    System.out.println("Enter your name to check your grade and ranking, enter x to exit");
    String name = input.nextLine();
    // ask for another input if the name is not in the system.
    while (!name.replaceAll("\\s", "").toUpperCase().equals("X")) {
      try {
        System.out.println("Hi, " + name + ". Your score is " + backend.getScore(name)
            + ". Your ranking is " + backend.getPlacement(name));
        System.out.println(evaluateRanking(name));
        name = input.nextLine();
      } catch (NoSuchElementException e) {
        System.out.println("The user's name is not in the system, please re-enter the name.");
        name = input.nextLine();
      }
    }
    System.out.println("Thanks for playing the trivia game!!!!! Bye Bye!");
    input.close();
  }

  /**
   * The method initializes the game setting for each player. Also, the method will ask for the
   * players' nickname in order to store the name and scores in the red black tree.
   */
  public static void gameSetting() {
    System.out.println("\nHello!! Player NO." + player);
    System.out.println("Welcome to the Trivia Game!");
    System.out.println("The Questions are randomly chosen from database.");
    System.out.println(
        "If you want to exit during the game, please enter x. You grade will not be stored.");
    System.out.println("Please enter your prefered nickname to start.");
    playerName = input.nextLine();
    /// refuse bad inputs and ask for another
    while (playerName == null || playerName.replaceAll("\\s", "").equals("")) {
      System.out.println("Please enter a valid nickname, Do Not Only Enter Space.");
      playerName = input.nextLine();
    }
    /// exit the game if enter x
    if (playerName.replaceAll("\\s", "").toLowerCase().equals("x")) {
      System.out.println("Thanks for playing the Trivia Game! Have a good day!!\n\n");
    } else {
      backend.setName(playerName);
      System.out.println("Hello, " + playerName + ".");
      gameMode();
    } ;
  }

  /**
   * The method prints out randomly chosen 15 multiple selection questions for user and asking for
   * the answer from the user. After finish all the questions in the database, the system will ask
   * the user whether he/she want to see his/her ranking, and automatically print his/her scores and
   * top three players.
   */
  private static void gameMode() {
    questions = backend.getQuestions();
    // shuffle the questions in the datebase, which makes each user have different experience.
    Collections.shuffle(questions);
    ArrayList<Question> tenQuestions = new ArrayList<Question>();
    // randomly pick first 10 questions and shuffle them.
    for (int i = 0; i < 15; ++i) {
      tenQuestions.add(questions.get(i));
    }
    Collections.shuffle(tenQuestions);
    System.out.println("There are total " + tenQuestions.size() + " questions. Good Luck!!");
    while (questionNumber <= tenQuestions.size()) {
      System.out.println("\nQuestion No." + questionNumber + ".");
      System.out.println(tenQuestions.get(questionNumber - 1).getQuestion());
      System.out.println(
          "(Please enter A/a B/b C/c D/d for your choice, enter x if you want to exit the game)");
      // stored all the choice and shuffle them. Stop always enter A to get correct answer.
      ArrayList<String> choices = new ArrayList<String>();
      choices.add(tenQuestions.get(questionNumber - 1).getCorrectAnswer());
      // Split the incorrect answers string.
      String[] incorrect = tenQuestions.get(questionNumber - 1).getIncorrectAnswers().split(",", 3);
      for (int i = 0; i < incorrect.length; ++i) {
        choices.add(incorrect[i]);
      }
      Collections.shuffle(choices);
      System.out.println("Which choice is correct?");
      // let A be the first choice in the choices, let B.......
      System.out.println("A)" + choices.get(0) + "   " + "B)" + choices.get(1) + "   " + "C)"
          + choices.get(2) + "   " + "D)" + choices.get(3) + "   ");
      System.out.println("[Source: " + tenQuestions.get(questionNumber - 1).getSource() + "]");
      String choice = input.nextLine();
      /// refuse only invalid inputs and ask for another
      while (choice == null || (!choice.replaceAll("\\s", "").toUpperCase().equals("A")
          && !choice.replaceAll("\\s", "").toUpperCase().equals("B")
          && !choice.replaceAll("\\s", "").toUpperCase().equals("C")
          && !choice.replaceAll("\\s", "").toUpperCase().equals("D")
          && !choice.replaceAll("\\s", "").toLowerCase().equals("x"))) {
        System.out.println("Please enter a valid choice.");
        choice = input.nextLine();
      }
      // exit the game if enter x
      if (choice.replaceAll("\\s", "").toLowerCase().equals("x")) {
        System.out.println("Thanks for playing the Trivia Game! Have a good day!!\n\n");
        return;
      }
      calculateScore(choice.replaceAll("\\s", "").toUpperCase(), choices,
          tenQuestions.get(questionNumber - 1));
      questionNumber++;
    }
    System.out.println("You final score is " + backend.getScore());
    // save the score the RBT
    backend.enterScore();
    System.out.println("Do you want to see your ranking? (please enter yes/no)");
    String rank = input.nextLine();
    // refuse the bad inputs and ask for another
    while (rank == null || (!rank.replaceAll("\\s", "").toUpperCase().equals("YES")
        && !rank.replaceAll("\\s", "").toUpperCase().equals("NO"))) {
      System.out.println("Please enter a valid input.");
      rank = input.nextLine();
    }
    // shows the ranking and evaluations if the player enter yes
    if (rank.replaceAll("\\s", "").toUpperCase().equals("YES")) {
      System.out.println("Your ranking is " + backend.getPlacement(playerName));
      System.out.println(evaluateRanking(playerName));
    }
    // reset the question number for next player
    questionNumber = 1;
    System.out.println("Top three players: \n(nickname:score)");
    System.out.println(backend.getTopScores(3));
    System.out.println("Thanks for playing the Trivia Game! Have a good day!!\n\n");
  }

  /**
   * The private helper method checks the players'answer correctness and calculates the score using
   * the addScore method in the backend. If the player makes consecutive strikes, the player will
   * get more points. If the player get incorrect answers, the system will reset the consecutive
   * number to 1.
   * 
   * @param choice   The player's answer
   * @param choices  All multiple selections in the question
   * @param question the question that player is asked
   */
  private static void calculateScore(String choice, ArrayList<String> choices, Question question) {
    // A is the first choice in the choices
    if (choice.equals("A")) {
      if (choices.get(1).equals(question.getCorrectAnswer())) {
        // if answer is consecutive correct
        backend.addScore(consecutiveCorrect);
        consecutiveCorrect++;
      }
    } else if (choice.equals("B")) {
      if (choices.get(1).equals(question.getCorrectAnswer())) {
        backend.addScore(consecutiveCorrect);
        consecutiveCorrect++;
      }
    } else if (choice.equals("C")) {
      if (choices.get(1).equals(question.getCorrectAnswer())) {
        backend.addScore(consecutiveCorrect);
        consecutiveCorrect++;
      }
    } else {
      if (choices.get(1).equals(question.getCorrectAnswer())) {
        backend.addScore(consecutiveCorrect);
        consecutiveCorrect++;
      }
    }
    // reset the consecutive correct if the player gets one question wrong
    consecutiveCorrect = 1;
  }

  /**
   * The private helper method returns the evaluation for the players' ranking. The player will get
   * different comments if the player is No.1 among all players, or if the player is top 3, or
   * otherwise.
   * 
   * @param playerName The name of the player
   * @return The evaluation for the players' ranking
   */
  private static String evaluateRanking(String playerName) {
    if (backend.getPlacement(playerName) == 1) {
      return "You are the smartest person in the world!";
    } else if (backend.getPlacement(playerName) < 3) {
      return "Well done!";
    } else {
      return "Rome wasn't built in a day. Keep working!";
    }
  }

}
