package kevin.mafia;

public abstract class Person {

	public boolean isDead;
	public String name;
	
	public abstract String getRoleName();
	public abstract String getRoleString();
	
	public Person()
	{
		isDead = false;
	}
	
	public Person(String newName)
	{
		name = newName;
		isDead = false;
	}
	
	public void die()
	{
		isDead = true;
	}
	
	public void setName(String newName)
	{
		name = newName;
	}
	
	public String toString()
	{
		return name;
	}
}
