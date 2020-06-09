package huentps08729.com.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.squareup.picasso.Target;

import java.io.ByteArrayOutputStream;

import static com.google.firebase.storage.FirebaseStorage.getInstance;

public class Update_theloai_Activity extends AppCompatActivity {
    EditText edttenup, edtmotaup, edtvitriup;
    Button btnaddup,btncancelup,btnChoseup;
    ImageView imgup,backtl;
    String ten,images,mota;
    int vitri;
    int REQUEST_CODE_FOLDER=432;
    ProgressDialog mProgressDialog;
    StorageReference mStorageReference;
    DatabaseReference mdatabaseReference;
    String mStoragePath=" All_Image_upload/";
    Uri mFilePathUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_theloai_);
        mProgressDialog = new ProgressDialog(this);
        mStorageReference= getInstance().getReference();
        String mdatabasePath="Categories";
        //
        mdatabaseReference= FirebaseDatabase.getInstance().getReference(mdatabasePath);
        //progress dialog
        mProgressDialog = new ProgressDialog(this);
        edtmotaup = findViewById(R.id.edtmotaup);
        edttenup = findViewById(R.id.edttenup);
        edtvitriup = findViewById(R.id.edtvitriup);
        imgup = findViewById(R.id.imgup);
        btnaddup = findViewById(R.id.btnADDup);
        btncancelup = findViewById(R.id.btnCancelup);
        btnChoseup= findViewById(R.id.btnChoseup);
        backtl= findViewById(R.id.imgbacktl);

        backtl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Bundle intent= getIntent().getExtras();
        if(intent!=null){
            ten= intent.getString("tentl");
            images= intent.getString("image");
            mota= intent.getString("mota");
            vitri= intent.getInt("vitri",0);
            edttenup.setText(ten);
            edtmotaup.setText(mota);
            edtvitriup.setText(String.valueOf(vitri));
            Picasso.get().load(images).into( imgup);






        }

        imgup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent();
                intent.setType("image/*");

                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"select image"),REQUEST_CODE_FOLDER);
            }
        });

        btnaddup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            beginUpdate();
            }
        });

        btncancelup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
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
                  Toast.makeText(Update_theloai_Activity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                  mProgressDialog.dismiss();

              }
          });
    }

    private void uploadNewImage() {

        String imagename= System.currentTimeMillis()+ ".png";
        StorageReference storageReference2= mStorageReference.child(mStoragePath+imagename);
        //get bitmap from img
        Bitmap bitmap = ((BitmapDrawable)imgup.getDrawable()).getBitmap();
        ByteArrayOutputStream baos= new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.PNG,100,baos);
        byte[]data= baos.toByteArray();
        UploadTask uploadTask= storageReference2.putBytes(data);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(Update_theloai_Activity.this, "new image", Toast.LENGTH_SHORT).show();

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
                Toast.makeText(Update_theloai_Activity.this,e.getMessage(), Toast.LENGTH_SHORT).show();
                mProgressDialog.dismiss();
            }
        });

    }

    private void updatedatabas(final String s) {
        final String tentl= edttenup.getText().toString();
        final String mota= edtmotaup.getText().toString();
        final int vitri= Integer.parseInt(edtvitriup.getText().toString());

        FirebaseDatabase mFirebaseDatabase= FirebaseDatabase.getInstance();
        DatabaseReference mref= mFirebaseDatabase.getReference("Categories");
        Query query= mref.orderByChild("name").equalTo(ten);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    ds.getRef().child("name").setValue(tentl);
                    ds.getRef().child("moTa").setValue(mota);
                    ds.getRef().child("image").setValue(s);
                    ds.getRef().child("viTri").setValue(vitri);
                }

                mProgressDialog.dismiss();
                Toast.makeText(Update_theloai_Activity.this, "update tc", Toast.LENGTH_SHORT).show();
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
                imgup.setImageBitmap(bitmap);
            }catch (Exception e){
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
