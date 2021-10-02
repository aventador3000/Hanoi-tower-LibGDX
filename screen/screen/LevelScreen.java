package ru.kow.honoigame.screen.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import ru.kow.honoigame.screen.*;
import  ru.kow.honoigame.screen.screen.PlayStage.OnHardKeyListener;


public class LevelScreen implements Screen {
    final HonoiGame game;
    private PlayStage stage;
    private Table table;
    public Label.LabelStyle labelStyle;
    private TextButton level;


    private Array<String> levels;

    public LevelScreen(HonoiGame gam) {
        game=gam;

        stage = new PlayStage(new ScreenViewport());

        Skin skin = new Skin();
        TextureAtlas buttonAtlas = new TextureAtlas(Gdx.files.internal("images/game/images.pack"));
        skin.addRegions(buttonAtlas);
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = game.levels;
        textButtonStyle.up = skin.getDrawable("level-up");
        textButtonStyle.down = skin.getDrawable("level-down");
        textButtonStyle.checked = skin.getDrawable("level-up");

        //Парсим наши уровни
        XMLparse parseLevels = new XMLparse();
        levels = parseLevels.XMLparseLevels();

     //   labelStyle.font= game.levels; // Берем размер шрифта из класса MyGame
        table = new Table();
        table.row().pad(20); // Новая строка + отступы
        table.center();
        table.setFillParent(true);

        for (int i = 0; i < levels.size; i++) {
            final String cur_level= levels.get(i);

            level = new TextButton(cur_level, textButtonStyle);

            level.addListener(new ClickListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    Gdx.input.vibrate(20);
                    return true;
                };
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

                        game.setScreen(new PlayScreen(game, cur_level)); // Передаем выбранный уровень в PlayScreen
                        dispose();

                    };
            });
            table.add(level);

            // А эта жесть для того, чтобы переходить на новую строку при достижении количества в пять уровней в одной строке

            float indexLevel = Float.parseFloat(String.valueOf(i)) + 1;
            if (indexLevel % 5.0f == 0) table.row().padLeft(20).padRight(20).padBottom(20);
        }

        final String cur_level_8="C";
        // if(i==levels.size){cur_level="П";}
        level = new TextButton(cur_level_8, textButtonStyle);
        //final String finalCur_level = cur_level;

        level.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.input.vibrate(20);
                return true;
            };
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

                game.setScreen(new PlayScreen(game, cur_level_8)); // Передаем выбранный уровень в PlayScreen
                    dispose();


            };
        });
        table.add(level);
        stage.addActor(table); // Добавляем нашу таблицу с уровнями на сцену

        Gdx.input.setInputProcessor(stage);
        Gdx.input.setCatchBackKey(true);
        // Это случится, когда пользователь нажмет на кнопку Назад на своем устройстве. Мы переведем его на прошлый экран.
        stage.setHardKeyListener(new OnHardKeyListener() {
            @Override
            public void onHardKey(int keyCode, int state) {
                if (keyCode == Input.Keys.BACK && state == 1){
                    game.setScreen(new MainMenuScreen(game));
                }
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void show() {}

    @Override
    public void hide() {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void dispose() {
        stage.dispose();
        game.dispose();
    }


}



