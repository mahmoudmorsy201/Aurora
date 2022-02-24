package com.iti.aurora.home.presenter;

import com.iti.aurora.home.view.HomeFragmentViewInterface;
import com.iti.aurora.model.RepositoryInterface;

public class FragmentHomePresenter implements FragmentHomePresenterInterface {
    private HomeFragmentViewInterface _view;
    private RepositoryInterface _repo;

    public FragmentHomePresenter(HomeFragmentViewInterface _view, RepositoryInterface _repo) {
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