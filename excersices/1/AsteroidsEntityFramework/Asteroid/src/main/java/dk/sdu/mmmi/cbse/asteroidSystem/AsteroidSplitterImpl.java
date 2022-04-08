package dk.sdu.mmmi.cbse.asteroidSystem;

import dk.sdu.mmmi.cbse.common.data.*;
import dk.sdu.mmmi.cbse.common.data.entityparts.*;
import dk.sdu.mmmi.cbse.commonasteroids.*;

public class AsteroidSplitterImpl implements IAsteroidSplitter {

    @Override
    public void createSplitAsteroid(Entity entity, World world) {
        PositionPart otherPos = entity.getPart(PositionPart.class);
        LifePart otherLife = entity.getPart(LifePart.class);
        float radians = otherPos.getRadians();
        int radius = 0;
        float speed = 5;
        int life = otherLife.getLife() - 1;
        if (life == 1) {
            radius = 6;
            speed = (float) Math.random() * 30f + 70f;
        } else if (life == 2) {
            radius = 10;
            speed = (float) Math.random() * 10f + 50f;
        } else if (life <= 0) {
            world.removeEntity(entity);
            return;
        }

        Entity asteroid1 = new Asteroid();

        asteroid1.setRadius(radius);
        float radians1 = radians - 0.5f;

        float by1 = (float) (Math.sin(radians1) * entity.getRadius() * asteroid1.getRadius());
        float bx1 = (float) (Math.cos(radians1) * entity.getRadius() * asteroid1.getRadius());

        PositionPart astPositionPart1 = new PositionPart(otherPos.getX() + bx1, otherPos.getY() + by1, radians1);
        asteroid1.add(new MovingPart(0, 5000, speed, 0));
        asteroid1.add(astPositionPart1);
        asteroid1.add(new LifePart(life));

        world.addEntity(asteroid1);

        Entity asteroid2 = new Asteroid();

        asteroid2.setRadius(radius);
        float radians2 = radians + 0.5f;

        float by2 = (float) (Math.sin(radians2) * entity.getRadius() * asteroid2.getRadius());
        float bx2 = (float) (Math.cos(radians2) * entity.getRadius() * asteroid2.getRadius());
        PositionPart astPositionPart2 = new PositionPart(otherPos.getX() + bx2, otherPos.getY() + by2, radians2);

        asteroid2.add(new MovingPart(0, 5000, speed, 0));
        asteroid2.add(astPositionPart2);
        asteroid2.add(new LifePart(life));

        world.addEntity(asteroid2);

        world.removeEntity(entity);
    }

}
