package kevin.mafia;

import kevin.mafia.strings.Strings;

public class Vigilante extends Person {
	
	public boolean hasUsedShot = false;

	public Vigilante()
	{
		super();
	}
	
	public Vigilante(String newName)
	{
		super(newName);
	}
	
	public String getRoleName()
	{
		return "Vigilante";
	}
	
	public String getRoleString()
	{
		if (this.hasUsedShot)
			return "You've already used your shot.";
		return Strings.vigilanteRole;
	}
	
	public void shoot()
	{
		hasUsedShot = true;
	}
}
