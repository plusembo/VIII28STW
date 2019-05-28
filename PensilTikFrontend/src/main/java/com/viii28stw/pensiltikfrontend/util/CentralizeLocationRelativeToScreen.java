package com.viii28stw.pensiltikfrontend.util;

import lombok.NoArgsConstructor;

import java.awt.*;

@NoArgsConstructor
public class CentralizeLocationRelativeToScreen {

    private static CentralizeLocationRelativeToScreen uniqueInstance;

    public static synchronized CentralizeLocationRelativeToScreen getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new CentralizeLocationRelativeToScreen();
        }
        return uniqueInstance;
    }

    public static double getX(double prefWidth) {
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension dm = tk.getScreenSize();
        return (dm.width - prefWidth) / 2.0;
    }
    public static double getY(double prefHeight) {
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension dm = tk.getScreenSize();
        return (dm.height - prefHeight) / 2.0;
    }

}
