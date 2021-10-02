package ru.kow.honoigame.screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import ru.kow.honoigame.screen.framework.DrawableObject;
import ru.kow.honoigame.screen.framework.Updatable;

import java.util.ArrayList;
public class Colona extends DrawableObject implements Updatable  {
    public  Boolean activColona;
    int rightchet = 0, leftnchet = 2, downchet = 1, upchet = 0;
    float z=0;
    boolean downclick=false;
    public Colona(Model model,Integer level) {
        super(model);
        transform.translate(0f, -9, 0f);
    }
    @Override
    public void update(float dt) {
        if(Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            if(!downclick) {
                if (rightchet < 2) {
                    z += 8f;
                    BaseColona.getBase().ColonaActive++;
                    System.out.println("" + z);
                    transform.translate(0, 0, +8f);
                    sleep();
                    rightchet++;
                    leftnchet--;
                } else {
                    BaseColona.getBase().ColonaActive=1;
                    z = 0;
                    System.out.println("" + z);
                    transform.translate(0, 0, -16f);
                    rightchet = 0;
                    leftnchet = 2;
                    sleep();
                }
            }
        }
    }
    public void sleep(){
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
