package com.example.demande_stage;

import android.graphics.Bitmap;

public class CreatePdf {

    // declaring width and height
    // for our PDF file.
    int pageHeight = 1120;
    int pagewidth = 792;



    // constant code for runtime permissions
    public static final int PERMISSION_REQUEST_CODE = 200;

    public CreatePdf() {
        this.pageHeight=1120;
        this.pagewidth=792;
    }

    public int getPageHeight() {
        return pageHeight;
    }

    public void setPageHeight(int pageHeight) {
        this.pageHeight = pageHeight;
    }

    public int getPagewidth() {
        return pagewidth;
    }

    public void setPagewidth(int pagewidth) {
        this.pagewidth = pagewidth;
    }


    //Create PDF CODE

}
