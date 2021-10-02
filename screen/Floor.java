package ru.kow.honoigame.screen;

import com.badlogic.gdx.graphics.g3d.Model;
import ru.kow.honoigame.screen.framework.DrawableObject;
import ru.kow.honoigame.screen.framework.Updatable;

public class Floor extends DrawableObject implements Updatable {
    public Floor(Model model) {
        super(model);
        //transform.translate(0,-9f,0);
    }

    @Override
    public void update(float dt) {

    }
}
