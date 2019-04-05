package com.viii28stw.pensiltikfrontend.model.domain;

import com.viii28stw.pensiltikfrontend.enumeration.MenuEnum;
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