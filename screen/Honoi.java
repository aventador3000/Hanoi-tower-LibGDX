package ru.kow.honoigame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector3;
import ru.kow.honoigame.screen.framework.DrawableObject;
import ru.kow.honoigame.screen.framework.Updatable;

import ru.kow.honoigame.screen.screen.PlayScreen;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;


public  class Honoi extends DrawableObject implements Updatable {
    Sound sound=Gdx.audio.newSound(Gdx.files.internal("sounds/win.mp3"));;
    private final Vector3 mouseInWorld3D = new Vector3();
    boolean ifn;
    public static Honoi base;

    public AssetManager assetManager;
    static Colona colona;
    public Map<Integer, Circle> circles = new LinkedHashMap<>();
    Integer tmpCircl, tmpColona;
    int level;
    public long id; // воспроизводит новый звук и сохраняет идентификатор для дальнейших изменений

    public Honoi(Model model, Colona colona, Integer level, Map circles,boolean coxr) {
        super(model);
        assetManager = new AssetManager();
        assetManager.load("sounds/win.mp3", Sound.class);
        assetManager.load("sounds/padenie-dosok.mp3", Sound.class);


        assetManager.finishLoading(); //Important!
        this.colona = colona;
        this.circles = circles;
        this.level = level;
      /*  if(coxr){try (
            InputStream input = new FileInputStream("application.properties")) {

            Properties prop = new Properties();




        } catch (IOException ex) {
            ex.printStackTrace();
        }}
        else {

        }*/
        transform.translate(0f, -9, 0);


    }
    public void soundstart(){
    if (assetManager.isLoaded("sounds/padenie-dosok.mp3")){
        sound = assetManager.get("sounds/padenie-dosok.mp3", Sound.class);
        sound.play();
    }


}

    public void soundstop() {
        if (assetManager.isLoaded("sounds/win.mp3")) {
            sound = assetManager.get("sounds/win.mp3", Sound.class);
            sound.play();
        }
    }

