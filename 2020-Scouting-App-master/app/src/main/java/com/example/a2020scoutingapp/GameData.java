package com.example.a2020scoutingapp;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "RawData")
public class GameData {

//Pre Game data
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name= "MatchNumber")
    public String matchNumber;

    @ColumnInfo(name= "TeamNumber")
    public String teamNumber;

    // options: center,right,left
    @ColumnInfo(name= "Start Position")
    public String startSide;

    @ColumnInfo(name= "Has Preloaded Balls")
    public boolean preloadedBalls;
//Auto
    @ColumnInfo(name="Did they Cross the Line")
    public boolean crossedLine;

    @ColumnInfo(name= "Power cells shot in Hexagon Auto")
    public int cellsShotHexagonAuto;

    @ColumnInfo(name= "Power cells shot in Rectangle Auto")
    public int cellsShotRectAuto;

    @ColumnInfo(name= "Power cells shot in Circle Auto")//may not be used
    public int cellsShotCircleAuto;

    public GameData(String matchNumber, String teamNumber, String startSide, boolean preloadedBalls, boolean crossedLine, int cellsShotHexagonAuto, int cellsShotRectAuto, int cellsShotCircleAuto, int cellsCollected, int cellsShotHexagon, int cellsShotRect, int cellsShotCircle, int cellsMissedTele, int rotationControl, boolean colorControl, boolean isParked, boolean isClimbed, boolean isHelperClimbed, boolean isBalance, boolean isDefended, String additionalComments) {
        this.matchNumber = matchNumber;
        this.teamNumber = teamNumber;
        this.startSide = startSide;
        this.preloadedBalls = preloadedBalls;
        this.crossedLine = crossedLine;
        this.cellsShotHexagonAuto = cellsShotHexagonAuto;
        this.cellsShotRectAuto = cellsShotRectAuto;
        this.cellsShotCircleAuto = cellsShotCircleAuto;
        this.cellsCollected = cellsCollected;
        this.cellsShotHexagon = cellsShotHexagon;
        this.cellsShotRect = cellsShotRect;
        this.cellsShotCircle = cellsShotCircle;
        this.cellsMissedTele = cellsMissedTele;
        this.rotationControl = rotationControl;
        this.colorControl = colorControl;
        this.isParked = isParked;
        this.isClimbed = isClimbed;
        this.isHelperClimbed = isHelperClimbed;
        this.isBalance = isBalance;
        this.isDefended = isDefended;
        this.additionalComments = additionalComments;
    }

    // max 20
    @ColumnInfo(name= "Power Cells Collected in Auto")
    public int cellsCollected;


    // Teleop

    @ColumnInfo(name= "Power cells shot in Hexagon")
    public int cellsShotHexagon;

    @ColumnInfo(name= "Power cells shot in Rectangle")
    public int cellsShotRect;

    @ColumnInfo(name= "Power cells shot in Circle")//remove maybe
    public int cellsShotCircle;

    @ColumnInfo(name= "Power cells Missed in Teleop")//remove maybe
    public int cellsMissedTele;


    //how many successful rotation control (0 to 2)
    @ColumnInfo(name= "Ability to Spin")//remove
    public int rotationControl;




    //how may successful choose color control
    @ColumnInfo(name= "Ability To Choose Color")
    public boolean colorControl;

//End Game

    @ColumnInfo(name= "Parked")
    public boolean isParked;

    @ColumnInfo(name= "Climbed")
    public boolean isClimbed;

    @ColumnInfo(name= "Helped Climbed")
    public boolean isHelperClimbed;

    @ColumnInfo(name= "Balanced")
    public boolean isBalance;
    @ColumnInfo(name= "Defended")
    public boolean isDefended;

    // an large edit text
    @ColumnInfo(name= "Additional Comments")
    public String additionalComments;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMatchNumber() {
        return matchNumber;
    }

    public void setMatchNumber(String matchNumber) {
        this.matchNumber = matchNumber;
    }

    public String getTeamNumber() {
        return teamNumber;
    }

    public void setTeamNumber(String teamNumber) {
        this.teamNumber = teamNumber;
    }

    public String getStartSide() {
        return startSide;
    }

    public void setStartSide(String startSide) {
        this.startSide = startSide;
    }

    public boolean isPreloadedBalls() {
        return preloadedBalls;
    }

    public void setPreloadedBalls(boolean preloadedBalls) {
        this.preloadedBalls = preloadedBalls;
    }

    public boolean isCrossedLine() {
        return crossedLine;
    }

    public void setCrossedLine(boolean crossedLine) {
        this.crossedLine = crossedLine;
    }

    public int getCellsShotHexagonAuto() {
        return cellsShotHexagonAuto;
    }

    public void setCellsShotHexagonAuto(int cellsShotHexagonAuto) {
        this.cellsShotHexagonAuto = cellsShotHexagonAuto;
    }

    public int getCellsShotRectAuto() {
        return cellsShotRectAuto;
    }

    public void setCellsShotRectAuto(int cellsShotRectAuto) {
        this.cellsShotRectAuto = cellsShotRectAuto;
    }

    public int getCellsShotCircleAuto() {
        return cellsShotCircleAuto;
    }

    public void setCellsShotCircleAuto(int cellsShotCircleAuto) {
        this.cellsShotCircleAuto = cellsShotCircleAuto;
    }

    public int getCellsCollected() {
        return cellsCollected;
    }

    public void setCellsCollected(int cellsCollected) {
        this.cellsCollected = cellsCollected;
    }

    public int getCellsShotHexagon() {
        return cellsShotHexagon;
    }

    public void setCellsShotHexagon(int cellsShotHexagon) {
        this.cellsShotHexagon = cellsShotHexagon;
    }

    public int getCellsShotRect() {
        return cellsShotRect;
    }

    public void setCellsShotRect(int cellsShotRect) {
        this.cellsShotRect = cellsShotRect;
    }

    public int getCellsShotCircle() {
        return cellsShotCircle;
    }

    public void setCellsShotCircle(int cellsShotCircle) {
        this.cellsShotCircle = cellsShotCircle;
    }

    public int getCellsMissedTele() {
        return cellsMissedTele;
    }

    public void setCellsMissedTele(int cellsMissedTele) {
        this.cellsMissedTele = cellsMissedTele;
    }

    public int getRotationControl() {
        return rotationControl;
    }

    public void setRotationControl(int rotationControl) {
        this.rotationControl = rotationControl;
    }

    public boolean isColorControl() {
        return colorControl;
    }

    public void setColorControl(boolean colorControl) {
        this.colorControl = colorControl;
    }

    public boolean isParked() {
        return isParked;
    }

    public void setParked(boolean parked) {
        isParked = parked;
    }

    public boolean isClimbed() {
        return isClimbed;
    }

    public void setClimbed(boolean climbed) {
        isClimbed = climbed;
    }

    public boolean isHelperClimbed() {
        return isHelperClimbed;
    }

    public void setHelperClimbed(boolean helperClimbed) {
        isHelperClimbed = helperClimbed;
    }

    public boolean isBalance() {
        return isBalance;
    }

    public void setBalance(boolean balance) {
        isBalance = balance;
    }

    public boolean isDefended() {
        return isDefended;
    }

    public void setDefended(boolean defended) {
        isDefended = defended;
    }

    public String getAdditionalComments() {
        return additionalComments;
    }

    public void setAdditionalComments(String additionalComments) {
        this.additionalComments = additionalComments;
    }
}
