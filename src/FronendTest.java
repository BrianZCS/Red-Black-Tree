import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.StringReader;

public class FronendTest {
	PrintStream standardOut = System.out;
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	System.setOut(new PrintStream(outputStreamCaptor));
	
}
public boolean enterXToExit() {
	PrintStream standardOut = System.out;
	InputStream standardIn = System.in;
	try {
		// set the input stream to our input (with an x to test of the program exists)
		String input = "x";
		InputStream inputStreamSimulator = new ByteArrayInputStream(input.getBytes());
		System.setIn(inputStreamSimulator);
		ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
		// set the output to the stream captor to read the output of the front end
		System.setOut(new PrintStream(outputStreamCaptor));
		// instantiate when front end is implemented
		Object frontend = new Frontend();
		((Frontend)frontend).run(new Backend(new StringReader(
				"title,original_title,year,genre,duration,country,language,director,writer,production_company,actors,description,avg_vote\n"
				+ "The Source of Shadows,The Source of Shadows,2020,Horror,83,USA,English,\"Ryan Bury, Jennifer Bonior\",\"Jennifer Bonior, Trevor Botkin\",Four Thieves Productions,\"Ashleigh Allard, Tom Bonington, Eliane Gagnon, Marissa Kaye Grinestaff, Jenna Heffernan, Joshua Hummel, Janice Kingsley, Chris Labasbas, Jared Laufree, Dominic Lee, Vic May, Sienna Mazzone, Lizzie Mounter, Grace Mumm, Ashley Otis\",\"A series of stories woven together by one of our most primal fears, the fear of the unknown.\",3.5\n"
				+ "The Insurrection,The Insurrection,2020,Action,90,USA,English,Rene Perez,Rene Perez,,\"Michael Paré, Wilma Elles, Joseph Camilleri, Rebecca Tarabocchia, Jeanine Harrington, Malorie Glavan, Danner Boyd, Michael Cendejas, Woody Clendenen, Keely Dervin, Aaron Harvey, Tony Jackson, Michael Jarrod, Angelina Karo, Bernie Kelly\",The director of the largest media company wants to expose how left-wing powers use film to control populations.,2.9\n"
				+ "Valley Girl,Valley Girl,2020,\"Comedy, Musical, Romance\",102,USA,English,Rachel Lee Goldenberg,\"Amy Talkington, Andrew Lane\",Sneak Preview Productions,\"Jessica Rothe, Josh Whitehouse, Jessie Ennis, Ashleigh Murray, Chloe Bennet, Logan Paul, Mae Whitman, Mario Revolori, Rob Huebel, Judy Greer, Alex Lewis, Alex MacNicoll, Danny Ramirez, Andrew Kai, Allyn Rachel\",\"Set to a new wave '80s soundtrack, a pair of young lovers from different backgrounds defy their parents and friends to stay together. A musical adaptation of the 1983 film.\",5.4\n"
		)));
		// set the output back to standard out for running the test
		System.setOut(standardOut);
		// same for standard in
		System.setIn(standardIn);
		if (frontend == null) {
			// test fails
			return false;
		} else {
			// test passed
			return true;
		}
	} catch (Exception e) {
		// make sure stdin and stdout are set correctly after we get exception in test
		System.setOut(standardOut);
		System.setIn(standardIn);
		e.printStackTrace();
		// test failed
		return false;
	}
}

