'use client'

import { useEffect, useState } from "react"
import Button from "@/app/components/Button"
import ModalNovoCurso from "@/app/components/ModalNovoCurso"
import CursoCard from "@/app/components/CursoCard"
import Loading from "@/app/components/Loading"
import { api } from "@/app/utils/api"
import { Curso } from "@/app/types"

export default function CursosPage() {

    const [modalOpen, setModalOpen] = useState(false)
    const [cursos, setCursos] = useState<Curso[]>([])
    const [loading, setLoading] = useState(true)

    useEffect(() => {
        buscaCursos()
    }, [])

    const buscaCursos = async () => {
        try {
            const response = await api.get<Curso[]>("/cursos")

            if (response.status !== 200) {
                throw new Error("Erro ao buscar cursos")
            }

            setCursos(response.data)

        } catch (error) {
            console.error(error)
            alert("Erro ao carregar cursos!")
        } finally {
            setLoading(false)
        }
    }

    if (loading) {
        return <Loading message="Carregando cursos..." fullScreen />
    }

    return (
        <div className="space-y-8">

            {/* HEADER */}
            <div className="flex items-center justify-between">

                <div>
                    <h1 className="text-2xl font-semibold text-zinc-100">
                        Meus Cursos
                    </h1>

                    <p className="text-sm text-zinc-400 mt-1">
                        Gerencie os cursos publicados na plataforma
                    </p>
                </div>

                <Button onClick={() => setModalOpen(true)}>
                    + Novo Curso
                </Button>

            </div>


            {/* BUSCA */}
            <div className="bg-zinc-900 border border-zinc-800 rounded-xl p-4">
                <input
                    type="text"
                    placeholder="Buscar cursos..."
                    className="
                        w-full
                        bg-zinc-950
                        border border-zinc-800
                        rounded-lg
                        px-4 py-2
                        text-sm
                        text-zinc-200
                        placeholder:text-zinc-500
                        outline-none
                        focus:border-blue-500
                        focus:ring-2
                        focus:ring-blue-500/30
                        transition
                    "
                />
            </div>

            <div className="grid gap-6 sm:grid-cols-2 lg:grid-cols-3">

                {cursos.map((curso) => (
                    <CursoCard key={curso.id} curso={curso} />
                ))}

                {/* CARD NOVO */}
                <div
                    onClick={() => setModalOpen(true)}
                    className="
                        border border-dashed border-zinc-700
                        rounded-xl
                        p-6
                        flex flex-col
                        items-center
                        justify-center
                        text-zinc-400
                        hover:border-blue-500
                        hover:text-blue-400
                        transition
                        cursor-pointer
                    "
                >
                    <span className="text-3xl mb-2">＋</span>
                    <span className="text-sm">
                        Criar novo curso
                    </span>
                </div>

            </div>

            <ModalNovoCurso
                open={modalOpen}
                onClose={() => setModalOpen(false)}
            />

        </div>
    )
}