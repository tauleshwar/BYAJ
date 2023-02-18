package com.tauleshwarthakur.byaj;

import android.media.MediaScannerConnection;
import android.net.Uri;

import java.io.File;

public class SingleMediaScanner implements MediaScannerConnection.MediaScannerConnectionClient {
    private  MediaScannerConnection mScan;
    private File newfile;
    public SingleMediaScanner(Result result, File file) {
        newfile = file;
        mScan = new MediaScannerConnection(result.getApplicationContext(), this);
        mScan.connect();

    }

    @Override
    public void onScanCompleted(String path, Uri uri){
        mScan.disconnect();
    }

    @Override
    public void onMediaScannerConnected() {
        mScan.scanFile(newfile.getAbsolutePath(),null);
    }
}
