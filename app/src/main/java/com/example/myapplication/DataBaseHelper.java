package com.example.myapplication;

import android.widget.EditText;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper {

    public FirebaseFirestore db;
    public CollectionReference users;
    public static final String USERS_COLLECTION_NAME = "users";

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

    public boolean activateBand(String userName, String nickname, String phone, String timeOfDay, String district, String adress){

        Task<Void> task = users.document(userName).update("nickname", nickname, "phone", phone, "timeOfDay", timeOfDay, "district", district, "adress", adress, "active", true);

        while (!task.isComplete()){}

        if(task.isSuccessful())
            return true;
        else
            return false;
    }

    public void activateMusicianSearch(User user, ArrayList<String> selectedMusicians){
        users.document(user.getUserName()).update("musicianSearching", selectedMusicians);
    }

    public void editMusicianProfile(User user, EditText instrumento, EditText nombre, EditText password) {
        users.document(user.getUserName()).update("userName",nombre,"instrument",instrumento,"password",password);
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

}
