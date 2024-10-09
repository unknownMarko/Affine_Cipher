module com.affinecipher.affine_cipher {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    opens com.affinecipher.affine_cipher to javafx.fxml;
    exports com.affinecipher.affine_cipher;
}