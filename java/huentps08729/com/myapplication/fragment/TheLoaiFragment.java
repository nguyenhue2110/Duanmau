package huentps08729.com.myapplication.fragment;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.ColorSpace;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import huentps08729.com.myapplication.ItemClickListener;
import huentps08729.com.myapplication.R;
import huentps08729.com.myapplication.ThemSachActivity;
import huentps08729.com.myapplication.ThemtheloaiActivity;
import huentps08729.com.myapplication.Update_theloai_Activity;
import huentps08729.com.myapplication.adapter.theloaiadapter;
import huentps08729.com.myapplication.model.TheLoai;

import static androidx.recyclerview.widget.RecyclerView.*;
import static com.google.firebase.storage.FirebaseStorage.getInstance;


public class TheLoaiFragment extends Fragment {
    RecyclerView rycle1;
    FirebaseDatabase database;

        FloatingActionButton fab;
    EditText edttendl, edtmotadl, edtvitridl;
    Button btnuploadd, btnChose,btnadddl,btncanceldl;
    ImageView imgdl;
    int REQUEST_CODE_FOLDER=432;
    //folder path for firebase
    String mStoragePath=" All_Image_upload/";
    //Root databasename for firebase database
    String mdatabasePath="Categories";
    //Creat URI
    Uri mFilePathUri;
    //creating Storageference and DataBase referrence
    StorageReference mStorageReference;
    DatabaseReference mdatabaseReference;
    ProgressDialog mProgressDialog;
    FirebaseStorage storage= getInstance();

        FirebaseRecyclerAdapter<TheLoai, theloaiadapter>firebaseRecyclerAdapter;
        FirebaseRecyclerOptions<TheLoai>options;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_the_loai, container, false);
        fab= view.findViewById(R.id.fab);

        fab.setBackgroundTintList(getResources().getColorStateList(R.color.white));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               dialogtl();
            }
        });
        rycle1 = view.findViewById(R.id.rycle1);
        rycle1.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rycle1.setLayoutManager(layoutManager);
//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(), layoutManager.getOrientation());
//        rycle1.addItemDecoration(dividerItemDecoration);
        database = FirebaseDatabase.getInstance();
        mdatabaseReference = database.getReference("Categories");
        mStorageReference= getInstance().getReference();


        //
        mdatabaseReference= FirebaseDatabase.getInstance().getReference(mdatabasePath);
      //progress dialog
       mProgressDialog = new ProgressDialog(getContext());
