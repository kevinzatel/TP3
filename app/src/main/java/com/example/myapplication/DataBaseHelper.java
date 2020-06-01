package com.example.myapplication;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper {

    public FirebaseFirestore db;
    public CollectionReference users;
    public static final String USERS_COLLECTION_NAME = "users";
    public static final String DB_ERROR_MSG = "Ocurrió un error al obtener los datos";
    public static final String DB_USERS_EMPTY_RESULT = "No se encontraron usuarios";


    public DataBaseHelper() {
        db = FirebaseFirestore.getInstance();
        setCollections();
    }


    private void setCollections(){
        users = db.collection(USERS_COLLECTION_NAME);
    }


    public boolean insertUser(String userName, String password, String instrument, int isBand, String timeOfDay, String description) {
        boolean bandFlag = isBand == 1 ? true : false;
        User user = new User(userName, password, description, instrument, timeOfDay, bandFlag, null);
        Task<Void> task = users.document(user.getUserName()).set(user);

        while (!task.isComplete()){}

        if(task.isSuccessful())
            return true;
        else
            return false;

    }

    public void activateMusicianSearch(User user, ArrayList<String> selectedMusicians){
        users.document(user.getUserName()).update("musicianSearching", selectedMusicians);
    }

    public void desactivateMusicianSearch(User user){
        users.document(user.getUserName()).update("musicianSearching", null);
    }


    public String getUsers() {

        String usersList = null;
        Task<QuerySnapshot> task = users.get();

        while (!task.isComplete()) {}

        if (task.isSuccessful()) {
            QuerySnapshot queryDocumentSnapshots = task.getResult();
            if (queryDocumentSnapshots.isEmpty()) {
                return DB_USERS_EMPTY_RESULT;
            } else {
                List<DocumentSnapshot> userList = queryDocumentSnapshots.getDocuments();
                StringBuffer buffer = new StringBuffer();

                for (DocumentSnapshot u : userList) {
                    User user = u.toObject(User.class);
                    buffer.append("USERNAME: " + user.getUserName() + "\n");
                    buffer.append("PASSWORD: " + user.getPassword() + "\n");
                    buffer.append("INSTRUMENT: " + user.getInstrument() + "\n");
                    String isBand = user.isBand() ? "SI" : "NO";
                    buffer.append("IS BAND?: " + isBand + "\n");
                    usersList = buffer.toString();
                }
                return usersList;
            }
        } else {
            return DB_ERROR_MSG;
        }
        
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

}
