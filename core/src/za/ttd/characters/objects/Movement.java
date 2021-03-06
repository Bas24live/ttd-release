package za.ttd.characters.objects;

import za.ttd.characters.Actor;
import za.ttd.mapgen.Map;

public class Movement {

    protected Position position;
    private float speed;
    private Map map;
    private boolean moving;
    private Direction nextDirection;

    public Movement(Map map) {
        this.map = map;
        moving = false;
    }

    public Direction move(Actor actor, Direction nextDirection) {
        this.position = actor.getPosition();
        this.speed = actor.getMovementSpeed();
        this.nextDirection = nextDirection;
        Direction curDirection = actor.getDirection();

        switch (nextDirection) {
            case UP:
                if(tryMoveUp(curDirection))
                    curDirection = nextDirection;
                break;
            case DOWN:
                if(tryMoveDown(curDirection))
                    curDirection = nextDirection;
                break;
            case LEFT:
                if(tryMoveLeft(curDirection))
                    curDirection = nextDirection;
                break;
            case RIGHT:
                if(tryMoveRight(curDirection))
                    curDirection = nextDirection;
                break;
            default:
                break;
        }

        switch (curDirection) {
            case UP:
                moveUp();
                break;
            case DOWN:
                moveDown();
                break;
            case LEFT:
                moveLeft();
                break;
            case RIGHT:
                moveRight();
                break;
            default:
                break;
        }

        if(moving)
            return curDirection;
        else
            return Direction.NONE;
    }

    private void moveUp() {
        int y = position.getChangedFloorY(-speed);
        int x = position.getIntX();

        if (tryMove(x, y)) {
            position.increaseY(-speed);
            position.setX(position.getIntX());
            moving = true;
        }
        else {
            position.increaseY(position.getIntY() - position.getY());
            moving = false;
        }
    }

    private void moveDown() {
        int y = position.getChangedCeilY(speed);
        int x = position.getIntX();

        if (tryMove(x, y)) {
            position.increaseY(speed);
            position.setX(position.getIntX());
            moving = true;
        }
        else {
            position.increaseY(position.getIntY() - position.getY());
            moving = false;
        }
    }

    private void moveLeft() {
        int y = position.getIntY();
        int x = position.getChangedFloorX(-speed);

        if (tryMove(x, y)) {
            position.increaseX(-speed);
            position.setY(position.getIntY());
            moving = true;
        }
        else {
            position.increaseX(position.getIntX() - position.getX());
            moving = false;
        }
    }

    private void moveRight() {
        int y = position.getIntY();
        int x = position.getChangedCeilX(speed);

        if (tryMove(x, y)) {
            position.increaseX(speed);
            position.setY(position.getIntY());
            moving = true;
        }
        else {
            position.increaseX(position.getIntX() - position.getX());
            moving = false;
        }
    }


    private boolean tryMoveUp(Direction dir) {
        int y = position.getChangedFloorY(-speed);
        float x = position.getIntX() - position.getX();

        if (x < 0)
            x*= -1;

        if (dir == Direction.LEFT)
            x = Math.round(position.getX() + x);
        else
            x = Math.round(position.getX() - x);

        return tryMove((int)x,y);
    }

    private boolean tryMoveDown(Direction dir) {
        int y = position.getChangedCeilY(speed);
        float x = position.getIntX() - position.getX();

        if (x < 0)
            x*= -1;

        if (dir == Direction.LEFT)
            x = Math.round(position.getX() + x);
        else
            x = Math.round(position.getX() - x);

        return tryMove((int)x,y);
    }

    private boolean tryMoveLeft(Direction dir) {
        float y = position.getIntY() - position.getY();
        int x = position.getChangedFloorX(-speed);

        if (y < 0)
            y *= -1;

        if (dir == Direction.UP)
            y = Math.round(position.getY() + y);
        else
            y = Math.round(position.getY() - y);


        return tryMove(x,(int)y);
    }

    private boolean tryMoveRight(Direction dir) {
        float y = position.getIntY() - position.getY();
        int x = position.getChangedCeilX(speed);

        if (y < 0)
            y *= -1;


        if (dir == Direction.UP)
            y = Math.round(position.getY() + y);
        else
            y = Math.round(position.getY() - y);


        return tryMove(x,(int)y);
    }

    private boolean tryMove(int x, int y) {
        if (nextDirection == Direction.NONE)
            return !map.isType(x, y, Map.WALL);
        else
            return !map.isType(x, y, Map.WALL) && !map.isType(x, y, Map.DOOR);
    }


}
