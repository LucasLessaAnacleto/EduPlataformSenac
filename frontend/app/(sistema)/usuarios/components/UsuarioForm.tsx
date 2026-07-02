'use client'

import { atualizarUsuario, salvarUsuarioProfessor } from "@/app/services/usuarioService"
import { atualizarProfessorPorUsuarioId } from "@/app/services/professorService"
import { Professor } from "@/app/types/professor"
import { Usuario } from "@/app/types/usuarios"
import Link from "next/link"
import { useRouter } from "next/navigation"
import { useState } from "react"

interface UsuarioFormProps {
    usuarioExistente?: Usuario
    professorExistente?: Professor | null
}

export default function UsuarioForm({
    usuarioExistente,
    professorExistente
}: UsuarioFormProps) {

    const [usuario, setUsuario] = useState<Usuario>(
        usuarioExistente || new Usuario(
            null,
            "",
            "ATIVO",
            "ROLE_PROFESSOR"
        )
    )

    const [professor, setProfessor] = useState({
        nome: professorExistente?.nome || "",
        cpf: professorExistente?.cpf || "",
        biografia: professorExistente?.biografia || ""
    })

    const router = useRouter()

    const ehEdicao = usuario.id !== null && usuario.id > 0
    const ehAdmin = usuario.role === "ROLE_ADMIN"
    const ehProfessor = usuario.role === "ROLE_PROFESSOR"

    const handleChangeUsuario = (campo: string, valor: string) => {
        setUsuario((prev: any) => ({
            ...prev,
            [campo]: valor
        }))
    }

    const handleChangeProfessor = (campo: string, valor: string) => {
        setProfessor((prev) => ({
            ...prev,
            [campo]: valor
        }))
    }

    const handleSalvar = async (formData: FormData) => {
        const senha = formData.get("senha")?.toString()

        if (!usuario.email) {
            alert("Informe o e-mail!")
            return
        }

        if (!ehEdicao && !senha) {
            alert("A senha é obrigatória!")
            return
        }

        if (ehProfessor) {
            if (!professor.nome || !professor.cpf || !professor.biografia) {
                alert("Preencha os dados do professor!")
                return
            }
        }

        try {
            if (!ehEdicao) {
                await salvarUsuarioProfessor({
                    email: usuario.email,
                    senha: senha || "",
                    nome: professor.nome,
                    cpf: professor.cpf,
                    biografia: professor.biografia
                })

                alert("Professor criado com sucesso!")
                router.push("/usuarios")
                return
            }

            await atualizarUsuario(Number(usuario.id), {
                email: usuario.email,
                senha: senha!,
                status: usuario.status
            })

            if (ehProfessor) {
                await atualizarProfessorPorUsuarioId(Number(usuario.id), {
                    nome: professor.nome,
                    cpf: professor.cpf,
                    biografia: professor.biografia
                })
            }

            alert("Usuário salvo com sucesso!")
            router.push("/usuarios")

        } catch (error) {
            console.error(error)
            alert("Erro ao salvar usuário!")
        }
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
                <div className="grid grid-cols-1 md:grid-cols-2 gap-6">

                    <div className="flex flex-col gap-2">
                        <label className="text-sm text-zinc-400">
                            Perfil
                        </label>

                        <input
                            type="text"
                            value={ehAdmin ? "Administrador" : "Professor"}
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
                            value={usuario.email}
                            onChange={(e) => handleChangeUsuario("email", e.target.value)}
                            placeholder="usuario@email.com"
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
                            placeholder={ehEdicao ? "Deixe em branco para não alterar" : "••••••••"}
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
                                transition
                            "
                            name="senha"
                        />
                    </div>

                    <div className="flex flex-col gap-2">
                        <label className="text-sm text-zinc-400">
                            Status
                        </label>

                        <select
                            value={usuario.status}
                            onChange={(e) => handleChangeUsuario("status", e.target.value)}
                            disabled={!ehEdicao}
                            className={`
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
                                transition
                                ${!ehEdicao ? "cursor-not-allowed opacity-50" : ""}
                            `}
                            name="status"
                        >
                            <option value="ATIVO">Ativo</option>
                            <option value="INATIVO">Inativo</option>
                        </select>
                    </div>

                    {ehProfessor && (
                        <>
                            <div className="flex flex-col gap-2">
                                <label className="text-sm text-zinc-400">
                                    Nome completo
                                </label>

                                <input
                                    type="text"
                                    value={professor.nome}
                                    onChange={(e) => handleChangeProfessor("nome", e.target.value)}
                                    placeholder="João da Silva"
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
                                        transition
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
                                    onChange={(e) => handleChangeProfessor("cpf", e.target.value)}
                                    placeholder="000.000.000-00"
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
                                        transition
                                    "
                                    name="cpf"
                                />
                            </div>

                            <div className="flex flex-col gap-2 md:col-span-2">
                                <label className="text-sm text-zinc-400">
                                    Biografia
                                </label>

                                <textarea
                                    value={professor.biografia}
                                    onChange={(e) => handleChangeProfessor("biografia", e.target.value)}
                                    placeholder="Descreva a experiência do professor"
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
                                        transition
                                        min-h-28
                                        resize-none
                                    "
                                    name="biografia"
                                />
                            </div>
                        </>
                    )}

                </div>

                <div className="border-t border-zinc-800 pt-6 flex items-center justify-between">

                    <div className="text-sm text-zinc-500">
                        {ehEdicao ? `ID #${usuario.id}` : "Novo cadastro"}
                    </div>

                    <div className="flex items-center gap-3">

                        <Link
                            href="/usuarios"
                            className="
                                px-4 py-2
                                text-sm
                                rounded-lg
                                text-zinc-400
                                hover:text-white
                                hover:bg-zinc-800
                                transition
                                cursor-pointer
                            "
                        >
                            Cancelar
                        </Link>

                        <button
                            type="submit"
                            className="
                                px-6 py-2
                                bg-blue-600
                                hover:bg-blue-500
                                active:bg-blue-700
                                text-white
                                text-sm
                                font-medium
                                rounded-lg
                                transition
                                shadow-lg shadow-blue-600/20
                                cursor-pointer
                            "
                        >
                            {ehEdicao ? "Salvar alterações" : "Criar professor"}
                        </button>

                    </div>

                </div>

            </div>

        </form>
    )
}