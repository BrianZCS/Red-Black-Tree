// --== CS400 File Header Information ==--
// Name: Zhi Zheng
// Email: zzheng94@wisc.edu
// Team: KD
// TA: Keren
// Lecturer: Gary
// Notes to Grader: <optional extra notes>
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * The class implements Junit-5 tests to test the functionality of frontend.java
 * 
 * @author Zhi Zheng
 *
 */
public class FrontEndDeveloperTests {

  /**
   * This method tests whether the fronend prints the question and choices for the question after
   * the user try to start the app and give the username.
   * 
   */
  @Test
  public void testQuestionsOutput() {
    PrintStream standardOut = System.out;
    InputStream standardIn = System.in;
    try {
      // give input 1 player, player name Z, and check the output, then exit.
      String input = "1" + System.lineSeparator() + "Z" + System.lineSeparator() + "x"
          + System.lineSeparator() + "x";
      InputStream inputStreamSimulator = new ByteArrayInputStream(input.getBytes());
      System.setIn(inputStreamSimulator);
      ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
      System.setOut(new PrintStream(outputStreamCaptor));
      Frontend.main(null);
      // set the output back to standard out for running the test
      System.setOut(standardOut);
      // same for standard in
      System.setIn(standardIn);
      // add all tests to this method
      String appOutput = outputStreamCaptor.toString();
      assertEquals(true, appOutput.contains("There are total 15 questions. Good Luck!!"));
      // test whether print question one and give choices.
      assertEquals(true, appOutput.contains("Question No.1."));
      assertEquals(true, appOutput.contains("A)"));
      assertEquals(true, appOutput.contains("B)"));
      assertEquals(true, appOutput.contains("C)"));
      assertEquals(true, appOutput.contains("D)"));
    } catch (Exception e) {
      // make sure stdin and stdout are set correctly after we get exception in test
      System.setOut(standardOut);
      System.setIn(standardIn);
      e.printStackTrace();
    }
  }

  /**
   * This method tests that the system will store the name of the user after the user types his/her
   * name.
   */
  @Test
  public void testNameInputs() {
    PrintStream standardOut = System.out;
    InputStream standardIn = System.in;
    try {
      // give input 2 players, player1 named Brian, player 2 named Zhi, and check the output, then
      // exit.
      String input = "2" + System.lineSeparator() + "Brian" + System.lineSeparator() + "x"
          + System.lineSeparator() + "Zhi" + System.lineSeparator() + "x" + System.lineSeparator()
          + "x";
      InputStream inputStreamSimulator = new ByteArrayInputStream(input.getBytes());
      System.setIn(inputStreamSimulator);
      ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
      System.setOut(new PrintStream(outputStreamCaptor));
      Frontend.main(null);
      // set the output back to standard out for running the test
      System.setOut(standardOut);
      // same for standard in
      System.setIn(standardIn);
      // add all tests to this method
      String appOutput = outputStreamCaptor.toString();
      // check whether print the first username
      assertEquals(true, appOutput.contains("Hello, Brian"));
      // check whether print the second username
      assertEquals(true, appOutput.contains("Hello, Zhi"));
    } catch (Exception e) {
      // make sure stdin and stdout are set correctly after we get exception in test
      System.setOut(standardOut);
      System.setIn(standardIn);
      e.printStackTrace();
    }
  }

