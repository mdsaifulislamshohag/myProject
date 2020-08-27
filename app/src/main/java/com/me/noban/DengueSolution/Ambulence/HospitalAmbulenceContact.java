package com.me.noban.DengueSolution.Ambulence;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.me.noban.DengueSolution.Ambulence.ViewHolder.AmbulenceContactViewholder;
import com.me.noban.DengueSolution.Ambulence.model.ambulenceContactmodel;
import com.me.noban.DengueSolution.R;

public class HospitalAmbulenceContact extends Fragment {

    RecyclerView Mypost;
    RecyclerView.LayoutManager layoutManager;
    FirebaseDatabase database;
    DatabaseReference Mypost_reference, user;
    FirebaseRecyclerAdapter<ambulenceContactmodel, AmbulenceContactViewholder> adapter;
    ImageButton search;
    EditText et;

    public static void hideKeyboard(Activity activity) {
        View view = activity.findViewById(android.R.id.content);
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final View view = inflater.inflate(R.layout.activity_hospital_ambulence_contact, container, false);
        database = FirebaseDatabase.getInstance();
        Mypost_reference = database.getReference("data").child("0").child("ambulance_contacts");
        Mypost = (RecyclerView) view.findViewById(R.id.AmbuleceContactRecykler);
        //     liscatagory.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        Mypost.setLayoutManager(layoutManager);
        loadMypost();
        //  search =view.findViewById(R.id.Ambulencesearchbutton);
        et = view.findViewById(R.id.et_search);
        et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    hideKeyboard(getActivity());
                    loadsearchMypost(et.getText().toString());
                    return true;
                }
                return false;
            }
        });
//        search.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                loadsearchMypost(et.getText().toString());
//            }
//        });
        return view;

    }

    private void loadMypost() {
        adapter = new FirebaseRecyclerAdapter<ambulenceContactmodel, AmbulenceContactViewholder>(ambulenceContactmodel.class, R.layout.ambulence_contact_card, AmbulenceContactViewholder.class, Mypost_reference) {
            @Override
            protected void populateViewHolder(AmbulenceContactViewholder viewHolder, final ambulenceContactmodel model, int position) {
                viewHolder.Name.setText(model.getName());
                viewHolder.location.setText(model.getLocation());
                viewHolder.contactnumber.setText(model.getPhone());
                viewHolder.callNow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        call(model.getPhone());
                    }
                });


                //   viewHolder.contactnumber.setText(model.getPhone());
            }


        };
        adapter.notifyDataSetChanged();
        Mypost.setAdapter(adapter);
    }

    private void call(String phone) {
        try {
            if (Build.VERSION.SDK_INT > 22) {
                if (ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling

                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, 101);

                    return;
                }

                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + phone));
                startActivity(callIntent);

            } else {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + phone));
                startActivity(callIntent);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void loadsearchMypost(final String searchtext) {

        if (!searchtext.equals("")) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Query firebasequery = Mypost_reference.orderByChild("location").startAt(searchtext).endAt(searchtext + "\uf8ff");

                    adapter = new FirebaseRecyclerAdapter<ambulenceContactmodel, AmbulenceContactViewholder>(ambulenceContactmodel.class, R.layout.ambulence_contact_card, AmbulenceContactViewholder.class, firebasequery) {

                        @Override
                        protected void populateViewHolder(AmbulenceContactViewholder viewHolder, final ambulenceContactmodel model, int position) {
                            viewHolder.Name.setText(model.getName());
                            viewHolder.location.setText(model.getLocation());
                            viewHolder.contactnumber.setText(model.getPhone());
                            viewHolder.callNow.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    call(model.getPhone());
                                }
                            });
                            //   viewHolder.contactnumber.setText(model.getPhone());
                        }
                    };
                    adapter.notifyDataSetChanged();
                    Mypost.setAdapter(adapter);
                }
            }, 2000);
        } else {
            loadMypost();
            Toast.makeText(getActivity().getApplicationContext(), "Please fill search input", Toast.LENGTH_SHORT).show();
        }


    }
}
