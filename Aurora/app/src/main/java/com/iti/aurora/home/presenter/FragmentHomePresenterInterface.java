package com.iti.aurora.home.presenter;

public interface FragmentHomePresenterInterface {
    void getLocalDoses();

    void getDosesByDay(long start, long end);
}
