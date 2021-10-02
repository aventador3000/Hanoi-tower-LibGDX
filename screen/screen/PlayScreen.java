package ru.kow.honoigame.screen.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import ru.kow.honoigame.screen.*;
import ru.kow.honoigame.screen.framework.Game3D;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.io.*;
import java.util.*;
import java.util.List;


public class PlayScreen extends Game3D implements Screen {
    SpriteBatch batch;
    boolean clickagain;
    //public GameScreen game;
    public HonoiGame game;
    Material img;
    Colona colona;

    ModelBatch modelBatch;
    ModelInstance cone;
    public boolean coxr=false;
    ModelInstance axes;
    Honoi honoi;
    Circle circle;
    Circle c;
    int level = 3;
    int levl;
    int a=0;
    Stage stage;
    Skin skin;
    String strLevel;
    public static PlayScreen instance;
    boolean pauza=false;

    //private final Vector2 mouseInWorld2D = new Vector2();
    public Vector3 mouseInWorld3D = new Vector3();
    public  Map<Integer,Circle> circles=new LinkedHashMap<>();

    public PlayScreen(Circle c) {
this.c=c;
            }

    public PlayScreen(HonoiGame game, String strLevel) {
        this.game = game;
        this.strLevel=strLevel;

        if(strLevel.equals("C")){

            try (InputStream input = new FileInputStream("application.properties")) {
                Properties prop = new Properties();
                // load a properties file
                prop.load(input);
                levl = Integer.parseInt(prop.getProperty("Level"));
                BaseColona.getBase().level = levl;
                levl += 2;
            }catch (IOException ex) {
                ex.printStackTrace();
            }

        }else {
            levl = Integer.parseInt(strLevel);
            BaseColona.getBase().level = levl;
            levl += 2;
        }

        create();
    }

