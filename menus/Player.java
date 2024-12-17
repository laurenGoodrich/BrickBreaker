package menus;

import java.io.Serializable;

public class Player implements Serializable {
	
	private String initials;
	private int score;
	
	public Player(String initials, int score) {
		this.initials = initials;
		this.score = score;
	}

	public String getInitials() {
		
		// Default initials are AAA
		if (initials == null || initials.length() == 0) {
			initials = "AAA";
		}
		// Initials are all upper case
		if (!(initials.equals(initials.toUpperCase()))) {
			initials = initials.toUpperCase();
		}
		// Initials are all 3 characters long
		if (initials.length() > 3) {
			initials = initials.substring(0, 3);
		}
		
		// Initials are all letters
		String newInitials = "";
		for (int i = 0; i < 3; i++) {
			if (Character.isLetter(initials.charAt(i))) {
				newInitials += initials.charAt(i);
			}
			else {
				newInitials += 'A';
			}
		}
		
		initials = newInitials;
		
		return initials;
	}

	public void setInitials(String initials) {
		this.initials = initials;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
}
