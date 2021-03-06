package game;

import common.Settings;
import common.core.Vector2;
import game.towerlogic.Tower;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Represents the inspection panel that shows the stats of the tower and
 * the sell button is the tower is sellable
 * @version $revision $
 */
public class InspectionPanel {

    private final double width;
    private final double height;
    private final double leftOffset;
    private final double topOffset;
    private Tower selectedTower;
    private Button sellButton;
    private Button upgradeButton;
    private Button leftArrowButton;
    private Button rightArrowButton;
    private Queue<String> logQueue;

    private static final Logger LOGGER = Logger.getLogger(InspectionPanel.class);

    /**
     * Default constructor
     *
     * @param width      width of the inspection panel
     * @param height     height of the inspection panel
     * @param leftOffset left offset of the inspection panel
     * @param topOffset  top offset of the inspection panel
     */
    public InspectionPanel(double width, double height, double leftOffset, double topOffset) {
        this.height = height;
        this.leftOffset = leftOffset;
        this.topOffset = topOffset;
        this.width = width;
        Vector2 sellPosition = new Vector2(leftOffset, topOffset);
        Vector2 upgradePosition = new Vector2(leftOffset, topOffset);
        sellButton = new Button(ButtonType.SELL, sellPosition);
        upgradeButton = new Button(ButtonType.UPGRADE, upgradePosition);
        leftArrowButton = new Button(ButtonType.LEFTARROW, new Vector2());
        rightArrowButton = new Button(ButtonType.RIGHTARROW, new Vector2());
        logQueue = new LinkedList<>();
    }

    /**
     * Gets the selected tower
     *
     * @return current selected tower
     */
    public Tower getSelectedTower() {
        return selectedTower;
    }

    /**
     * Sets the selected tower
     *
     * @param selectedTower new selected tower
     */
    public void setSelectedTower(Tower selectedTower) {
        if (selectedTower != this.selectedTower) {
            String nullStr = "null";
            LOGGER.info("Selected tower changed from "
                    + (this.selectedTower == null ? nullStr : this.selectedTower.getUniqueId())
                    + " to "
                    + (selectedTower == null ? nullStr : selectedTower.getUniqueId()));
            this.selectedTower = selectedTower;
            drawTowerLogText(selectedTower);
        }
    }

    /**
     * Draws the logging appearing in the inspection panel
      * @param selectedTower tower to refresh console with
     */
    public void drawTowerLogText(Tower selectedTower) {
        if (selectedTower != null) {
            Queue<String> localLogs = selectedTower.getLocalLogs();
            clearLog();
            for (int i = 0; i < localLogs.size(); i++) {
                String current = localLogs.remove();
                addLog(current);
                localLogs.add(current);
            }
        }
    }

