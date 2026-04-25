'use client'

import { useRouter } from "next/navigation"
import Link from "next/link"
import Logo from "../components/Logo"
import { useAuth } from "../context/AuthContext"
import { api } from "../utils/api"
import Button from "../components/Button"
import { LoginResponse } from "../types/Auth"
import { Usuario } from "../types/usuario"

export default function LoginPage() {

    const { login } = useAuth()
    const router = useRouter()

    async function handleLogin(formData: FormData) {

        const email = formData.get("email")?.toString()
        const senha = formData.get("senha")?.toString()

        if (!email || !senha) {
            alert("Preencha todos os campos!")
            return
        }

        try {

            const response = await api.post<LoginResponse>(
                "/auth/login",
                { email, senha }
            )

            if (response.status !== 200) {
                alert("Usuário ou senha inválido!")
                return
            }

            const data = response.data

            const usuario = new Usuario(
                data.usuario.id,
                data.usuario.nome,
                data.usuario.email,
            )

            login(usuario, data.token)

            router.push("/home")

        } catch (error) {
            console.error(error)
            alert("Erro ao entrar no sistema!")
        }
    }

    return (
        <div className="min-h-screen flex items-center justify-center bg-linear-to-br from-zinc-950 via-zinc-900 to-black text-zinc-100">

            <div className="w-full max-w-md bg-zinc-900/70 backdrop-blur border border-zinc-800 rounded-2xl shadow-2xl p-8">
                <div className="flex justify-center mb-6">
                    <Logo />
                </div>

                <div className="text-center mb-8 space-y-2">
                    <h1 className="text-2xl font-semibold text-white tracking-tight">
                        Acessar plataforma
                    </h1>

                    <p className="text-sm text-zinc-400">
                        Entre com sua conta de professor
                    </p>

                </div>

                <form className="space-y-5" action={handleLogin}>
                    <div className="flex flex-col gap-2">

                        <label className="text-sm text-zinc-400">
                            E-mail
                        </label>

                        <input
                            type="email"
                            name="email"
                            placeholder="professor@email.com"
                            className="bg-zinc-950 border border-zinc-800 rounded-lg px-4 py-2.5 text-sm text-zinc-200 outline-none 
                                focus:border-blue-500 focus:ring-2 focus:ring-blue-500/30 transition"
                        />

                    </div>

                    <div className="flex flex-col gap-2">

                        <label className="text-sm text-zinc-400">
                            Senha
                        </label>

                        <input
                            type="password"
                            name="senha"
                            placeholder="••••••••"
                            className="bg-zinc-950 border border-zinc-800 rounded-lg px-4 py-2.5 text-sm text-zinc-200 outline-none 
                                focus:border-blue-500focus:ring-2 focus:ring-blue-500/30 transition"
                        />
                    </div>

                    <div className="flex flex-col gap-3 pt-2">
                        <Button type="submit" className="py-2.5">Entrar</Button>

                        <Link href="/nova-conta" className="text-center border border-zinc-700 hover:border-zinc-500 text-zinc-300 text-sm py-2.5 rounded-lg transition">
                            Criar conta
                        </Link>
                    </div>
                </form>
            </div>
        </div>
    )
}