package ru.kow.honoigame.screen.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import ru.kow.honoigame.screen.BaseColona;
import ru.kow.honoigame.screen.HonoiGame;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

public class MenuScreenHonoi implements Screen {
    final HonoiGame game;

    // Объявим все необходимые объекты
    private PlayStage stage;
    private TextButton play, exit,save;
    private Table table;
    private Label.LabelStyle labelStyle;
    private TextButton pravila;

    public MenuScreenHonoi(HonoiGame gam) {

        game = gam;
        stage = new PlayStage(new ScreenViewport());
        Skin skin = new Skin();
        TextureAtlas buttonAtlas = new TextureAtlas(Gdx.files.internal("images/game/images.pack"));
        skin.addRegions(buttonAtlas);

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = game.font;
        textButtonStyle.up = skin.getDrawable("button-up");
        textButtonStyle.down = skin.getDrawable("button-down");
        textButtonStyle.checked = skin.getDrawable("button-up");
        TextButton.TextButtonStyle textButtonStylepravila = new TextButton.TextButtonStyle();
        textButtonStylepravila.font=game.font;
        labelStyle = new Label.LabelStyle();
        labelStyle.font = game.font;

        table = new Table();
        table.setFillParent(true);
       // pravila= new TextButton("Цель этой игры заключается \n в перемещении колец с левой стороны \n на стержень с правой стороны.\n Ход игры состоит в том, чтобы перенести \n одно кольцо на другой стержень, \n с условием что меньшее кольцо нужно \n положить на большее ",textButtonStylepravila);
        // Кнопка играть. Добавляем новый listener, чтобы слушать события касания. После касания, выбрирует и переключает на экран выбора уровней, а этот экран уничтожается
        play = new TextButton("Продолжить", textButtonStyle);
        play.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.input.vibrate(20);
                return true;
            };
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new LevelScreen(game));
                dispose();
            };
        });
        save = new TextButton("Сохранить", textButtonStyle);
        save.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.input.vibrate(20);
                save();
                return true;
            };
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new LevelScreen(game));
                dispose();
            };
        });
        // Кнопка выхода. Вообще это не обязательно. Просто для красоты, ибо обычно пользователь жмет на кнопку телефона.
        exit = new TextButton("Выход", textButtonStyle);
        exit.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.input.vibrate(20);
                return true;
            };
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.exit();
                dispose();
            };
        });
        //table.add(pravila);
        //table.row();
        table.add(play);
        table.row();
        table.add(save);
        table.row();
        table.add(exit);
        stage.addActor(table);

        Gdx.input.setInputProcessor(stage);  // Устанавливаем нашу сцену основным процессором для ввода (нажатия, касания, клавиатура etc.)
        Gdx.input.setCatchBackKey(true); // Это нужно для того, чтобы пользователь возвращался назад, в случае нажатия на кнопку Назад на своем устройстве
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Рисуем сцену
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
        game.dispose();
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
