package com.iti.aurora.home.presenter;

import com.iti.aurora.home.view.MainActivityViewInterface;
import com.iti.aurora.model.RepositoryInterface;

public class MainActivityPresenter implements MainActivityPresenterInterface{
    private MainActivityViewInterface _view;
    private RepositoryInterface _repo;

    public MainActivityPresenter(MainActivityViewInterface _view, RepositoryInterface _repo) {
        this._view = _view;
        this._repo = _repo;
    }

    @Override
    public void getLocalDoses() {
        _view.showLocalData(_repo.getAllStoredDoses());
    }

    @Override
    public void getDosesByDay(long start, long end) {
        _view.showLocalData(_repo.getDosesByDay(start , end));
    }
}
