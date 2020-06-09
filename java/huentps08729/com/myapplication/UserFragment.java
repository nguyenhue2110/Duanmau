package huentps08729.com.myapplication;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import huentps08729.com.myapplication.adapter.RecyclerView_User;
import huentps08729.com.myapplication.model.User;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragment extends Fragment {

        RecyclerView_User recyclerView_user;
    RecyclerView recuser;
        List<User>userList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_user, container, false);


            recuser= view.findViewById(R.id.rycuser);
            recuser.setHasFixedSize(true);
            recuser.setLayoutManager(new LinearLayoutManager(getActivity()));
            userList= new ArrayList<>();

        getAllUser();

            return view;
    }

    private void getAllUser(){
            //get curremt user



        final FirebaseUser fuser= FirebaseAuth.getInstance().getCurrentUser();
        //get path of database name"users" contaning user infor
        DatabaseReference ref= FirebaseDatabase.getInstance().getReference("User");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userList.clear();
               for(DataSnapshot ds:dataSnapshot.getChildren()){
                   User user= ds.getValue(User.class);


                   userList.add(user);

               }

                recyclerView_user= new RecyclerView_User(getActivity(),userList);
                recuser.setAdapter(recyclerView_user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
