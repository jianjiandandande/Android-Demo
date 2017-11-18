package com.vincent.customdemo.lockPatter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vincent.customdemo.R;


/**
 * 密码碎片
 * @author Vincent
 */

public class PasswordFragment extends Fragment implements LockPatternView.OnPatterChangeListener, View.OnClickListener {

    private LockPatternView mLockPatternView;
    private TextView mHint;
    private LinearLayout btnLayout;
    private Button commit;

    private String mPassword;


    public static final String TYPE_SETTING = "setting";
    public static final String TYPE_CHECK = "check";

    private static final String ARG_PARAM1 = "type";

    public PasswordFragment() {
    }

    public static PasswordFragment newInstance(String type) {
        PasswordFragment fragment = new PasswordFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_password, container, false);

        mHint =view.findViewById(R.id.tv_hint);
        mLockPatternView =view.findViewById(R.id.lock_view);
        btnLayout = (LinearLayout)view.findViewById(R.id.btn_layout);
        view.findViewById(R.id.btn_commit).setOnClickListener(this);

        Log.d("TAG", "onCreateView: ---jinlaile");

        if (getArguments()!=null){
            Log.d("TAG", "onCreateView: ---not null");
            if (TYPE_SETTING.equals(getArguments().getString(ARG_PARAM1))){
                btnLayout.setVisibility(View.VISIBLE);
                Log.d("TAG", "onCreateView: ---visible");
            }
        }

        mLockPatternView.setOnPatterChangeListener(this);
        return view;
    }

    @Override
    public void onPatterChange(String password) {
        mPassword = password;
        if (TextUtils.isEmpty(password)){
            mHint.setText("至少绘制4个点");
        }else {
            mHint.setText(password);
            if (getArguments()!=null){
                if (TYPE_CHECK.equals(getArguments().getString(ARG_PARAM1))){
                    SharedPreferences sp = getActivity().getSharedPreferences("sp", Context.MODE_PRIVATE);
                    //检查成功

                    if (password.equals(sp.getString("password",""))){
                        getActivity().startActivity(new Intent(getContext(),LockActivity.class));
                        getActivity().finish();
                        //检查失败
                    }else {
                        mHint.setText("密码错误");
                        mLockPatternView.resetPoint();
                    }
                }
            }
        }


    }

    @Override
    public void onPatterStart(boolean isStart) {

        if (isStart){
            mHint.setText("请绘制图案密码");
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_commit:
                SharedPreferences sp = getActivity().getSharedPreferences("sp",Context.MODE_PRIVATE);
                sp.edit().putString("password",mPassword).commit();
                getActivity().finish();
                break;
        }
    }
}