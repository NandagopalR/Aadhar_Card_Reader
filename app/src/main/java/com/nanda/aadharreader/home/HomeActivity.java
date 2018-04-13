package com.nanda.aadharreader.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.nanda.aadharreader.R;
import com.nanda.aadharreader.base.BaseActivity;
import com.nanda.aadharreader.base.QrBarCaptureActivity;
import com.nanda.aadharreader.utils.AadharAttributes;
import com.nanda.aadharreader.utils.TextUtils;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends BaseActivity {

    @BindView(R.id.btn_scan)
    Button btnScan;

    String uid, name, gender, dateOfBirth, careOf, villageTehsil, postOffice, district, state, postCode;
    @BindView(R.id.tv_sd_uid)
    TextView tvSdUid;
    @BindView(R.id.tv_sd_name)
    TextView tvSdName;
    @BindView(R.id.tv_sd_gender)
    TextView tvSdGender;
    @BindView(R.id.tv_sd_dob)
    TextView tvSdDob;
    @BindView(R.id.tv_sd_co)
    TextView tvSdCo;
    @BindView(R.id.tv_sd_vtc)
    TextView tvSdVtc;
    @BindView(R.id.tv_sd_po)
    TextView tvSdPo;
    @BindView(R.id.tv_sd_dist)
    TextView tvSdDist;
    @BindView(R.id.tv_sd_state)
    TextView tvSdState;
    @BindView(R.id.tv_sd_pc)
    TextView tvSdPc;
    @BindView(R.id.scrollView)
    ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_scan)
    public void onScanButtonClicked() {
        new IntentIntegrator(this).setCaptureActivity(QrBarCaptureActivity.class).setBeepEnabled(true).
                setPrompt("Scan Aadhar Card...").initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (scanningResult != null) {
            //we have a result
            String scanContent = scanningResult.getContents();
            String scanFormat = scanningResult.getFormatName();

            // process received data
            if (scanContent != null && !scanContent.isEmpty()) {
                processScannedData(scanContent);
            } else {
                showToast("Scan Cancelled");
            }

        } else {
            showToast("No scan data received!");
        }
    }

    private void processScannedData(String scanData) {
        XmlPullParserFactory pullParserFactory;

        try {
            // init the parserfactory
            pullParserFactory = XmlPullParserFactory.newInstance();
            // get the parser
            XmlPullParser parser = pullParserFactory.newPullParser();

            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(new StringReader(scanData));

            // parse the XML
            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_DOCUMENT) {
                } else if (eventType == XmlPullParser.START_TAG && AadharAttributes.AADHAAR_DATA_TAG.equals(parser.getName())) {
                    // extract data from tag
                    //uid
                    uid = parser.getAttributeValue(null, AadharAttributes.AADHAR_UID_ATTR);
                    //name
                    name = parser.getAttributeValue(null, AadharAttributes.AADHAR_NAME_ATTR);
                    //gender
                    gender = parser.getAttributeValue(null, AadharAttributes.AADHAR_GENDER_ATTR);
                    // Date of birth
                    dateOfBirth = parser.getAttributeValue(null, AadharAttributes.AADHAR_DOB_ATTR);
                    // care of
                    careOf = parser.getAttributeValue(null, AadharAttributes.AADHAR_CO_ATTR);
                    // village Tehsil
                    villageTehsil = parser.getAttributeValue(null, AadharAttributes.AADHAR_VTC_ATTR);
                    // Post Office
                    postOffice = parser.getAttributeValue(null, AadharAttributes.AADHAR_PO_ATTR);
                    // district
                    district = parser.getAttributeValue(null, AadharAttributes.AADHAR_DIST_ATTR);
                    // state
                    state = parser.getAttributeValue(null, AadharAttributes.AADHAR_STATE_ATTR);
                    // Post Code
                    postCode = parser.getAttributeValue(null, AadharAttributes.AADHAR_PC_ATTR);

                } else if (eventType == XmlPullParser.END_TAG) {
                    Log.d("Aadhar", "End tag " + parser.getName());

                } else if (eventType == XmlPullParser.TEXT) {
                    Log.d("Aadhar", "Text " + parser.getText());

                }
                // update eventType
                eventType = parser.next();
            }

            // display the data on screen
            displayScannedData();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void displayScannedData() {
        scrollView.setVisibility(View.VISIBLE);
        tvSdUid.setText(TextUtils.getString(uid));
        tvSdName.setText(TextUtils.getString(name));
        tvSdGender.setText(TextUtils.getString(gender));
        tvSdDob.setText(TextUtils.getString(dateOfBirth));
        tvSdCo.setText(TextUtils.getString(careOf));
        tvSdVtc.setText(TextUtils.getString(villageTehsil));
        tvSdPo.setText(TextUtils.getString(postOffice));
        tvSdDist.setText(TextUtils.getString(district));
        tvSdState.setText(TextUtils.getString(state));
        tvSdPc.setText(TextUtils.getString(postCode));
    }

}
