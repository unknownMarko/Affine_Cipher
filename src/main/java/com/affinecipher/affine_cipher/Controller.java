// MIT License
//
// Copyright (c) 2024 Marko
//
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
//
// The above copyright notice and this permission notice shall be included in all
// copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
// SOFTWARE.

package com.affinecipher.affine_cipher;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;

public class Controller {

    //Alphabet
    StringBuilder alphabet = new StringBuilder("ABCDEFGHIJKLMNOPQRSTUVWXYZ");

    //Initialization of TextField controllers
    @FXML
    private TextField field_key_a, field_key_b, field_input, field_output,
            field_filtered_input, field_alphabet, field_cipher_alphabet;

    //Initialization of Label controllers
    @FXML
    private Label error_msg_key_a, error_msg_key_b, error_msg_input, error_msg_alphabet;

    //Initialization of Button controllers
    @FXML
    private Button btn_encrypt, btn_decrypt;

    boolean keyAisOK = false;
    boolean keyBisOK = false;

    //Enables buttons
    void enableButtons() {
        btn_encrypt.setDisable(false);
        btn_decrypt.setDisable(false);
    }

    //Disables buttons
    void disableButtons() {
        btn_encrypt.setDisable(true);
        btn_decrypt.setDisable(true);
    }

    //Checking if bools keyAisOK and keyBisOK are true
    boolean checkKeys() {
        return keyAisOK && keyBisOK;
    }

    void create_cyphered_alphabet() {
        if (checkKeys()) {
            field_cipher_alphabet.setText(
                    AffineCipherLogic.encrypt(
                            alphabet.toString(),
                            Integer.parseInt(field_key_a.getText()),
                            Integer.parseInt(field_key_b.getText()),
                            alphabet.toString()).replaceAll(" ", "")
            );
        }
    }

    //Checking if input in KeyA field is number and conditions connected with alphabet
    @FXML
    void handleCheckTextFieldKeyA() {
        String keyA = field_key_a.getText();

        if (!keyA.isEmpty()) {
            for (int i = 0; i < keyA.length(); i++) {
                if (keyA.charAt(i) < '0' || keyA.charAt(i) > '9') {
                    keyAisOK = false;
                    error_msg_key_a.setText("INVALID INPUT!");
                    disableButtons();
                    return;
                } else {
                    keyAisOK = true;
                    if (checkKeys()) {
                        error_msg_key_a.setText("");
                        enableButtons();
                        create_cyphered_alphabet();
                    }
                }
            }
            int parsedKeyA = Integer.parseInt(keyA);
            if (!(parsedKeyA >= 1 && parsedKeyA <= alphabet.length() - 1 && AffineCipherLogic.gcd(parsedKeyA, alphabet.length()) == 1)) {
                keyAisOK = false;
                error_msg_key_a.setText("INVALID INPUT!");
                disableButtons();
            }
        } else {
            keyAisOK = false;
            disableButtons();
        }
    }

    //Checking if input in KeyB field is number and conditions connected with alphabet
    @FXML
    void handleCheckTextFieldKeyB() {
        String keyB = field_key_b.getText();

        if (!keyB.isEmpty()) {
            for (int i = 0; i < keyB.length(); i++) {
                if ((int)keyB.charAt(i) < 48 || (int)keyB.charAt(i) > 57) {
                    keyBisOK = false;
                    error_msg_key_b.setText("INVALID INPUT!");
                    disableButtons();
                    return;
                } else {
                    keyBisOK = true;
                    if (checkKeys()) {
                        error_msg_key_b.setText("");
                        enableButtons();
                        create_cyphered_alphabet();
                    }
                }
            }
            int parsedKeyB = Integer.parseInt(keyB);
            //if (!AffineCipherLogic.checkKeyB(Integer.parseInt(keyB), alphabet.toString())) {
            if (!(parsedKeyB >= 0 && parsedKeyB <= alphabet.length() - 1)) {
                keyBisOK = false;
                error_msg_key_b.setText("INVALID INPUT!");
                disableButtons();
            }
        } else {
            keyBisOK = false;
            disableButtons();
        }
    }

    //Checks chars in input field, may show warning in GUI and shows formatted input
    @FXML
    void handleCheckTextInput() {
        String input = field_input.getText();

        for (int i = 0; i < input.length(); i++) {
            if (alphabet.indexOf(String.valueOf(Character.toUpperCase(input.charAt(i)))) != -1) {
                error_msg_input.setText("");
                create_cyphered_alphabet();
            } else {
                error_msg_input.setText("OUTPUT MAY BE DIFFERENT!");
                break;
            }
            field_filtered_input.setText(AffineCipherLogic.formatInput(input));
        }
    }   

    //Update string alphabet from GUI field
    @FXML
    void handleUpdateFieldAlphabet() {
        alphabet = new StringBuilder(field_alphabet.getText());

        handleCheckTextFieldKeyA();
        handleCheckTextFieldKeyB();
        handleCheckTextInput();

        if (alphabet.chars().distinct().count() == 1) {
            error_msg_alphabet.setText("ALPHABET CONTAINS SAME SYMBOLS!");
        } else {
            error_msg_alphabet.setText("");
        }

        if (checkKeys()) {
            enableButtons();
        } else {
            disableButtons();
        }
    }

    //Calling encrypt func and showing result in GUI
    @FXML
    void handleEncryptButton() {
        if (!field_input.getText().isEmpty()) {
            field_output.setText(AffineCipherLogic.encrypt(
                    field_input.getText(),
                    Integer.parseInt(field_key_a.getText()),
                    Integer.parseInt(field_key_b.getText()),
                    alphabet.toString()
            ));
        }
    }

    //Calling decrypt func and showing result in GUI
    @FXML
    void handleDecryptButton() {
        if (!field_input.getText().isEmpty()) {
            field_output.setText(AffineCipherLogic.decrypt(
                    field_input.getText(),
                    Integer.parseInt(field_key_a.getText()),
                    Integer.parseInt(field_key_b.getText()),
                    alphabet.toString()
            ));
        }
    }
}
