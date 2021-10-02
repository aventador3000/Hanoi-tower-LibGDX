package ru.kow.honoigame.screen.framework;


import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;

public abstract class DrawableObject extends ModelInstance {
   protected boolean killed = false;
    public boolean oneraz=false;
    // реальные размеры в пространстве
    private BoundingBox bb = new BoundingBox();
    // размеры объекта
    private BoundingBox size = new BoundingBox();

    Vector3 tmpVec1 = new Vector3();
    Vector3 tmpVec2 = new Vector3();

    public DrawableObject(Model model) {
        super(model);
        calculateBoundingBox(size);
    }
    public BoundingBox calcBB() {
        // получаем положение и коэффициент растяжения объекта
        transform.getTranslation(tmpVec1);
        transform.getScale(tmpVec2);
        bb.set(size.min.cpy().scl(tmpVec2).add(tmpVec1), size.max.cpy().scl(tmpVec2).add(tmpVec1));
        return bb;
    }
    // возвращает расстояние от заданной точки до центра модели
    public Vector3 getCenter() {
        Vector3 pos = new Vector3();
        return transform.getTranslation(pos).cpy();
    }

    public boolean isKilled() {
        return killed;
    }

    public void die(DrawableObject killer) {
        killed = true;
    }
    public void draw(ModelBatch modelBatch, Environment env) {
        modelBatch.render(this, env);
    }

}
