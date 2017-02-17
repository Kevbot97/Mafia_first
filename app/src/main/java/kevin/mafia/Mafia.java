package kevin.mafia;

import kevin.mafia.strings.Strings;

public class Mafia extends Person {

	public Mafia()
	{
		super();
	}
	
	public Mafia(String newName)
	{
		super(newName);
	}
	
	public String getRoleName()
	{
		return "Mafia";
	}
	
	public String getRoleString()
	{
		return Strings.mafiaRole;
	}
}
