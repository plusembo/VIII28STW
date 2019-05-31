package com.viii28stw.pensiltikfrontend.model.domain;

import lombok.*;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class I18n implements Serializable {
    private String language;
    private String country;
    private String flagPath;
}
