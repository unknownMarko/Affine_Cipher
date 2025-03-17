package com.affinecipher.affine_cipher;

public class AffineCipherLogic {

    //Array of special characters
    static char[][] specialChars = {
            {'á', 'A'}, {'Á', 'A'},
            {'č', 'C'}, {'Č', 'C'},
            {'ď', 'D'}, {'Ď', 'D'},
            {'é', 'E'}, {'É', 'E'},
            {'í', 'I'}, {'Í', 'I'},
            {'ň', 'N'}, {'Ň', 'N'},
            {'ó', 'O'}, {'Ó', 'O'},
            {'ř', 'R'}, {'Ř', 'R'},
            {'š', 'S'}, {'Š', 'S'},
            {'ť', 'T'}, {'Ť', 'T'},
            {'ú', 'U'}, {'Ú', 'U'},
            {'ů', 'U'}, {'Ů', 'U'},
            {'ý', 'Y'}, {'Ý', 'Y'},
            {'ž', 'Z'}, {'Ž', 'Z'},
            {'ě', 'E'}, {'Ě', 'E'}
    };

    //Format input before encryption/decryption
    public static String formatInput(String inputString) {
        StringBuilder output = new StringBuilder();

        for (int i = 0; i < inputString.length(); i++) {
            char character = inputString.charAt(i);

            //Numbers, Cap. letters, Low. letters, Space, Spec. chars
            if ((character >= '0' && character <= '9')) {
                output.append(character);
            } else if (character >= 'A' && character <= 'Z') {
                output.append(character);
            } else if (character >= 'a' && character <= 'z') {
                output.append((char)(character-32));
            } else if (character == ' ') {
                output.append("XMEDZERAX");
            } else {
                for (char[] specialChar : specialChars) {
                    if (character == specialChar[0]) output.append(specialChar[1]);
                }
            }
        }
        System.out.println("Filtered = " + output);

        return output.toString();
    }

    public static int gcd(int key, int alphabet) {
        if (alphabet == 0)
            return key;
        else {
            return gcd(alphabet, key % alphabet);
        }
    }

    //Encrypting
    public static String encrypt(String inputString, int keyA, int keyB, String alphabet) {
        StringBuilder output = new StringBuilder();

        String formattedInput = formatInput(inputString);

        for (int i = 0; i < formattedInput.length(); i++) {
            int index = alphabet.indexOf(formattedInput.charAt(i));
            if (index >= 0) {
                int result = (keyA * index + keyB) % alphabet.length();
                if (i % 5 == 0 && i != 0)  output.append(" ");
                output.append(alphabet.charAt(result));
                System.out.println(formattedInput.charAt(i) + " = " + keyA + "*" + (formattedInput.charAt(i) - 65) + "+" + keyB + " mod 26 = " + result);
            } else {
                output.append(formattedInput.charAt(i));
            }
        }
        System.out.println("Encrypted = " + output);
        return output.toString();
    }

    //Decrypting
    public static String decrypt(String inputString, int keyA, int keyB, String alphabet) {
        StringBuilder output = new StringBuilder();

        int aLen = alphabet.length();

        for (int i = 0; i < inputString.length(); i++) {
            if (inputString.charAt(i) != ' ') {
                int aIndexOfChar = alphabet.indexOf(inputString.charAt(i));

                if (aIndexOfChar == -1) {
                    output.append(inputString.charAt(i));
                } else {
                    int result = ((modInverse(keyA, aLen) * (aIndexOfChar - keyB) % aLen) + aLen) % aLen;
                    //Printing equations with variables
                    System.out.println(modInverse(keyA, aLen) + "*(" + aIndexOfChar + "-" + keyB + ")%" + alphabet.length() + " = " + result);
                    output.append(alphabet.charAt(result));
                }
            }
        }

        System.out.println("Decrypted = " + output);

        output = new StringBuilder(output.toString().replace("XMEDZERAX", " "));

        return output.toString();
    }

    public static int modInverse(int x, int m) {
        x = x % m;
        for (int i = 1; i < m; i++) {
            if ((x * i) % m == 1) return i;
        }
        return -1;
    }
}
