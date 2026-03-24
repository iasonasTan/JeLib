package com.je.game.score;

import lib.io.Configuration;
import lib.io.InputProperties;
import lib.io.OutputProperties;

import java.io.Closeable;

public final class ScoreManager implements Closeable {
    public static ScoreManager fromSaved() {
        InputProperties inputProperties = Configuration.loadProperties("score.properties");
        return new ScoreManager(inputProperties.getDouble("best_score", 0d));
    }

    public static ScoreManager fromBestScore(double bs) {
        return new ScoreManager(bs);
    }

    private final double mBestScore;
    private double mCurrentScore;

    private ScoreManager(double bestScore) {
        mBestScore = bestScore;
    }

    @Override
    public String toString() {
        return String.format("Score: %f, BestScore: %f", mCurrentScore, mBestScore);
    }

    @Override
    public void close() {
        if(mCurrentScore >mBestScore) {
            var op = new OutputProperties().put("best_score", mCurrentScore);
            Configuration.storeProperties("score.properties", op);
        }
    }

    public void increaseByPercentage(double percentage) {
        double value = (percentage*mBestScore)/100;
        increaseScore(value);
    }

    public double getAsPercentage() {
        if(mBestScore==0) return 0;
        return (100* mCurrentScore)/mBestScore;
    }

    public void increaseScore() {
        mCurrentScore +=1f;
    }

    public void increaseScore(double s) {
        mCurrentScore += s;
    }

    public double getScore() {
        return mCurrentScore;
    }

    public double getBestScore() {
        return mBestScore;
    }
}
