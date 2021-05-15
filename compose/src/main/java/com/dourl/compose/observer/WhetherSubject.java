package com.dourl.compose.observer;

import java.util.ArrayList;

/**
 *  被观察者 天气主题
 */
public class WhetherSubject implements Subject {

    private ArrayList<Observer> observerArrayList = new ArrayList<>();
    private float temperature;

    @Override
    public void registerObserver(Observer observer) {
        observerArrayList.add(observer);
    }

    @Override
    public void removeObserve(Observer observer) {
        observerArrayList.remove(observer);
    }

    @Override
    public void notifyObservers() {
      for (Observer observer:observerArrayList){
          observer.update(temperature); //
      }
    }

    public float getTemperature() {
        return temperature;
    }

    /**
     * 上层 调用时 更新
     * @param temperature
     */
    public void setTemperature(float temperature) {
        this.temperature = temperature;
        notifyObservers();
    }
}
