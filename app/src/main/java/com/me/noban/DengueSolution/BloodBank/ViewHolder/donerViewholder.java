package com.me.noban.DengueSolution.BloodBank.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.me.noban.DengueSolution.R;

public class donerViewholder extends RecyclerView.ViewHolder {

    public TextView Name, BloodGroup, Location;
    public donerViewholder itemclicklistener;

    public donerViewholder(View itemView) {
        super(itemView);

        Name = (TextView) itemView.findViewById(R.id.name_b_donor);
        BloodGroup = (TextView) itemView.findViewById(R.id.Group_b_donor);
        Location = (TextView) itemView.findViewById(R.id.location_b_donor);

    }
}
