package com.example.pocklet;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import android.widget.Button;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BottomSheetAddDialog extends BottomSheetDialogFragment {

    private BottomSheetListener mListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bottom_sheet_layout, container, false);
        CardView incomeButton = v.findViewById(R.id.addIncCard);
        CardView expenseButton = v.findViewById(R.id.addExpCard);

        incomeButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                mListener.onButtonClick(1);
                dismiss();
            }
        });

        expenseButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                mListener.onButtonClick(2);
                dismiss();
            }
        });
        return v;
    }

    public interface BottomSheetListener {
        void onButtonClick(int start);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mListener = (BottomSheetListener) context;
        } catch(ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    " must implement BottomSheetListener");
        }
    }
}
