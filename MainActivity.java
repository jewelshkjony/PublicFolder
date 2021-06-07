package com.jewel.lottieanimation;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdOptions;

public class MainActivity extends AppCompatActivity {
    Context context;
    FrameLayout frameLayout;
    LinearLayout mainLayout;
    LinearLayout imageLayout;
    LinearLayout bodyLayout;

    TextView primaryView;
    TextView secondaryView;
    RatingBar ratingBar;
    TextView tertiaryView;
    ImageView iconView;
    Button callToActionView;
    String secondaryText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();
        frameLayout = findViewById(R.id.frameLayout);

        LoadNativeAd();
    }

    private void LoadNativeAd() {
        AdLoader adLoader = new AdLoader.Builder(context, "ca-app-pub-3940256099942544/2247696110")
                .forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
                    @Override
                    public void onNativeAdLoaded(NativeAd nativeAd) {
                        mainLayout = new LinearLayout(context);
                        imageLayout = new LinearLayout(context);
                        bodyLayout = new LinearLayout(context);

                        primaryView = new TextView(context);
                        secondaryView = new TextView(context);
                        ratingBar = new RatingBar(context);
                        tertiaryView = new TextView(context);
                        iconView = new ImageView(context);
                        callToActionView = new Button(context);

                        mainLayout.setOrientation(LinearLayout.HORIZONTAL);
                        imageLayout.setOrientation(LinearLayout.VERTICAL);
                        imageLayout.setGravity(Gravity.CENTER);
                        bodyLayout.setOrientation(LinearLayout.VERTICAL);
                        mainLayout.setBackgroundColor(Color.parseColor("White"));
                        mainLayout.addView(imageLayout);
                        mainLayout.addView(bodyLayout);

                        imageLayout.addView(iconView);
                        bodyLayout.addView(primaryView, 0);
                        bodyLayout.addView(secondaryView, 1);
                        bodyLayout.addView(tertiaryView, 2);
                        bodyLayout.addView(ratingBar, 3);
                        bodyLayout.addView(callToActionView, 4);
                        bodyLayout.setPadding(5, 0, 0, 0);


                        primaryView.setText(nativeAd.getHeadline());
                        primaryView.setTypeface(Typeface.DEFAULT_BOLD);
                        primaryView.setPadding(5, 0, 0, 0);
                        primaryView.setTextColor(Color.parseColor("Black"));
                        tertiaryView.setText(nativeAd.getBody());
                        tertiaryView.setPadding(5, 0, 0, 0);
                        tertiaryView.setTextColor(Color.parseColor("Black"));
                        callToActionView.setText(nativeAd.getCallToAction());
                        callToActionView.setTextColor(Color.parseColor("White"));
                        callToActionView.setBackgroundColor(Color.parseColor("Blue"));

                        NativeAd.Image icon = nativeAd.getIcon();
                        callToActionView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                            }
                        });
                        if (icon != null) {
                            iconView.setVisibility(View.VISIBLE);
                            iconView.setImageDrawable(icon.getDrawable());
                            iconView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    startActivity(new Intent(Intent.ACTION_VIEW, icon.getUri()));
                                }
                            });
                        } else {iconView.setVisibility(View.GONE);}



                        String store = nativeAd.getStore();
                        String advertiser = nativeAd.getAdvertiser();

                        if (!TextUtils.isEmpty(store)) {
                            secondaryText = store;
                        } else if (!TextUtils.isEmpty(advertiser)) {
                            secondaryText = advertiser;
                        } else {
                            secondaryText = "";
                        }

                        Double starRating = nativeAd.getStarRating();
                        if (starRating != null && starRating > 0) {
                            secondaryView.setVisibility(View.GONE);
                            ratingBar.setVisibility(View.VISIBLE);
                            ratingBar.setRating(starRating.floatValue());
                            ratingBar.setMax(5);
                        } else {
                            secondaryView.setText(secondaryText);
                            secondaryView.setVisibility(View.VISIBLE);
                            ratingBar.setVisibility(View.GONE);
                        }
                        frameLayout.addView(mainLayout, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT));
                    }
                })
                .withAdListener(new AdListener() {
                    @Override
                    public void onAdFailedToLoad(LoadAdError adError) {}
                })
                .withNativeAdOptions(new NativeAdOptions.Builder().build())
                .build();
        adLoader.loadAd(new AdRequest.Builder().build());
    }

}

