package com.fluffy.format;

public enum TextAlignment {
    LEFT {
        @Override
        public String applyLocal(String input, int maxLength) {
            return input + getPaddingString(maxLength - input.length());
        }
    },
    CENTER {
        @Override
        public String applyLocal(String input, int maxLength) {
            double padding = (maxLength - input.length()) / 2.;
            return getPaddingString((int) Math.floor(padding)) + input + getPaddingString((int) Math.ceil(padding));
        }
    },
    RIGHT {
        @Override
        public String applyLocal(String input, int maxLength) {
            return getPaddingString(maxLength - input.length()) + input;
        }
    };

    public final String apply(String input, int maxLength) {
        return (input.length() > maxLength) ? input.substring(0, maxLength) : applyLocal(input, maxLength);
    }

    public abstract String applyLocal(String input, int maxLength);

    protected String getPaddingString(int padding) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < padding; i++) {
            stringBuilder.append(' ');
        }
        return stringBuilder.toString();
    }
}
