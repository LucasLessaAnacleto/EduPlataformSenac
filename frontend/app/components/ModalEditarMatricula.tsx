'use client'

import { api } from "../utils/api"
import { Matricula } from "../types"

type Props = {
    open: number | null
    onClose: () => void
    matricula: Matricula
    onUpdate: (id: Number, matricula: Matricula) => void
}

export default function ModalEditarMatricula({
    open,
    onClose,
    matricula,
    onUpdate
}: Props) {

    if (!open || !matricula) return null

    async function handleUpdate(formData: FormData) {
        const nomeAluno = formData.get("nomeAluno")?.toString()
        const emailAluno = formData.get("emailAluno")?.toString()

        if (!nomeAluno || !emailAluno) {
            alert("Preencha todos os campos!")
            return
        }

        const response = await api.put(`/matriculas/${matricula.id}`, {
            nomeAluno,
            emailAluno
        })

        if (response.status !== 200) {
            alert("Erro ao atualizar matrícula!")
            return
        }

        alert("Matrícula atualizada!")

        onUpdate(matricula.id,{
            ...matricula,
            nomeAluno,
            emailAluno
        })

        onClose()
    }

    return (
        <div className="fixed inset-0 z-50 flex items-center justify-center bg-black/60 backdrop-blur-sm">

            <div className="w-full max-w-md bg-zinc-900 border border-zinc-800 rounded-xl shadow-2xl p-6">

                {/* Header */}
                <div className="flex items-center justify-between mb-6">
                    <h2 className="text-lg font-semibold text-zinc-100">
                        Editar matrícula
                    </h2>

                    <button
                        onClick={onClose}
                        className="text-zinc-400 hover:text-zinc-200 text-sm"
                    >
                        ✕
                    </button>
                </div>

                <form className="space-y-5" action={handleUpdate}>

                    <div className="flex flex-col gap-2">
                        <label className="text-sm text-zinc-400">
                            Nome
                        </label>

                        <input
                            name="nomeAluno"
                            defaultValue={matricula.nomeAluno}
                            className="bg-zinc-950 border border-zinc-800 rounded-lg px-4 py-2 text-sm text-zinc-200"
                        />
                    </div>

                    <div className="flex flex-col gap-2">
                        <label className="text-sm text-zinc-400">
                            Email
                        </label>

                        <input
                            name="emailAluno"
                            defaultValue={matricula.emailAluno}
                            className="bg-zinc-950 border border-zinc-800 rounded-lg px-4 py-2 text-sm text-zinc-200"
                        />
                    </div>

                    <div className="flex justify-end gap-3 pt-4">
                        <button
                            type="button"
                            onClick={onClose}
                            className="px-4 py-2 text-sm text-zinc-400 hover:text-zinc-200"
                        >
                            Cancelar
                        </button>

                        <button
                            type="submit"
                            className="bg-blue-600 hover:bg-blue-500 text-white text-sm px-4 py-2 rounded-lg"
                        >
                            Salvar alterações
                        </button>
                    </div>

                </form>

            </div>
        </div>
    )
}