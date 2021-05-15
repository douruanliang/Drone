package com.dourl.compose.observer;

public interface Subject {

    public void registerObserver(Observer observer);

    public void removeObserve(Observer observer);

    public void notifyObservers();
}
