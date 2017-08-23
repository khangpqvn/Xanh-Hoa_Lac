package com.example.deathdevilt_t.googlemaps_testv1.fragment;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.deathdevilt_t.googlemaps_testv1.R;

/**
 * Created by DeathDevil.T_T on 29-May-17.
 */

public class FragmentDialogAccessError  extends DialogFragment implements View.OnClickListener{
    private TextView tView_access_error;
    private Button  btn_Access_Error_Ok;
    public void EditNameDialog() {
        // Empty constructor required for DialogFragment
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog_access_error, container);
        tView_access_error = (TextView) view.findViewById(R.id.tView_access_error);
        btn_Access_Error_Ok = (Button) view.findViewById(R.id.btn_Access_Error_Ok);
        tView_access_error.setText("Kiểm tra lại quyền truy cập tài khoản hoặc kết nối mạng!");
        btn_Access_Error_Ok.setOnClickListener(this);
        getDialog().setTitle("Thông Báo!");


        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_Access_Error_Ok:
                getDialog().dismiss();
                break;
        }
    }
}
