export class Usuario {
    constructor(
        public id: number,
        public nome: string,
        public email: string,
    ) { }
}
 
export interface AuthContextType {
    usuario: Usuario | null,
    login: (usuario: Usuario, tokenLogin: string) => void,
    logout: () => void,
    loading: boolean
}

 
export interface UsuarioFormProps {
    usuarioExistente?: Usuario
}