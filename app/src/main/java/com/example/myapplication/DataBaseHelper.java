package com.example.myapplication;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class DataBaseHelper {

    public FirebaseFirestore db;
    public CollectionReference users;
    public static final String USERS_COLLECTION_NAME = "users";
    public static final String REQUESTS_COLLECTION_NAME = "requests";

    public DataBaseHelper() {
        db = FirebaseFirestore.getInstance();
        setCollections();
    }

    private void setCollections(){
        users = db.collection(USERS_COLLECTION_NAME);
    }



    public boolean insertUser(String userName, String password, int isBand) {

        boolean bandFlag = isBand == 1 ? true : false;
        User user = new User(userName, password, bandFlag);
        Task<Void> task = users.document(user.getUserName()).set(user);

        while (!task.isComplete()){}

        if(task.isSuccessful())
            return true;
        else
            return false;

    }

    public boolean activateMusician(String userName, String nickname, String phone){

        Task<Void> task = users.document(userName).update("nickname", nickname, "phone", phone, "active", true);

        while (!task.isComplete()){}

        if(task.isSuccessful())
            return true;
        else
            return false;
    }

    public boolean activateBand(String userName, String nickname, String phone, String timeOfDay, String district, String latitude, String longitude){

        Task<Void> task = users.document(userName).update("nickname", nickname, "phone", phone, "timeOfDay", timeOfDay, "district", district, "latitude", latitude, "longitude", longitude, "active", true);

        while (!task.isComplete()){}

        if(task.isSuccessful())
            return true;
        else
            return false;
    }

    public void activateMusicianSearch(User user, ArrayList<String> selectedMusicians){
        users.document(user.getUserName()).update("musicianSearching", selectedMusicians);
    }

    public void editMusicianProfile(User user, String nombre, String password,String Phone) {
        users.document(user.getUserName()).update("userName",nombre,"password",password,"phone",Phone);
    }

    public void desactivateMusicianSearch(User user){
        users.document(user.getUserName()).update("musicianSearching", null);
    }


    public User getUser(String username){

        User user = null;
        Task<QuerySnapshot> task = users.get();

        while (!task.isComplete()) {}

        if (task.isSuccessful()) {
            QuerySnapshot queryDocumentSnapshots = task.getResult();
            if (!queryDocumentSnapshots.isEmpty()) {

                List<DocumentSnapshot> userList = queryDocumentSnapshots.getDocuments();

                for(DocumentSnapshot u : userList){
                    User readedUser = u.toObject(User.class);
                    if (readedUser.getUserName().equals(username)){
                        user = readedUser;
                        break;
                    }
                }
            }
        }
        return user;
    }

    public ArrayList<User> getBand(String instrument, String district, String timeOfDay){

        ArrayList<User> usersList = null;


        Task<QuerySnapshot> task = db.collection(USERS_COLLECTION_NAME)
                .whereEqualTo("active", true)
                .whereEqualTo("band", true)
                .whereEqualTo("district", district)
                .whereEqualTo("timeOfDay", timeOfDay)
                .whereArrayContains("musicianSearching", instrument)
                .get();

        while (!task.isComplete()) {}

        if (task.isSuccessful()) {
            QuerySnapshot queryDocumentSnapshots = task.getResult();
            if (!queryDocumentSnapshots.isEmpty()) {

                List<DocumentSnapshot> userList = queryDocumentSnapshots.getDocuments();
                usersList = new ArrayList<User>();

                for(DocumentSnapshot u : userList){
                    User readedUser = u.toObject(User.class);
                    usersList.add(readedUser);
                }
            }
        }
        return usersList;
    }

    public ArrayList<Requests> getRequest(String idmusician){


        ArrayList<Requests> requestsArrayList = null;
        Task<QuerySnapshot> task = db.collection(REQUESTS_COLLECTION_NAME)

                .whereEqualTo("idMusician",idmusician)
                .get();

        while (!task.isComplete()) {}

        if (task.isSuccessful()) {
            QuerySnapshot queryDocumentSnapshots = task.getResult();

            if (!queryDocumentSnapshots.isEmpty()) {
                List<DocumentSnapshot> reqList = queryDocumentSnapshots.getDocuments();
                requestsArrayList = new ArrayList<Requests>();

                for(DocumentSnapshot u : reqList){
                    Requests readedReq = u.toObject(Requests.class);
                    requestsArrayList.add(readedReq);
                }
            }
        }
        return requestsArrayList;
    }




}
