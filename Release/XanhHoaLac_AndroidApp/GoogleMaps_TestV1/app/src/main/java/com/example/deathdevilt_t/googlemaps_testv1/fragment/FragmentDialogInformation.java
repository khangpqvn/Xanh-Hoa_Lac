package com.example.deathdevilt_t.googlemaps_testv1.fragment;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.deathdevilt_t.googlemaps_testv1.R;

/**
 * Created by DeathDevil.T_T on 27-May-17.
 */

public class FragmentDialogInformation extends DialogFragment {
    private ImageView ImgView_TestInfor;
    public void EditNameDialog() {
        // Empty constructor required for DialogFragment
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog_information, container);

        getDialog().setTitle("Thông tin ứng dụng");

        return view;
    }
}
