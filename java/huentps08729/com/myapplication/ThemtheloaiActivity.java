package huentps08729.com.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import huentps08729.com.myapplication.model.TheLoai;

import static com.google.firebase.storage.FirebaseStorage.getInstance;

public class ThemtheloaiActivity extends AppCompatActivity {
    EditText edttendla, edtmotadla, edtvitridla;
    Button btnadddla,btncanceldla;
    ImageView imgdla;
    int REQUEST_CODE_FOLDER=432;
    Uri mFilePathUri;

    //folder path for firebase
    String mStoragePath=" All_Image_upload/";
    //Root databasename for firebase database
    String mdatabasePath="Categories";


    //creating Storageference and DataBase referrence
    StorageReference mStorageReference;
    DatabaseReference mdatabaseReference;
    ProgressDialog mProgressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_themtheloai);

        edttendla = findViewById(R.id.edttendla);
        edtmotadla = findViewById(R.id.edtmotadla);
        edtvitridla = findViewById(R.id.edtvitridla);

        imgdla = findViewById(R.id.imgdla);
        btnadddla = findViewById(R.id.btnADDa);
        btncanceldla = findViewById(R.id.btnCanceldla);
        mStorageReference= getInstance().getReference();

        //
        mdatabaseReference= FirebaseDatabase.getInstance().getReference(mdatabasePath);
        //progress dialog
        mProgressDialog = new ProgressDialog(this);
        imgdla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent();
                intent.setType("image/*");

                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"select image"),REQUEST_CODE_FOLDER);
            }
        });

        btnadddla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            uploadDaTatoFirebase();
            finish();
            }
        });

        btncanceldla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    private void uploadDaTatoFirebase(){
   if(mFilePathUri != null){
        //setting progress bar title
        mProgressDialog.setTitle("Image is uplad..");
        //show dialg
        mProgressDialog.show();
        //create second storageRefence
        final StorageReference storageReference2nd= mStorageReference.child(mStoragePath +
                System.currentTimeMillis()+ "." + getFileExtension(mFilePathUri));



        //adding addOnSucessListener to StorafeReference
        storageReference2nd.putFile(mFilePathUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                storageReference2nd.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        final Uri dowload= uri;

                        //get title
                        String tentl= edttendla.getText().toString();
                        String mota= edtmotadla.getText().toString();

                        int vitri= Integer.parseInt(edtvitridla.getText().toString());

                        mProgressDialog.dismiss();

                        Toast.makeText(ThemtheloaiActivity.this, "Images up load", Toast.LENGTH_SHORT).show();
                        TheLoai theLoai= new TheLoai(tentl,dowload.toString(),mota,vitri);
                        //getting image uload id
                        String imageuploadId= mdatabaseReference.push().getKey();
                        // add image upload child element into databaser
                        mdatabaseReference.child(imageuploadId).setValue(theLoai);


                    }
                });



            }
        })

                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        mProgressDialog.dismiss();
                        Toast.makeText(ThemtheloaiActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                })

                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                        mProgressDialog.setTitle("Images is upload");
                    }
                });
    }

        else {
       Toast.makeText(ThemtheloaiActivity.this, "please select...", Toast.LENGTH_SHORT).show();


   }
    }


    private String getFileExtension(Uri uri) {

        ContentResolver contentResolver= getContentResolver();
        MimeTypeMap mimeTypeMap= MimeTypeMap.getSingleton();
        //returning the file extension
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE_FOLDER
                && resultCode== ThemtheloaiActivity.RESULT_OK
                && data != null
                && data.getData()!= null){
            mFilePathUri= data.getData();
            try {
                //getting select media
                Bitmap bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),mFilePathUri);

                //getting select image intobitmap
                imgdla.setImageBitmap(bitmap);
            }catch (Exception e){
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
