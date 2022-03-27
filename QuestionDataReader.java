// --== CS400 File Header Information ==--
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.zip.DataFormatException;
import java.util.Scanner;
import java.util.ArrayList;

/**
 * This class implements the QuestionDataReader interface. It is used to read a cvs
 * data file which contains a list of questions, with correct answers, incorrect answers,
 * and sources. 
 * 
 * @author jackgundrum
 */
public class QuestionDataReader implements QuestionDataReaderInterface{

    /**
     * Backend calls this method to obtain an Array List of Questions which they can use
     * to sort the data. This method specifically reads the question data from a cvs file. It uses
     * helper methods in order to sort the data in an Object Oriented way through the question class.
     * 
     * @param inputFileReader Question data
     * @return questions The sorted question data that the backend utilizes
     */
    @Override
    public List<Question> readDataSet(Reader inputFileReader)
        throws FileNotFoundException, IOException, DataFormatException {
        Scanner input = new Scanner(inputFileReader);   
        input.useDelimiter("\n");       // makes it break for every newline
        
        String question = "";           // variable initializations 
        String correctAnswer = "";
        String incorrectAnswer = "";
        String source = "";
        
        // list that will be returned to backend
        ArrayList<Question> questions = new ArrayList<Question>();
        String[] quesArray = new String[500];
        int counter = 0;
        int quesArrSize = 0;
        input.nextLine(); // skips top of data file bc it is not needed
        
        // loops through data file and creates an array of question data 
        // corresponding to each line in the file 
        while(input.hasNext()) {    
            quesArray[counter] = input.nextLine();
            counter++;
            quesArrSize++;
        }
        for(int i = 0; i < quesArrSize; i++) {  // sorts & adds data to array list of questions
            question = getQuestionHelper(quesArray[i]); // calls helper methods
            correctAnswer = getCorrectAnswerHelper(quesArray[i]);
            incorrectAnswer = getIncorrectAnswersHelper(quesArray[i]);
            source = getSourceHelper(quesArray[i]);
            Question newQues = new Question(question,correctAnswer,incorrectAnswer,source);
            questions.add(newQues); // adds sorted question
        }
        input.close();  // closes scanner
        return questions;   // returns sorted questions to backend
    }
    
    /**
    * Helper method that sorts the line of question data into just the question
    * 
    * @param questionData The specific line of question data to be sorted
    * @return questionData The new sorted data of the question
    */
    private String getQuestionHelper(String questionData) {
        int index = questionData.indexOf("?");  // index of where question ends
        return questionData.substring(0,index + 1); // sorted question
    }
    
    /**
     * Helper method that sorts the line of question data into just the correct answer
     * 
     * @param questionData The specific line of question data to be sorted
     * @return questionData The new sorted data of the correct answer
     */
    private String getCorrectAnswerHelper(String questionData) {
        int index = questionData.indexOf(">"); // index of where correct answer beings
        questionData = questionData.substring(index);   // update question data
        int index2 = questionData.indexOf(","); // index of where answer ends
        return questionData.substring(1,index2); // sorted correct answer
    }
    
    /**
     * Helper method that sorts the line of question data into incorrect answers. The 
     * incorrect answers are in a comma separated list
     * 
     * @param questionData The specific line of question data to be sorted
     * @return questionData The new sorted data of incorrect answers
     */
    private String getIncorrectAnswersHelper(String questionData) {
        int index = questionData.indexOf(">"); // index of where correct answer beings
        questionData = questionData.substring(index);   // cut off question
        int index2 = questionData.indexOf(",");
        int index3 = questionData.indexOf("<"); // index of where incorrect answers end
        questionData = questionData.substring(index2 + 1,index3); // obtains incorrect answers
        return questionData;
    }
    
    /**
     * Helper method that sorts the line of question data into just the source
     * 
     * @param questionData The specific line of question data to be sorted
     * @return questionData The new sorted data of sources
     */
    private String getSourceHelper(String questionData) {
        int startingIndex = questionData.indexOf("<");  // pointer to see where sources start
        return questionData.substring(startingIndex + 2); // obtain sources & return
    }
}
