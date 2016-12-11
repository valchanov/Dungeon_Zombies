
public class Fight {

	public void autoCombat(Hero hero, Enemy enemy) {
		do {
			// TODO calculate how to attack hero
			enemy.takeDamage(hero.attack());
			if (!enemy.isAlive()) {
				break;
			}
			hero.takeDamage(enemy.attack());

		} while (hero.isAlive() && enemy.isAlive());

		if (hero.isAlive()) {
			System.out.println("You won the battle. Your HP is "+hero.getHealth()+" and mana is "+ hero.getMana());
		} else {
			System.out.println("You died in battle! Game over!");
		}
	}

}
