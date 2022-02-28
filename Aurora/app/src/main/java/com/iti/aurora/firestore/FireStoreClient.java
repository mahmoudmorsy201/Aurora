package com.iti.aurora.firestore;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.protobuf.Any;
import com.iti.aurora.model.User;
import com.iti.aurora.model.medicine.Dose;
import com.iti.aurora.model.medicine.Medicine;
import com.iti.aurora.model.medicine.Treatment;
import com.iti.aurora.utils.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FireStoreClient implements RemoteSourceFireStore {

    private FirebaseFirestore firebaseFirestore;
    private static final String TAG = "FireStoreClient";
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private CollectionReference userRef;
    private String medId;
    private static FireStoreClient fireStoreClient = null;
    private List<Medicine> medicineList;
    private List<Treatment> treatmentList;
    private List<Dose> doseList;

    private FireStoreClient() {
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        userRef = firebaseFirestore.collection(Constants.FirestoreConstants.USERS);
        medicineList = new ArrayList<>();
        treatmentList = new ArrayList<>();
        doseList =  new ArrayList<>();

    }

    public static FireStoreClient getInstance() {
        if (fireStoreClient == null) {
            fireStoreClient = new FireStoreClient();

        }
        return fireStoreClient;
    }


    @Override
    public void putUser(User user) {
        firebaseFirestore.collection(Constants.FirestoreConstants.USERS)
                .add(user)
                .addOnSuccessListener(documentReference -> {
                    //TODO Do something
                }).addOnFailureListener(e -> Log.d(TAG, "onFailure: Adding user failed"));
    }

    public void putMedicine(Medicine medicine) {
        medId = String.valueOf(medicine.getMedId());
        userRef.document(firebaseUser.getUid())
                .collection(Constants.FirestoreConstants.MEDICINE_FIRESTORE)
                .document(String.valueOf(medicine.getMedId()))
                .set(medicine)
                .addOnFailureListener(e -> {
                    Log.d(TAG, "onFailure: Adding medicine failed");
                }).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    public void putTreatment(Treatment treatment) {

        DocumentReference document = firebaseFirestore.collection(Constants.FirestoreConstants.USERS).document(firebaseAuth.getCurrentUser().getUid())
                .collection(Constants.FirestoreConstants.MEDICINE_FIRESTORE).document(medId);

        document.collection(Constants.FirestoreConstants.TREATMENT_FIRESTORE)
                .document(medId)
                .set(treatment);
    }

    public void putDoses(List<Dose> doseList) {
        Map<String, Object> doseData = new HashMap<>();
        doseData.put("dose", doseList);
        DocumentReference document = firebaseFirestore.collection(Constants.FirestoreConstants.USERS).document(firebaseAuth.getCurrentUser().getUid())
                .collection(Constants.FirestoreConstants.MEDICINE_FIRESTORE)
                .document(medId)
                .collection(Constants.FirestoreConstants.TREATMENT_FIRESTORE)
                .document(medId);
        document.collection(Constants.FirestoreConstants.DOSE_FIRESTORE)
                .document()
                .set(doseData);
    }

    @Override
    public void putDose(Dose dose) {
        DocumentReference document = firebaseFirestore.collection(Constants.FirestoreConstants.USERS).document(firebaseAuth.getCurrentUser().getUid())
                .collection(Constants.FirestoreConstants.MEDICINE_FIRESTORE)
                .document(medId)
                .collection(Constants.FirestoreConstants.TREATMENT_FIRESTORE)
                .document(medId);
        document.collection(Constants.FirestoreConstants.DOSE_FIRESTORE)
                .document(String.valueOf(dose.getDoseId()))
                .set(dose);
    }


    @Override
    public void getUser() {
        firebaseFirestore.collection(Constants.FirestoreConstants.USERS)
                .document(firebaseUser.getUid())
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }

    @Override
    public void getMedicine() {
        userRef.document(firebaseUser.getUid())
                .collection(Constants.FirestoreConstants.MEDICINE_FIRESTORE)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    List<DocumentSnapshot> documentSnapshots = task.getResult().getDocuments();
                    for (DocumentSnapshot document : documentSnapshots) {
                        Medicine medicine = document.toObject(Medicine.class);
                        medicineList.add(medicine);
                    }
                }
            }
        });


    }

    @Override
    public void getTreatment(String medId) {
        userRef.document(firebaseUser.getUid())
                .collection(Constants.FirestoreConstants.MEDICINE_FIRESTORE)
                .document(medId)
                .collection(Constants.FirestoreConstants.TREATMENT_FIRESTORE)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    List<DocumentSnapshot> documentSnapshots = task.getResult().getDocuments();
                    for (DocumentSnapshot document : documentSnapshots) {
                        Treatment treatment = document.toObject(Treatment.class);
                        treatmentList.add(treatment);
                        Log.d(TAG, "onComplete: "+ treatment.getRecurrency());
                    }
                }
            }
        });

    }

    @Override
    public void getDoses() {

    }

    @Override
    public void updateUserData(User user) {

    }

    @Override
    public void updateMedicine(Medicine medicine) {

    }

    @Override
    public void updateTreatment(Treatment treatment) {

    }

    @Override
    public void updateDoses(List<Dose> doseList) {

    }

    @Override
    public void removeUser(User user) {

    }

    @Override
    public void removeMedicine(Medicine medicine) {

    }

    @Override
    public void removeTreatment(Treatment treatment) {

    }

    @Override
    public void removeDoses(List<Dose> doseLists) {

    }
}
