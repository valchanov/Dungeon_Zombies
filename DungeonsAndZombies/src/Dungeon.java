import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Dungeon {
	static List<Spell> spells;
	static List<Weapon> weapons;
	List<Enemy> enemies;
	static boolean gameIsRunnig = true;
	static int stageNumber = 1;
	static int battleNumber = 0;

	public int heroCol;
	public int heroRow;
	public boolean isCompleted = false;;
	private char[][] mapLevel;

	public Dungeon(char[][] currentStage) {
		mapLevel = currentStage;
	}

	private void printMap() {
		for (int row = 0; row < mapLevel.length; row++) {
			for (int col = 0; col < mapLevel[0].length; col++) {
				System.out.print(mapLevel[row][col] + " ");
			}
			System.out.println();
		}
	}

	private boolean spawn() {
		for (int row = 0; row < mapLevel.length; row++) {
			for (int col = 0; col < mapLevel[0].length; col++) {
				if (mapLevel[row][col] == 'S') {
					mapLevel[row][col] = 'H';
					heroCol = col;
					heroRow = row;
					return true;
				}
			}
		}
		return false;
	}

	void checkCurrentHeroSquare(Hero hero, int row, int col) {
		// If level gate found
		if (mapLevel[row][col] == 'G') {
			isCompleted = true;

			// If treasure found
		} else if (mapLevel[row][col] == 'T') {
			generateRandomTreasure(hero);

			// If enemy found
		} else if (mapLevel[row][col] == 'E') {
			// Autocombat mode
			Enemy enemy = enemies.get(battleNumber);
			int rnd = (int) (Math.random() * 1);
			if (rnd == 1) {
				Collections.shuffle(spells);
				enemy.learn(spells.get(0));
			}

			battleNumber++;
			System.out.println("You encounter an enemy. Fight for your life in your " + battleNumber + " battle!");
			Fight fight = new Fight();
			fight.autoCombat(hero, enemy);
		}
	}

	void markCurrentHeroSquare() {
		mapLevel[heroRow][heroCol] = 'H';
	}

	void changePathAfterHero() {
		mapLevel[heroRow][heroCol] = '.';
	}

	void moveHero(Hero hero, String direction) {
		try {
			switch (direction) {
			case "u":
				if (heroRow != 0 && mapLevel[heroRow - 1][heroCol] != '#') {
					changePathAfterHero();
					checkCurrentHeroSquare(hero, --heroRow, heroCol);
					markCurrentHeroSquare();
					hero.takeMana(hero.getManaRegenerationRate());
				}
				break;
			case "d":
				if (heroRow != mapLevel.length - 1 && mapLevel[heroRow + 1][heroCol] != '#') {
					changePathAfterHero();
					checkCurrentHeroSquare(hero, ++heroRow, heroCol);
					markCurrentHeroSquare();
					hero.takeMana(hero.getManaRegenerationRate());
				}
				break;
			case "r":
				if (heroCol != mapLevel[1].length - 1 && mapLevel[heroRow][heroCol + 1] != '#') {
					changePathAfterHero();
					checkCurrentHeroSquare(hero, heroRow, ++heroCol);
					markCurrentHeroSquare();
					hero.takeMana(hero.getManaRegenerationRate());
				}
				break;
			case "l":
				if (heroCol != 0 && mapLevel[heroRow][heroCol - 1] != '#') {
					changePathAfterHero();
					checkCurrentHeroSquare(hero, heroRow, --heroCol);
					markCurrentHeroSquare();
					hero.takeMana(hero.getManaRegenerationRate());
				}
				break;
			case "m":
				if (hero.getManaPotion() > 0) {
					hero.takeMana(30);
					hero.setManaPotion(hero.getManaPotion() - 1);
					System.out.println("You drank 1 mana potion: + 30 mana. Your current mana is: " + hero.getMana()
							+ ". You have " + hero.getManaPotion() + " mana potions left");
				} else {
					System.out.println("You dont't have any mana potions!");
				}
				break;
			case "h":
				if (hero.getHealthPotion() > 0) {
					hero.takeHealing(30);
					hero.setHealthPotion(hero.getHealthPotion() - 1);
					System.out.println("You drank 1 health potion: + 30 HP. Your current HP is: " + hero.getHealth()
							+ ". You have " + hero.getHealthPotion() + " HP potions left");
				} else {
					System.out.println("You dont't have any health potions!");
				}
				break;
			default:
				System.out.println("Invalid Input");
				break;
			}
		} catch (InputMismatchException e) {
			System.out.println("Invalid value!");
			// reader.next(); // this consumes the invalid token
		}
	}

	void equipWeapon(Hero hero) {
		Collections.shuffle(weapons);
		Weapon weapon = weapons.get(1);
		System.out.println("With a weapon-> " + weapon.toString());
		if (!hero.hasWeapon) {
			hero.equip(weapon);
			System.out.println("You no more have to fight with fists :)!");
		} else {
			// Already have weapon
			if (hero.weapon.getDamage() < weapon.getDamage()) {
				hero.equip(weapon);
				System.out.println("You have equiped new weapon!");
			} else {
				System.out.println("You discard it since it is not stronger than your current one.");
			}
		}
	}

	void generateRandomTreasure(Hero hero) {
		int randomNumber = (int) (Math.random() * 4 + 1);
		System.out.print("You found a treasure box: ");

		// Case 1: hero health potion
		if (randomNumber == 1) {
			hero.setHealthPotion(hero.getHealthPotion() + 1);
			System.out.println("Health potion +30");
			System.out.println(
					"Your HP is " + hero.getHealth() + ". You have " + hero.getHealthPotion() + " HP potion(s).");

			// Case 2: hero mana potion
		} else if (randomNumber == 2) {
			hero.setManaPotion(hero.getManaPotion() + 1);
			System.out.println("Mana potion +30");
			System.out.println(
					"Your mana is " + hero.getMana() + ". You have " + hero.getManaPotion() + " mana potion(s).");

			// Case 3: hero found spell scroll
		} else if (randomNumber == 3) {
			learnNewSpell(hero);
			// Case 4: hero found weapon. He might have found the same
			// one!?!
		} else {
			equipWeapon(hero);
		}
	}

	void learnNewSpell(Hero hero) {
		Collections.shuffle(spells);
		Spell spell = spells.get(1);
		System.out.println("With a magic scroll -> " + spell.toString());
		if (!hero.hasSpell) {
			hero.learn(spell);
			System.out.println("You have learned your 1-st spell!");
		} else {
			// Already knew 1 spell
			if (hero.spell.getDamage() < spell.getDamage()) {
				hero.learn(spell);
				System.out.println("You have learned your next spell!");
			} else {
				System.out.println("You discard it since it is not stronger than yours: " + spell.toString());
			}
		}
	}

	static void genereateListOfSpells() {
		Spell fireBall = new Spell("FireBall", 30, 20, 3);
		Spell iceBolt = new Spell("IceBolt", 30, 20, 3);
		Spell thunderStroke = new Spell("ThunderStroke", 25, 15, 3);
		Spell poison = new Spell("Poison", 22, 15, 3);

		spells = new ArrayList<Spell>();
		spells.add(fireBall);
		spells.add(iceBolt);
		spells.add(thunderStroke);
		spells.add(poison);
	}

	public static void generateListOfWeapons() {
		Weapon knife = new Knife();
		Weapon axe = new Axe();
		Weapon gun = new Gun();
		Weapon sword = new Sword();
		Weapon bow = new Bow();

		weapons = Arrays.asList(knife, axe, gun, sword, bow);
	}

	public void generateListOfEnemies() {
		Enemy darthVader = new Enemy("Darth Vader", "The bad guy", 100, 70, new Sword());
		Enemy jabba = new Enemy("Jabba", "Fat Frog", 80, 60, new Gun());
		Enemy darthMaul = new Enemy("Darth Maul", "The bad guy", 90, 50, new Axe());
		Enemy general = new Enemy("General Hux", "The General", 100, 50, new Bow());

		enemies = new ArrayList<Enemy>();
		enemies.add(darthMaul);
		enemies.add(darthVader);
		enemies.add(jabba);
		enemies.add(general);
		Collections.shuffle(enemies);
	}

	//TODO Manual attack
	public static void heroAttack(String by) {
		if (by == "weapon") {

		} else if (by == "spell") {

		}
	}

	public static void main(String[] args) {
		genereateListOfSpells();
		generateListOfWeapons();
		System.out.println("Enter movement direction: u, d, l, r");
		System.out.println("Drink health or mana potion: h , m");

		try (Scanner input = new Scanner(System.in)) {

			while (gameIsRunnig && ReadLevelFromTextFile.hasNextLevel) {
				System.out.println("Stage " + stageNumber);
				ReadLevelFromTextFile level = new ReadLevelFromTextFile();
				level.readTextFile();
				Dungeon stage = new Dungeon(level.getCurrentStage());
				stage.generateListOfEnemies();

				Hero hero = new Hero("Luke", "Skywalker", 100, 100, 2);
				stage.spawn();
				stage.printMap();

				while (hero.isAlive() && !stage.isCompleted) {
					String direction = input.nextLine();
					stage.moveHero(hero, direction);
					stage.printMap();
					System.out.println("Mana:" + hero.getMana());
				}

				if (hero.isAlive()) {
					System.out.println("Congrats. Stage " + stageNumber + " complete!\n");
					stageNumber++;
					if (!ReadLevelFromTextFile.hasNextLevel) {
						System.out.println("You've managed trough all stages!!! Game over!");
					}
				} else {
					gameIsRunnig = false;
				}

			}
		}
	}
}
