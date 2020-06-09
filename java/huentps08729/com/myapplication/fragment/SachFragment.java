package huentps08729.com.myapplication.fragment;


import android.app.AlertDialog;
import android.app.Dialog;
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
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

import huentps08729.com.myapplication.R;
import huentps08729.com.myapplication.ThemSachActivity;
import huentps08729.com.myapplication.Update_SachActivity;
import huentps08729.com.myapplication.Update_theloai_Activity;
import huentps08729.com.myapplication.adapter.SachAdapter;
import huentps08729.com.myapplication.adapter.theloaiadapter;
import huentps08729.com.myapplication.model.Sach;
import huentps08729.com.myapplication.model.TheLoai;

import static com.google.firebase.storage.FirebaseStorage.getInstance;

/**
 * A simple {@link Fragment} subclass.
 */
public class SachFragment extends Fragment {
    ImageView imgsach;
    EditText edttensach, edtNXB, edtTacGia, edtGia,edtMasach,edtbv;
    Spinner spnersach;
    Button btnADDs, btnCanceldls;
    RecyclerView recycl2;
    FloatingActionButton fab2;
    DatabaseReference mdatabaseReference;
    FirebaseDatabase database;
    StorageReference mStorageReference;
   DatabaseReference table_book;
    int REQUEST_CODE_FOLDER = 432;
    Uri mFilePathUri;
    //folder path for firebase
    String mStoragePath=" All_Image_upload/";
    //Root databasename for firebase database
    String mdatabasePath="Categories";
    String mbooks="Books";
    ProgressDialog mProgressDialog;


    FirebaseRecyclerAdapter<Sach, SachAdapter>firebaseadapter;
    FirebaseRecyclerOptions<Sach>option;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_sach2, container, false);
        recycl2= view.findViewById(R.id.rycle2);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recycl2.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(), layoutManager.getOrientation());
        recycl2.addItemDecoration(dividerItemDecoration);
        fab2= view.findViewById(R.id.fab2);
        fab2.setBackgroundTintList(getResources().getColorStateList(R.color.white));
        database = FirebaseDatabase.getInstance();
        mdatabaseReference = database.getReference();

        table_book= database.getReference("Books");

        mStorageReference= getInstance().getReference();
        table_book= FirebaseDatabase.getInstance().getReference(mbooks);
