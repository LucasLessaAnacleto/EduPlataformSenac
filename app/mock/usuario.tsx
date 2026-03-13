import { Usuario } from "@/app/context/AuthContext";

export class UsuarioMock{
 
    private static usuarioDB: Usuario[] = [
        new Usuario(1,"Professor Samuel Matos", "0001", true),
        new Usuario(2,"Mateus", "0002", true),
        new Usuario(3,"Carlos", "0003", true),
        new Usuario(4,"Jose", "0004", true),
        new Usuario(5,"Paulo", "0005", true)
    ];
 
    public static async listarTodos(): Promise<Usuario[]>{
        return [...this.usuarioDB]
    }
       
}