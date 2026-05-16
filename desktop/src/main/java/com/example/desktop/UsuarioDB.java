package com.example.desktop;

import java.util.ArrayList;
import java.util.List;

public class UsuarioDB {

    // Lista em memória
    private static List<Usuario> usuarios = new ArrayList<>();

    // Adicionar usuário
    public static void adicionarUsuario(String nome, String email, String senha) {
        usuarios.add(new Usuario(nome, email, senha));
    }

    // Listar usuários
    public static List<Usuario> listarUsuarios() {
        return usuarios;
    }

    // Buscar usuário por email
    public static Usuario buscarPorEmail(String email) {
        for (Usuario usuario : usuarios) {
            if (usuario.getEmail().equalsIgnoreCase(email)) {
                return usuario;
            }
        }
        return null;
    }

    static class Usuario {

        private String nome;
        private String email;
        private String senha;

        public Usuario(String nome, String email, String senha) {
            this.nome = nome;
            this.email = email;
            this.senha = senha;
        }

        public String getNome() {
            return nome;
        }

        public String getEmail() {
            return email;
        }

        public String getSenha() {
            return senha;
        }

        @Override
        public String toString() {
            return "Usuario{" +
                    "nome='" + nome + '\'' +
                    ", email='" + email + '\'' +
                    '}';
        }
    }

    // Teste
//    public static void main(String[] args) {
//
//        adicionarUsuario("João", "joao@email.com", "123");
//        adicionarUsuario("Maria", "maria@email.com", "456");
//
//        // Listar todos
//        for (Usuario u : listarUsuarios()) {
//            System.out.println(u);
//        }
//
//        // Buscar
//        Usuario encontrado = buscarPorEmail("maria@email.com");
//
//        if (encontrado != null) {
//            System.out.println("Encontrado: " + encontrado.getNome());
//        }
//    }
}