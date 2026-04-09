'use client'

import { api } from "../utils/api"
import { Curso } from "../types"
import { useState } from "react"

type Props = {
    open: boolean
    onClose: () => void
    curso: Curso
    onUpdate: (curso: Curso) => void
}

export default function ModalEditarCurso({
    open,
    onClose,
    curso,
    onUpdate
}: Props) {

    if (!open || !curso) return null

    async function handleUpdate(formData: FormData) {
        const titulo = formData.get("titulo")?.toString()
        const descricao = formData.get("descricao")?.toString()
        const preco = Number(formData.get("preco")?.toString() || 0)

        if (!titulo || !descricao || !preco) {
            alert("Preencha todos os campos!")
            return
        }

        const response = await api.put<Curso>(`/cursos/${curso.id}`, {
            titulo,
            descricao,
            preco
        })

        if (response.status !== 200) {
            alert("Erro ao atualizar curso!")
            return
        }

        alert("Curso atualizado com sucesso!")

        onUpdate(response.data)

        onClose()
    }

    return (
        <div className="fixed inset-0 z-50 flex items-center justify-center bg-black/60 backdrop-blur-sm">

            <div className="w-full max-w-md bg-zinc-900 border border-zinc-800 rounded-xl shadow-2xl p-6">

                {/* HEADER */}
                <div className="flex items-center justify-between mb-6">
                    <h2 className="text-lg font-semibold text-zinc-100">
                        Editar curso
                    </h2>

                    <button
                        onClick={onClose}
                        className="text-zinc-400 hover:text-zinc-200 text-sm"
                    >
                        ✕
                    </button>
                </div>

                {/* FORM */}
                <form className="space-y-5" action={handleUpdate}>

                    <div className="flex flex-col gap-2">
                        <label className="text-sm text-zinc-400">Título</label>

                        <input
                            name="titulo"
                            defaultValue={curso.titulo}
                            className="bg-zinc-950 border border-zinc-800 rounded-lg px-4 py-2 text-sm text-zinc-200"
                        />
                    </div>

                    <div className="flex flex-col gap-2">
                        <label className="text-sm text-zinc-400">Descrição</label>

                        <textarea
                            name="descricao"
                            defaultValue={curso.descricao}
                            className="bg-zinc-950 border border-zinc-800 rounded-lg px-4 py-2 text-sm text-zinc-200"
                        />
                    </div>

                    <div className="flex flex-col gap-2">
                        <label className="text-sm text-zinc-400">Preço</label>

                        <input
                            name="preco"
                            type="number"
                            step="0.01"
                            defaultValue={curso.preco}
                            className="bg-zinc-950 border border-zinc-800 rounded-lg px-4 py-2 text-sm text-zinc-200"
                        />
                    </div>

                    {/* FOOTER */}
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
                            Salvar
                        </button>

                    </div>

                </form>

            </div>
        </div>
    )
}