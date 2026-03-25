package com.je.game.score;

import com.je.core.util.Bundle;
import com.je.io.configuration.Configuration;

import java.io.Closeable;

public final class ScoreManager implements Closeable {
    public static ScoreManager fromSaved() {
        Bundle inputBundle = Configuration.loadBundle("score.properties");
        return new ScoreManager(inputBundle.getDouble("best_score", 0d));
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
            var outBundle = Bundle.builder()
                    .put("best_score", mCurrentScore)
                    .build();
            Configuration.storeBundle("score.properties", outBundle);
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
