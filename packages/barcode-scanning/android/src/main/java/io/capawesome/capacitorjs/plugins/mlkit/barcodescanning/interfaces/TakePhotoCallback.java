package io.capawesome.capacitorjs.plugins.mlkit.barcodescanning.interfaces;

public interface TakePhotoCallback {
    void success(String uri);
    void error(Exception exception);
}
