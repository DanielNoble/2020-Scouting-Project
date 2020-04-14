package com.example.a2020scoutingapp;


import android.util.Log;

import java.util.Scanner;


public class DeString {
    public String str;

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
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

    public String getAlliance() {
        return alliance;
    }

    public void setAlliance(String alliance) {
        this.alliance = alliance;
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

    public int getCellsMissed() {
        return cellsMissed;
    }

    public void setCellsMissed(int cellsMissed) {
        this.cellsMissed = cellsMissed;
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

    public boolean isBalanced() {
        return isBalanced;
    }

    public void setBalanced(boolean balanced) {
        isBalanced = balanced;
    }

    public boolean isDefense() {
        return defense;
    }

    public void setDefense(boolean defense) {
        this.defense = defense;
    }

    public String getAdditionalComments() {
        return additionalComments;
    }

    public void setAdditionalComments(String additionalComments) {
        this.additionalComments = additionalComments;
    }

    public DeString(String s){
        this.str = s;
        Log.d("sp","String received: "+s);
        Scanner deScan = new Scanner(s);
        deScan.useDelimiter("#@;-;@#");
        this.matchNumber = deScan.next();
        this.teamNumber = deScan.next();
        this.alliance = deScan.next();
        this.cellsCollected = deScan.nextInt();
        this.preloadedBalls=deScan.nextBoolean();
        this.cellsShotRectAuto = deScan.nextInt();
        this.cellsShotHexagonAuto = deScan.nextInt();
        this.crossedLine=deScan.nextBoolean();
        this.cellsShotRect= deScan.nextInt();
        this.cellsShotHexagon = deScan.nextInt();
        this.cellsMissed = deScan.nextInt();
        this.rotationControl = deScan.nextInt();
        this.colorControl = deScan.nextBoolean();
        this.isParked = deScan.nextBoolean();
        this.isClimbed = deScan.nextBoolean();
        this.isHelperClimbed = deScan.nextBoolean();
        this.isBalanced = deScan.nextBoolean();
        this.defense = deScan.nextBoolean();
        this.additionalComments = deScan.next();
    }

    public String matchNumber;


    public String teamNumber;

    public String alliance;

    // options: center,right,left
    public String startSide;

    public boolean preloadedBalls;
    //Auto
    public boolean crossedLine;

    public int cellsShotHexagonAuto;

    public int cellsShotRectAuto;

    public int cellsShotCircleAuto;

    public int cellsMissed;

    // max 20
    public int cellsCollected;

// Teleop

    public int cellsShotHexagon;

    public int cellsShotRect;

    public int cellsShotCircle;

    public int cellsMissedTele;


    //how many successful rotation control (0 to 2)
    public int rotationControl;




    //how may successful choose color control
    public boolean colorControl;

//End Game

    public boolean isParked;

    public boolean isClimbed;

    public boolean isHelperClimbed;

    public boolean isBalanced;

    public boolean defense;
    // an large edit text
    public String additionalComments;



}

