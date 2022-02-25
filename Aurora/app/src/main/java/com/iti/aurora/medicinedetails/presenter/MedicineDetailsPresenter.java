package com.iti.aurora.medicinedetails.presenter;

import com.iti.aurora.medicinedetails.view.MedicineDetailsViewInterface;
import com.iti.aurora.model.RepositoryInterface;
import com.iti.aurora.model.medicine.Medicine;

public class MedicineDetailsPresenter implements MedicineDetailsPresenterInterface {
    MedicineDetailsViewInterface iView;
    RepositoryInterface repositoryInterface;

    public MedicineDetailsPresenter(MedicineDetailsViewInterface iView, RepositoryInterface repositoryInterface) {
        this.iView = iView;
        this.repositoryInterface = repositoryInterface;
    }

    @Override
    public void deleteMedicine(Medicine medicine) {
        repositoryInterface.deleteMedicine(medicine);
    }
}