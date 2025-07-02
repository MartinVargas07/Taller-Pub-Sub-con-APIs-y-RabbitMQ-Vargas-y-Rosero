package com.stremedu.plagiarism;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

public class Tarea implements Serializable {
    private String estudiante;
    private String curso;
    private String archivo;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime fechaEnvio;

    // Getters, Setters y toString...
    public String getEstudiante() { return estudiante; }
    public void setEstudiante(String estudiante) { this.estudiante = estudiante; }
    public String getCurso() { return curso; }
    public void setCurso(String curso) { this.curso = curso; }
    public String getArchivo() { return archivo; }
    public void setArchivo(String archivo) { this.archivo = archivo; }
    public LocalDateTime getFechaEnvio() { return fechaEnvio; }
    public void setFechaEnvio(LocalDateTime fechaEnvio) { this.fechaEnvio = fechaEnvio; }

    @Override
    public String toString() {
        return "Tarea{" +
                "estudiante='" + estudiante + '\'' +
                ", curso='" + curso + '\'' +
                ", archivo='" + archivo + '\'' +
                ", fechaEnvio=" + fechaEnvio +
                '}';
    }
}