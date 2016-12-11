public class Hero extends Character {
	private int manaRegenerationRate;

	public Hero(String name, String title, int health, int mana, int manaRegenerationRate) {
		super(name, title, health, mana);
		this.manaRegenerationRate = manaRegenerationRate;
	}

	public int getManaRegenerationRate() {
		return manaRegenerationRate;
	}



}
