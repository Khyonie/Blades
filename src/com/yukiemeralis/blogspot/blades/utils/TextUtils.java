package com.yukiemeralis.blogspot.blades.utils;

public class TextUtils 
{
    /**
     * Removes all underscores from a string, turns the string to lowercase, and capitalizes the first letter.
     * Assumes that any formatting has been trimmed first.
     * @param input
     * @return
     */
    public static String formatNicely(String input)
    {
        char first = input.toUpperCase().charAt(0);

        StringBuilder buffer = new StringBuilder(input.toLowerCase().replace("_", " "));
        buffer.deleteCharAt(0);

        return first + buffer.toString();
    }

    public static String trimFormatting(String input)
    {
        if (input.charAt(0) == '§')
        {
            return trimFormatting(new StringBuilder(input).delete(0, 2).toString());
        } else {
            return input;
        }
    }

    // Not a true roman numeral generator, just from 1-4
    public static String toRomanNumeral(int in)
    {
        switch (in)
        {
            case 0: return "0";
            case 1: return "I";
            case 2: return "II";
            case 3: return "III";
            case 4: return "IV";
            default: return "Out of bounds";
        }
    }
}
