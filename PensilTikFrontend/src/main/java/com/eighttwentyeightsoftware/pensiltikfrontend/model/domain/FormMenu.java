package com.eighttwentyeightsoftware.pensiltikfrontend.model.domain;

import com.eighttwentyeightsoftware.pensiltikfrontend.enumeration.MenuEnum;
import javafx.stage.Stage;
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
public class FormMenu {
    private MenuEnum menum;
    private Stage stage;
}