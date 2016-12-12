import java.util.InputMismatchException;

public abstract class Character {
	public Spell spell;
	public Weapon weapon;
	private String name;
	private String title;
	private int health;
	private int maxHealth;
	private int mana;
	private int maxMana;
	private int healthPotion;
	private int manaPotion;
	public boolean hasSpell;
	public boolean hasWeapon;

	public Character(String name, String title, int health, int mana) {
		this.setName(name);
		this.title = title;
		this.health = health;
		maxHealth = health;
		this.mana = mana;
		maxMana = mana;
		hasSpell = false;
		hasWeapon = false;
		healthPotion = 0;
		manaPotion = 0;
	}

	String knownAs() {
		return getName() + " " + title;
	}

	public int getHealth() {
		return health;
	}

	public int getMana() {
		return mana;
	}

	public boolean isAlive() {
		if (health < 1) {
			return false;
		}
		return true;
	}

	public boolean canCast() {
		// TODO check it
		if (mana < 1) {
			return false;
		}

		return true;
	}

	public void takeDamage(int damagePoints) {
		health -= damagePoints;
		if (health < 0) {
			health = 0;
		}
	}

	public void takeHealing(int healingPoints) {
		health += healingPoints;
		if (health > maxHealth) {
			health = maxHealth;
		}
	}

	public void takeMana(int manaPoints) {
		// 2 ways to increase it!!!
		mana += manaPoints;
		if (mana > maxMana) {
			mana = maxMana;
		}
	}

	public void learn(Spell spell) {
		this.spell = spell;
		hasSpell = true;
	}

	public void equip(Weapon weapon) {
		this.weapon = weapon;
		hasWeapon = true;
	}

	// Try to attack from distance with
	// TODO obsticles, enemy movement, count squares distance
	public int tryManualAttack(String weaponOrSpell) {
		int hitPoints = 0;
		try {
			if (weaponOrSpell == "weapon" && hasWeapon && (weapon.getRange() > 1)) {
				hitPoints = weapon.getDamage();
			} else if (weaponOrSpell == "spell" && hasSpell && (weapon.getRange() > 1)) {
				hitPoints = spell.getDamage();
			}
		} catch (InputMismatchException e) {
			System.out.println("Invalid value!");
			// reader.next(); // this consumes the invalid token
		}
		return hitPoints;
	}

	//Autocombat
	public int attack() {
		int hitPoints = 0;

		// Try to auto attack with the strongest spell/weapon
		if (canCast() && hasWeapon) {
			if (weapon.getDamage() < spell.getDamage()) {
				hitPoints = attack(spell);
				System.out.println(this.knownAs() + " attacks with " + this.spell);
			} else {
				hitPoints = attack(weapon);
				System.out.println(this.knownAs() + " attacks with " + this.weapon);
			}
		} else if (canCast()) {
			hitPoints = attack(spell);
			System.out.println(this.knownAs() + " attacks with " + this.spell);
		} else if (hasWeapon) {
			hitPoints = attack(weapon);
			System.out.println(this.knownAs() + " attacks with " + this.weapon);
		} else {
			// bare fists
			hitPoints = 5;
			System.out.println(this.knownAs() + " attacks with " + "bare fists");
		}
		return hitPoints;
	}

	//TODO manually choose to attack by weapon
	public int attack(Weapon byWeapon) {
		return byWeapon.getDamage();
	}

	//TODO manually choose to attack by spell
	public int attack(Spell bySpell) {
		setMana(getMana() - bySpell.getManaCost());
		return bySpell.getDamage();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getHealthPotion() {
		return healthPotion;
	}

	public void setHealthPotion(int healthPotion) {
		this.healthPotion = healthPotion;
	}

	public int getManaPotion() {
		return manaPotion;
	}

	public void setManaPotion(int manaPotion) {
		this.manaPotion = manaPotion;
	}

	public void setMana(int mana) {
		this.mana = mana;
	}

}
