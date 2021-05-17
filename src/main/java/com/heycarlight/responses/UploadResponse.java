package com.heycarlight.responses;

import java.util.List;

public class UploadResponse {

    private String msg;
    private List<String> failed;

    public UploadResponse(String msg) {
        this.msg = msg;
    }

    public UploadResponse(String msg, List<String> notUploaded) {
        this.msg =msg;
        this.failed = notUploaded;
    }

    public String getMsg() {
        return msg;
    }

    public List<String> getFailed() {
        return failed;
    }
}
