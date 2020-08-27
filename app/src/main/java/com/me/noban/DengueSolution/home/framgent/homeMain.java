package com.me.noban.DengueSolution.home.framgent;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.me.noban.DengueSolution.Cause;
import com.me.noban.DengueSolution.DengueFeverDisease;
import com.me.noban.DengueSolution.Prevention;
import com.me.noban.DengueSolution.R;
import com.me.noban.DengueSolution.SignAndSymptoms;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomButtons.TextInsideCircleButton;
import com.nightonke.boommenu.BoomMenuButton;
import com.smarteist.autoimageslider.SliderLayout;
import com.smarteist.autoimageslider.SliderView;

public class homeMain extends Fragment {
    SliderLayout sliderLayout;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final View view = inflater.inflate(R.layout.homemainfrag, container, false);
        sliderLayout = view.findViewById(R.id.slider_image);
        sliderLayout.setIndicatorAnimation(SliderLayout.Animations.FILL); //set indicator animation by using SliderLayout.Animations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderLayout.setScrollTimeInSec(1); //set scroll delay in seconds :

        setSliderViews();

        BoomMenuButton bmb = view.findViewById(R.id.bmb);
        for (int i = 0; i < bmb.getPiecePlaceEnum().pieceNumber(); i++) {

            if (i == 0) {
                TextInsideCircleButton.Builder builder = new TextInsideCircleButton.Builder()
                        .listener(new OnBMClickListener() {


                            @Override
                            public void onBoomButtonClick(int index) {

                                // When the boom-button corresponding this builder is clicked.
                                //  Toast.makeText(homeMain.this, "Clicked " + index, Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(getActivity(), DengueFeverDisease.class);
                                startActivity(intent);


                            }
                        })

                        .normalImageRes(R.drawable.diseas)
                        .normalText("Dengue Fever Disease")
                        .textPadding(new Rect(0, 15, 0, 0))
                        .imagePadding(new Rect(10, 10, 10, 10));
                bmb.addBuilder(builder);

                //Intent intent = new Intent(getActivity() , Cause.class);
                // startActivity(intent);


            } else if (i == 1) {


                TextInsideCircleButton.Builder builder = new TextInsideCircleButton.Builder()
                        .listener(new OnBMClickListener() {


                            @Override
                            public void onBoomButtonClick(int index) {

                                // When the boom-button corresponding this builder is clicked.
                                //   Toast.makeText(homeMain.this, "Clicked " + index, Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(getActivity(), Prevention.class);
                                startActivity(intent);


                            }
                        })

                        .normalImageRes(R.drawable.preventionn)
                        .normalText("Prevention")
                        .textPadding(new Rect(0, 15, 0, 0))
                        .imagePadding(new Rect(10, 10, 10, 10));

                bmb.addBuilder(builder);
            } else if (i == 2) {


                TextInsideCircleButton.Builder builder = new TextInsideCircleButton.Builder()
                        .listener(new OnBMClickListener() {


                            @Override
                            public void onBoomButtonClick(int index) {

                                // When the boom-button corresponding this builder is clicked.
                                //  Toast.makeText(homeMain.this, "Clicked " + index, Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getActivity(), SignAndSymptoms.class);
                                startActivity(intent);


                            }
                        })

                        .normalImageRes(R.drawable.symptoms)
                        .normalText("Sign And Symptoms")
                        .textPadding(new Rect(0, 10, 0, 0))
                        .imagePadding(new Rect(10, 10, 10, 10))
                        .shadowEffect(true);

                bmb.addBuilder(builder);
            } else {


                TextInsideCircleButton.Builder builder = new TextInsideCircleButton.Builder()
                        .listener(new OnBMClickListener() {


                            @Override
                            public void onBoomButtonClick(int index) {

                                // When the boom-button corresponding this builder is clicked.
                                //Toast.makeText(this, "Clicked " + index, Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getActivity(), Cause.class);
                                startActivity(intent);


                            }
                        })

                        .normalImageRes(R.drawable.causee)
                        .normalText("Cause")
                        .textPadding(new Rect(0, 15, 0, 0))
                        .imagePadding(new Rect(10, 10, 10, 10));

                bmb.addBuilder(builder);
            }
        }


        return view;

    }


    private void setSliderViews() {

        for (int i = 0; i <= 3; i++) {

            SliderView sliderView = new SliderView(getContext());

            switch (i) {
                case 0:
                    sliderView.setImageDrawable(R.drawable.a);
                    break;
                case 1:
                    sliderView.setImageDrawable(R.drawable.e);
                    break;
                case 2:
                    sliderView.setImageDrawable(R.drawable.f);
                    break;
                case 3:
                    sliderView.setImageDrawable(R.drawable.g);
                    break;
            }

            sliderView.setImageScaleType(ImageView.ScaleType.CENTER_CROP);

            final int finalI = i;
            sliderView.setOnSliderClickListener(new SliderView.OnSliderClickListener() {
                @Override
                public void onSliderClick(SliderView sliderView) {
                    // Toast.makeText(MainActivity.this, "This is slider " + (finalI + 1), Toast.LENGTH_SHORT).show();
                }
            });

            //at last add this view in your layout :
            sliderLayout.addSliderView(sliderView);
        }
    }

}
