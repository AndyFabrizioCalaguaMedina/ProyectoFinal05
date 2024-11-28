/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

public class Errores {
    //Atributos de la clase Errores
    private String mensajeError;
    private String tipoError;
    private String fechaError;
    private String horaError;
    private String usuarioError;

    //Contructor de la clase Errores que incializar sus atributos
    public Errores(String mensajeError, String tipoError, String fechaError, String horaError, String usuarioError) {
        this.mensajeError = mensajeError;
        this.tipoError = tipoError;
        this.fechaError = fechaError;
        this.horaError = horaError;
        this.usuarioError = usuarioError;
    }

    //Metodos get y set para obtener y establecer mensajes de error, tipo de error, fecha de erro, hora de error y el usuario del error
    public String getMensajeError() {
        return mensajeError;
    }
    public void setMensajeError(String mensajeError) {
        this.mensajeError = mensajeError;
    }

    public String getTipoError() {
        return tipoError;
    }
    public void setTipoError(String tipoError) {
        this.tipoError = tipoError;
    }

    public String getFechaError() {
        return fechaError;
    }
    public void setFechaError(String fechaError) {
        this.fechaError = fechaError;
    }

    public String getHoraError() {
        return horaError;
    }
    public void setHoraError(String horaError) {
        this.horaError = horaError;
    }

    public String getUsuarioError() {
        return usuarioError;
    }
    public void setUsuarioError(String usuarioError) {
        this.usuarioError = usuarioError;
    }

    // Método sobrescrito toString para representar el error en una cadena de texto
    @Override
    public String toString() {
        return "Errores{" + "mensajeError=" + mensajeError + ", tipoError=" + tipoError + ", fechaError=" + fechaError + ", horaError=" + horaError + ", usuarioError=" + usuarioError + '}';
    }
}