export class Usuario {
    constructor(
        public id: number | null,
        public email: string,
        public status: string,
        public role: string,
        public senha?: string
    ) { }
}

export interface UsuarioProfessorRequest {
    email: string
    senha: string
    nome: string
    cpf: string
    biografia: string
}

export interface UsuarioRequest {
    email: string
    senha: string
    status: string
}

export interface UsuarioAdmRequest {
    email: string
    senha: string
    secretKey: string
}

export interface UsuarioResponse {
    id: number
    email: string
    status: string
    role: string
}

export interface UsuarioFormProps {
    usuarioExistente?: Usuario
}