//        mdatabaseReference= FirebaseDatabase.getInstance().getReference(mdatabasePath);
        //progress dialog
        mProgressDialog = new ProgressDialog(getContext());

        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              dialogsach();
        }
        });

        showData();
        return view;
    }




    public void dialogsach(){

        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_sach);
        imgsach = dialog.findViewById(R.id.imgsach);
        edttensach = dialog.findViewById(R.id.edttensach);
        edtNXB = dialog.findViewById(R.id.edtNXB);
        edtGia = dialog.findViewById(R.id.edtGia);
        edtTacGia = dialog.findViewById(R.id.edtTacGia);
        edtMasach = dialog.findViewById(R.id.edtMasach);
        spnersach = dialog.findViewById(R.id.spnersach);

        edtbv = dialog.findViewById(R.id.edtbv);
        btnADDs = dialog.findViewById(R.id.btnADDs);
        btnCanceldls = dialog.findViewById(R.id.btnCanceldls);

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

                    spnersach=dialog.findViewById(R.id.spnersach);
                    ArrayAdapter<String> addressAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, tentllist);
                    addressAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spnersach.setAdapter(addressAdapter);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
        });

        int width = (int) (getContext().getResources().getDisplayMetrics().widthPixels * 1);
        int height = (int) (getContext().getResources().getDisplayMetrics().heightPixels * 1);
        dialog.getWindow().setLayout(width, height);
        dialog.show();



        imgsach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent();
                intent.setType("image/*");

                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"select image"),REQUEST_CODE_FOLDER);
            }
        });

        btnADDs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadDaTatoFirebase();

            }
        });

        btnCanceldls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


    }



    public void showData(){
        option= new FirebaseRecyclerOptions.Builder<Sach>().setQuery(table_book,Sach.class).build();
        firebaseadapter= new FirebaseRecyclerAdapter<Sach, SachAdapter>(option) {
            @Override
            protected void onBindViewHolder(@NonNull SachAdapter holder, int position, @NonNull Sach model) {
                holder.setDetails(getContext().getApplicationContext(), model.getImgSach(), model.getName(), model.getTenTL(),
                        model.getTenNXB(),model.getTenTG(),model.getGia(),model.getBaove());
            }

            @NonNull
            @Override
            public SachAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sach, parent, false);
                SachAdapter viewholder= new SachAdapter(view);

                TextView txttentl1= view.findViewById(R.id.txttentl1);
                ImageView imgsach1= view.findViewById(R.id.imgsach1);
                TextView txttensach1= view.findViewById(R.id.txttensach1);
                TextView txtNXB= view.findViewById(R.id.txtNXB);
                TextView txtTG= view.findViewById(R.id.txtTG);
                TextView txtGia= view.findViewById(R.id.txtGia);
                TextView txtbv=view.findViewById(R.id.txtbv);
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
                        final   String curentMasach = getItem(position).getBookid();
                        final   String curenttentl = getItem(position).getTenTL();
                        final String curentimgsach= getItem(position).getImgSach();
                        final String curenttenSach = getItem(position).getName();
                        final String curenttentacgia = getItem(position).getTenTG();
                        final String curenttenNXB = getItem(position).getTenNXB();
                        final String edtbv = getItem(position).getBaove();

                        final int curentgia = getItem(position).getGia();
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                        builder.setTitle("Choose an action");
                        String[] item ={ "Update", "Delete"};
                        builder.setItems(item, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                if(which==0){

                                    Intent intent= new Intent(getContext(), Update_SachActivity.class);
                                    intent.putExtra("bookid",curentMasach);
                                    intent.putExtra("tentl",curenttentl);
                                    intent.putExtra("image",curentimgsach);
                                    intent.putExtra("tensach",curenttenSach);
                                    intent.putExtra("tenNXB",curenttenNXB);
                                    intent.putExtra("tentg",curenttentacgia);
                                    intent.putExtra("baove",edtbv);
                                    intent.putExtra("gia",curentgia);

                                    startActivity(intent);
                                }
                                if(which==1){

                                    showdeleteDataDialog(curentMasach,curentimgsach,curenttenSach,curenttentl,curenttenNXB,curenttentacgia,curentgia,edtbv);

                                }
                            }
                        });
                        builder.create().show();
                    }


                });
                return viewholder;
            }
        };


        recycl2.setAdapter(firebaseadapter);
        firebaseadapter.startListening();
        firebaseadapter.notifyDataSetChanged();
    }

    private void uploadDaTatoFirebase(){

        if(mFilePathUri != null){
            //setting progress bar title
            mProgressDialog.setTitle("UpLoading..");
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

                            String masach= edtMasach.getText().toString();
                            String tentl= spnersach.getSelectedItem().toString();
                            String tensach= edttensach.getText().toString();
                            String NXB= edtNXB.getText().toString();
                            String TenTG= edtTacGia.getText().toString();
                            String baove= edtbv.getText().toString();
                            int gia= Integer.parseInt(edtGia.getText().toString());

                            mProgressDialog.dismiss();

                            Toast.makeText(getContext(), "upload successfully", Toast.LENGTH_SHORT).show();
                            Sach sach= new Sach(masach,dowload.toString(),tensach,tentl,NXB,TenTG,gia,baove);
                            //getting image uload id
                            String imageuploadId= table_book.push().getKey();
                            // add image upload child element into databaser
                            table_book.child(imageuploadId).setValue(sach);



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
                            mProgressDialog.setTitle("upload  ");

                        }
                    });
        }

        else {
            Toast.makeText(getContext(), "please select...", Toast.LENGTH_SHORT).show();
        }
    }
    // method to get selected image extension from file path uri
    private String getFileExtension(Uri uri) {

        ContentResolver contentResolver= getActivity().getContentResolver();
        MimeTypeMap mimeTypeMap= MimeTypeMap.getSingleton();
        //returning the file extension
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }



    private void showdeleteDataDialog(final String currentmasach, final String currentImage, final String currenttentl, final String curenttensach, String curentNXB, String crenttacgia, final int gia,final String bv) {
        AlertDialog.Builder builder= new AlertDialog.Builder(getContext());
        builder.setTitle("Delete");
        builder.setMessage("Are you sure to delete this?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


                Query mquery = table_book.orderByChild("bookid").equalTo(currentmasach);
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

                StorageReference mPictureRefe= getInstance().getReferenceFromUrl(currentImage);
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
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_FOLDER
                && resultCode ==getActivity(). RESULT_OK
                && data != null
                && data.getData() != null) {
            mFilePathUri = data.getData();
            try {
                //getting select media
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), mFilePathUri);

                //getting select image intobitmap
                imgsach.setImageBitmap(bitmap);
            } catch (Exception e) {
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
