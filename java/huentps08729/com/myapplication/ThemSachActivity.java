package huentps08729.com.myapplication;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
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
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

import huentps08729.com.myapplication.adapter.SachAdapter;
import huentps08729.com.myapplication.adapter.theloaiadapter;
import huentps08729.com.myapplication.model.Sach;
import huentps08729.com.myapplication.model.TheLoai;

import static com.google.firebase.storage.FirebaseStorage.getInstance;

public class ThemSachActivity extends AppCompatActivity {
    RecyclerView recycl2;
    ImageView imgsach;
    EditText edttensach, edtNXB, edtTacGia, edtGia,edtmasach,edtbv1;
    Spinner spnersach1;
    Button btnADDs, btnCanceldls;
    ArrayList<TheLoai> theLoaiArrayList;
    DatabaseReference mdatabaseReference;

    FirebaseDatabase database;
    StorageReference mStorageReference;
    DatabaseReference sach1;
    int REQUEST_CODE_FOLDER=432;
    //folder path for firebase
    String mStoragePath=" All_Image_upload/";
    //Root databasename for firebase database
    String table_sach="Books";
    //Creat URI
    Uri mFilePathUri;
    //creating Storageference and DataBase referrence

    ProgressDialog mProgressDialog;
    FirebaseStorage storage= getInstance();


    FirebaseRecyclerAdapter<Sach, SachAdapter>firebaseRecyclerAdapter;
    FirebaseRecyclerOptions<Sach>options;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_sach);
        imgsach = findViewById(R.id.imgsach);
        edttensach = findViewById(R.id.edttensach);
        edtNXB = findViewById(R.id.edtNXB);
        edtGia = findViewById(R.id.edtGia);
        edtTacGia = findViewById(R.id.edtTacGia);
        edtmasach = findViewById(R.id.edtMasach);
        edtbv1=findViewById(R.id.edtbv1);
        spnersach1 = findViewById(R.id.spnersach1);
        btnADDs = findViewById(R.id.btnADDs);
        btnCanceldls = findViewById(R.id.btnCanceldls);


        database = FirebaseDatabase.getInstance();
        mdatabaseReference = database.getReference();
        mStorageReference= getInstance().getReference();
        sach1= database.getReference();

//        mdatabaseReference= FirebaseDatabase.getInstance().getReference(mdatabasePath);
        //progress dialog
        mProgressDialog = new ProgressDialog(this);
        imgsach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");

                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "select image"), REQUEST_CODE_FOLDER);
            }
        });

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

                Spinner spnersach1 = (Spinner) findViewById(R.id.spnersach1);
                ArrayAdapter<String> addressAdapter = new ArrayAdapter<String>(ThemSachActivity.this, android.R.layout.simple_spinner_item, tentllist);
                addressAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spnersach1.setAdapter(addressAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        showData();

    }



    public void showData(){
        options= new FirebaseRecyclerOptions.Builder<Sach>().setQuery(sach1,Sach.class).build();
        firebaseRecyclerAdapter= new FirebaseRecyclerAdapter<Sach, SachAdapter>(options) {
            @Override
            protected void onBindViewHolder(@NonNull SachAdapter holder, int position, @NonNull Sach model) {
                holder.setDetails(ThemSachActivity.this.getApplicationContext(), model.getImgSach(), model.getName(), model.getTenTL(), model.getTenNXB(),model.getTenTG(),model.getGia(),model.getBaove());
            }

            @NonNull
            @Override
            public SachAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sach, parent, false);
                SachAdapter viewholder= new SachAdapter(view);

                TextView txttentl1= view.findViewById(R.id.txttentl1);
                TextView txttensach1= view.findViewById(R.id.txttensach1);
                TextView txtNXB= view.findViewById(R.id.txtNXB);
                TextView txtTG= view.findViewById(R.id.txtTG);
                TextView txtGia= view.findViewById(R.id.txtGia);
                ImageView imgsach1= view.findViewById(R.id.imgsach);
                viewholder.setOnClickListener(new theloaiadapter.ClickListener(){

                    @Override
                    public void onItemClick(View view, int position) {
//                            String currentten=getItem(position).getName();
//                            String currentImage= getItem(position).getImage();
//                            String curentmota= getItem(position).getMoTa();
//                            int curentvitri= getItem(position).getViTri();
//                            showdeleteDataDialog(currentten,currentImage,curentmota,curentvitri);

                    }

                    @Override
                    public void onItemLongClick(View view, int position) {

                        final   String currentten = getItem(position).getName();
                        final String currentImage = getItem(position).getImgSach();
                        final String curenttl = getItem(position).getTenTL();
                        final String curentNXB = getItem(position).getTenNXB();
                        final String curentTG = getItem(position).getTenTG();

                        final int curentgia = getItem(position).getGia();
                        AlertDialog.Builder builder = new AlertDialog.Builder(ThemSachActivity.this);

                        builder.setTitle("Choose an action");
                        String[] item ={ "Update", "Delete"};
                        builder.setItems(item, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                if(which==0){

//                                    Intent intent= new Intent(ThemSachActivity.this, Update_theloai_Activity.class);
//                                    intent.putExtra("tentl",currentten);
//                                    intent.putExtra("image",currentImage);
//                                    intent.putExtra("mota",curentmota);
//                                    intent.putExtra("vitri",curentvitri);
//                                    startActivity(intent);
                                }
                                if(which==1){

//                                    showdeleteDataDialog(currentten,currentImage,curentmota,curentvitri);

                                }
                            }
                        });
                        builder.create().show();
                    }


                });
                return viewholder;
            }
        };



    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_FOLDER
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {
            mFilePathUri = data.getData();
            try {
                //getting select media
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), mFilePathUri);

                //getting select image intobitmap
                imgsach.setImageBitmap(bitmap);
            } catch (Exception e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
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
                            String masach=edtmasach.getText().toString();
                            String tentl= spnersach1.getSelectedItem().toString();
                            String tensach= edttensach.getText().toString();
                            String NXB= edtNXB.getText().toString();
                            String TenTG= edtTacGia.getText().toString();
                            String bv= edtbv1.getText().toString();
                            int gia= Integer.parseInt(edtGia.getText().toString());

                            mProgressDialog.dismiss();

                            Toast.makeText(ThemSachActivity.this, "Images up load", Toast.LENGTH_SHORT).show();
                            Sach sach= new Sach(masach,dowload.toString(),tensach,tentl,NXB,TenTG,gia,bv);
                            //getting image uload id
                            String imageuploadId= sach1.push().getKey();
                            // add image upload child element into databaser
                            sach1.child(imageuploadId).setValue(sach);



                        }
                    });



                }
            })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            mProgressDialog.dismiss();
                            Toast.makeText(ThemSachActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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
            Toast.makeText(ThemSachActivity.this, "please select...", Toast.LENGTH_SHORT).show();
        }
    }
    // method to get selected image extension from file path uri
    private String getFileExtension(Uri uri) {

        ContentResolver contentResolver= getContentResolver();
        MimeTypeMap mimeTypeMap= MimeTypeMap.getSingleton();
        //returning the file extension
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

}
