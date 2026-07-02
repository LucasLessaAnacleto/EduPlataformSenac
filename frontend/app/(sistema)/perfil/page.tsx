'use client'

import Loading from "@/app/components/Loading"
import { buscarProfessorLogado, atualizarProfessorLogado } from "@/app/services/professorService"
import { Professor } from "@/app/types/professor"
import Link from "next/link"
import { useEffect, useState } from "react"
import { useSelector } from "react-redux"
import { RootState } from "@/app/redux/store"

export default function PerfilProfessor() {
    const usuarioLogado = useSelector((state: RootState) => state.auth.usuario)

    const [professor, setProfessor] = useState<Professor | null>(null)
    const [carregando, setCarregando] = useState(true)

    const ehAdmin = usuarioLogado?.role === "ROLE_ADMIN"
    const ehProfessor = usuarioLogado?.role === "ROLE_PROFESSOR"

    useEffect(() => {
        if (ehProfessor) {
            fetchProfessor()
        } else {
            setCarregando(false)
        }
    }, [usuarioLogado])

    const fetchProfessor = async () => {
        try {
            const professorDados = await buscarProfessorLogado()
            setProfessor(professorDados)

        } catch (err) {
            console.error(err)
            alert("Erro ao carregar perfil")

        } finally {
            setCarregando(false)
        }
    }

    const handleChange = (campo: string, valor: string) => {
        setProfessor((prev: any) => ({
            ...prev,
            [campo]: valor
        }))
    }

    const handleSalvar = async () => {
        if (!professor) return

        if (!professor.nome || !professor.cpf || !professor.biografia) {
            alert("Preencha todos os campos!")
            return
        }

        try {
            const professorAtualizado = await atualizarProfessorLogado({
                nome: professor.nome,
                cpf: professor.cpf,
                biografia: professor.biografia
            })

            setProfessor(professorAtualizado)

            alert("Perfil atualizado com sucesso!")

        } catch (error) {
            console.error(error)
            alert("Erro ao atualizar perfil!")
        }
    }

    if (carregando) {
        return (
            <Loading message="Carregando perfil..." fullScreen />
        )
    }

    if (ehAdmin) {
        return (
            <div className="space-y-8">

                <div>
                    <h1 className="text-2xl font-semibold text-white">
                        Meu perfil
                    </h1>

                    <p className="text-sm text-zinc-400 mt-1">
                        Informações do usuário administrador
                    </p>
                </div>

                <div className="
                    bg-zinc-900
                    border border-zinc-800
                    rounded-xl
                    p-6 md:p-8
                    space-y-8
                ">

                    <div className="grid grid-cols-1 md:grid-cols-2 gap-6">

                        <div className="flex flex-col gap-2">
                            <label className="text-sm text-zinc-400">
                                Perfil
                            </label>

                            <input
                                type="text"
                                value="Administrador"
                                disabled
                                className="
                                    bg-zinc-950
                                    border border-zinc-800
                                    rounded-lg
                                    px-4 py-2.5
                                    text-sm
                                    text-zinc-400
                                    outline-none
                                    cursor-not-allowed
                                    opacity-70
                                "
                            />
                        </div>

                        <div className="flex flex-col gap-2">
                            <label className="text-sm text-zinc-400">
                                E-mail
                            </label>

                            <input
                                type="email"
                                value={usuarioLogado?.email || ""}
                                disabled
                                className="
                                    bg-zinc-950
                                    border border-zinc-800
                                    rounded-lg
                                    px-4 py-2.5
                                    text-sm
                                    text-zinc-400
                                    outline-none
                                    cursor-not-allowed
                                    opacity-70
                                "
                            />
                        </div>

                        <div className="flex flex-col gap-2">
                            <label className="text-sm text-zinc-400">
                                Status
                            </label>

                            <input
                                type="text"
                                value={usuarioLogado?.status || ""}
                                disabled
                                className="
                                    bg-zinc-950
                                    border border-zinc-800
                                    rounded-lg
                                    px-4 py-2.5
                                    text-sm
                                    text-zinc-400
                                    outline-none
                                    cursor-not-allowed
                                    opacity-70
                                "
                            />
                        </div>

                    </div>

                    <div className="border-t border-zinc-800 pt-6 flex items-center justify-between">

                        <div className="text-sm text-zinc-500">
                            ID #{usuarioLogado?.id}
                        </div>

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

                    </div>

                </div>

            </div>
        )
    }

    if (!professor) {
        return (
            <div className="p-6 text-zinc-400">
                Perfil não encontrado.
            </div>
        )
    }

    return (
        <form action={handleSalvar} className="space-y-8">

            <div>
                <h1 className="text-2xl font-semibold text-white">
                    Meu perfil
                </h1>

                <p className="text-sm text-zinc-400 mt-1">
                    Atualize suas informações de professor
                </p>
            </div>

            <div className="
                bg-zinc-900
                border border-zinc-800
                rounded-xl
                p-6 md:p-8
                space-y-8
            ">

                <div className="grid grid-cols-1 md:grid-cols-2 gap-6">

                    <div className="flex flex-col gap-2">
                        <label className="text-sm text-zinc-400">
                            Nome completo
                        </label>

                        <input
                            type="text"
                            value={professor.nome}
                            onChange={(e) => handleChange("nome", e.target.value)}
                            className="
                                bg-zinc-950
                                border border-zinc-800
                                rounded-lg
                                px-4 py-2.5
                                text-sm
                                text-zinc-200
                                outline-none
                                focus:border-blue-500
                                focus:ring-2
                                focus:ring-blue-500/30
                            "
                            name="nome"
                        />
                    </div>

                    <div className="flex flex-col gap-2">
                        <label className="text-sm text-zinc-400">
                            CPF
                        </label>

                        <input
                            type="text"
                            value={professor.cpf}
                            onChange={(e) => handleChange("cpf", e.target.value)}
                            className="
                                bg-zinc-950
                                border border-zinc-800
                                rounded-lg
                                px-4 py-2.5
                                text-sm
                                text-zinc-200
                                outline-none
                                focus:border-blue-500
                                focus:ring-2
                                focus:ring-blue-500/30
                            "
                            name="cpf"
                        />
                    </div>

                    <div className="flex flex-col gap-2">
                        <label className="text-sm text-zinc-400">
                            E-mail
                        </label>

                        <input
                            type="email"
                            value={usuarioLogado?.email || ""}
                            disabled
                            className="
                                bg-zinc-950
                                border border-zinc-800
                                rounded-lg
                                px-4 py-2.5
                                text-sm
                                text-zinc-400
                                outline-none
                                cursor-not-allowed
                                opacity-70
                            "
                        />
                    </div>

                    <div className="flex flex-col gap-2">
                        <label className="text-sm text-zinc-400">
                            Status
                        </label>

                        <input
                            type="text"
                            value={usuarioLogado?.status || ""}
                            disabled
                            className="
                                bg-zinc-950
                                border border-zinc-800
                                rounded-lg
                                px-4 py-2.5
                                text-sm
                                text-zinc-400
                                outline-none
                                cursor-not-allowed
                                opacity-70
                            "
                        />
                    </div>

                    <div className="flex flex-col gap-2 md:col-span-2">
                        <label className="text-sm text-zinc-400">
                            Biografia
                        </label>

                        <textarea
                            value={professor.biografia || ""}
                            onChange={(e) => handleChange("biografia", e.target.value)}
                            rows={4}
                            className="
                                bg-zinc-950
                                border border-zinc-800
                                rounded-lg
                                px-4 py-2.5
                                text-sm
                                text-zinc-200
                                outline-none
                                focus:border-blue-500
                                focus:ring-2
                                focus:ring-blue-500/30
                                resize-none
                            "
                            name="biografia"
                        />
                    </div>

                </div>

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