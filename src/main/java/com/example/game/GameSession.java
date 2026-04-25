package com.example.game;

public class GameSession {
    private int secretNumber;      // загаданное число
    private int attempts;          // попытки в текущей игре
    private int totalAttempts;     // всего попыток за весь сеанс
    private int gamesCount;        // количество сыгранных игр
    private boolean gameWon;       // угадал ли пользователь

    public GameSession() {
        this.secretNumber = (int)(Math.random() * 101); // число от 0 до 100
        this.attempts = 0;
        this.totalAttempts = 0;
        this.gamesCount = 0;
        this.gameWon = false;
    }

    public int getSecretNumber() {
        return secretNumber;
    }

    public int getAttempts() {
        return attempts;
    }

    public int getTotalAttempts() {
        return totalAttempts;
    }

    public int getGamesCount() {
        return gamesCount;
    }

    public boolean isGameWon() {
        return gameWon;
    }

    public void setSecretNumber(int secretNumber) {
        this.secretNumber = secretNumber;
    }

    public void setAttempts(int attempts) {
        this.attempts = attempts;
    }

    public void setTotalAttempts(int totalAttempts) {
        this.totalAttempts = totalAttempts;
    }

    public void setGamesCount(int gamesCount) {
        this.gamesCount = gamesCount;
    }

    public void setGameWon(boolean gameWon) {
        this.gameWon = gameWon;
    }

    public void newGame() {
        this.secretNumber = (int)(Math.random() * 101);
        this.attempts = 0;
        this.gameWon = false;
        this.gamesCount++;
    }
}