  /**
   * This method tests that the system will printout the ranking of the user after receiving input
   * y/Y. test two cases: 1: The system print the rank after receiving the yes 2: The systen will
   * search for the ranking after giving name input
   */
  @Test
  public void testRankingOutput() {
    PrintStream standardOut = System.out;
    InputStream standardIn = System.in;
    try {
      // give input 1 player, player named Brian, then simulate the answering process, then input
      // yes to see the ranking and check the output, then exit.
      String input = "1" + System.lineSeparator() + "Brian" + System.lineSeparator() + "a"
          + System.lineSeparator() + "a" + System.lineSeparator() + "a" + System.lineSeparator()
          + "a" + System.lineSeparator() + "a" + System.lineSeparator() + "a"
          + System.lineSeparator() + "a" + System.lineSeparator() + "a" + System.lineSeparator()
          + "a" + System.lineSeparator() + "a" + System.lineSeparator() + "a"
          + System.lineSeparator() + "a" + System.lineSeparator() + "a" + System.lineSeparator()
          + "a" + System.lineSeparator() + "a" + System.lineSeparator() + "yes"
          + System.lineSeparator() + "Brian" + System.lineSeparator() + "x";
      InputStream inputStreamSimulator = new ByteArrayInputStream(input.getBytes());
      System.setIn(inputStreamSimulator);
      ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
      System.setOut(new PrintStream(outputStreamCaptor));
      Frontend.main(null);
      // set the output back to standard out for running the test
      System.setOut(standardOut);
      // same for standard in
      System.setIn(standardIn);
      // add all tests to this method
      String appOutput = outputStreamCaptor.toString();
      // check whether give ranking output after the player finish the question.
      // check whether print the comments for the ranking
      // only one player, so rank 1.
      assertEquals(true, appOutput.contains("Do you want to see your ranking?"));
      assertEquals(true, appOutput.contains("Your ranking is"));
      assertEquals(true, appOutput.contains("You are the smartest person in the world!"));
      // check whether allow the ranking search using name after all players finish the questions.
      assertEquals(true, appOutput.contains("Hi, Brian. Your score is "));// The line include the //
                                                                          // ranking
    } catch (Exception e) {
      // make sure stdin and stdout are set correctly after we get exception in test
      System.setOut(standardOut);
      System.setIn(standardIn);
      e.printStackTrace();
    }
  }

  /**
   * The method tests that the system will show the score of the user after finishing all questions,
   * and check whether the user can search for his/her scores after all players finish the
   * questions.
   *
   */
  @Test
  public void testGetScores() {
    PrintStream standardOut = System.out;
    InputStream standardIn = System.in;
    try {
      // give input 1 player, player named Brian, then simulate the answering process, than check
      // the scores, then exit.
      String input = "1" + System.lineSeparator() + "Brian" + System.lineSeparator() + "a"
          + System.lineSeparator() + "a" + System.lineSeparator() + "a" + System.lineSeparator()
          + "a" + System.lineSeparator() + "a" + System.lineSeparator() + "a"
          + System.lineSeparator() + "a" + System.lineSeparator() + "a" + System.lineSeparator()
          + "a" + System.lineSeparator() + "a" + System.lineSeparator() + "a"
          + System.lineSeparator() + "a" + System.lineSeparator() + "a" + System.lineSeparator()
          + "a" + System.lineSeparator() + "a" + System.lineSeparator() + "no"
          + System.lineSeparator() + "Brian" + System.lineSeparator() + "x";
      InputStream inputStreamSimulator = new ByteArrayInputStream(input.getBytes());
      System.setIn(inputStreamSimulator);
      ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
      System.setOut(new PrintStream(outputStreamCaptor));
      Frontend.main(null);
      // set the output back to standard out for running the test
      System.setOut(standardOut);
      // same for standard in
      System.setIn(standardIn);
      // add all tests to this method
      String appOutput = outputStreamCaptor.toString();
      // check whether give score output after the player finish the question.
      assertEquals(true, appOutput.contains("Your score is"));
      // check whether allow the score search using name after all players finish the questions.
      assertEquals(true, appOutput.contains("Hi, Brian. Your score is"));
    } catch (Exception e) {
      // make sure stdin and stdout are set correctly after we get exception in test
      System.setOut(standardOut);
      System.setIn(standardIn);
      e.printStackTrace();
    }
  }

