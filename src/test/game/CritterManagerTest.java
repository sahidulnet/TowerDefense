package test.game;

import common.SpriteType;
import common.Tile;
import common.core.Vector2;
import game.Critter;
import game.CritterManager;
import game.CritterType;
import game.pathlogic.PathFinder;
import game.towerlogic.Tower;
import game.towerlogic.TowerType;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import test.JavaFXThreadingRule;
import common.core.Rect;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by saddamtahir on 2016-03-17.
 */
public class CritterManagerTest {

    @Rule
    public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();
    CritterManager leCritterManager;
    private Tile[][] leTiles = new Tile[6][6];
    private PathFinder lePath;
    private ArrayList<Critter> leCritters;
    private Tower leTower;

    @Before
    public void BeforeTestingSetup()
    {
        leTiles[0][0] = new Tile(SpriteType.ENTRY_POINT,32,32,new Vector2(0,0));
        leTiles[0][1] = new Tile(SpriteType.PATH,32,32,new Vector2(1,0));
        leTiles[0][2] = new Tile(SpriteType.PATH,32,32,new Vector2(2,0));
        leTiles[0][3] = new Tile(SpriteType.PATH, 32,32,new Vector2(3,0));
        leTiles[0][4] = new Tile(SpriteType.PATH, 32,32,new Vector2(3,0));
        leTiles[0][5] = new Tile(SpriteType.EXIT_POINT, 32, 32, new Vector2(4,0));

        lePath = new PathFinder(leTiles, 6, 6);
        leCritterManager = new CritterManager(lePath);
        leCritters = new ArrayList<>();
        leCritters.add(new Critter(new Vector2(0,0), CritterType.AIR));
        leCritters.add(new Critter(new Vector2(1,0), CritterType.AIR));
        leCritterManager.setCritterList(leCritters);

        leTower = new Tower(TowerType.ARROW, new Vector2(1,2));
    }

    @Test
    public void getShootableCrittersTest()
    {
        ArrayList<Critter> returnList = leCritterManager.getShootableCritters(leTower);
        Assert.assertTrue(returnList.size() > 0);
    }

    @Test
    public void getCrittersNeighboursTest()
    {
        ArrayList<Critter> returnList = leCritterManager.getCritterNeighbours(leTower,leCritters, new Critter(new Vector2(3, 3), CritterType.GROUND));
        assertEquals(2,returnList.size());
    }

    @Test
    public void getSplashRectangleWidthTest()
    {
        Vector2 splashVector = new Vector2(2, 2);
        Rect splashArea = leCritterManager.getSplashRectangle(96, splashVector);
        assertEquals(192,splashArea.getWidth(),0.001);
    }

    @Test
    public void getSplashRectangleHeightTest()
    {
        Vector2 splashVector = new Vector2(3, 2);
        Rect splashArea = leCritterManager.getSplashRectangle(96, splashVector);
        assertEquals(192,splashArea.getHeight(),0.001);
    }

    @Test
    public void getNextTileTest()
    {
        Vector2 critterVector = new Vector2(3, 2);
        Vector2 tilePosition = new Vector2(1.0,1.0);
        Tile leTile = new Tile(SpriteType.SCENERY,2.0,2.0,tilePosition);
        Critter critter = new Critter(critterVector,CritterType.GROUND);
        critter.setNextPathTile(leTile);
        Tile tile = leCritterManager.getNextTile(critter);
        assertEquals(SpriteType.ENTRY_POINT,tile.getType());
    }

    @Test
    public void isReachedToExitPointTest()
    {
        Vector2 critterVector = new Vector2(3, 2);
        Critter critter = new Critter(critterVector,CritterType.GROUND);
        Boolean result = leCritterManager.isReachedToExitPoint(critter);
        assertTrue(result);

    }
}
