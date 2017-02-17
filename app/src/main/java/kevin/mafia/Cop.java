package kevin.mafia;

import kevin.mafia.strings.Strings;

public class Cop extends Person {

	public Person previousSuspect;
	
	public Cop()
	{
		super();
	}
	
	public Cop(String newName)
	{
		super(newName);
	}
	
	public String getRoleName()
	{
		return "Police Officer";
	}
	
	public String getRoleString()
	{
		return Strings.copRole;
	}
	
	public String investigate(Person suspect)
	{
		// if first turn
		if (previousSuspect == null)
		{
			previousSuspect = suspect;
			return "Thank you.";
		}
		
		String retString = previousSuspect.name + " and " + suspect.name;
		// simple XOR on mafia check; if true, on different teams
		if ((previousSuspect instanceof Mafia) ^ (suspect instanceof Mafia))
			retString += " are not on the same team.";
		else retString += " are on the same team.";
		
		previousSuspect = suspect;
		
		return retString;
	}
}
