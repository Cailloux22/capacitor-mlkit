package io.capawesome.capacitorjs.plugins.mlkit.barcodescanning.interfaces;

public interface TakePhotoCallback {
    void success(String base64Image);
    void error(Exception exception);
}
