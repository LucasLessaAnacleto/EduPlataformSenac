'use client'

import { useAuth } from "@/app/_context/AuthContext"
import { Professor } from "@/app/types"
import { api } from "@/app/utils/api"
import Link from "next/link"
import { useRouter } from "next/navigation"
import { useEffect, useState } from "react"

export default function PerfilProfessor() {
    const { usuario } = useAuth();
    const [professor, setProfessor] = useState<Professor | null>(null)
    const router = useRouter()

    useEffect(() => {
        fetchProfessor()
    }, [])

    const fetchProfessor = async () => {
        try {
            const response = await api.get<Professor>("/professores/me")
            setProfessor(response.data)
        } catch (err) {
            console.error(err)
            alert("Erro ao carregar perfil")
        }
    }

    const handleChange = (campo: string, valor: string) => {
        setProfessor((prev: any) => ({
            ...prev,
            [campo]: valor
        }))
    }

    const handleSalvar = async (formData: FormData) => {
        if (!professor) return

        const senha = formData.get("senha")?.toString()

        const response = await api.put(`/professores/${professor.id}`, {
            nome: professor.nome,
            email: professor.email,
            biografia: professor.biografia,
            senha: senha || undefined
        })

        if (response.status !== 200) {
            alert("Erro ao atualizar perfil!")
            return
        }

        alert("Perfil atualizado com sucesso!");

    }

    if (!professor) {
        return (
            <div className="p-6 text-zinc-400">
                Carregando perfil...
            </div>
        )
    }

    return (
        <form action={handleSalvar} className="space-y-8">

            <div className="
                bg-zinc-900
                border border-zinc-800
                rounded-xl
                p-6 md:p-8
                space-y-8
            ">

                {/* HEADER */}
                <div>
                    <h2 className="text-lg font-semibold text-white">
                        Meu perfil
                    </h2>
                    <p className="text-sm text-zinc-400 mt-1">
                        Atualize suas informações pessoais
                    </p>
                </div>

                {/* GRID */}
                <div className="grid grid-cols-1 md:grid-cols-2 gap-6">

                    {/* NOME */}
                    <div className="flex flex-col gap-2">
                        <label className="text-sm text-zinc-400">
                            Nome completo
                        </label>

                        <input
                            type="text"
                            value={professor.nome}
                            onChange={(e) => handleChange('nome', e.target.value)}
                            className="bg-zinc-950 border border-zinc-800 rounded-lg px-4 py-2.5 text-sm text-zinc-200 outline-none focus:border-blue-500 focus:ring-2 focus:ring-blue-500/30"
                            name="nome"
                        />
                    </div>

                    {/* EMAIL */}
                    <div className="flex flex-col gap-2">
                        <label className="text-sm text-zinc-400">
                            E-mail
                        </label>

                        <input
                            type="email"
                            value={professor.email}
                            onChange={(e) => handleChange('email', e.target.value)}
                            className="bg-zinc-950 border border-zinc-800 rounded-lg px-4 py-2.5 text-sm text-zinc-200 outline-none focus:border-blue-500 focus:ring-2 focus:ring-blue-500/30"
                            name="email"
                        />
                    </div>

                    {/* BIOGRAFIA */}
                    <div className="flex flex-col gap-2 md:col-span-2">
                        <label className="text-sm text-zinc-400">
                            Biografia
                        </label>

                        <textarea
                            value={professor.biografia || ""}
                            onChange={(e) => handleChange('biografia', e.target.value)}
                            rows={4}
                            className="bg-zinc-950 border border-zinc-800 rounded-lg px-4 py-2.5 text-sm text-zinc-200 outline-none focus:border-blue-500 focus:ring-2 focus:ring-blue-500/30 resize-none"
                            name="biografia"
                        />
                    </div>

                    {/* SENHA */}
                    <div className="flex flex-col gap-2 md:col-span-2">
                        <label className="text-sm text-zinc-400">
                            Nova senha
                        </label>

                        <input
                            type="password"
                            placeholder="Deixe em branco para não alterar"
                            className="bg-zinc-950 border border-zinc-800 rounded-lg px-4 py-2.5 text-sm text-zinc-200 outline-none focus:border-blue-500 focus:ring-2 focus:ring-blue-500/30"
                            name="senha"
                        />
                    </div>

                </div>

                {/* FOOTER */}
                <div className="border-t border-zinc-800 pt-6 flex items-center justify-between">

                    <div className="text-sm text-zinc-500">
                        ID #{professor.id}
                    </div>

                    <div className="flex items-center gap-3">

                        <Link
                            href="/home"
                            className="
                                px-4 py-2
                                text-sm
                                rounded-lg
                                text-zinc-400
                                hover:text-white
                                hover:bg-zinc-800
                                transition
                            "
                        >
                            Voltar
                        </Link>

                        <button
                            type="submit"
                            className="
                                px-6 py-2
                                bg-blue-600
                                hover:bg-blue-500
                                text-white
                                text-sm
                                font-medium
                                rounded-lg
                                transition
                                shadow-lg shadow-blue-600/20
                            "
                        >
                            Salvar alterações
                        </button>

                    </div>

                </div>

            </div>

        </form>
    )
}