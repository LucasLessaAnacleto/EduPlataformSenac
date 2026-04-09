'use client'

import { useRouter } from "next/navigation"
import Link from "next/link"
import Logo from "../components/Logo"
import { api } from "../utils/api"

export default function NovaContaPage() {
  const router = useRouter()

  async function handleRegister(formData: FormData) {
    const nome = formData.get("nome")?.toString()
    const email = formData.get("email")?.toString()
    const senha = formData.get("senha")?.toString()
    const confirmarSenha = formData.get("confirmarSenha")?.toString()

    if (!nome || !email || !senha || !confirmarSenha) return alert("Preencha todos os campos!")
    if (senha !== confirmarSenha) return alert("As senhas não coincidem!")

    try {
      const response = await api.post<Number>("/professores", { nome, email, senha })
      if (response.status !== 200) return alert("Erro ao criar conta!")
      alert("Conta criada com sucesso!")
      router.push("/login")
    } catch (error) {
      console.error(error)
      alert("Erro ao cadastrar usuário!")
    }
  }

  return (
    <div className="min-h-screen flex items-center justify-center bg-linear-to-br from-zinc-950 via-zinc-900 to-black text-zinc-100">
      <div className="w-full max-w-md bg-zinc-900/70 backdrop-blur border border-zinc-800 rounded-2xl shadow-2xl p-8">
        
        <div className="flex justify-center mb-6">
          <Logo />
        </div>

        <div className="text-center mb-8 space-y-2">
          <h1 className="text-2xl font-semibold text-white tracking-tight">Criar conta</h1>
          <p className="text-sm text-zinc-400">Cadastre-se para começar a criar seus cursos</p>
        </div>

        <form className="space-y-5" action={handleRegister}>
          
          <div className="flex flex-col gap-2">
            <label className="text-sm text-zinc-400">Nome</label>
            <input type="text" name="nome" placeholder="Seu nome" className="bg-zinc-950 border border-zinc-800 rounded-lg px-4 py-2.5 text-sm text-zinc-200 outline-none focus:border-blue-500 focus:ring-2 focus:ring-blue-500/30 transition"/>
          </div>

          <div className="flex flex-col gap-2">
            <label className="text-sm text-zinc-400">E-mail</label>
            <input type="email" name="email" placeholder="professor@email.com" className="bg-zinc-950 border border-zinc-800 rounded-lg px-4 py-2.5 text-sm text-zinc-200 outline-none focus:border-blue-500 focus:ring-2 focus:ring-blue-500/30 transition"/>
          </div>

          <div className="flex flex-col gap-2">
            <label className="text-sm text-zinc-400">Senha</label>
            <input type="password" name="senha" placeholder="••••••••" className="bg-zinc-950 border border-zinc-800 rounded-lg px-4 py-2.5 text-sm text-zinc-200 outline-none focus:border-blue-500 focus:ring-2 focus:ring-blue-500/30 transition"/>
          </div>

          <div className="flex flex-col gap-2">
            <label className="text-sm text-zinc-400">Confirmar senha</label>
            <input type="password" name="confirmarSenha" placeholder="••••••••" className="bg-zinc-950 border border-zinc-800 rounded-lg px-4 py-2.5 text-sm text-zinc-200 outline-none focus:border-blue-500 focus:ring-2 focus:ring-blue-500/30 transition"/>
          </div>

          <div className="flex flex-col gap-3 pt-2">
            <button type="submit" className="w-full bg-blue-600 hover:bg-blue-500 active:bg-blue-700 text-white text-sm font-medium py-2.5 rounded-lg transition shadow-lg shadow-blue-600/20">
              Criar conta
            </button>

            <Link href="/login" className="w-full text-center border border-zinc-700 hover:border-zinc-500 text-zinc-300 text-sm py-2.5 rounded-lg transition">
              Já tenho conta
            </Link>
          </div>

        </form>
      </div>
    </div>
  )
}