    @Override
    public void update(float dt) {
        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
            PlayScreen.instance.saving();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (!colona.downclick && Gdx.input.isKeyPressed(Input.Keys.UP)) {
            // PlayScreen.instance.gameOver();
            colona.downclick = true;
            if (!BaseColona.getBase().colon1.isEmpty() &&colona.z == 0f) {
                tmpCircl = BaseColona.getBase().colon1.get(BaseColona.getBase().colon1.size() - 1);
                tmpColona = 1;
                circles.get(BaseColona.getBase().colon1.get(BaseColona.getBase().colon1.size() - 1)).KeysUP((9 - BaseColona.getBase().colon1.size()) * 1.15f + 4f);

            } else if (!BaseColona.getBase().colon2.isEmpty() &&colona.z == 8f) {
                tmpCircl = BaseColona.getBase().colon2.get(BaseColona.getBase().colon2.size() - 1);
                tmpColona = 2;
                circles.get(BaseColona.getBase().colon2.get(BaseColona.getBase().colon2.size() - 1)).KeysUP((9 - BaseColona.getBase().colon2.size()) * 1.15f + 4f);

            } else if (!BaseColona.getBase().colon3.isEmpty() &&colona.z == 16f) {
                tmpCircl = BaseColona.getBase().colon3.get(BaseColona.getBase().colon3.size() - 1);
                tmpColona = 3;
                circles.get(BaseColona.getBase().colon3.get(BaseColona.getBase().colon3.size() - 1)).KeysUP((9 - BaseColona.getBase().colon3.size()) * 1.15f + 4f);
            }else {
                colona.downclick = false;
            }
        }


        if (colona.downclick && Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            colona.downclick = false;
            Vector3 pos = new Vector3();
            pos = circles.get(tmpCircl).getCenter();
            if (tmpColona == 1) {
                BaseColona.getBase().colon1.remove(BaseColona.getBase().colon1.size() - 1);
            } else if (tmpColona == 2) {
                BaseColona.getBase().colon2.remove(BaseColona.getBase().colon2.size() - 1);
            } else if (tmpColona == 3) {
                BaseColona.getBase().colon3.remove(BaseColona.getBase().colon3.size() - 1);
            }
            Gdx.app.log("colona1", BaseColona.getBase().colon1.toString());
            Gdx.app.log("colona2", BaseColona.getBase().colon2.toString());
            Gdx.app.log("colona3", BaseColona.getBase().colon3.toString());
            if (pos.z == 0f) {
                if (BaseColona.getBase().colon1.isEmpty() || BaseColona.getBase().colon1.get(BaseColona.getBase().colon1.size() - 1) < tmpCircl) {
                    circles.get(tmpCircl).KeysDOWN((-8 + BaseColona.getBase().colon1.size()) * 1.15f - 4f);
                    BaseColona.getBase().colon1.add(tmpCircl);
                    soundstart();
                    Winproverka();

                } else {
                    //sound.play(1000000f);
                    soundstart();
                    PlayScreen.instance.gameError();
                    colona.downclick = true;
                    ErrorAddInColona();
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } else if (pos.z == 8f) {
                if (BaseColona.getBase().colon2.isEmpty() || BaseColona.getBase().colon2.get(BaseColona.getBase().colon2.size() - 1) < tmpCircl) {
                    //sound.play(10000f);
                    soundstart();
                    circles.get(tmpCircl).KeysDOWN((-8 + BaseColona.getBase().colon2.size()) * 1.15f - 4f);
                    BaseColona.getBase().colon2.add(tmpCircl);
                    Winproverka();
                } else {
                    //sound.play(100000f);
                    soundstart();
                    PlayScreen.instance.gameError();
                    colona.downclick = true;
                    ErrorAddInColona();
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } else if (pos.z == 16f) {
                if (BaseColona.getBase().colon3.isEmpty() || BaseColona.getBase().colon3.get(BaseColona.getBase().colon3.size() - 1) < tmpCircl) {
                    //sound.play();
                    soundstart();
                    circles.get(tmpCircl).KeysDOWN((-8 + BaseColona.getBase().colon3.size()) * 1.15f - 4f);
                    BaseColona.getBase().colon3.add(tmpCircl);
                    Winproverka();
                } else {
                    //sound.play();
                    soundstart();
                    PlayScreen.instance.gameError();
                    colona.downclick = true;
                    ErrorAddInColona();
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }


        }


        if (colona.downclick && (Gdx.input.isKeyPressed(Input.Keys.RIGHT))) {


            if (tmpColona == 1) {
                circles.get(BaseColona.getBase().colon1.get(BaseColona.getBase().colon1.size() - 1)).KeysRight();

            } else if (tmpColona == 2) {
                circles.get(BaseColona.getBase().colon2.get(BaseColona.getBase().colon2.size() - 1)).KeysRight();

            } else if (tmpColona == 3) {
                circles.get(BaseColona.getBase().colon3.get(BaseColona.getBase().colon3.size() - 1)).KeysRight();
            }


        }

        if (colona.downclick && Gdx.input.isKeyPressed(Input.Keys.LEFT)) {

            if (tmpColona == 1) {
                circles.get(BaseColona.getBase().colon1.get(BaseColona.getBase().colon1.size() - 1)).KeysLeft();

            } else if (tmpColona == 2) {
                circles.get(BaseColona.getBase().colon2.get(BaseColona.getBase().colon2.size() - 1)).KeysLeft();


            } else if (tmpColona == 3) {
                circles.get(BaseColona.getBase().colon3.get(BaseColona.getBase().colon3.size() - 1)).KeysLeft();
            }

        }


    }

    public void ErrorAddInColona() {
        if (tmpColona == 1) {
            BaseColona.getBase().colon1.add(tmpCircl);
        } else if (tmpColona == 2) {
            BaseColona.getBase().colon2.add(tmpCircl);
        } else if (tmpColona == 3) {
            BaseColona.getBase().colon3.add(tmpCircl);
        }
    }

    public void Winproverka() {

            if(BaseColona.getBase().colon1.isEmpty()&&BaseColona.getBase().colon2.isEmpty()){
                PlayScreen.instance.win();
                soundstop();
            }
            if(BaseColona.getBase().colon1.isEmpty()&&BaseColona.getBase().colon3.isEmpty()){
                PlayScreen.instance.win();
                soundstop();

            }
    }

}








