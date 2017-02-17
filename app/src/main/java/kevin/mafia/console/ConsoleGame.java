package kevin.mafia.console;

import java.util.Scanner;

import kevin.mafia.Game;
import kevin.mafia.Person;

public class ConsoleGame {
	
	public static void main(String[] args)
	{
		Game game = new Game();
		game.numMafia = 1;
		game.addPerson("Kevin");
		game.addPerson("Ian");
		game.addPerson("Becky");
		game.addPerson("Hannah");
		game.addPerson("Steven");
		game.addPerson("Lukas");
		game.start();
		
		Scanner keyboard = new Scanner(System.in);
		
		int numPeople = game.townspeople.size();
		
		// this loop is for initial role reveal.  Buffer does not work as well as it could in android
		// obviously you'd switch activities instead of just writing newlines a bunch.
		for (int i = 0; i < numPeople; i++)
		{
			// buffer screen
			Person currentPerson = game.getCurrentPerson();
			System.out.println("Hi " + currentPerson.toString() + ".  Type any key to continue.");
			keyboard.nextLine();
			System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
			System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
			System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
			// once input is processed, a new screen is shown.
			System.out.println("You are the " + currentPerson.getRoleName() + ".  Press a key once you are good.");
			keyboard.nextLine();
			System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
			System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
			System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
			game.advanceCurrentPerson();
		}
		
		while (true)
		{
			// ***************DAY
			System.out.println("Who will you kill?");
			
			// here we'd get a list of live people obviously.
			int kill = keyboard.nextInt();
			if (kill >= 0)
			{
				game.townspeople.get(kill).die();
				System.out.println(game.townspeople.get(kill) + " has died.");
			}
			
			
			// **************NIGHT
			for (int i = 0; i < numPeople; i++)
			{
				// buffer screen
				Person currentPerson = game.getCurrentPerson();
				System.out.println("Hi " + currentPerson.toString() + ".  Type any key to continue.");
				keyboard.nextLine();
				System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
				System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
				System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
				// once input is processed, a new screen is shown.
				if (currentPerson.isDead)
				{
					System.out.println("You are dead.");
					System.out.println(game.summary());
				}
				else {
					System.out.println("You are the " + currentPerson.getRoleName() + ".  " + currentPerson.getRoleString());
					System.out.println(game.currentPersonTurn(keyboard.nextInt()));
				}
				keyboard.nextLine();
				System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
				System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
				System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
				game.advanceCurrentPerson();
			}
			System.out.println(game.gameOver());
			if (game.gameOver() != "The game is still in progress.")
				break;
			System.out.println(game.dayTime());
			game.resetFlags();
		}
	}
}
