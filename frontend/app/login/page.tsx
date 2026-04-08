'use client'
import { useState } from "react";
import { useRouter } from "next/navigation";
import Logo from "../components/Logo";
import { useAuth, Usuario } from "../context/AuthContext";
import { api } from "../utils/api";

interface LoginResponse{
    token: string;
    usuario: Usuario;
}

export default function LoginPage() {
    const { login } = useAuth();
    const router = useRouter();

     async function handleLogin(formData: FormData) {
        const email = formData.get("email")?.toString();
        const senha = formData.get("senha")?.toString();

        if (!email || !senha) {
            alert("Preencha todos os campos!");
            return;
        }

        try {
            const response = await api.post<LoginResponse>(
                "http://localhost:8080/auth/login",
                { email, senha }
            );

            if (response.status !== 200) {
                alert("Usuário ou senha inválido!");
                return;
            }
            const data = response.data;
            const usuario = new Usuario(
                data.usuario.id,
                data.usuario.nome,
                data.usuario.email,
                data.usuario.status
            );

            login(usuario, data.token);

            router.push("/home");

        } catch (error) {
            console.error(error);
            alert("Erro ao entrar no sistema!");
        }
    }
    
    return (
        <div className="min-h-screen flex items-center justify-center bg-linear-to-br from-zinc-950 via-zinc-900 to-black text-zinc-100">

            <div className="w-full max-w-md bg-zinc-900/70 backdrop-blur border border-zinc-800 rounded-2xl shadow-2xl p-8">
                <Logo />
                <div className="mb-8 text-center">
                    <h1 className="text-3xl font-semibold tracking-tight">
                        EduPlatform
                    </h1>
                    <p className="text-zinc-400 text-sm mt-2">
                        Acesse sua conta de professor
                    </p>
                </div>

                <form className="space-y-6" action={handleLogin}>

                    <div className="flex flex-col gap-2">
                        <label className="text-sm text-zinc-400">
                            E-mail
                        </label>

                        <input
                            type="email"
                            placeholder="professor@email.com"
                            className="
                                bg-zinc-950
                                border border-zinc-800
                                rounded-lg
                                px-4 py-2.5
                                text-sm
                                outline-none
                                focus:border-blue-500
                                focus:ring-2
                                focus:ring-blue-500/30
                                transition
                            "
                            name="email"
                        />
                    </div>

                    <div className="flex flex-col gap-2">
                        <label className="text-sm text-zinc-400">
                            Senha
                        </label>

                        <input
                            type="password"
                            placeholder="••••••••"
                            className="
                                bg-zinc-950
                                border border-zinc-800
                                rounded-lg
                                px-4 py-2.5
                                text-sm
                                outline-none
                                focus:border-blue-500
                                focus:ring-2
                                focus:ring-blue-500/30
                                transition
                            "
                            name="senha"
                        />
                    </div>

                    <button
                        type="submit"
                        className="
                            w-full
                            bg-blue-600
                            hover:bg-blue-500
                            active:bg-blue-700
                            text-white
                            font-medium
                            py-2.5
                            rounded-lg
                            transition
                            shadow-lg
                            shadow-blue-600/20
                        "
                    >
                        Acessar
                    </button>

                </form>

            </div>
        </div>
    )
}