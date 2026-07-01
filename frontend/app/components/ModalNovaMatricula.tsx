'use client'

import { Matricula } from "../types/matriculas"
import { salvarMatricula } from "../services/matriculaService"

type Props = {
    open: boolean
    onClose: () => void
    cursoId: number
    onCreate: (matricula: Matricula) => void
}

export default function ModalNovaMatricula({
    open,
    onClose,
    cursoId,
    onCreate
}: Props) {

    if (!open) return null

    async function handleNovaMatricula(formData: FormData) {
        const nomeAluno = formData.get("nomeAluno")?.toString()
        const emailAluno = formData.get("emailAluno")?.toString()

        if (!nomeAluno || !emailAluno) {
            alert("Preencha todos os campos!")
            return
        }

        try {
            const matriculaId = await salvarMatricula({
                nomeAluno,
                emailAluno,
                cursoId
            })

            alert("Matrícula criada com sucesso!")

            onCreate({
                id: Number(matriculaId),
                nomeAluno,
                emailAluno,
                data: new Date().toISOString(),
                cursoId
            })

            onClose()

        } catch (error) {
            console.error(error)
            alert("Erro ao criar matrícula!")
        }
    }

    return (
        <div className="fixed inset-0 z-50 flex items-center justify-center bg-black/60 backdrop-blur-sm">

            <div className="w-full max-w-md bg-zinc-900 border border-zinc-800 rounded-xl shadow-2xl p-6">

                <div className="flex items-center justify-between mb-6">
                    <h2 className="text-lg font-semibold text-zinc-100">
                        Nova matrícula
                    </h2>

                    <button
                        onClick={onClose}
                        className="text-zinc-400 hover:text-zinc-200 text-sm"
                    >
                        ✕
                    </button>
                </div>

                <form className="space-y-5" action={handleNovaMatricula}>

                    <div className="flex flex-col gap-2">
                        <label className="text-sm text-zinc-400">
                            Nome do aluno
                        </label>

                        <input
                            name="nomeAluno"
                            className="bg-zinc-950 border border-zinc-800 rounded-lg px-4 py-2 text-sm text-zinc-200"
                        />
                    </div>

                    <div className="flex flex-col gap-2">
                        <label className="text-sm text-zinc-400">
                            Email
                        </label>

                        <input
                            name="emailAluno"
                            type="email"
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
                            Criar matrícula
                        </button>
                    </div>

                </form>
            </div>
        </div>
    )
}