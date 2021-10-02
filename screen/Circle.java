package ru.kow.honoigame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;

import com.badlogic.gdx.utils.Array;
import ru.kow.honoigame.screen.framework.DrawableObject;
import ru.kow.honoigame.screen.framework.Game3D;
import ru.kow.honoigame.screen.framework.Updatable;

public class Circle extends DrawableObject implements Updatable,  GestureDetector.GestureListener {
   int rightchet = 0, leftnchet = 2, downchet = 1, upchet = 0;
   Integer columnNumber=1, circleNumber;
   boolean downclick=false,upclick=false;
   Colona colona;

    public void setRightchet(int rightchet) {
        this.rightchet = rightchet;
    }

    public void setLeftnchet(int leftnchet) {
        this.leftnchet = leftnchet;
    }

    public Circle(Model model, Integer circleNumber, Colona colona, float y, float z) {

        super(model);
        this.colona=colona;
        transform.translate(0f,y,z);
        this.circleNumber=circleNumber;

    }

    public void KeysUP(float i) {
                    upclick = true;
                    transform.translate(0, i, 0);
    }
    public void KeysDOWN(float i){
        transform.translate(0, i, 0);
        upclick=false;
    }


    public void KeysLeft(){
            if (upclick) {
                if (leftnchet < 2) {
                    transform.translate(0, 0, -8);
                    sleep();
                    leftnchet++;
                    rightchet--;
                } else {
                    transform.translate(0, 0, 16f);
                    rightchet = 2;
                    leftnchet = 0;
                    sleep();
                }
            }


    }
    public void KeysRight(){
            if (upclick ) {
                if (rightchet < 2) {
                    transform.translate(0, 0, 8f);
                    sleep();
                    rightchet++;
                    leftnchet--;
                } else {
                    transform.translate(0, 0, -16f);

                    rightchet = 0;
                    leftnchet = 2;
                    sleep();
                }
            }


    }
    @Override
    public void update(float dt) {

    }



    public void sleep(){
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }

    @Override
    public void pinchStop() {

    }


}
