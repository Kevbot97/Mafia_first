package kevin.mafia;

import kevin.mafia.strings.Strings;

public class Medic extends Person {

	public Medic()
	{
		super();
	}
	
	public Medic(String newName)
	{
		super(newName);
	}
	
	public String getRoleName()
	{
		return "Medic";
	}
	
	public String getRoleString()
	{
		return Strings.medicRole;
	}
}
