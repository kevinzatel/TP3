package com.example.myapplication;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DataBaseHelper {

    public FirebaseFirestore db;
    public CollectionReference users, requests;
    public static final String USERS_COLLECTION_NAME = "users";
    public static final String REQUESTS_COLLECTION_NAME = "requests";

    public DataBaseHelper() {
        db = FirebaseFirestore.getInstance();
        setCollections();
    }

    private void setCollections(){
        users = db.collection(USERS_COLLECTION_NAME);
        requests = db.collection(REQUESTS_COLLECTION_NAME);
    }

    private String getFormattedDate() {

        Date actualDate = new Date();
        SimpleDateFormat dateFormatted = new SimpleDateFormat("dd/MM/yyyy");
        String strDate = dateFormatted.format(actualDate);
        return strDate;

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

    public boolean insertRequest(String idBand, String idMusician) {

        String state = "pendiente";
        String date = getFormattedDate();

        Requests request = new Requests(idBand, idMusician, state, date);

        Task<Void> task = requests.document().set(request);

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

    public boolean activateBand(String userName, String nickname, String phone, String timeOfDay, String district, String address, String latitude, String longitude){

        Task<Void> task = users.document(userName).update("nickname", nickname, "phone", phone, "timeOfDay", timeOfDay, "district", district, "address", address, "latitude", latitude, "longitude", longitude, "active", true);

        while (!task.isComplete()){}

        if(task.isSuccessful())
            return true;
        else
            return false;
    }

    public void updateBandAccount(User user, String userName, String password ){
        users.document(user.getUserName()).delete();
        users.document(userName).set(user);
        users.document(userName).update("userName", userName, "password", password);
    }

    public void updateBandProfile(User user, String nickName, String phone, String address, String latitude, String longitude, String district, String timeOfDay){
        users.document(user.getUserName()).update("nickname", nickName, "phone", phone, "address", address, "latitude", latitude, "longitude", longitude, "district", district, "timeOfDay", timeOfDay);
    }

    public void activateMusicianSearch(User user, ArrayList<String> selectedMusicians){
        users.document(user.getUserName()).update("musicianSearching", selectedMusicians);
    }

    public void editMusicianProfile(User user, String nombre, String password,String Phone) {
        users.document(user.getUserName()).update("nickname",nombre,"password",password,"phone",Phone);
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

    public ArrayList<User> getBand(String user, String instrument, String district, String timeOfDay){

        ArrayList<User> usersList = null;
        ArrayList<Requests> requestsList = null;

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

        Task<QuerySnapshot> task2 = db.collection(REQUESTS_COLLECTION_NAME)
                .whereEqualTo("idMusician", user)
                .whereEqualTo("state", "pendiente")
                .get();

        while (!task2.isComplete()) {}

        if (task2.isSuccessful()) {
            QuerySnapshot queryDocumentSnapshots2 = task2.getResult();
            if (!queryDocumentSnapshots2.isEmpty()) {

                List<DocumentSnapshot> requestList = queryDocumentSnapshots2.getDocuments();
                requestsList = new ArrayList<Requests>();

                for(DocumentSnapshot r : requestList){
                    Requests readedRequest = r.toObject(Requests.class);
                    requestsList.add(readedRequest);
                }
            }
        }

        Task<QuerySnapshot> task3 = db.collection(REQUESTS_COLLECTION_NAME)
                .whereEqualTo("idMusician", user)
                .whereEqualTo("state", "aceptada")
                .get();

        while (!task3.isComplete()) {}

        if (task3.isSuccessful()) {
            QuerySnapshot queryDocumentSnapshots3 = task3.getResult();
            if (!queryDocumentSnapshots3.isEmpty()) {

                List<DocumentSnapshot> requestList2 = queryDocumentSnapshots3.getDocuments();

                if(requestsList == null){
                    requestsList = new ArrayList<Requests>();
                }

                for(DocumentSnapshot r2 : requestList2){
                    Requests readedRequest2 = r2.toObject(Requests.class);
                    requestsList.add(readedRequest2);
                }
            }
        }

        if (usersList != null && requestsList != null){
            for(Requests req : requestsList){
                String bandId = req.getIdBand();
                for(int i = 0; i < usersList.size(); i++){
                    if(usersList.get(i).getUserName().equals(bandId)){
                        usersList.remove(i);
                    }
                }
            }
        }

        return usersList;
    }

    public ArrayList<Requests> getRequestMusician(String idmusician){

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

    /*getRequestBand*/

    public ArrayList<Requests> getRequestBand(String idband){

        ArrayList<Requests> requestsArrayList = null;
        Task<QuerySnapshot> task = db.collection(REQUESTS_COLLECTION_NAME)

                .whereEqualTo("idBand",idband)
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
