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
    UNITED_STATES_OF_AMERICA("US", "United States of America", "United States of America", "en","", ""),
    GERMANY("DE", "Germany", "Deutschland", "de", "",""),
    FRANCE("FR", "France", "France", "fr", "",""),
    SPAIN("ES", "Spain", "España", "es", "",""),
    CHINA("CN", "China", "中国", "zh", "",""),
    THE_NETHERLANDS("NL", "The Netherlands", "Nederland", "nl", "",""),
    BRAZIL("BR", "Brazil", "Brasil", "pt", "",""),
    ITALY("IT", "Italy", "Italia", "it", "",""),
    RUSSIA("RU", "Russia", "Россия", "ru", "",""),
    JAPAN("JA", "Japan", "日本", "ja", "","");

    private final String countryCode;
    private final String countryNameEnglish;
    private final String contryNameLocal;
    private final String languageCode;
    private final String languageNameEnglish;
    private final String languageNameLocal;

    public static List<NominatimCountryCodesEnum> getList() {
        List<NominatimCountryCodesEnum> listNominatimCountryCodesEnum = new ArrayList<NominatimCountryCodesEnum>
                (Arrays.asList(NominatimCountryCodesEnum.values()));
        return listNominatimCountryCodesEnum;
    }

}
