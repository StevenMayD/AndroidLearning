package com.example.androidlearning.view.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.androidlearning.R;

public class FragmentWeChat extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private String mTextStr;
    View rootView;

    public FragmentWeChat() {
        // Required empty public constructor
    }

    public static FragmentWeChat newInstance(String param1) {
        FragmentWeChat fragment = new FragmentWeChat();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTextStr = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_we_chat, container, false);
        }
        initView();
        return rootView;
    }

    private void initView() {
        TextView textView = rootView.findViewById(R.id.textView);
        textView.setText(mTextStr);
    }
}