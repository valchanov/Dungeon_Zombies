import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReadLevelFromTextFile {

	List<String> lines = new ArrayList<>();
	char[][] currentStage;

	static int firstLineOfMatrix = 0;
	static boolean hasNextLevel = true;

	public char[][] getCurrentStage() {
		return currentStage;
	}

	private char[][] readTextLinesToMatrix() {
		// enter chars in 2d array
		currentStage = new char[lines.size()][lines.get(0).length()];
		for (int row = 0; row < lines.size(); row++) {
			for (int col = 0; col < lines.get(0).length(); col++) {
				currentStage[row][col] = lines.get(row).charAt(col);
			}
		}

		return currentStage;
	}

	public void readTextFile() {
		try (BufferedReader br = new BufferedReader(new FileReader(".\\game_levels.txt"))) {
			// Find the beginning line of next level map
			int i = 0;
			while (i < firstLineOfMatrix) {
				br.readLine();
				i++;
			}

			String line;
			while (hasNextLevel) {
				firstLineOfMatrix++;
				if ((line = br.readLine()) != null) {
					if (line.length() == 0) {
						break;
					}
					lines.add(line);
				} else {
					hasNextLevel = false;
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		readTextLinesToMatrix();
	}

}
