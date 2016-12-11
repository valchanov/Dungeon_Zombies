public class Spell {
	private String name;
	private int damage;
	private int manaCost;
	private int castRange;

	Spell(String name, int damage, int manaCost, int castRange) {
		this.name = name;
		this.damage = damage;
		this.manaCost = manaCost;
		this.castRange = castRange;
	}

	public String getName() {
		return name;
	}

	public int getDamage() {
		return damage;
	}

	public int getManaCost() {
		return manaCost;
	}

	public int getRange() {
		return castRange;
	}

	@Override
	public String toString() {
		return name + " spell: " + damage + " damage, " + manaCost + " manacost, " + castRange+ " cast range " ;
	}
}