//       loadTheLoai();
        showData();
        return view;

    }



    public void showData(){
        options= new FirebaseRecyclerOptions.Builder<TheLoai>().setQuery(mdatabaseReference,TheLoai.class).build();
        firebaseRecyclerAdapter= new FirebaseRecyclerAdapter<TheLoai, theloaiadapter>(options) {
            @Override
            protected void onBindViewHolder(@NonNull theloaiadapter holder, int position, @NonNull TheLoai model) {
                holder.setDetails(getContext().getApplicationContext(), model.getName(), model.getImage(), model.getMoTa(), model.getViTri());
            }

            @NonNull
            @Override
            public theloaiadapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_theloai, parent, false);
                theloaiadapter viewholder= new theloaiadapter(view);

                TextView txtTenLoai= view.findViewById(R.id.txtTenLoai);
                ImageView imgh1= view.findViewById(R.id.imgh1);
                TextView txtMota= view.findViewById(R.id.txtMota);
                TextView txtVitri= view.findViewById(R.id.txtViTri);

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
                           final String currentImage = getItem(position).getImage();
                          final String curentmota = getItem(position).getMoTa();
                           final int curentvitri = getItem(position).getViTri();
                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                            builder.setTitle("Choose an action");
                            String[] item ={ "Update", "Delete"};
                            builder.setItems(item, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int which) {
                                   if(which==0){

                                       Intent intent= new Intent(getContext(), Update_theloai_Activity.class);
                                       intent.putExtra("tentl",currentten);
                                       intent.putExtra("image",currentImage);
                                       intent.putExtra("mota",curentmota);
                                       intent.putExtra("vitri",curentvitri);
                                       startActivity(intent);
                                   }
                                   if(which==1){

                                       showdeleteDataDialog(currentten,currentImage,curentmota,curentvitri);

                                   }
                                }
                            });
                                    builder.create().show();
                        }


                    });
                return viewholder;
            }
        };


        rycle1.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();
        firebaseRecyclerAdapter.notifyDataSetChanged();
    }
    public void loadTheLoai() {
        FirebaseRecyclerOptions<TheLoai> options = new FirebaseRecyclerOptions.Builder<TheLoai>().setQuery(mdatabaseReference, TheLoai.class).build();

        FirebaseRecyclerAdapter<TheLoai, theloaiadapter> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<TheLoai, theloaiadapter>(options) {
            @Override
            protected void onBindViewHolder(theloaiadapter holder, int position, @NonNull TheLoai model) {
                holder.setDetails(getContext().getApplicationContext(), model.getName(), model.getImage(), model.getMoTa(), model.getViTri());

//            holder.setItemClickListener(new ItemClickListener() {
//                @Override
//                public void onclick(View view, int position, boolean isLongclick) {
//                    if(isLongclick){
//                    }else {
//
//                        String currentten=getItem(position).getName();
//                        String currentImage= getItem(position).getImage();
//                        String curentmota= getItem(position).getMoTa();
//                        int curentvitri= getItem(position).getViTri();
//                        showdeleteDataDialog(currentten,currentImage,curentmota,curentvitri);
//
//
//                    }
//                }
//            });

            }


            @NonNull
            @Override
            public theloaiadapter onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_theloai, parent, false);

                TextView txtTenLoai= view.findViewById(R.id.txtTenLoai);
                ImageView imgh1= view.findViewById(R.id.imgh1);
                TextView txtMota= view.findViewById(R.id.txtMota);
                TextView txtVitri= view.findViewById(R.id.txtViTri);



                theloaiadapter viewHolder= new theloaiadapter(view);


                return new theloaiadapter(view);
            }


        };

        rycle1.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();
        firebaseRecyclerAdapter.notifyDataSetChanged();
    }





    private void showdeleteDataDialog(final String currenttentl, final String currenhinh, String currenmota, int currenvitri) {
        AlertDialog.Builder builder= new AlertDialog.Builder(getContext());
        builder.setTitle("Delete");
        builder.setMessage("Are you sure to delete this?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


                Query mquery = mdatabaseReference.orderByChild("name").equalTo(currenttentl);
                mquery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot ds: dataSnapshot.getChildren()){
                            ds.getRef().removeValue();
                        }
                        Toast.makeText(getContext(), "đã xóa", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(getContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

                StorageReference mPictureRefe= getInstance().getReferenceFromUrl(currenhinh);
                mPictureRefe.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
//                        Toast.makeText(getContext(), "da xoa hinh", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        builder.create().show();


    }

    public void dialogtl(){

    final Dialog dialog = new Dialog(getContext());
    dialog.setContentView(R.layout.theloaii_dialog);
    edttendl = dialog.findViewById(R.id.edttendl);
    edtmotadl = dialog.findViewById(R.id.edtmotadl);
    edtvitridl = dialog.findViewById(R.id.edtvitridl);
    btnChose = dialog.findViewById(R.id.btnChose);

    imgdl = dialog.findViewById(R.id.imgdl);
    btnadddl = dialog.findViewById(R.id.btnADD);
    btncanceldl = dialog.findViewById(R.id.btnCanceldl);


    int width = (int) (getContext().getResources().getDisplayMetrics().widthPixels * 1);
    int height = (int) (getContext().getResources().getDisplayMetrics().heightPixels * 0.7);
    dialog.getWindow().setLayout(width, height);
    dialog.show();

        btnChose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent intent= new Intent();
            intent.setType("image/*");

            intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"select image"),REQUEST_CODE_FOLDER);
            }
        });

        btnadddl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadDaTatoFirebase();

            }
        });

        btncanceldl.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
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
                            String tentl= edttendl.getText().toString();
                            String mota= edtmotadl.getText().toString();

                            int vitri= Integer.parseInt(edtvitridl.getText().toString());

                            mProgressDialog.dismiss();

                            Toast.makeText(getContext(), "Images up load", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
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
            Toast.makeText(getContext(), "please select...", Toast.LENGTH_SHORT).show();
        }
 }
        // method to get selected image extension from file path uri
    private String getFileExtension(Uri uri) {

        ContentResolver contentResolver= getContext().getContentResolver();
        MimeTypeMap mimeTypeMap= MimeTypeMap.getSingleton();
        //returning the file extension
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE_FOLDER
                && resultCode== getActivity().RESULT_OK
                && data != null
                && data.getData()!= null){
            mFilePathUri= data.getData();
            try {
                //getting select media
                Bitmap bitmap= MediaStore.Images.Media.getBitmap(getContext().getContentResolver(),mFilePathUri);

                //getting select image intobitmap
                imgdl.setImageBitmap(bitmap);
            }catch (Exception e){
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }



}