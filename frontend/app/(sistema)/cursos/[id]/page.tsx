'use client'

import Loading from "@/app/components/Loading";
import ModalEditarModulo from "@/app/components/ModalEditarModulo";
import ModalNovoModulo from "@/app/components/ModalNovoModulo";
import { Curso, Matricula, Modulo } from "@/app/types";
import { api } from "@/app/utils/api";
import { useParams } from "next/navigation";
import { useEffect, useState } from "react";

export default function CursoDetalhe() {

    const params = useParams()
    const cursoId = Number(params.id)

    const [curso, setCurso] = useState<Curso | null>(null)
    const [modulos, setModulos] = useState<Modulo[]>([])
    const [matriculas, setMatriculas] = useState<Matricula[]>([])
    const [loading, setLoading] = useState(true)

    const [modalOpen, setModalOpen] = useState(false)
    const [modalOpenEdit, setModalOpenEdit] = useState<number | null>(null)

    useEffect(() => {
        fetchData()
    }, [])

    const fetchData = async () => {
        try {

            const [cursoRes, modulosRes, matriculasRes] = await Promise.all([
                api.get<Curso>(`/cursos/${cursoId}`),
                api.get<Modulo[]>(`/cursos/${cursoId}/modulos`),
                api.get<Matricula[]>(`/cursos/${cursoId}/matriculas`)
            ])

            setCurso(cursoRes.data)
            setModulos(modulosRes.data)
            setMatriculas(matriculasRes.data)

        } catch (error) {
            console.error(error)
            alert("Erro ao carregar dados do curso!")
        } finally {
            setLoading(false)
        }
    }

    if (loading) {
        return <Loading message="Carregando curso..." />
    }

    if (!curso) {
        return <div className="text-red-400 p-6">Curso não encontrado</div>
    }

    return (
        <div className="space-y-8">
            <div className="bg-zinc-900 border border-zinc-800 rounded-xl p-6">
                <div className="flex items-start justify-between">
                    <div>
                        <h1 className="text-2xl font-semibold text-zinc-100">
                            {curso.titulo}
                        </h1>

                        <p className="text-sm text-zinc-400 mt-2 max-w-xl">
                            {curso.descricao}
                        </p>
                    </div>

                    <div className="
                        bg-blue-600/20
                        text-blue-400
                        text-sm
                        px-3 py-1.5
                        rounded-lg
                    ">
                        R$ {curso.preco}
                    </div>
                </div>
            </div>

            <div className="grid gap-8 lg:grid-cols-2">

                <div className="bg-zinc-900 border border-zinc-800 rounded-xl p-6">

                    <div className="flex items-center justify-between mb-6">

                        <h2 className="font-semibold text-zinc-100">
                            Módulos do Curso
                        </h2>

                        <button
                            onClick={() => setModalOpen(true)}
                            className="
                                text-sm
                                bg-blue-600
                                hover:bg-blue-500
                                text-white
                                px-3 py-1.5
                                rounded-lg
                                transition
                            "
                        >
                            + Novo módulo
                        </button>

                    </div>


                    <div className="space-y-3">

                        {modulos
                            .sort((a, b) => a.ordem - b.ordem)
                            .map((modulo) => (
                                <div
                                    key={modulo.id}
                                    className="
                                        flex items-center justify-between
                                        p-3
                                        rounded-lg
                                        bg-zinc-950
                                        border border-zinc-800
                                    "
                                >

                                    <span className="text-sm text-zinc-300">
                                        {modulo.ordem}. {modulo.titulo}
                                    </span>

                                    <button
                                        onClick={() => setModalOpenEdit(modulo.id)}
                                        className="
                                            text-xs
                                            text-blue-400
                                            hover:text-blue-300
                                        "
                                    >
                                        Editar
                                    </button>

                                </div>
                            ))}

                    </div>

                </div>



                {/* MATRÍCULAS */}
                <div className="bg-zinc-900 border border-zinc-800 rounded-xl p-6">

                    <h2 className="font-semibold text-zinc-100 mb-6">
                        Alunos Matriculados
                    </h2>

                    <div className="overflow-x-auto">

                        <table className="w-full text-sm">

                            <thead className="text-zinc-400 border-b border-zinc-800">
                                <tr>
                                    <th className="text-left py-2">Aluno</th>
                                    <th className="text-left py-2">Email</th>
                                    <th className="text-left py-2">Data</th>
                                </tr>
                            </thead>

                            <tbody className="text-zinc-300">

                                {matriculas.map((m) => (
                                    <tr key={m.id} className="border-b border-zinc-800">

                                        <td className="py-3">{m.nomeAluno}</td>
                                        <td>{m.emailAluno}</td>
                                        <td>
                                            {new Date(m.data).toLocaleDateString()}
                                        </td>

                                    </tr>
                                ))}

                            </tbody>

                        </table>

                    </div>

                </div>

            </div>


            {/* MODAIS */}
            <ModalNovoModulo
                open={modalOpen}
                onClose={() => setModalOpen(false)}
                cursoId={cursoId}
            />

            <ModalEditarModulo
                open={modalOpenEdit}
                onClose={() => setModalOpenEdit(null)}
                modulo={modulos.find(m => m.id === modalOpenEdit)}
            />

        </div>
    )
}