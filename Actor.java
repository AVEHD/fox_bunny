import java.util.List;

/**
 * @author Adriaan van Elk, Eric Gunnink & Jelmer Postma
 * @version 27-1-2015
 */

public interface Actor
{
	public void act(List<Actor> newActors);
	public boolean isAlive();
	public void setDead();
}