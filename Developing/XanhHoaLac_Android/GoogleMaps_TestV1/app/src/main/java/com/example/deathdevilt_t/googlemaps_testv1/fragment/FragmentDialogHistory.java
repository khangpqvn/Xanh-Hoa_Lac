package com.example.deathdevilt_t.googlemaps_testv1.fragment;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.deathdevilt_t.googlemaps_testv1.R;

/**
 * Created by DeathDevil.T_T on 28-May-17.
 */

public class FragmentDialogHistory  extends DialogFragment {
    private ListView listView_History;
    public void EditNameDialog() {
        // Empty constructor required for DialogFragment
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog_history, container);
        listView_History = (ListView) view.findViewById(R.id.listView_History);
        getDialog().setTitle("Lịch sử thao tác");

        return view;
    }
}
