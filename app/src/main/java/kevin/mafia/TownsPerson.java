package kevin.mafia;

import kevin.mafia.strings.Strings;

public class TownsPerson extends Person {

	public TownsPerson()
	{
		super();
	}
	
	public TownsPerson(String newName)
	{
		super(newName);
	}
	
	public String getRoleName()
	{
		return "Townsperson";
	}
	
	public String getRoleString()
	{
		return Strings.townsPersonRole;
	}
}
