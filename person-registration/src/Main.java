/*
 * Aluno: Lucas Rodrigues Cunha
 * Disciplina: Laboratório de Programação
 * Atividade: ED02 - 02 | 01 Atividade Prática (Formulário CPF)
 *
 * Modelo do projeto (separação em camadas):
 *   entities -> os dados: Person e os enums State e Role
 *   ui       -> os componentes visuais: o painel do formulário e o diálogo de resultado
 *   app      -> a janela (JFrame), que liga o formulário ao diálogo
 *   Main     -> ponto de entrada, apenas abre a janela
 */

import app.PersonRegistrationForm;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // O Swing não é thread-safe: a interface deve ser criada na thread de
        // eventos (EDT), e o invokeLater agenda a criação da janela nela.
        SwingUtilities.invokeLater(() -> {
            PersonRegistrationForm form = new PersonRegistrationForm();
            form.setVisible(true);
        });
    }
}
