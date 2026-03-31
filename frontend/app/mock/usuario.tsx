// arquivo: /app/mock/usuario.ts
import { Usuario } from "@/app/context/AuthContext";

export class UsuarioMock {

    private static usuarioDB: Usuario[] = [
        new Usuario(1, "Professor Samuel Matos", "samuel@exemplo.com", "123456", "ATIVO"),
        new Usuario(2, "Matheus", "matheus@exemplo.com", "123456", "ATIVO"),
        new Usuario(3, "Carlos", "carlos@exemplo.com", "123456", "ATIVO"),
        new Usuario(4, "Joze", "joze@exemplo.com", "123456", "ATIVO"),
        new Usuario(5, "Paulo", "paulo@exemplo.com", "123456", "ATIVO")
    ];

    // Retorna todos os usuários
    static async listarTodos(): Promise<Usuario[]> {
        return new Promise(resolve => {
            setTimeout(() => resolve([...this.usuarioDB]), 300);
        });
    }

    // Busca usuário por ID
    static async buscarPorId(id: number): Promise<Usuario | null> {
        return new Promise(resolve => {
            setTimeout(() => {
                const usuario = this.usuarioDB.find(u => u.id === id) || null;
                resolve(usuario);
            }, 300);
        });
    }

    // Salva ou atualiza um usuário
    static async salvar(usuario: Usuario): Promise<void> {
        return new Promise(resolve => {
            setTimeout(() => {
                const index = this.usuarioDB.findIndex(u => u.id === usuario.id);
                if (index >= 0) {
                    this.usuarioDB[index] = usuario; // atualiza
                } else {
                    usuario.id = this.usuarioDB.length + 1;
                    this.usuarioDB.push(usuario); // adiciona novo
                }
                resolve();
            }, 300);
        });
    }
}