// --== CS400 File Header Information ==--
// Name: Jack Gundrum
// Email: jpgundrum@wisc.edu
// Team: Blue
// Group: KD
// TA: Keren Chen
// Lecturer: Gary Dahl
// Notes to Grader:
import java.util.List;
import java.util.zip.DataFormatException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;

/**
 * Interface to be implemented by QuestionDataReader class
 * 
 * @author jackgundrum
 */
public interface QuestionDataReaderInterface {
        
        public List<Question> readDataSet(Reader inputFileReader) 
            throws FileNotFoundException, IOException, DataFormatException;
    }

