package dk.sdu.mmmi.cbse.asteroidSystem;

import dk.sdu.mmmi.cbse.common.data.*;
import dk.sdu.mmmi.cbse.common.data.entityparts.*;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.commonasteroids.Asteroid;
import org.openide.util.lookup.ServiceProvider;


@ServiceProvider(service = IGamePluginService.class)
public class AsteroidPlugin
  implements IGamePluginService
{
  private Entity asteroid;

    @Override
    public void start(GameData gameData, World world) {
       Entity asteroid = createAsteroid(gameData);
       world.addEntity(asteroid);
    }

    @Override
    public void stop(GameData gameData, World world) {
        for (Entity asteroid : world.getEntities(Asteroid.class)) {
            world.removeEntity(asteroid);
        }
    }
    
    private Entity createAsteroid(GameData gameData){
        Entity asteroid = new Asteroid();
        float radians = (float) (Math.random()) * 2 * 3.1415f;
        float speed = (float) (Math.random()) * 10f + 20f;
        float deacceleration = 10;
        float rotationSpeed = 5;
        int life = 3;


        asteroid.setRadius(20);
        asteroid.add(new MovingPart(deacceleration, speed, speed, rotationSpeed));
        asteroid.add(new PositionPart(30, 30, radians));
        asteroid.add(new LifePart(life));

        return asteroid;
    }

}
