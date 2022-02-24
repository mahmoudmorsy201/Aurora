package com.iti.aurora.mainactivity.home.presenter;

import com.iti.aurora.mainactivity.home.view.HomeFragmentViewInterface;
import com.iti.aurora.model.RepositoryInterface;

public class HomeFragmentPresenter implements HomeFragmentPresenterInterface {
    private HomeFragmentViewInterface _view;
    private RepositoryInterface _repo;

    public HomeFragmentPresenter(HomeFragmentViewInterface _view, RepositoryInterface _repo) {
        this._view = _view;
        this._repo = _repo;
    }

    @Override
    public void getLocalDoses() {
        _view.showLocalData(_repo.getAllStoredDoses());
    }

    @Override
    public void getDosesByDay(long start, long end) {
        _view.showLocalDataByDay(_repo.getDosesByDay(start , end));
    }
}