package ru.kow.honoigame.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import ru.kow.honoigame.screen.screen.MainMenuScreen;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

public class HonoiGame extends Game {
    public SpriteBatch batch;
    public BitmapFont font, levels;
    private static final String FONT_CHARACTERS = "абвгдеёжзийклмнопрстуфхцчшщъыьэюяАБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯabcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789][_!$%#@|\\/?-+=()*&.;,{}\"´`'<>";
    public boolean MainMenuActive=false;
    public boolean MainMenuActive2=false;
    public int a;
    Honoi honoi;
    //public static HonoiGame include;
    public HonoiGame(){}

    @Override
    public void create() {

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/russoone.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter param = new FreeTypeFontGenerator.FreeTypeFontParameter();
        param.size = Gdx.graphics.getHeight() / 18; // Размер шрифта.
        param.characters = FONT_CHARACTERS; // Наши символы
        font = generator.generateFont(param); // Генерируем шрифт
        param.size = Gdx.graphics.getHeight() / 20;
        levels = generator.generateFont(param);
        font.setColor(Color.WHITE); // Цвет белый
        levels.setColor(Color.WHITE);
        setScreen(new MainMenuScreen(this));
        generator.dispose(); // Уничтожаем наш генератор за ненадобностью.
    }

    @Override
    public void render() {
        super.render();



        }

    @Override
    public void dispose() {
        super.dispose();

        if (a == 2) {



            }
        else{
                a++;
            }
//        batch.dispose();
            //      font.dispose();
        }

    }

