package huentps08729.com.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import huentps08729.com.myapplication.model.Sach;

import static com.google.firebase.storage.FirebaseStorage.getInstance;

public class Update_SachActivity extends AppCompatActivity {
    Spinner spnersachup;
    ImageView imgsachup, imgbackbook;
    EditText edttensachup, edtNXBup, edtTacGiaup, edtGiaup,edtbvup;
    Button btnADDsup, btnCanceldlsup;
    String masach,tentl, images, tensach, tentg, tennxb,bv;
    int gia;
    int REQUEST_CODE_FOLDER = 432;
    ProgressDialog mProgressDialog;
    StorageReference mStorageReference;
    DatabaseReference table_book;
    String mStoragePath = " All_Image_upload/";
    Uri mFilePathUri;
    String mbooks = "Books";
    FirebaseDatabase database;
    DatabaseReference mdatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update__sach);
        imgsachup = findViewById(R.id.imgsachup);
        edtGiaup = findViewById(R.id.edtGiaup);
        edtNXBup = findViewById(R.id.edtNXBup);
        edttensachup = findViewById(R.id.edttensachup);
        edtTacGiaup = findViewById(R.id.edtTacGiaup);
        btnADDsup = findViewById(R.id.btnADDsup);
        edtbvup = findViewById(R.id.edtbvup);
        btnCanceldlsup = findViewById(R.id.btnCanceldlsup);
        spnersachup = findViewById(R.id.spnersachup);
        imgbackbook = findViewById(R.id.imgbackbook);

        imgbackbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mProgressDialog = new ProgressDialog(this);

        database = FirebaseDatabase.getInstance();
        mdatabaseReference = database.getReference();
        mStorageReference = getInstance().getReference();
        Bundle intent = getIntent().getExtras();
        if (intent != null) {
            masach=intent.getString("bookid");
            tentl = intent.getString("tentl");
            images = intent.getString("image");
            tensach = intent.getString("tensach");
            gia = intent.getInt("gia", 0);
            tennxb = intent.getString("tenNXB");
            tentg = intent.getString("tentg");
            bv= intent.getString("baove");
            edtbvup.setText(bv);
            edtNXBup.setText(tennxb);
            edtTacGiaup.setText(tentg);
            edtGiaup.setText(String.valueOf(gia));
            edttensachup.setText(tensach);
            Picasso.get().load(images).into(imgsachup);

        }


        mdatabaseReference.child("Categories").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                final List<String> tentllist = new ArrayList<String>();

                for (DataSnapshot addressSnapshot : dataSnapshot.getChildren()) {
                    String tentl = addressSnapshot.child("name").getValue(String.class);
                    if (tentl != null) {
                        tentllist.add(tentl);
                    }
                }

                spnersachup = findViewById(R.id.spnersachup);
                ArrayAdapter<String> addressAdapter = new ArrayAdapter<String>(Update_SachActivity.this, android.R.layout.simple_spinner_item, tentllist);
                addressAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spnersachup.setAdapter(addressAdapter);

                spnersachup.setSelection(getindex(spnersachup,tentl));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        imgsachup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");

                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "select image"), REQUEST_CODE_FOLDER);
            }
        });

        btnADDsup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                beginUpdate();
            }
        });

        btnCanceldlsup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public int getindex(Spinner spinner, String item) {
        int index = 0;
        for (int i = 0; i < spnersachup.getCount(); i++) {
            if (spnersachup.getItemAtPosition(i).equals(item)) {
                index = i;
                break;
            }
        }

        return index;

    }



    public void beginUpdate(){

        mProgressDialog.setMessage("updating..");
        mProgressDialog.show();
        deletpreviuosImge();
    }

    public void deletpreviuosImge(){
        StorageReference mPictrueRe= getInstance().getReferenceFromUrl(images);
        mPictrueRe.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                uploadNewImage();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Update_SachActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                mProgressDialog.dismiss();

            }
        });
    }

    private void uploadNewImage() {

        String imagename= System.currentTimeMillis()+ ".png";
        StorageReference storageReference2= mStorageReference.child(mStoragePath+imagename);
        //get bitmap from img
        Bitmap bitmap = ((BitmapDrawable)imgsachup.getDrawable()).getBitmap();
        ByteArrayOutputStream baos= new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.PNG,100,baos);
        byte[]data= baos.toByteArray();
        UploadTask uploadTask= storageReference2.putBytes(data);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(Update_SachActivity.this, "new image", Toast.LENGTH_SHORT).show();

                //get url of new upload image
                Task<Uri>uriTask= taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isSuccessful());
                Uri dowloadUri= uriTask.getResult();

                //upload datawwith new data
                updatedatabas(dowloadUri.toString());

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Update_SachActivity.this,e.getMessage(), Toast.LENGTH_SHORT).show();
                mProgressDialog.dismiss();
            }
        });

    }

    private void updatedatabas(final String s) {


        final String tentl= spnersachup.getSelectedItem().toString();
        final String tensach= edttensachup.getText().toString();
        final  String NXB= edtNXBup.getText().toString();
        final  String TenTG= edtTacGiaup.getText().toString();
        final String bv1= edtbvup.getText().toString();
        final int gia= Integer.parseInt(edtGiaup.getText().toString());

        FirebaseDatabase mFirebaseDatabase= FirebaseDatabase.getInstance();
        DatabaseReference mref= mFirebaseDatabase.getReference("Books");
        Query query= mref.orderByChild("bookid").equalTo(masach);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren()){

                    ds.getRef().child("imgSach").setValue(s);
                    ds.getRef().child("name").setValue(tensach);
                    ds.getRef().child("tenTL").setValue(tentl);
                    ds.getRef().child("tenNXB").setValue(NXB);
                    ds.getRef().child("tenTG").setValue(TenTG);
                    ds.getRef().child("baove").setValue(bv1);

                    ds.getRef().child("gia").setValue(gia);

                }

                mProgressDialog.dismiss();
                Toast.makeText(Update_SachActivity.this, "update tc", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE_FOLDER
                && resultCode== RESULT_OK
                && data != null
                && data.getData()!= null){
            mFilePathUri= data.getData();
            try {
                //getting select media
                Bitmap bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),mFilePathUri);

                //getting select image intobitmap
                imgsachup.setImageBitmap(bitmap);
            }catch (Exception e){
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
