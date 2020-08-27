package com.me.noban.DengueSolution.checkApointmentDoctor;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class checkAppointMentViewHolder extends RecyclerView.ViewHolder {
    TextView apName, apCell;

    public checkAppointMentViewHolder(View itemView) {
        super(itemView);
        apCell = itemView.findViewById(com.me.noban.DengueSolution.R.id.check_ap_cell);
        apName = itemView.findViewById(com.me.noban.DengueSolution.R.id.check_ap_name);
    }
}
