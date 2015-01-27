import java.util.*;

/**
 * A class representing shared characteristics of animals.
 * 
 * @author Adriaan van Elk, Eric Gunnink & Jelmer Postma
 * @version 27-1-2015
 */
public abstract class Animal implements Actor
{
    // Whether the animal is alive or not.
    private boolean alive;
    // The animal's field.
    private Field field;
    // The animal's position in the field.
    private Location location;
    // The animal's age
    private int age;
    //A Random number generator to control breeding
    private static final Random rand = Randomizer.getRandom();
    //The animal's hunger level. Decreases when eating other animals
    //which are the animals prey
    protected int foodLevel;
    
    /**
     * Create a new animal at location in field.
     * 
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Animal(Field field, Location location)
    {
        alive = true;
        this.field = field;
        setLocation(location);
        this.age = 0;
    }
    
    /**
     * Make this animal act - that is: make it do
     * whatever it wants/needs to do.
     * @param newActors A list to receive newly born animals.
     */
    abstract public void act(List<Actor> newActors);

    /**
     * Check whether the animal is alive or not.
     * @return true if the animal is still alive.
     */
    public boolean isAlive()
    {
        return alive;
    }

    /**
     * Indicate that the animal is no longer alive.
     * It is removed from the field.
     */
    public void setDead()
    {
        alive = false;
        if(location != null) {
            field.clear(location);
            location = null;
            field = null;
        }
    }

    /**
     * Return the animal's location.
     * @return The animal's location.
     */
    protected Location getLocation()
    {
        return location;
    }
    
    /**
     * Place the animal at the new location in the given field.
     * @param newLocation The animal's new location.
     */
    protected void setLocation(Location newLocation)
    {
        if(location != null) {
            field.clear(location);
        }
        location = newLocation;
        field.place(this, newLocation);
    }
    
    /**
     * Return the animal's field.
     * @return The animal's field.
     */
    protected Field getField()
    {
        return field;
    }
    
    /**
     * Return the animals age
     */
    protected int getAge()
    {
    	return this.age;
    }
    
    /**
     * Set the animals age
     * @param the age of the animal
     */
    protected void setAge(int age)
    {
    	this.age = age;
    }
    
    /**
     * Return the MAX_AGE of the animal
     */
    abstract protected int getMaxAge();
    
    /**
     * Return the breeding age of the animal
     */
    abstract protected int getBreedingAge();
    
    /**
     * Increase the age
     * This could result in the animal's death.
     */
    protected void incrementAge()
    {
    	setAge(getAge()+1);
    	if (getAge() > getMaxAge())
    	{
    		setDead();
    	}
    }
    
    /**
     * Increase the hunger
     * This could result in the animals death.
     */
    protected void incrementHunger()
    {
    	foodLevel--;
    	if (foodLevel <= 0)
    	{
    		setDead();
    	}
    }
    
    /**
     * Generate a random number of births if the animal can breed
     * @return The number of births
     */
    protected int breed()
    {
    	int births = 0;
    	if (canBreed() && rand.nextDouble() <= getBreedingProbability())
    	{
    		births = rand.nextInt(getMaxLitterSize()) + 1;
    	}
    	return births;
    }
    
    /**
     * Returns a double with the chance the animal can reproduce itself
     * @return the breeding probability of the animal
     */
    protected abstract double getBreedingProbability();
    
    /**
     * Returns a int with the max litter size the animal can have.
     * @return the max litter size of the animal
     */
    protected abstract int getMaxLitterSize();
    
    /**
     * An animal can reproduce itself when it reaches the breeding age
     * @return true if animal can reproduce
     * @return false if animal can't reproduce
     */
    public boolean canBreed()
    {
    	return age >= getBreedingAge();
    }
    
    /**
     * Check if the animal can give birth in the current step. New
     * births will be made on free adjacent tiles
     * @param newAnimals A list to return newly born animals
     */
    protected void giveBirth(List<Actor> newActors)
    {
    	//New animals are born on adjacent tiles
    	//Get a list of free tiles
    	Field field = getField();
    	List<Location> free = field.getFreeAdjacentLocations(getLocation());
    	int births = breed();
    	for (int b = 0; b < births && free.size() > 0; b++);
    	{
    		Location loc = free.remove(0);
    		Animal young = null;
    		if (this instanceof Rabbit)
    		{
    			young = new Rabbit(false, field, loc);
    		}
    		if (this instanceof Fox)
    		{
    			young = new Fox(false, field, loc);
    		}
    		newActors.add(young);
    	}
    }
    
    /**
     * Makes this animal more hungry. This could result in the animals death.
     */
    protected void incementHunger()
    {
    	foodLevel--;
    	if (foodLevel <= 0)
    	{
    		setDead();
    	}
    }
}