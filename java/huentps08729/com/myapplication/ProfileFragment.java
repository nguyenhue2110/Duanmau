package huentps08729.com.myapplication;


import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import huentps08729.com.myapplication.model.User;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

DatabaseReference databaseReference;
FirebaseDatabase database;
 ImageView imvavata;
 FirebaseUser firebaseUser;
 TextView txtUser,txtPhone;
FirebaseAuth firebaseAuth;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view= inflater.inflate(R.layout.fragment_profile, container, false);

        database = FirebaseDatabase.getInstance();

        databaseReference = database.getReference("User");
        imvavata= view.findViewById(R.id.imvavata);
        txtUser= view.findViewById(R.id.txtUser);
        txtPhone=view.findViewById(R.id.txtPhone);


       return view;
    }

}
