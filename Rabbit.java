import java.util.List;
import java.util.Random;

/**
 * A simple model of a rabbit.
 * Rabbits age, move, breed, and die.
 * 
 * @author Adriaan van Elk, Eric Gunnink & Jelmer Postma
 * @version 27-1-2015
 */
public class Rabbit extends Animal
{
    // Characteristics shared by all rabbits (class variables).

    // The age at which a rabbit can start to breed.
    private static int BREEDING_AGE = 5;
    // The age to which a rabbit can live.
    private static int MAX_AGE = 40;
    // The likelihood of a rabbit breeding.
    private static double BREEDING_PROBABILITY = 0.12;
    // The maximum number of births.
    private static int MAX_LITTER_SIZE = 4;
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();
    // The probability of a rabbit catching the disease
    //private static final double DISEASE_PROBABILITY = 0.90;

    /**
     * Create a new rabbit. A rabbit may be created with age
     * zero (a new born) or with a random age.
     * 
     * @param randomAge If true, the rabbit will have a random age.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Rabbit(boolean randomAge, Field field, Location location)
    {
        super(field, location);
        setAge(0);
        if(randomAge) {
            setAge(rand.nextInt(MAX_AGE));
        }
    }
    
    /**
     * This is what the rabbit does most of the time - it runs 
     * around. Sometimes it will breed or die of old age.
     * @param newRabbits A list to return newly born rabbits.
     */
    public void act(List<Actor> newRabbits)
    {
        incrementAge();
        if(isAlive()) {
            giveBirth(newRabbits);            
            // Try to move into a free location.
            Location newLocation = getField().freeAdjacentLocation(getLocation());
            if(newLocation != null) {
                setLocation(newLocation);
            }
            else {
                // Overcrowding.
                setDead();
            }
        }
    }
    
    //Getters for the rabbit
    
    /**
     * Return the maximum age of the rabbit
     * @return Maximum age
     */
    protected int getMaxAge()
    {
    	return MAX_AGE;
    }
    
    /**
     * Return the breeding age of the rabbit
     * @return Breeding age
     */
    protected int getBreedingAge()
    {
    	return BREEDING_AGE;
    }
    
    /**
     * Return the breeding probability of the rabbit
     * @return Breeding probability
     */
    protected double getBreedingProbability()
    {
    	return BREEDING_PROBABILITY;
    }
    
    /**
     * Return the maximum litter size of the rabbit
     * @return Maximum litter size
     */
    protected int getMaxLitterSize()
    {
    	return MAX_LITTER_SIZE;
    }
    
    //Setters for the rabbit
    
    /**
     * Set the maximum age of the rabbit
     */
    protected void setMaxAge(int maxAge)
    {
    	MAX_AGE = maxAge;
    }
    
    /**
     * Set the breeding probability
     */
    protected void setBreedingProbability(double BreedingProbability)
    {
    	BREEDING_PROBABILITY = BreedingProbability;
    }
    
    /**
     * Set the maximum litter size
     */
    protected void setMaxLitterSize(int maxLitterSize)
    {
    	MAX_LITTER_SIZE = maxLitterSize;
    }
    
}
