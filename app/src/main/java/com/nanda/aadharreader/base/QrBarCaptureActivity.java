package com.nanda.aadharreader.base;

import com.journeyapps.barcodescanner.CaptureActivity;
import com.journeyapps.barcodescanner.CompoundBarcodeView;
import com.nanda.aadharreader.R;

public class QrBarCaptureActivity extends CaptureActivity {

    @Override
    protected CompoundBarcodeView initializeContent() {
        setContentView(R.layout.activity_code_capture);
        return (CompoundBarcodeView) findViewById(R.id.zxing_barcode_scanner);
    }

}
