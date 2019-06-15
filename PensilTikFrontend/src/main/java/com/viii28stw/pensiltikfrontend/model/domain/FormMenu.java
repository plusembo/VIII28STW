package com.viii28stw.pensiltikfrontend.model.domain;

import com.viii28stw.pensiltikfrontend.enumeration.MenuMatch;
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
    private MenuMatch menuMatch;
    private Stage stage;
}