    @Override
    public void create() {
        instance = this;
        super.create();
        BaseColona.getBase().colon1=new ArrayList<Integer>();
        BaseColona.getBase().colon2=new ArrayList<Integer>();
        BaseColona.getBase().colon3=new ArrayList<Integer>();




        //Floor floor=new Floor(getModel("models/floor.g3db"));
        //objects.add(floor);
        Table table=new Table(getModel("models/Wood_Table_74.g3db"));
        objects.add(table);
        //Table table2=new Table(getModel("models/table34.g3db"));
        //objects.add(table2);
        colona = new Colona(getModel("models/colonanew.g3db"),levl);

       /* float y=-9;
        for (int i = 1; i <=levl ; i++) {
            circle = new Circle(getModel("models/circlenew"+i+".g3db"),(Integer)(i-2),colona,y);
            circles.put(i,circle);
            objects.add(circle);
            y+=1.15f;
        }
        */
       if(strLevel.equals("C")){
            try (InputStream input = new FileInputStream("application.properties")) {
                Properties prop = new Properties();
                // load a properties file
                prop.load(input);
              //  levl=Integer.parseInt(prop.getProperty("Level"));
               // levl+=2;
                coxr=true;
                // get the property value and print it out

                // load a properties file
                //  prop.load(input);
                String gg=prop.getProperty("Colona1");
                String result = gg.replaceAll("\\[", "").replaceAll("\\]","").replaceAll("\\p{P}","").replaceAll(" ","");
                char[] array = result.toCharArray();
                float y=-9;
                for (int i = 0; i < array.length ; i++) {
                    BaseColona.getBase().colon1.add(Character.getNumericValue(array[i]));
                    circle = new Circle(getModel("models/circlenew"+Character.getNumericValue(array[i])+".g3db"),(Integer)Character.getNumericValue(array[i]),colona,y,0f);
                    circles.put(Character.getNumericValue(array[i]),circle);
                    objects.add(circle);
                    y+=1.15f;
                    System.out.println(BaseColona.getBase().colon1.get(i)+"");
                }



                String gg2=prop.getProperty("Colona2");
                String result2 = gg2.replaceAll("\\[", "").replaceAll("\\]","").replaceAll("\\p{P}","").replaceAll(" ","");
                char[] array2 = result2.toCharArray();
                y=-9;

                for (int i = 0; i < array2.length ; i++) {
                    BaseColona.getBase().colon2.add(Character.getNumericValue(array2[i]));
                    circle = new Circle(getModel("models/circlenew"+Character.getNumericValue(array2[i])+".g3db"),(Integer)Character.getNumericValue(array2[i]),colona,y,8f);
                    circles.put(Character.getNumericValue(array2[i]),circle);
                    circle.setLeftnchet(1);
                    circle.setRightchet(1);
                    objects.add(circle);
                    y+=1.15f;
                    System.out.println(BaseColona.getBase().colon2.get(i)+"");
                }

                String gg3=prop.getProperty("Colona3");
                String result3 = gg3.replaceAll("\\[", "").replaceAll("\\]","").replaceAll("\\p{P}","").replaceAll(" ","");
                y=-9;
                char[] array3 = result3.toCharArray();

                for (int i = 0; i < array3.length ; i++) {
                    BaseColona.getBase().colon3.add(Character.getNumericValue(array3[i]));
                    circle = new Circle(getModel("models/circlenew"+Character.getNumericValue(array3[i])+".g3db"),(Integer)Character.getNumericValue(array3[i]),colona,y,16f);
                    circles.put(Character.getNumericValue(array3[i]),circle);
                    circle.setLeftnchet(0);
                    circle.setRightchet(2);
                    objects.add(circle);
                    y+=1.15f;
                    System.out.println(BaseColona.getBase().colon3.get(i)+"");
                }
                // get the property value and print it out


            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        else {
          //  levl = Integer.parseInt(strLevel);
           // BaseColona.getBase().level=levl;
         //   levl += 2;
            coxr=false;
            float y=-9;
            for (int i = 1; i <= levl; i++) {
                BaseColona.getBase().colon1.add(i);
                circle = new Circle(getModel("models/circlenew"+i+".g3db"),(Integer)(i-2),colona,y,0f);
                circles.put(i,circle);
                objects.add(circle);
                y+=1.15f;
            }

        }

        honoi = new Honoi(getModel("models/honoibasegg.g3db"),colona,levl,circles,coxr);
        objects.add(honoi);
        objects.add(colona);

        stage = new Stage();
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        Gdx.input.setInputProcessor(new CameraInputController(cam));
    }

    public void gameError() {
        if(!pauza) {
            pauza=true;
            Dialog diag = new Dialog("", skin, "dialog") {
                public void result(Object obj) {
                    if ((Boolean) obj == true) {
                        pauza=false;
                        remove();
                        Gdx.input.setInputProcessor(new CameraInputController(cam));
                    } else {
                        pauza=false;
                        create();
                    }

                }
            };

            //Cкин не поддерживает русских букв
            diag.text("Error");
            diag.button("continue", true);
            diag.button("start again", false);
            diag.key(Input.Keys.ENTER, true);
            diag.show(stage);
            //переключаем управление на диалоговое окно
            Gdx.input.setInputProcessor(stage);
        }
    }





    @Override
    protected void loadAssets() {
        loadModel("models/honoibasegg.g3db");
        loadModel("models/colonanew.g3db");
        loadModel("models/Wood_Table_74.g3db");
        //loadModel("models/floor.g3db");
        //loadModel("models/table34.g3db");

        for (int i = 1; i <=levl ; i++) {
            add(i);
        }


    }
        void cameraUpdate () {

            cam.update();
        }

        public void update ( float dt){
            super.update(dt);

            cameraUpdate();


        }



        @Override
        protected void setEnvironment () {
            env.set(new ColorAttribute(ColorAttribute.Fog, Color.BLACK));
            env.add(new DirectionalLight().set(Color.WHITE, 0, 0, -1));
            env.add(new DirectionalLight().set(Color.WHITE, 0, 0, 1));
            env.add(new DirectionalLight().set(Color.WHITE, 0, -1, 1));
            env.add(new DirectionalLight().set(Color.WHITE, 0, 1, 1));

        }

        @Override
        public void show () {

        }

        @Override
        public void render ( float delta){
           // Gdx.gl.glClearColor(0xdc/255.0f, 0xdc/255.0f, 0xdc/255.0f, 0xff/255.0f);

            Gdx.gl.glClearColor(0.3f, 0.3f, 0.3f, 1.f);
            Gdx.gl.glClear(GL20.GL_DEPTH_BUFFER_BIT | GL20.GL_COLOR_BUFFER_BIT);


            draw();
            stage.draw();

            // Сколько времени прошло с последней отрисовки
            float dt = Gdx.graphics.getDeltaTime();
            update(dt);
        }


        @Override
        public void resize ( int width, int height){

        }


        @Override
        public void render () {

        }

        @Override
        public void pause () {

        }

        @Override
        public void resume () {

        }

        @Override
        public void hide () {

        }

        @Override
        public void dispose () {
       // System.out.println("Приложение закрываеться!!!!");
        }
        public void add(int i){

                loadModel("models/circlenew" + i + ".g3db");


        }

    public void win(){
        if(!pauza) {
            Dialog diag = new Dialog("", skin, "dialog") {
                public void result(Object obj) {
                    if ((Boolean) obj == true) {
                        pauza=false;
                        create();
                        remove();
                    } else {
                        pauza=false;
                        game.a -= 1;
                        game.setScreen(new LevelScreen(game));
                    }

                }
            };

            //Cкин не поддерживает русских букв
            diag.text("You Win!");
            diag.button("Start again", true);
            diag.button("Go to levels", false);
            diag.key(Input.Keys.ENTER, true);
            diag.show(stage);
            //переключаем управление на диалоговое окно
            Gdx.input.setInputProcessor(stage);
        }
    }

    public void saving(){
        //if(!clickagain) {
            //clickagain=true;
            if (!pauza) {
                pauza=true;
                Dialog diag = new Dialog("", skin, "dialog") {
                    public void result(Object i) {
                        if ((int) i == 1) {
                            clickagain=false;
                            pauza = false;
                            remove();
                        } else if ((int) i == 2) {
                            clickagain=false;
                            save();
                            pauza = false;
                            remove();
                        } else {
                            Gdx.app.exit();
                        }
                    }
                };

                //Cкин не поддерживает русских букв
                diag.text("Menu");
                diag.button("next", 1);
                diag.button("save", 2);
                diag.button("exit", 3);
                diag.key(Input.Keys.ENTER, 1);
                diag.show(stage);
                //переключаем управление на диалоговое окно
                Gdx.input.setInputProcessor(stage);

        }

        //game.setScreen(new MenuScreenHonoi(game));
    }

    public void save(){
        System.out.println("Приложение закрываеться!!!!");
        System.out.println("level " + BaseColona.getBase().level);

        try {
            Properties props = new Properties();
            String[] colon1=new String[BaseColona.getBase().colon1.size()];
            String[] colon2=new String[BaseColona.getBase().colon2.size()];
            String[] colon3=new String[BaseColona.getBase().colon3.size()];

            for (int i = 0; i <BaseColona.getBase().colon1.size(); i++) {
                colon1[i]=BaseColona.getBase().colon1.get(i)+"";
            }
            for (int i = 0; i <BaseColona.getBase().colon2.size(); i++) {
                colon2[i]=BaseColona.getBase().colon2.get(i)+"";
            }
            for (int i = 0; i <BaseColona.getBase().colon3.size(); i++) {
                colon3[i]=BaseColona.getBase().colon3.get(i)+"";
            }
            props.put("Colona1", Arrays.toString(colon1));
            props.put("Colona2", Arrays.toString(colon2));
            props.put("Colona3", Arrays.toString(colon3));
            props.put("Colona_Active", "" + BaseColona.getBase().ColonaActive);
            props.put("Level", "" + BaseColona.getBase().level);
            System.out.println("" + BaseColona.getBase().ColonaActive);
            String path = "application.properties";
            FileOutputStream outputStrem = null;

            outputStrem = new FileOutputStream(path);

            props.store(outputStrem, "This is a sample properties file");
        } catch(FileNotFoundException e){
            e.printStackTrace();
        } catch(IOException e){
            e.printStackTrace();
        }

    }
}





