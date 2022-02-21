package com.iti.aurora.home.presenter;

public interface MainActivityPresenterInterface {
    void getLocalDoses();

    void getDosesByDay(long start, long end);
}
