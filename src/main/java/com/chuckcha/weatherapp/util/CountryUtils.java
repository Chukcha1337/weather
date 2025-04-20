package com.chuckcha.weatherapp.util;

import lombok.experimental.UtilityClass;

import java.util.Locale;

@UtilityClass
public class CountryUtils {

    public static String convertCodeToName(String code) {
        String fullCountryName = Locale.forLanguageTag("und-" + code).getDisplayCountry(Locale.ENGLISH);
        return fullCountryName.isBlank() ? code : fullCountryName;
    }
}
