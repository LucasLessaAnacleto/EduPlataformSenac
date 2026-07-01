import { Usuario } from "./usuarios"

export interface LoginRequest {
    email: string
    senha: string
}

export interface LoginResponse {
    token: string
}

export interface AuthState {
    usuario: Usuario | null
    token: string
}