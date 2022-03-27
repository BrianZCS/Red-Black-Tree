// --== CS400 File Header Information ==--
// Name: Jack Gundrum
// Email: jpgundrum@wisc.edu
// Team: Blue
// Group: KD
// TA: Keren Chen
// Lecturer: Gary Dahl
// Notes to Grader: Sources I used for the questions can be found easily in Questions.csv file 

/**
 * This class implements the Question interface. It is used to sort the question data into 
 * different components. The components are: Question, correct answer, incorrect answers, 
 * and sources.
 * 
 * @author jackgundrum
 */
public class Question implements QuestionInterface{
    // instance variables
    private String question;
    private String correctAnswer;
    private String incorrectAnswers;
    private String source;
   
    /**
     * Default constructor of the question when no inputs are passed
     */
    public Question() {
        this.question = "";
        this.correctAnswer = "";
        this.incorrectAnswers = "";
        this.source = "";
    }
    
    /**
     * Constructor that initializes the instance variables with the sorted data from the 
     * QuestionDataReader class.
     * 
     * @param currQuestion The current question
     * @param answer The answer of the question
     * @param badAnswers The incorrect answers
     * @param source The source for the given question
     */
    public Question(String currQuestion, String answer, String badAnswers, String source) {
        this.question = currQuestion;
        this.correctAnswer = answer;
        this.incorrectAnswers = badAnswers;
        this.source = source;
    }
    
    /**
     * Getter method to obtain the question
     * 
     * @return this.question The current question
     */
    @Override
    public String getQuestion() {
        return this.question;
        // TODO Auto-generated method stub
        
    }
    
    /**
     * Getter method to obtain the correct answer
     * 
     * @return this.correctAnswer The correct answer
     */
    @Override
    public String getCorrectAnswer() {
        return this.correctAnswer;
        // TODO Auto-generated method stub
        
    }

    /**
     * Getter method to obtain the incorrect answers
     * 
     * @return this.IncorrectAnswers The incorrect answers
     */
    @Override
    public String getIncorrectAnswers() {
       return this.incorrectAnswers;
    }

    /**
     * Getter method to obtain the source for the question
     * 
     * @return this.source The source
     */
    @Override
    public String getSource() {
        return this.source;
    }

}
