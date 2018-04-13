package com.nanda.aadharreader.base;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class BaseActivity extends AppCompatActivity {

    protected LayoutInflater inflater;
    protected AppCompatActivity mActivity;
    boolean isScreenXLarge;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        mActivity = this;
        inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        setOrientation();
    }


    private void setOrientation() {
        int screen = getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK;
        if (screen == Configuration.SCREENLAYOUT_SIZE_SMALL
                || screen == Configuration.SCREENLAYOUT_SIZE_NORMAL
                || screen == Configuration.SCREENLAYOUT_SIZE_LARGE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else if (screen == Configuration.SCREENLAYOUT_SIZE_XLARGE) {
            isScreenXLarge = true;
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    public void showBackButton() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void hideBackButton() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }


    Fragment setFArgs(Fragment f, String... param) {
        Bundle args = new Bundle();
        for (int i = 0; i < param.length; i += 2) {
            args.putString(param[i], param[i + 1]);
        }
        f.setArguments(args);
        return f;
    }

    public void showToast(String input) {
        Toast.makeText(mActivity, "" + input, Toast.LENGTH_LONG).show();
    }

    Bundle setFArgs(String... param) {
        Bundle args = new Bundle();
        for (int i = 0; i < param.length; i += 2) {
            args.putString(param[i], param[i + 1]);
        }
        return args;
    }


    Fragment getFragment(int id) {
        return getSupportFragmentManager().findFragmentById(id);
    }

    void setText(String input, int id) {
        ((TextView) findViewById(id)).setText(input);
    }


    String checkNull(JSONObject obj, String key) {
        return obj.isNull(key) ? "" : obj.optString(key);
    }

    public String setParam(String... param) {
        JSONObject objParam = new JSONObject();
        for (int i = 0; i < param.length; i += 2) {
            try {
                objParam.put(param[i], param[i + 1]);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return objParam.toString();
    }


    public void hideSoftInput() {
        InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(getWindow().getCurrentFocus()
                .getWindowToken(), 0);
    }


    public Fragment setFrag(Fragment f, String key, int value) {
        Bundle b = new Bundle();
        b.putInt(key, value);
        f.setArguments(b);
        return f;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        View v = getCurrentFocus();
        boolean evnt = super.dispatchTouchEvent(event);
        if (v instanceof EditText) {
            View w = getCurrentFocus();
            int scr[] = new int[2];
            w.getLocationOnScreen(scr);
            float x = event.getRawX() + w.getLeft() - scr[0];
            float y = event.getRawY() + w.getTop() - scr[1];
            if (event.getAction() == MotionEvent.ACTION_UP
                    && (x < w.getLeft() || x >= w.getRight() || y < w.getTop() || y > w
                    .getBottom())) {
                hideSoftInput();
            }

        }
        return evnt;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return false;
    }
}
