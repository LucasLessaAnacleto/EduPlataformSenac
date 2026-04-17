'use client'
import { createContext, ReactNode, use, useContext, useEffect, useState } from "react";
import Cookies from "js-cookie";
import { useRouter } from "next/navigation";
import { Usuario } from "../types/usuario";

interface AuthContextType {
    usuario: Usuario | null,
    login: (usuario: Usuario, tokenLogin: string) => void,
    logout: () => void,
    loading: boolean,
    token: string | null
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export function AuthProvider({ children }: { children: ReactNode }) {
    const [usuario, setUsuario] = useState<Usuario | null>(null);
    const [token, setToken] = useState<string | null>(null);
    const [loading, setLoading] = useState(true);

    const router = useRouter();
    useEffect(() => {
        const usuarioRecover = Cookies.get('usuario');
        if (usuarioRecover) {
            try {
                const jsonUsuario = JSON.parse(usuarioRecover);
                const usuario = new Usuario(
                    jsonUsuario.id,
                    jsonUsuario.nome,
                    jsonUsuario.email,
                );
                setUsuario(usuario);
            } catch (e) {
                console.error(e);
            }
        }
        setLoading(false);
    }, []);

    const login = (usuario: Usuario, tokenLogin: string) => {
        setUsuario(usuario);
        setToken(tokenLogin);
        Cookies.set('usuario', JSON.stringify(usuario), { expires: 7 });
        Cookies.set('token', tokenLogin, { expires: 7 });
    }
    
    const logout = () => {
        console.log("Logout iniciado");
        setUsuario(null);
        Cookies.remove('usuario');
        Cookies.remove('token');
        router.push("/login");
    }
    
    return (
        <AuthContext.Provider value={{ usuario, loading, login, logout, token }}>
            {children}
        </AuthContext.Provider>
    )
}

export const useAuth = () => {
    const context = useContext(AuthContext);
    if (!context) throw new Error('useAuth deve ser usado dentro do provider!')
    return context;
}