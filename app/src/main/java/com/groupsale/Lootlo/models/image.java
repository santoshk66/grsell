package com.groupsale.Lootlo.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class image {

    @SerializedName("file")
    @Expose
    String file;

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }
}
