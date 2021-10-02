package ru.kow.honoigame.screen;

import com.badlogic.gdx.graphics.g3d.Model;
import ru.kow.honoigame.screen.framework.DrawableObject;
import ru.kow.honoigame.screen.framework.Updatable;

public class Table extends DrawableObject implements Updatable {
    public Table(Model model) {
        super(model);
        transform.translate(0,-33.1f,0);
        transform.scl(0.5f,0.5f,0.5f);
    }

    @Override
    public void update(float dt) {

    }
}
