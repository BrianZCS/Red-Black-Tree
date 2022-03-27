// --== CS400 File Header Information ==--
// Name: Ryan Stevenson
// Email: rstevenson5@wisc.edu
// Team: KD
// Role: Backend Developer
// TA: Keren
// Lecturer: Gary Dahl
// Notes to Grader: none
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import java.util.zip.DataFormatException;

public class Backend {
    private ArrayList<Question> questions;
    private int score;
    private Question currentQuestion;
    private Random rand;
    private String name;
    private RedBlackTree<Integer> rbTree;
    
    /**
     * One-argument constructor for the Backend. Takes in a fileReader with a csv file with a list
     * of questions as a parameter and stores the questions into an arraylist
     * @param fileReader The csv file with the list of questions
     * @throws FileNotFoundException
     * @throws IOException
     * @throws DataFormatException
     */
    public Backend(FileReader fileReader) throws FileNotFoundException, IOException, DataFormatException {
	QuestionDataReader myQuestions = new QuestionDataReader(); // instantiate interface
	QuestionInterface moviesList;
	questions = (ArrayList<Question>) myQuestions.readDataSet(fileReader);
	score = 0;
	rbTree = new RedBlackTree<>();
    }
    
    /*
    public String getRandomQuestion() {
	rand = new Random();
	currentQuestion = questions.get(rand.nextInt(questions.size()));
	return currentQuestion.getQuestion();
    }
    */
    
    public ArrayList<Question> getQuestions() {
	return this.questions;
    }
    
    /**
     * Adds to the current player's score. The amount added is multiplied by the number of 
     * consecutive correct answers that the player has made
     * @param numCorrectAnswers The number of consecutive correct answers the player has.
     */
    public void addScore(int numCorrectAnswers) {
	score += (10 * numCorrectAnswers);
    }
    
    public String getName() {
	return this.name;
    }
    
    public void setName(String name) {
	this.name = name;
    }
    
    
    /**
     * Inserts the current player's name and score into the red black tree and resets the
     * player's score. Set name will likely need to be used after this to allow a new player
     * to play.
     */
    public void enterScore() {
	rbTree.insert(score, name);
	score = 0;
    }
    
    /**
     * @param numOfScores The number of scores to be displayed on the scoreboard
     * @return A string representation of the top player's scores
     */
    public String getTopScores(int numOfScores) {
	return rbTree.getTopValues(numOfScores);
    }
    /**
     * Finds the placement of a player given their name
     * @param name The name of the player to search for
     * @return The placement of the specified player
     * @throws NoSuchElementException when the red black tree does not contain the given name
     */
    public int getPlacement(String name) {
	return rbTree.getPlacement(name);
    }
    
    /**
     * @param name The player whose score you want to find
     * @return The score of a specified player
     */
    public int getScore(String name) {
	return rbTree.search(name);
    }
    /**
     * @return The current player's score
     */
    public int getScore() {
	return score;
    }
}
