package ru.kow.honoigame.screen.framework;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

import java.util.ArrayList;

/**
 * Основной класс игры
 */
abstract public class Game3D extends Game implements Disposable, Screen {

	// В этой версии игры мы поступим похоже на первую версию,
	// положим все объекты в одну стопку

	public static Array<DrawableObject> objects;

	
	ArrayList<Disposable> trashCan = new ArrayList<Disposable>();

	/**
	 * Камера. Мы можем направлять её на различные места в трёхмерном
	 * пространстве и смотреть на них.
	 */
	protected PerspectiveCamera cam;
	/**
	 * Загрузчик всяческих объектов.
	 */
	public AssetManager assets;
	/**
	 * Если представить рисование объектов как оркестр, то этот объект будет
	 * дирежером.
	 */
	ModelBatch batch;
	/**
	 * Окружающая среда. В ней мы будем настраивать освещение.
	 */
	protected Environment env;

	public Game3D() {}

	
	public void create()
	{

		assets = new AssetManager();
		batch = new ModelBatch();

		/* Настраиваем камеру */
		cam = new PerspectiveCamera();
		/* Ставим её на место - за экран */
		cam.position.set(-40, 0.0f, 0);
		/* И смотрим на нулевую координату */
		cam.lookAt(0, 0, 0);
		/*
		 * Приближаем её как можно ближе к экрану, и устанавливаем в размер
		 * окна.
		 */
		cam.up.set(0, 1, 0);
		cam.near = 0.1f;
		cam.far = 100f;
		cam.viewportWidth = Gdx.graphics.getWidth();
		cam.viewportHeight = Gdx.graphics.getHeight();
		/* Обновляем */
		cam.update();

		/* Настраиваем наше окружение*/

		env = new Environment();
		setEnvironment();
		
		// создаем оси
		createAxes();

		// Загружаем все ресурсы
		loadAssets();
		assets.finishLoading();
		objects = new Array<DrawableObject>();



		/* Добавляем в список для удаления всё, что не может удалится само */
		trashCan.add(batch);
		trashCan.add(assets);
	}

	abstract protected void loadAssets();
	abstract protected void setEnvironment();

	protected void loadModel(String filename) {
		assets.load(filename, Model.class);
	}

	protected Model getModel(String filename) {
		return assets.get(filename, Model.class);
	}

	ModelInstance axes;
	protected boolean drawAxes = false;

	private void createAxes() {

		Material xMat = new Material(ColorAttribute.createDiffuse(Color.YELLOW));
		Material yMat = new Material(ColorAttribute.createDiffuse(Color.RED));
		Material zMat = new Material(ColorAttribute.createDiffuse(Color.GREEN));

		ModelBuilder mb = new ModelBuilder();
		mb.begin();
		mb.node().id = "X";
		mb.part("X", GL20.GL_LINES, Usage.Position, xMat).line(-100, 0, 0, 100, 0, 0);
		mb.node().id = "Y";
		mb.part("Y", GL20.GL_LINES, Usage.Position, yMat).line(0, -100, 0, 0, 100, 0);
		mb.node().id = "Z";
		mb.part("Z", GL20.GL_LINES, Usage.Position, zMat).line(0, 0, -100, 0, 0, 100);
		Model axesModel = mb.end();

		axes = new ModelInstance(axesModel);
	}

	public void update(float dt) {

		/* Обновляем все объекты */
		for (int i = 0; i < objects.size; i++) {
			DrawableObject a = objects.get(i);
			if (a instanceof Updatable)
				((Updatable) a).update(dt);


		}

		// В обратном порядке, потому что происходят удаления
		for (int i = objects.size - 1; i >= 0; i--) {
			DrawableObject obj = objects.get(i);
			if (obj.isKilled()) objects.removeIndex(i);

			//Vector3 dir = obj.getCenter().sub(cam.position);
			//if (obj instanceof Despawnable && dir.z * cam.direction.z < 0 &&
			// Math.abs(dir.z) > cam.far / 2)

			//
		}



	}

	/**
	 * Вызывается при прорисовке
	 */

	public void draw() {

		/* Очищаем экран */
		Gdx.gl.glClear(GL20.GL_DEPTH_BUFFER_BIT | GL20.GL_COLOR_BUFFER_BIT);

		/* Сообщаем о том, что мы начали прорисовку */
		batch.begin(cam);

		// рисуем оси
		if (drawAxes)
			batch.render(axes);

		for (int i = 0; i < objects.size; i++) {
			objects.get(i).draw(batch, env);
		}


		if (Gdx.input.isTouched()){

			Gdx.input.getDeltaX();
			float ScreenWidth = Gdx.graphics.getWidth();
			float ScreenHeight = Gdx.graphics.getHeight();
// on a 1080p screen this would return ScreenWidth = 1080, ScreenHeight = 1920;
//now you get the screen co-ordinates and convert them to cam co-ordinates:
			float x1 = Gdx.input.getX();
			float y1 = Gdx.input.getY();
			Vector3 v = new Vector3(x1, y1, 0);
			cam.unproject(v);

			//    float x = getCenter();
			//   float y = bounds.getCenterY();
			//   float z = instance.bounds.getCenterZ();
			//    float x1cam = (x1/ScreenWidth)*cam.
			 // float y1cam = (y1/ScreenHeight)*CamHeight

			//objects.get(0).unproject(v);
			Gdx.app.log("my", String.format("[VP to Object] x: %.1f, y: %.1f", x1, y1));

			Gdx.app.log("my", String.format("[S to VP] x: %.1f, y: %.1f, z: %.1f", v.x, v.y,v.z));
			float x = cam.position.x-cam.viewportWidth+v.x;
			float y = cam.position.y-cam.viewportHeight+v.y;
			Gdx.app.log("my", String.format("[S to hh] x: %.1f, y: %.1f", x, y));


		}


		/* И сообщаем о том, что закончили рисовать */
		batch.end();
	}


	@Override
	public void dispose() {
		/* Удаляем все объекты, которые нужно удалять. */
		for (Disposable disposable : trashCan)
			disposable.dispose();

	}
	public void downloadCircle(){

	}
}