  /**
   * The method tests whether the system will move to the next Question after the user has chosen
   * the answer.
   */
  @Test
  public void testNextQuestion() {
    PrintStream standardOut = System.out;
    InputStream standardIn = System.in;
    try {
      // give input 1 player, player name Z, and check the output, then exit.
      String input = "1" + System.lineSeparator() + "Z" + System.lineSeparator() + "a"
          + System.lineSeparator() + "a" + System.lineSeparator() + "a" + System.lineSeparator()
          + "a" + System.lineSeparator() + "a" + System.lineSeparator() + "a"
          + System.lineSeparator() + "a" + System.lineSeparator() + "a" + System.lineSeparator()
          + "a" + System.lineSeparator() + "a" + System.lineSeparator() + "a"
          + System.lineSeparator() + "a" + System.lineSeparator() + "a" + System.lineSeparator()
          + "a" + System.lineSeparator() + "a" + System.lineSeparator() + "no"
          + System.lineSeparator() + "Brian" + System.lineSeparator() + "x";
      InputStream inputStreamSimulator = new ByteArrayInputStream(input.getBytes());
      System.setIn(inputStreamSimulator);
      ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
      System.setOut(new PrintStream(outputStreamCaptor));
      Frontend.main(null);
      // set the output back to standard out for running the test
      System.setOut(standardOut);
      // same for standard in
      System.setIn(standardIn);
      // add all tests to this method
      String appOutput = outputStreamCaptor.toString();
      // test whether print all the questions after get the choice input.
      assertEquals(true, appOutput.contains("Question No.2."));
      assertEquals(true, appOutput.contains("A)"));
      assertEquals(true, appOutput.contains("B)"));
      assertEquals(true, appOutput.contains("C)"));
      assertEquals(true, appOutput.contains("D)"));
      assertEquals(true, appOutput.contains("Question No.15."));
    } catch (Exception e) {
      // make sure stdin and stdout are set correctly after we get exception in test
      System.setOut(standardOut);
      System.setIn(standardIn);
      e.printStackTrace();
    }
  }

  /**
   * The method tests the reaction of system when the user gives bad inputs. Test whether ask for
   * another inputs. Test four cases 1: The number of players is negative 2: The name is space (no
   * character) 3: The choice is not ABCD 4: The score/rank search doesn't receive the name in the
   * system.
   */
  @Test
  public void testBadInputs() {
    PrintStream standardOut = System.out;
    InputStream standardIn = System.in;
    try {
      // give input 1 player, player name Z, and check the output, then exit.
      String input = "-2" + System.lineSeparator() + "1" + System.lineSeparator() + "    "
          + System.lineSeparator() + "Z" + System.lineSeparator() + "a" + System.lineSeparator()
          + "e" + System.lineSeparator() + "a" + System.lineSeparator() + "a"
          + System.lineSeparator() + "a" + System.lineSeparator() + "a" + System.lineSeparator()
          + "a" + System.lineSeparator() + "a" + System.lineSeparator() + "a"
          + System.lineSeparator() + "a" + System.lineSeparator() + "a" + System.lineSeparator()
          + "a" + System.lineSeparator() + "a" + System.lineSeparator() + "a"
          + System.lineSeparator() + "a" + System.lineSeparator() + "a" + System.lineSeparator()
          + "no" + System.lineSeparator() + "Zhi" + System.lineSeparator() + "Brian"
          + System.lineSeparator() + "x";
      InputStream inputStreamSimulator = new ByteArrayInputStream(input.getBytes());
      System.setIn(inputStreamSimulator);
      ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
      System.setOut(new PrintStream(outputStreamCaptor));
      Frontend.main(null);
      // set the output back to standard out for running the test
      System.setOut(standardOut);
      // same for standard in
      System.setIn(standardIn);
      // add all tests to this method
      String appOutput = outputStreamCaptor.toString();
      // 1
      assertEquals(true, appOutput.contains("Please enter a valid player number (positive)."));
      // 2
      assertEquals(true,
          appOutput.contains("Please enter a valid nickname, Do Not Only Enter Space."));
      // 3
      assertEquals(true, appOutput.contains("Please enter a valid choice."));
      // 4
      assertEquals(true,
          appOutput.contains("The user's name is not in the system, please re-enter the name."));
    } catch (Exception e) {
      // make sure stdin and stdout are set correctly after we get exception in test
      System.setOut(standardOut);
      System.setIn(standardIn);
      e.printStackTrace();
    }
  }

}
