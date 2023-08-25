package Logic;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Pass extends Enemy {

    private Bomber target;
    private BufferedImage[][] sprites;
    private Point2D.Float targetPosition;
    private float interpolationFactor = 0.2f;
    private static final float RANDOM_MOVE_SPEED = 1.5f; // Velocidad para el movimiento aleatorio
    private static final float CHASE_MOVE_SPEED = 5.0f; // Velocidad para la persecución
    private static final float CHASE_DISTANCE_THRESHOLD = 50.0f; // Distancia para comenzar a perseguir
    private boolean isChasing = false;
    private Random random = new Random();

    public Pass(Point2D.Float position, BufferedImage[][] spriteMap, Bomber target) {
        super(position, spriteMap[1][0]);
        this.collider.setRect(position.x + 3.0F, position.y + 16.0F + 3.0F, this.width - 6.0F, this.height - 16.0F - 6.0F);
        this.sprites = spriteMap;
        this.targetPosition = new Point2D.Float(position.x, position.y);
        this.target = target;
        direction = random.nextInt(4); // 0: arriba, 1: abajo, 2: izquierda, 3: derecha
        moveSpeed = RANDOM_MOVE_SPEED;
    }

    @Override
    public void update() {
        updateCollider();
        if (!this.dead) {
            if (!isChasing) {
                move();
                checkChaseCondition();
            } else {
                chaseTarget();
            }
            updateSprite();
        } else {
            updateDeadSprite();
        }
        interpolatePosition();
    }

    private void randomMovement() {
        // Cambia de dirección de vez en cuando para movimiento aleatorio
        if (random.nextInt(100) < 5) {
            direction = random.nextInt(4); // Cambiar la dirección
        }
        move();
    }

    private void checkChaseCondition() {
        double distanceToTarget = position.distance(target.getPosition());
        if (distanceToTarget < CHASE_DISTANCE_THRESHOLD) {
            isChasing = true;
            moveSpeed = CHASE_MOVE_SPEED;
        }
    }

    private void updateSprite() {
        spriteTimer++;
        if (spriteTimer >= 10) {
            spriteTimer = 0;
            spriteIndex = (spriteIndex + 1) % sprites[direction].length;
            this.sprite = sprites[direction][spriteIndex];
        }
    }

    private void updateDeadSprite() {
        spriteTimer++;
        if (spriteTimer >= 10) {
            spriteTimer = 0;
            spriteIndex++;
            if (spriteIndex >= sprites[3].length) {
                this.destroy();
            } else {
                this.sprite = sprites[3][spriteIndex];
            }
        }
    }

    private void interpolatePosition() {
        position.x = lerp(position.x, targetPosition.x, interpolationFactor);
        position.y = lerp(position.y, targetPosition.y, interpolationFactor);
    }

    private void chaseTarget() {
        Point2D bomberPosition = target.getPosition();
        float deltaX = (float) (bomberPosition.getX() - position.x);
        float deltaY = (float) (bomberPosition.getY() - position.y);

        double angle = Math.atan2(deltaY, deltaX);

        if (angle > -Math.PI / 4 && angle <= Math.PI / 4) {
            direction = 3; // Move right
        } else if (angle > Math.PI / 4 && angle <= 3 * Math.PI / 4) {
            direction = 1; // Move down
        } else if (angle > -3 * Math.PI / 4 && angle <= -Math.PI / 4) {
            direction = 0; // Move up
        } else {
            direction = 2; // Move left
        }

        float moveX = (float) (moveSpeed * Math.cos(angle));
        float moveY = (float) (moveSpeed * Math.sin(angle));

        targetPosition.setLocation(position.x + moveX, position.y + moveY);
    }

    @Override
    public void move() {
        switch (direction) {
            case 0:
                moveUp();
                break;
            case 1:
                moveDown();
                break;
            case 2:
                moveLeft();
                break;
            case 3:
                moveRight();
                break;
        }

        float deltaX = 0, deltaY = 0;
        switch (direction) {
            case 0:
                deltaY = -moveSpeed;
                break;
            case 1:
                deltaY = moveSpeed;
                break;
            case 2:
                deltaX = -moveSpeed;
                break;
            case 3:
                deltaX = moveSpeed;
                break;
        }

        targetPosition.setLocation(position.x + deltaX, position.y + deltaY);
    }


}
