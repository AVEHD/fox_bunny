import java.util.List;
import java.util.Iterator;
import java.util.Random;

/**
 * A simple model of a fox.
 * Foxes age, move, eat rabbits, and die.
 * 
 * @author Adriaan van Elk, Eric Gunnink & Jelmer Postma
 * @version 27-1-2015
 */
public class Fox extends Animal
{
    // Characteristics shared by all foxes (class variables).
    
    // The age at which a fox can start to breed.
    private static int BREEDING_AGE = 15;
    // The age to which a fox can live.
    private static int MAX_AGE = 150;
    // The likelihood of a fox breeding.
    private static double BREEDING_PROBABILITY = 0.08;
    // The maximum number of births.
    private static int MAX_LITTER_SIZE = 2;
    // The food value of a single rabbit. In effect, this is the
    // number of steps a fox can go before it has to eat again.
    private static final int RABBIT_FOOD_VALUE = 9;
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();

    /**
     * Create a fox. A fox can be created as a new born (age zero
     * and not hungry) or with a random age and food level.
     * 
     * @param randomAge If true, the fox will have random age and hunger level.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Fox(boolean randomAge, Field field, Location location)
    {
        super(field, location);
        if(randomAge) {
            setAge(rand.nextInt(MAX_AGE));
            foodLevel = rand.nextInt(RABBIT_FOOD_VALUE);
        }
        else {
            setAge(0);
            foodLevel = RABBIT_FOOD_VALUE;
        }
    }
    
    /**
     * Look for rabbits adjacent to the current location.
     * Only the first live rabbit is eaten.
     * @return Where food was found, or null if it wasn't.
     */
    private Location findFood()
    {
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();
            Object animal = field.getObjectAt(where);
            if(animal instanceof Rabbit) {
                Rabbit rabbit = (Rabbit) animal;
                if(rabbit.isAlive()) { 
                    rabbit.setDead();
                    foodLevel = RABBIT_FOOD_VALUE;
                    // Remove the dead rabbit from the field.
                    return where;
                }
            }
        }
        return null;
    }
    
    /**
     * Return the breeding age of the fox
     * @return breeding age of the fox
     */
    protected int getBreedingAge()
    {
    	return BREEDING_AGE;
    }
    
    /**
     * Return the max age of the fox
     * @return max age of the fox
     */
    protected int getMaxAge()
    {
    	return MAX_AGE;
    }
    
    /**
     * Return a double with the probability a fox will reproduce
     * @return the breeding probability of the fox
     */
    protected double getBreedingProbability()
    {
    	return BREEDING_PROBABILITY;
    }
    
    /**
     * Return an int with the max litter size the fox can produce
     * @return the max litter size
     */
    protected int getMaxLitterSize()
    {
    	return MAX_LITTER_SIZE;
    }
    
    /**
     * set the maximum litter size of the fox
     */
    protected void setMaxLitterSize(int maxSize)
    {
    	MAX_LITTER_SIZE = maxSize;
    }
    
	/**
	 * This is what the fox does most of the time: it hunts for rabbits. In the
	 * process, it might breed, die of hunger, or die of old age.
	 * 
	 * @param field
	 *            The field currently occupied.
	 * @param newFoxes
	 *            A list to return newly born foxes.
	 */
	public void act(List<Actor> newFoxes) {
		incrementAge();
		incrementHunger();
		if (isAlive()) {
			giveBirth(newFoxes);
			// Move towards a source of food if found.
			Location newLocation = findFood();
			if (newLocation == null) {
				// No food found - try to move to a free location.
				newLocation = getField().freeAdjacentLocation(getLocation());
			}
			// See if it was possible to move.
			if (newLocation != null) {
				setLocation(newLocation);
			} else {
				// Overcrowding.
				setDead();
			}
		}
	}
}
