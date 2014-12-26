/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithms;

/**
 *
 * @author prakbans
 */
public class BinaryCode {

    public String[] decode(String encryptedMessage) {
        String[] decrypts = new String[2];
        // need to decrypt the encrypted input 
        // the decrypted message would either be binary or would not exists represented as - "NONE"
        //  and could start either from "0" or "1"
        int start = 0;
        // would run following loop 2 times
        while (start != 2) {
            int lookback = start;
            int lastAppended = start;
            // FIXME: better way for following
            StringBuilder prediction = new StringBuilder("").append(start);
            int i = 0;
            for (; i < encryptedMessage.length() - 1; i++) {
                char scannedChar = encryptedMessage.charAt(i);
                int scannedDigit = Character.digit(scannedChar, 10);

                if (scannedDigit == lookback) {
                    prediction.append(0);
                    if ((lookback == 1 && lastAppended == 0) || lookback == 2) {
                        lookback--;
                    }
                    if (lookback < 0) {
                        lookback = 0;
                    }
                    lastAppended = 0;
                } else if (scannedDigit - 1 == lookback) {
                    prediction.append(1);
                    if (lookback != 1 || lastAppended == 1) {
                        lookback++;
                    }
                    if (lookback > 2) {
                        lookback = 2;
                    }
                    lastAppended = 1;
                } else {
                    decrypts[start] = "NONE";
                    break;
                }
            }

            if (lookback != Character.digit(encryptedMessage.charAt(encryptedMessage.length() - 1), 10)) {
                decrypts[start] = "NONE";
            } else {
                decrypts[start] = prediction.toString();
            }
            start++;
        }

        return decrypts;
    }
}
