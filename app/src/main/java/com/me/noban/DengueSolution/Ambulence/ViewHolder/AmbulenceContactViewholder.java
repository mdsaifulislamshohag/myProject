package com.me.noban.DengueSolution.Ambulence.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.me.noban.DengueSolution.BloodBank.ViewHolder.donerViewholder;
import com.me.noban.DengueSolution.R;

public class AmbulenceContactViewholder extends RecyclerView.ViewHolder {

    public TextView Name, location, contactnumber;
    public donerViewholder itemclicklistener;
    public ImageView callNow;

    public AmbulenceContactViewholder(View itemView) {
        super(itemView);

        Name = itemView.findViewById(R.id.Ambulence_name);
        location = itemView.findViewById(R.id.Ambulence_location);
        contactnumber = itemView.findViewById(R.id.phoneNum_ambulence);
        callNow = itemView.findViewById(R.id.callnowAmbulence);
        //  contactnumber=itemView.findViewById(R.id.Ambulence_num);


    }
}