    /**
     * Draws the inspection panel
     *
     * @param gc target to draw on
     */
    public void draw(GraphicsContext gc) {
        if (getSelectedTower() != null) {
            Font titleFont = Font.font(Settings.FONT_NAME, FontWeight.BOLD, 20);
            Font parametersFont = Font.font(Settings.FONT_NAME, FontWeight.NORMAL, 16);
            Font logFont = Font.font(Settings.FONT_NAME, FontWeight.NORMAL, 10);

            float titleSeparation = 24;
            float linesSeparation = 17;
            float logSeparation = 11;

            gc.setFont(titleFont);
            String towerTitle = getSelectedTower().getTowerType().name() + " TOWER";
            Vector2 titlePosition = new Vector2(leftOffset,
                    topOffset);
            drawText(gc, towerTitle, titlePosition, Color.BLACK);

            //Setup stat lines
            List<String> statLines = new ArrayList<String>();
            gc.setFont(parametersFont);
            statLines.add("Level: " + getSelectedTower().getLevel());
            statLines.add("Rate of Fire: " + getSelectedTower().getRateOfFire());
            statLines.add("Range: " + getSelectedTower().getRange());
            statLines.add("Damage: " + getSelectedTower().getDamage());
            statLines.add("Special Effect: " + getSelectedTower().getAttackEffect().toString());
            if (!selectedTower.isActive()) {
                //If tower is not active, we can buy it
                statLines.add("Cost: " + getSelectedTower().getCost());
            } else {
                statLines.add("Upgrade Cost: " + getSelectedTower().getCost());
                statLines.add("Sell Refund: " + getSelectedTower().getRefund());

            }

            //draw each line
            for (int i = 0; i < statLines.size(); i++) {
                drawText(gc, statLines.get(i),
                        new Vector2(titlePosition.getX(), titlePosition.getY() + (i + 1) * linesSeparation),
                        Color.BLACK);
            }

            //Defining current Y position for the following lines
            double currentYPosition = titlePosition.getY() + linesSeparation + (statLines.size() * linesSeparation);

            //Drawing left arrow for strategySwapping
            getLeftArrowButton().setEnabled(selectedTower.isActive());
            Vector2 newLeftArrowButtonPosition = new Vector2(titlePosition.getX(), currentYPosition);
            getLeftArrowButton().setPosition(newLeftArrowButtonPosition);
            getLeftArrowButton().draw(gc);

            //Drawing the strategy flavour text
            if (getSelectedTower().isActive()) {
                String strategyText = getSelectedTower().getAttackStrategyEnum().toString();
                drawText(gc, strategyText,
                        new Vector2(titlePosition.getX() + getLeftArrowButton().getWidth() + 2,
                                currentYPosition + linesSeparation),
                        Color.BLACK);
            }

            //Drawing right arrow for strategySwapping
            getRightArrowButton().setEnabled(selectedTower.isActive());
            Vector2 newRightArrowButtonPosition =
                    new Vector2(
                            titlePosition.getX() + getLeftArrowButton().getWidth() + 2 + 192 + 2,
                            currentYPosition);
            getRightArrowButton().setPosition(newRightArrowButtonPosition);
            getRightArrowButton().draw(gc);

            currentYPosition += getRightArrowButton().getWidth() + 2;

            getSellButton().setEnabled(selectedTower.isActive());
            Vector2 newSellButtonPosition = new Vector2(titlePosition.getX(), currentYPosition);
            getSellButton().setPosition(newSellButtonPosition);
            getSellButton().draw(gc);
            getUpgradeButton().setEnabled(selectedTower.isActive());
            Vector2 newUpgradeButton = new Vector2(titlePosition.getX() + getSellButton().getWidth() + 2, currentYPosition);
            getUpgradeButton().setPosition(newUpgradeButton);
            getUpgradeButton().draw(gc);
            
            //DrawLogs
            currentYPosition += getUpgradeButton().getHeight() + 2 + logSeparation;

            gc.setFont(logFont);
            for (int i = 0; i< logQueue.size(); i++){
                String toDraw = logQueue.remove();
                drawText(gc,toDraw, new Vector2(titlePosition.getX() ,currentYPosition + i* logSeparation), Color.BLACK);
                logQueue.add(toDraw);
            }
            

        }

    }

    /**
     * Method to draw text in Inspection panel
     *
     * @param gc       Graphics context to draw on
     * @param text     Text to be drawn
     * @param position Position at which text will be drawn
     * @param color    Color of text
     */
    private void drawText(GraphicsContext gc, String text, Vector2 position, Color color) {
        gc.setFill(color);
        gc.fillText(text, position.getX(), position.getY());
    }

    /**
     * Method to get a button that has been upgraded
     *
     * @return Returns the upgraded Button
     */
    public Button getUpgradeButton() {
        return upgradeButton;
    }

    /**
     * Method to set the upgraded button
     *
     * @param upgradeButton represents the upgraded Button
     */
    public void setUpgradeButton(Button upgradeButton) {
        this.upgradeButton = upgradeButton;
    }

    /**
     * Method to get a  sell button
     *
     * @return Returns the sell Button
     */
    public Button getSellButton() {
        return sellButton;
    }

    /**
     * Method to set the sell button
     *
     * @param sellButton represents the sell Button
     */
    public void setSellButton(Button sellButton) {
        this.sellButton = sellButton;
    }

    /**
     * Method to get the right arrow button
     *
     * @return Returns the right arrow Button
     */
    public Button getRightArrowButton() {
        return rightArrowButton;
    }

    /**
     * Method to set the right arrow button
     *
     * @param rightArrowButton represents the right arrow Button
     */
    public void setRightArrowButton(Button rightArrowButton) {
        this.rightArrowButton = rightArrowButton;
    }

    /**
     * Method to get the left arrow button
     *
     * @return Returns the left arrow Button
     */
    public Button getLeftArrowButton() {
        return leftArrowButton;
    }

    /**
     * Method to set the left arrow button
     *
     * @param leftArrowButton represents the right arrow Button
     */
    public void setLeftArrowButton(Button leftArrowButton) {
        this.leftArrowButton = leftArrowButton;
    }

    /**
     * Adds a new line to the log. The maximum number of logs is 10 at the moment.
     * @param newLog New log line
     */
    public void addLog(String newLog){
        if(logQueue.size() > Settings.NUMBERTOFLOGLINESIN_INSPECTIONPANEL){
            logQueue.remove();
        }
        logQueue.add(newLog);
    }

    /**
     * Clears the current log
     */
    public void clearLog(){

        logQueue.clear();
    }

    public double getCurrentYPosition(double tilePositionYComponent, double linesSeparation, double statLines)
    {
        return (tilePositionYComponent + linesSeparation + (statLines * linesSeparation));
    }
}
