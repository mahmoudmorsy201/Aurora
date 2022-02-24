package com.iti.aurora.mainactivity.home.presenter;

public interface HomeFragmentPresenterInterface {
    void getLocalDoses();

    void getDosesByDay(long start, long end);
}