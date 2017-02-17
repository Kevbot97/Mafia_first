package kevin.mafia;

import java.util.ArrayList;
import java.util.Random;

public class Game {
	
	// game wide variables
	public int numMafia;
	public int currentPerson;
	public ArrayList<String> names;
	public ArrayList<Person> townspeople;
	public int currentDay;
	
	// turn wide variables
	public int mafiaTarget;
	public int medicTarget;
	public int vigilanteTarget;
	public boolean mafiaFuckedUp = false;
	public boolean skipCop = false;
	
	
	public Game()
	{
		numMafia = 1;
		currentPerson = 0;
		names = new ArrayList<String>();
		townspeople = new ArrayList<Person>();
		currentDay = 0;

		names.add("Kevin");
		names.add("Ian");
		names.add("Becky");
		names.add("Hannah");
		names.add("Steven");
		names.add("Lukas");

		mafiaTarget = -1;
		medicTarget = -1;
		vigilanteTarget = -1;
		skipCop = false;
	}

	public Game(ArrayList<String> previousNames)
	{
		numMafia = 1;
		currentPerson = 0;
		names = previousNames;
		townspeople = new ArrayList<Person>();
		currentDay = 0;

		mafiaTarget = -1;
		medicTarget = -1;
		vigilanteTarget = -1;
		skipCop = false;
	}
	
	public void addPerson(String newName)
	{
		names.add(newName);
	}
	
	public void start()
	{	
		chooseRoles();
		currentPerson = 0;
	}
	
	// randomizes roles
	public void chooseRoles()
	{
		Random rand = new Random();
		
		ArrayList<String> copyNames = new ArrayList<String>();
		for (int i = 0; i < names.size(); i++)
			copyNames.add(names.get(i));
		
		// mafia selection
		ArrayList<Mafia> mafia = new ArrayList<Mafia>();
		for (int i = 0; i < numMafia; i++)
		{
			mafia.add(new Mafia(names.remove(rand.nextInt(names.size()))));
		}
		
		// special roles
		Cop cop = new Cop(names.remove(rand.nextInt(names.size())));
		Medic medic = new Medic(names.remove(rand.nextInt(names.size())));
		Vigilante vigilante = new Vigilante(names.remove(rand.nextInt(names.size())));
		
		// townspeople selection
		ArrayList<TownsPerson> people = new ArrayList<TownsPerson>();
		for (int i = 0; i < names.size(); i++)
		{
			people.add(new TownsPerson(names.get(i)));
		}

		
		// add the individual people, now in their proper roles,
		// back in the order they should be in.
		for (int i = 0; i < copyNames.size(); i++)
		{
			String currentName = copyNames.get(i);
			for (int j = 0; j < mafia.size(); j++)
				if (mafia.get(j).name == currentName)
					townspeople.add(mafia.get(j));
			
			if (currentName == medic.name)
				townspeople.add(medic);
			if (currentName == cop.name)
				townspeople.add(cop);
			if (currentName == vigilante.name)
				townspeople.add(vigilante);
			
			for (int j = 0; j < people.size(); j++)
				if (people.get(j).name == currentName)
					townspeople.add(people.get(j));
		}
		
		// reset the names once they're added to townspeople
		names = copyNames;
	}
	
	public boolean advanceCurrentPerson()
	{
		currentPerson++;
		if (currentPerson >= townspeople.size())
		{
			currentPerson = 0;
			currentDay++;
            return true;
		}

        return false;
	}

    public int getIndexFromName(String name)
    {
        for (int i = 0; i < townspeople.size(); i++)
        {
            if (townspeople.get(i).name.equals(name))
                return i;
        }

        return -1;
    }

	public ArrayList<String> getLivePeople()
	{
		ArrayList<String> live = new ArrayList<String>();
		for (int i = 0; i < townspeople.size(); i++)
			if (!townspeople.get(i).isDead)
				live.add(townspeople.get(i).name);

        return live;
	}
	
	public Person getCurrentPerson()
	{
		return townspeople.get(currentPerson);
	}
	
	// hits whatever flags are necessary
	public String currentPersonTurn(int actor)
	{
		Person current = townspeople.get(currentPerson);
		if (current instanceof Mafia)
		{
			if (mafiaTarget != -1 && mafiaTarget != actor)
				mafiaFuckedUp = true;
			else mafiaTarget = actor;
		}
		
		else if (current instanceof Cop && !skipCop)
			return ((Cop) current).investigate(townspeople.get(actor));
		else if (current instanceof Medic)
			medicTarget = actor;
		else if (current instanceof Vigilante) {
			if (!((Vigilante) current).hasUsedShot && actor >= 0)
			{
				((Vigilante) current).shoot();
				vigilanteTarget = actor;
			}
		}
		return "Thank you.";
	}
	
	// calculates & returns who died
	public String dayTime()
	{
		skipCop = false;
		
		if (!mafiaFuckedUp)
		{
			ArrayList<String> dead = new ArrayList<String>();
			if (mafiaTarget != medicTarget)
			{
				townspeople.get(mafiaTarget).die();
				dead.add(townspeople.get(mafiaTarget).name);
			}
			if (vigilanteTarget != -1 && vigilanteTarget != medicTarget && vigilanteTarget != mafiaTarget)
			{
				townspeople.get(vigilanteTarget).die();
				dead.add(townspeople.get(vigilanteTarget).name);
			}
			
			switch (dead.size())
			{
			case 0:
				return "No one died last night.";
			case 1:
				return (dead.get(0) + " has been killed.");
			case 2:
				Random rand = new Random();
				int firstName = rand.nextInt(2);
				if (firstName == 1)
					return (dead.get(1) + " and " + dead.get(0) + " have been killed.");
				else return (dead.get(0) + " and " + dead.get(1) + " have been killed.");
			}
		}
		
		else skipCop = true;
		return "The mafia screwed up and chose different targets.  The round must be redone.";
	}
	
	// resets who the targets are
	public void resetFlags()
	{
		mafiaTarget = -1;
		medicTarget = -1;
		vigilanteTarget = -1;
		mafiaFuckedUp = false;
	}
	
	// print summary for those who died
	public String summary()
	{
		String retString ="";
		
		for (int i = 0; i < townspeople.size(); i++)
		{
			retString += townspeople.get(i).name + " : " + townspeople.get(i).getRoleName();
			if (townspeople.get(i).isDead)
				retString += " (Deceased)";
			retString += "\n";
		}
		return retString;
	}

    public void kill(int index)
    {
        if (index >= 0)
            townspeople.get(index).die();
    }
	
	public String gameOver()
	{
		int mafiaAlive = 0;
		int townspeopleAlive = 0;
		
		for (int i = 0; i < townspeople.size(); i++)
		{
			if (!townspeople.get(i).isDead)
			{
				if (townspeople.get(i) instanceof Mafia)
					mafiaAlive++;
				else townspeopleAlive++;
			}
		}
		
		if (mafiaAlive == 0)
			return "The townspeople have won!";
		else if (mafiaAlive >= townspeopleAlive)
			return "The mafia have won!";
		else if (mafiaAlive + townspeopleAlive <= 3)
			return "Three person standoff.";
		else return "The game is still in progress.";
	}
	
	public void reset()
	{
		currentDay = 0;
		currentPerson = 0;
		townspeople = new ArrayList<Person>();
		// names should be already reset to its original values
	}
}
