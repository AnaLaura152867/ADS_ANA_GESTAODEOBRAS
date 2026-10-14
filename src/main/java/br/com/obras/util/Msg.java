package br.com.obras.util;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 * Atalhos para mensagens do JSF (exibidas pelo p:growl do template).
 */
public final class Msg {

    private Msg() {
    }

    public static void info(String texto) {
        add(FacesMessage.SEVERITY_INFO, "Sucesso", texto);
    }

    public static void aviso(String texto) {
        add(FacesMessage.SEVERITY_WARN, "Atenção", texto);
    }

    public static void erro(String texto) {
        add(FacesMessage.SEVERITY_ERROR, "Erro", texto);
    }

    private static void add(FacesMessage.Severity severidade, String titulo, String texto) {
        FacesContext.getCurrentInstance()
                .addMessage(null, new FacesMessage(severidade, titulo, texto));
    }
}
