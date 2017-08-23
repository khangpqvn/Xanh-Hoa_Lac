package com.example.deathdevilt_t.googlemaps_testv1.FragmentSetting;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.deathdevilt_t.googlemaps_testv1.R;

/**
 * Created by chtnnnmtgkyp on 6/26/2017.
 */

public class PhoneRecharge extends Fragment implements View.OnClickListener{

    private EditText edt_numberCard;
    private Button btn_acceptCard;
    private Button btn_Cancel;
    private OnABCClick onABCClick;
    private LinearLayout linear_focus;
    private String phoneNumber="";

    Context context;

    public PhoneRecharge(String phoneNumber,OnABCClick onABCClick) {
        this.onABCClick = onABCClick;
        this.phoneNumber = phoneNumber;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.phone_recharge,container,false);
        initViews(view);
        initListeners();

        return view;
    }

    private void initViews(View view) {
        edt_numberCard = (EditText) view.findViewById(R.id.edit_numberCard);
        btn_acceptCard = (Button) view.findViewById(R.id.btn_nap);
        btn_Cancel = (Button) view.findViewById(R.id.btn_cancel);
        linear_focus = (LinearLayout) view.findViewById(R.id.linear_focus);
        context = getContext();

        onTapOutsideBehaviour(linear_focus);

    }
    private void onTapOutsideBehaviour(View view) {
        if(!(view instanceof EditText) || !(view instanceof Button)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(edt_numberCard);
                    return false;
                }
            });
        }
    }

    private static void hideSoftKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    private void initListeners() {
        btn_acceptCard.setOnClickListener(this);
        btn_Cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_nap:
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+"*136*"+phoneNumber+"*"+edt_numberCard.getText().toString()+Uri.encode("#")));
                Toast.makeText(context, phoneNumber, Toast.LENGTH_SHORT).show();
                startActivity(intent);
                break;
            case R.id.btn_cancel:
                onABCClick.OnABCClick();
                break;
        }
    }
}
