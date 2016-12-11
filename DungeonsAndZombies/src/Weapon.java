
public abstract class Weapon {
	private String name;
	private int damage;
	private int range;

	public Weapon(String name, int damage, int range) {
		this.damage = damage;
		this.name = name;
		this.range = range;
	}


	@Override
	public String toString() {
		return name + ": " + damage;
	}

	public String getName() {
		return name;
	}

	public int getDamage() {
		return damage;
	}
	
	public int getRange() {
		return range;
	}

}
