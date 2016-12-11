
public class Enemy extends Character {

	public Enemy(String name, String title, int health, int mana, Weapon weapon) {
		super(name, title, health, mana);
		//We decide enemeis to be equipped with weapon
		super.weapon = weapon;
		hasWeapon = true;
	}




}
