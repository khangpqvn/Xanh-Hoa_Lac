package com.example.deathdevilt_t.googlemaps_testv1.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.deathdevilt_t.googlemaps_testv1.Activity.MapsActivity;
import com.example.deathdevilt_t.googlemaps_testv1.R;

/**
 * Created by chtnnnmtgkyp on 5/5/2017.
 */

public class fragmentMapsButton extends Fragment {

    private EditText inputTopImageText;
    private EditText inputBottomImageText;
    private TextView mTextView;
    private MapsActivity mainActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_maps_button, container, false);

        inputTopImageText = (EditText) view.findViewById(R.id.input_top_image_text);
        inputBottomImageText = (EditText) view.findViewById(R.id.input_bottom_image_text);
        mTextView = (TextView) view.findViewById(R.id.txtID);

        mTextView.setText(getArguments().getString("edttext"));

        Button applyButton = (Button) view.findViewById(R.id.button_apply);

        applyButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                applyText();
            }
        });

        return view;
    }

    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof MapsActivity) {
            this.mainActivity = (MapsActivity) context;
        }
    }


    private void applyText() {
        String topText = this.inputTopImageText.getText().toString();
        String bottomText = this.inputBottomImageText.getText().toString();
        String id = this.mTextView.getText().toString();
        this.mainActivity.hide();
    }
}
