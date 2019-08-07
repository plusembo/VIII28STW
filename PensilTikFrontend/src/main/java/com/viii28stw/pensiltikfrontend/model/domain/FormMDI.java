package com.viii28stw.pensiltikfrontend.model.domain;

import com.viii28stw.pensiltikfrontend.enumeration.MenuMDI;
import javafx.scene.control.Tab;
import lombok.*;

/**
 * @author Plamedi L. Lusembo
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Builder
public class FormMDI {
    private MenuMDI menuMDI;
    private Tab tab;
}