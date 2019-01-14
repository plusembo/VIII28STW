package com.eighttwentyeightsoftware.maskedjfxtextfield.component;

import com.jfoenix.controls.JFXTextField;

import java.util.ArrayList;

/**
 * @author Plamedi L. Lusembo
 */

public class MaskedJFXTextField extends JFXTextField {
    private String mask;
    private ArrayList<String> patterns;

    public MaskedJFXTextField() {
        patterns = new ArrayList();
    }

    public MaskedJFXTextField(String text) {
        super(text);
        patterns = new ArrayList();
    }

    @Override
    public void replaceText(int start, int end, String text) {
        StringBuilder str = new StringBuilder(getText());
        String tempText = str.insert(getCaretPosition(), text).toString();
        StringBuilder tempP;
        if ((mask == null) || (mask.length() == 0)) {
            super.replaceText(start, end, text);
        } else if ((tempText.matches(mask)) || (tempText.length() == 0)) {
            super.replaceText(start, end, text);
        } else {
            tempP = new StringBuilder("^");

            for (String patt : patterns) {
                tempP.append(patt);
                if (tempText.matches(tempP.toString())) {
                    super.replaceText(start, end, text);
                    break;
                }
            }
        }
    }


    public String getMask() {
        return mask;
    }


    public void setMask(String mask) {
        StringBuilder tempMask = new StringBuilder("^");

        for (int i = 0; i < mask.length(); i++) {
            char temp = mask.charAt(i);

            int counter = 1;
            int step = 0;

            if (i < mask.length() - 1)
                for (int j = i + 1; j < mask.length(); j++)
                    if (temp == mask.charAt(j)) {
                        counter++;
                        step = j;
                    } else {
                        if (mask.charAt(j) != '!') break;
                        counter = -1;
                        step = j;
                    }
            StringBuilder regex;
            switch (temp) {
                case '*':
                    regex = new StringBuilder(".");
                    break;
                case 'S':
                    regex = new StringBuilder("[^\\s]");
                    break;
                case 'P':
                    regex = new StringBuilder("[A-z.]");
                    break;
                case 'M':
                    regex = new StringBuilder("[0-z.]");
                    break;
                case 'A':
                    regex = new StringBuilder("[0-z]");
                    break;
                case 'N':
                    regex = new StringBuilder("[0-9]");
                    break;
                case 'L':
                    regex = new StringBuilder("[A-z]");
                    break;
                case 'U':
                    regex = new StringBuilder("[A-Z]");
                    break;
                case 'l':
                    regex = new StringBuilder("[a-z]");
                    break;
                case '.':
                    regex = new StringBuilder("\\.");
                    break;
                default:
                    regex = new StringBuilder(Character.toString(temp));
            }


            if (counter != 1) {
                if (counter == -1) {
                    regex.append("+");
                    patterns.add(regex.toString());
                } else {
                    String tempRegex = regex.toString();
                    for (int k = 1; k < counter; k++) {
                        regex.append(tempRegex);
                        patterns.add(tempRegex);
                    }
                }

                i = step;
            } else {
                patterns.add(regex.toString());
            }

            tempMask.append(regex);
        }

        this.mask = tempMask.append("$").toString();
    }
}