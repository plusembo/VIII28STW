package com.viii28stw.pensiltikfrontend.util.Notification;

import javafx.geometry.Pos;
import javafx.util.Duration;

/**
 * @author Plamedi L. Lusembo
 */
public class NotificacaoCRUDFactory {

    private Notification.Notifier notifier;
    private Notification notification;
    private static NotificacaoCRUDFactory uniqueInstance;

    public NotificacaoCRUDFactory() {
    }

    public static synchronized NotificacaoCRUDFactory getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new NotificacaoCRUDFactory();
        }
        return uniqueInstance;
    }

    public void notificaSucesso(String texto) {
        notification = NotificationBuilder.create()
                .title("Sucesso")
                .message(texto)
                .image(Notification.SUCCESS_ICON)
                .build();
        notifica();
    }

    public void notificaInformacao(String mensagem) {
        notification = NotificationBuilder.create().title("informação")
                .message(mensagem).image(Notification.INFO_ICON).build();
        notifica();
    }

    public void notificaAdvertencia(String mensagem) {
        notification = NotificationBuilder.create().title("Advertência")
                .message(mensagem).image(Notification.WARNING_ICON).build();
        notifica();
    }

    public void notificaErro(String message) {
        notification = NotificationBuilder.create().title("Erro")
                .message(message).image(Notification.ERROR_ICON).build();
        notifica();
    }

    private void notifica() {
        notifier = NotifierBuilder.create()
                .popupLocation(Pos.BOTTOM_RIGHT)
                .popupLifeTime(Duration.millis(25))
                .build();

        notifier.notify(notification);
    }

}