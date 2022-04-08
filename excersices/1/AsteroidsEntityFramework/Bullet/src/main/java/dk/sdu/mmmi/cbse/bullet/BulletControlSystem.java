package dk.sdu.mmmi.cbse.bullet;


import dk.sdu.mmmi.cbse.common.data.*;
import dk.sdu.mmmi.cbse.common.data.entityparts.*;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.commonbullet.*;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

/**
 *
 * @author Dinh Phu
 */

@ServiceProviders(value = {
        @ServiceProvider(service = IEntityProcessingService.class),
        @ServiceProvider(service = BulletSPI.class)})
public class BulletControlSystem implements IEntityProcessingService, BulletSPI {

    @Override
    public void process(GameData gameData, World world) {

        for (Entity bullet : world.getEntities(Bullet.class)) {

            PositionPart positionPart = bullet.getPart(PositionPart.class);
            MovingPart movingPart = bullet.getPart(MovingPart.class);
            TimerPart timerPart = bullet.getPart(TimerPart.class);
            movingPart.setUp(true);
            if (timerPart.getExpiration() < 0) {
                world.removeEntity(bullet);
            }
            movingPart.process(gameData, bullet);
            positionPart.process(gameData, bullet);
            timerPart.process(gameData, bullet);

            setShape(bullet);
        }
    }

    @Override
    public Entity createBullet (Entity shooter, GameData gameData) {
        PositionPart shooterPos = shooter.getPart(PositionPart.class);
        MovingPart shooterMovingPart = shooter.getPart(MovingPart.class);

        float x = shooterPos.getX();
        float y = shooterPos.getY();
        float radians = shooterPos.getRadians();
        float dt = gameData.getDelta();
        float speed = 350;
        float deceleration = 0;
        float acceleration = 5000000;
        float rotationSpeed = 5;
        int life = 1;


        Entity bullet = new Bullet();
        bullet.setRadius(2);

        float bx = (float) (Math.cos(radians) * shooter.getRadius() * bullet.getRadius());
        float by = (float) (Math.sin(radians) * shooter.getRadius() * bullet.getRadius());

        bullet.add(new PositionPart(bx + x, by + y, radians));
        bullet.add(new LifePart(life));
        bullet.add(new MovingPart(deceleration, acceleration, speed, rotationSpeed));
        bullet.add(new TimerPart(1));

        bullet.setShapeX(new float[2]);
        bullet.setShapeY(new float[2]);

        return bullet;
    }

    private void setShape(Entity entity) {
        float[] shapex = entity.getShapeX();
        float[] shapey = entity.getShapeY();
        PositionPart positionPart = entity.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();
        float radians = positionPart.getRadians();

        shapex[0] = x;
        shapey[0] = y;

        shapex[1] = (float) (x + Math.cos(radians - 4 * 3.1415f / 5));
        shapey[1] = (float) (y + Math.sin(radians - 4 * 3.1145f / 5));


        entity.setShapeX(shapex);
        entity.setShapeY(shapey);
    }

}
