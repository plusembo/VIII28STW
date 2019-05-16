package com.viii28stw.pensiltikfrontend.util;

import com.viii28stw.pensiltikfrontend.MainApp;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class NotificacaoCRUD {

    private final ImageView imageView;
    private final Label label;

    public void notificaSalvo() {
        imageView.setImage(new Image(MainApp.class
                .getResource("/image/saved.png").toString()));
        label.setText("Salvo com sucesso!");
        label.setStyle("-fx-text-fill: #6AC259;");
        notifica();
    }

    public void notificaAtualizado() {
        imageView.setImage(new Image(MainApp.class
                .getResource("/image/updated.png").toString()));
        label.setText("Atualizado com sucesso!");
        label.setStyle("-fx-text-fill: #6AC259;");
        notifica();
    }

    public void notificaExcluido() {
        imageView.setImage(new Image(MainApp.class
                .getResource("/image/trash.png").toString()));
        label.setText("Excluido com sucesso!");
        label.setStyle("-fx-text-fill: #bf4646;");
        notifica();
    }

    public void notificaImprimindo() {
        imageView.setImage(new Image(MainApp.class
                .getResource("/image/printer.png").toString()));
        label.setText("Imprimindo relatório...");
        label.setStyle("-fx-text-fill: #42064d;");
        notifica();
    }

    public void notificaAdvertencia(String text) {
        imageView.setImage(new Image(MainApp.class
                .getResource("/image/warn.png").toString()));
        label.setText(text);
        label.setStyle("-fx-text-fill: #42064d;");
        notifica();
    }

    public void notificaInformacao(String txt, String iconResource) {
        imageView.setImage(new Image(MainApp.class.getResource(iconResource).toString()));
        label.setText(txt);
        label.setStyle("-fx-text-fill: #c00d0d;");
        notifica();
    }

    private void notifica() {
        Runnable rnbl = () -> {
            label.setVisible(true);
            imageView.setVisible(true);
            try {
                Thread.sleep(1775);
            } catch (InterruptedException ex) {
            }
            label.setVisible(false);
            imageView.setVisible(false);
        };
        Thread thrd = new Thread(rnbl);
        thrd.start();
    }

}