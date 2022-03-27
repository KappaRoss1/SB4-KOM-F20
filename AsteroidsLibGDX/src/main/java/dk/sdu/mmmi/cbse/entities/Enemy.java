/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dk.sdu.mmmi.cbse.entities;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import dk.sdu.mmmi.cbse.main.Game;

public class Enemy extends SpaceObject {
/**
 *
 * @author acelu
 */
        private float maxSpeed;
        private float acceleration;
        private float deceleration; 
        
    public Enemy() {
        
        float numseed = MathUtils.random();
        if (numseed <= 0.50){
            y = Game.HEIGHT * MathUtils.random();
            x = numseed < 0.25 ? Game.WIDTH : 0; 
        } else {
            x = Game.WIDTH * MathUtils.random();
            y = numseed < 0.75 ? Game.HEIGHT : 1;
        }

        maxSpeed = 10;
        acceleration = 200;
        deceleration = 10;

        shapex = new float[6];
        shapey = new float[6];

        radians = 3.1415f / 2;
        rotationSpeed = 3;    
    }
    
    public void update(float dt) {

        // turning
        float ranseed = MathUtils.random();
        if (ranseed <= 0.15) {
            radians += rotationSpeed * dt;
        } else if (ranseed <= 0.30) {
            radians -= rotationSpeed * dt;
        } else {
            dx += MathUtils.cos(radians) * acceleration * dt;
            dy += MathUtils.sin(radians) * acceleration * dt;
        }

        // deceleration
        float vec = (float) Math.sqrt(dx * dx + dy * dy);
        if (vec > 0) {
            dx -= (dx / vec) * deceleration * dt;
            dy -= (dy / vec) * deceleration * dt;
        }
        if (vec > maxSpeed) {
            dx = (dx / vec) * maxSpeed;
            dy = (dy / vec) * maxSpeed;
        }

        // set position
        x += dx * dt;
        y += dy * dt;

        // set shape
        setShape();

        // screen wrap
        wrap();

    }

    private void setShape() {
        shapex[0] = x + MathUtils.cos(radians) * 8 * 1.25f;
        shapey[0] = y + MathUtils.sin(radians) * 8 * 1.25f;

        shapex[1] = x + MathUtils.cos(radians + 2 * 3.1415f / 8) * 3 * 1.25f;
        shapey[1] = y + MathUtils.sin(radians + 2 * 3.1145f / 8) * 3 * 1.25f;

        shapex[2] = x + MathUtils.cos(radians + 13 * 3.1415f / 16) * 6 * 1.25f;
        shapey[2] = y + MathUtils.sin(radians + 13 * 3.1145f / 16) * 6 * 1.25f;

        shapex[3] = x + MathUtils.cos(radians + 3.1415f) * 6 * 1.25f;
        shapey[3] = y + MathUtils.sin(radians + 3.1415f) * 6 * 1.25f;
        
        shapex[4] = x + MathUtils.cos(radians - 13 * 3.1415f / 16) * 6 * 1.25f;
        shapey[4] = y + MathUtils.sin(radians - 13 * 3.1145f / 16) * 6 * 1.25f;
        
        shapex[5] = x + MathUtils.cos(radians - 2 * 3.1415f / 8) * 3 * 1.25f;
        shapey[5] = y + MathUtils.sin(radians - 2 * 3.1145f / 8) * 3 * 1.25f;
    }

    public void draw(ShapeRenderer sr) {

        sr.setColor(1, 1, 1, 0.8f);

        sr.begin(ShapeRenderer.ShapeType.Line);

        for (int i = 0, j = shapex.length - 1;
                i < shapex.length;
                j = i++) {

            sr.line(shapex[i], shapey[i], shapex[j], shapey[j]);

        }

        sr.end();

    }
}
