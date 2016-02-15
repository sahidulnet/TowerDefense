package game;

import common.*;
import common.core.GameLoop;
import common.core.MouseHandler;
import common.core.MouseState;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;

import java.util.Observable;
import java.util.Observer;

public class GameManager extends GameLoop implements Observer {
    public TileManager tileManager;
    public ScoreManager scoreManager;
    public MouseHandler mouseHandler;
    public SideBar sideBar;

    private GraphicsContext gc;
    private double width;
    private double height;

    /**
     * Main constructor for GameManager
     * @param gc The {@link javafx.scene.canvas.GraphicsContext} to use. All graphics will be placed here
     * @param scene The scene to use, mainly for the mouse
     * */
    public GameManager(GraphicsContext gc, Scene scene) {
        this.gc = gc;
        this.width = gc.getCanvas().getWidth();
        this.height = gc.getCanvas().getHeight();

        mouseHandler = new MouseHandler(scene);
        mouseHandler.addObserver(this);

        tileManager = new TileManager();
        tileManager.createScenery(12, 12);

        scoreManager = new ScoreManager(tileManager.getWidth(), 0);

        sideBar = new SideBar(gc.getCanvas().getWidth() - tileManager.getWidth(),
                gc.getCanvas().getHeight(),
                tileManager.getWidth(),
                0);
    }

    @Override
    protected void update(double delta) {
        scoreManager.setHealth(scoreManager.getHealth() + 1);
        scoreManager.setMoney(scoreManager.getMoney() + 1);
        scoreManager.updateMousePosition(mouseHandler.getMouseState());
    }

    @Override
    protected void clear() {
        mouseHandler.clearMouseState();
        gc.clearRect(0, 0, width, height);
    }

    @Override
    protected void draw() {
        tileManager.draw(gc);
        sideBar.draw(gc);
        //scoreManager.draw(gc);
    }

    /**
     * Method implemented from {@link java.util.Observer} to get notifications from {@link common.core.MouseHandler}
     *
     * @param o in this case, MouseHandler
     * @param arg arguments that the observable sends.
     * */
    @Override
    public void update(Observable o, Object arg) {
        MouseHandler sender = (MouseHandler)o;
        MouseState mouseState =  sender.getMouseState();

        sideBar.update(mouseState);

    }
}
