//Back END DEVELOPER

import java.util.List;

public class Score implements Comparable<Game>{
	int score;
	public Score() {}
	public Score(List<Questions> asked, List<String> answers) {}
	public int calculateScore() {}
	public int getScore() {}
	public boolean correctness(Questions question, String answered) {}
	public int compareTo(Game game) {}
}
