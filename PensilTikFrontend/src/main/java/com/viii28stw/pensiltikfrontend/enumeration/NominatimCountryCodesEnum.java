package com.viii28stw.pensiltikfrontend.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Plamedi L. Lusembo
 */

@AllArgsConstructor
@Getter
public enum NominatimCountryCodesEnum {
    UNITED_STATES_OF_AMERICA("US", "United States of America",
            "United States of America",
            "en","English", "English", true),
    GERMANY("DE", "Germany", "Deutschland",
            "de", "German",
            "Deutsch", false),
    FRANCE("FR", "France", "France",
            "fr", "French","Français", false),
    SPAIN("ES", "Spain", "España",
            "es", "Spanish","Español", false),
    CHINA("CN", "China", "Zhōngguó",
            "zh", "Chinese","Zhōngguó", false),
    THE_NETHERLANDS("NL", "The Netherlands", "Nederland",
            "nl", "Dutch","Nederlands", false),
    BRAZIL("BR", "Brazil", "Brasil",
            "pt", "Portuguese","Português", true),
    ITALY("IT", "Italy", "Italia",
            "it", "Italian","Italiano", false),
    RUSSIA("RU", "Russia", "Россия",
            "ru", "Russian\n","Russkiy", false),
    JAPAN("JA", "Japan", "Nihongo", "ja",
            "Japanese","Nihongo", false);

    private final String countryCode;
    private final String countryNameEnglish;
    private final String contryNameLocal;
    private final String languageCode;
    private final String languageNameEnglish;
    private final String languageNameLocal;
    private final boolean available;

    public static List<NominatimCountryCodesEnum> getList() {
        List<NominatimCountryCodesEnum> listNominatimCountryCodesEnum = new ArrayList<NominatimCountryCodesEnum>
                (Arrays.asList(NominatimCountryCodesEnum.values()));
        return listNominatimCountryCodesEnum;
    }

}
