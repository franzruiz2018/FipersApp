package com.franzruiz.fipere1.Model.Respons;

public class GenericRespons {
    private boolean Success ;
    private String Messages;
    private int UsuarioId;

    public GenericRespons() {
    }

    public GenericRespons(boolean success, String messages, int usuarioId) {
        Success = success;
        Messages = messages;
        UsuarioId = usuarioId;
    }

    public boolean isSuccess() {
        return Success;
    }

    public void setSuccess(boolean success) {
        Success = success;
    }

    public String getMessages() {
        return Messages;
    }

    public void setMessages(String messages) {
        Messages = messages;
    }

    public int getUsuarioId() {
        return UsuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        UsuarioId = usuarioId;
    }
}
