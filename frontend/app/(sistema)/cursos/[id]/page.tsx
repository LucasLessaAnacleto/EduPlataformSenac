'use client'

'use client'

import Loading from "@/app/components/Loading"
import ModalEditarMatricula from "@/app/components/ModalEditarMatricula"
import ModalEditarModulo from "@/app/components/ModalEditarModulo"
import ModalNovaMatricula from "@/app/components/ModalNovaMatricula"
import ModalNovoModulo from "@/app/components/ModalNovoModulo"
import ModalEditarCurso from "@/app/components/ModalEditarCurso"
import { Curso } from "@/app/types/cursos"
import { Modulo } from "@/app/types/modulos"
import { Matricula } from "@/app/types/matriculas"
import { buscarCursoPorId } from "@/app/services/cursoService"
import { buscarModulosPorCurso, deletarModulo } from "@/app/services/moduloService"
import { buscarMatriculasPorCurso, deletarMatricula } from "@/app/services/matriculaService"
import Link from "next/link"
import { useParams } from "next/navigation"
import { useEffect, useState } from "react"

export default function CursoDetalhe() {

    const params = useParams()
    const cursoId = Number(params.id)

    const [curso, setCurso] = useState<Curso | null>(null)
    const [modulos, setModulos] = useState<Modulo[]>([])
    const [matriculas, setMatriculas] = useState<Matricula[]>([])
    const [loading, setLoading] = useState(true)

    const [modalOpen, setModalOpen] = useState(false)
    const [modalOpenEdit, setModalOpenEdit] = useState<number | null>(null);
    const [modalNovaMatricula, setModalNovaMatricula] = useState(false)
    const [modalEditarMatricula, setModalEditarMatricula] = useState<number | null>(null);
    
    const [modalEditarCurso, setModalEditarCurso] = useState(false);


    useEffect(() => {
        fetchData()
    }, [])

    const fetchData = async () => {
        try {
            const [cursoDados, modulosDados, matriculasDados] = await Promise.all([
                buscarCursoPorId(cursoId),
                buscarModulosPorCurso(cursoId),
                buscarMatriculasPorCurso(cursoId)
            ])

            setCurso(cursoDados)
            setModulos(modulosDados)
            setMatriculas(matriculasDados)

        } catch (error) {
            console.error(error)
            alert("Erro ao carregar dados do curso!")
        } finally {
            setLoading(false)
        }
    }

    const handleDeleteModulo = async (id: number | null) => {
        if (!confirm("Deseja realmente excluir este módulo?")) return

        try {
            await deletarModulo(id)
            setModulos(prev => prev.filter(m => m.id !== id))
        } catch (error) {
            console.error(error)
            alert("Erro ao excluir módulo")
        }
    }

    const handleDeleteMatricula = async (id: number) => {
        if (!confirm("Remover matrícula?")) return

        try {
            await deletarMatricula(id)
            setMatriculas(prev => prev.filter(m => m.id !== id))
        } catch (error) {
            console.error(error)
            alert("Erro ao remover matrícula")
        }
    }

    const handleNovoModulo = async (modulo: Modulo) => {
        setModulos(prev => [...prev, modulo]);
    }

    const handleNovaMatricula = async(matricula: Matricula) => {
        setMatriculas(prev => [...prev, matricula]);
    }

    const handleEditarModulo = async (id: Number, modulo: Modulo) => {
        setModulos(prev => prev.map(item => item.id == id ? modulo : item))
    }

    const handleEditarMatricula = async (id: Number, matricula: Matricula) => {
        setMatriculas(prev => prev.map(item => item.id == id ? matricula : item))
    }

    const handleUpdateCurso = (cursoAtualizado: Curso) => {
        setCurso(cursoAtualizado)
    }

    if (loading) {
        return <Loading message="Carregando curso..." />
    }

    if (!curso) {
        return <div className="text-red-400 p-6">Curso não encontrado</div>
    }

    return (
        <div className="space-y-8">
            <Link href="/cursos"
                className="
                    inline-flex items-center gap-2
                    text-sm text-zinc-400
                    hover:text-zinc-200
                    transition
                "
            >
                ← Voltar para cursos
            </Link>
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

                    <div>
                        <div className="
                            bg-blue-600/20
                            text-blue-400
                            text-sm
                            px-3 py-1.5
                            rounded-lg
                        ">
                            R$ {curso.preco}
                        </div>
                        <button
                            onClick={() => setModalEditarCurso(true)}
                            className="text-xs text-blue-400 hover:text-blue-300 mt-4 cursor-pointer"
                        >
                            Editar curso
                        </button>
                    <div></div>
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

                                    <div className="flex items-center gap-3">
                                        <button
                                            onClick={() => setModalOpenEdit(modulo.id)}
                                            className="text-xs text-blue-400 hover:text-blue-300"
                                        >
                                            Editar
                                        </button>

                                        <button
                                            onClick={() => handleDeleteModulo(modulo.id)}
                                            className="text-xs text-red-400 hover:text-red-300"
                                        >
                                            Excluir
                                        </button>
                                    </div>

                                </div>
                            ))}

                    </div>

                </div>



                {/* MATRÍCULAS */}
                <div className="bg-zinc-900 border border-zinc-800 rounded-xl p-6">

                    <div className="flex items-center justify-between mb-6">
                        <h2 className="font-semibold text-zinc-100">
                            Alunos Matriculados
                        </h2>

                        <button
                            onClick={() => setModalNovaMatricula(true)}
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
                            + Adicionar aluno
                        </button>
                    </div>

                    <div className="overflow-x-auto">

                        <table className="w-full text-sm">

                            <thead className="text-zinc-400 border-b border-zinc-800">
                                <tr>
                                    <th className="text-left py-2">Aluno</th>
                                    <th className="text-left py-2">Email</th>
                                    <th className="text-left py-2">Data</th>
                                    <th className="text-right py-2">Ações</th>
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
                                        <td className="text-right">
                                            <button
                                                onClick={() => setModalEditarMatricula(m.id)}
                                                className="text-xs text-blue-400 hover:text-blue-300 mr-3"
                                            >
                                                Editar
                                            </button>

                                            <button
                                                onClick={() => handleDeleteMatricula(m.id)}
                                                className="text-xs text-red-400 hover:text-red-300"
                                            >
                                                Excluir
                                            </button>
                                        </td>

                                    </tr>
                                ))}

                            </tbody>

                        </table>

                    </div>

                </div>

            </div>


            {/* MODAIS */}
            <ModalEditarCurso
                open={modalEditarCurso}
                onClose={() => setModalEditarCurso(false)}
                curso={curso}
                onUpdate={handleUpdateCurso}
            />
            
            <ModalNovoModulo
                open={modalOpen}
                onClose={() => setModalOpen(false)}
                cursoId={cursoId}
                onCreate={handleNovoModulo}
            />

            <ModalEditarModulo
                open={modalOpenEdit}
                onClose={() => setModalOpenEdit(null)}
                modulo={modulos.find(m => m.id === modalOpenEdit)!}
                onUpdate={handleEditarModulo}
            />

            <ModalNovaMatricula
                open={modalNovaMatricula}
                onClose={() => setModalNovaMatricula(false)}
                cursoId={cursoId}
                onCreate={handleNovaMatricula}
            />

            <ModalEditarMatricula
                open={modalEditarMatricula}
                onClose={() => setModalEditarMatricula(null)}
                matricula={matriculas.find(m => m.id === modalEditarMatricula)!}
                onUpdate={handleEditarMatricula}
            />

        </div>
    )
}