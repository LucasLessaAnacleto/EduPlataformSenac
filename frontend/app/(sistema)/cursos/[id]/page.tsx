'use client'

import ModalEditarModulo from "@/app/components/ModalEditarModulo";
import ModalNovoModulo from "@/app/components/ModalNovoModulo";
import { useState } from "react";

export default function CursoDetalhe() {

    const [modalOpen, setModalOpen] = useState(false);
    const [modalOpenEdit, setModalOpenEdit] = useState<null|number>(null);

    const module = {
        id: "1",
        title: "Introdução ao React",
        order: 1
    }


    return (
        <div className="space-y-8">

            {/* Cabeçalho do curso */}
            <div className="
                bg-zinc-900
                border border-zinc-800
                rounded-xl
                p-6
            ">
                <div className="flex items-start justify-between">

                    <div>
                        <h1 className="text-2xl font-semibold text-zinc-100">
                            React para Iniciantes
                        </h1>

                        <p className="text-sm text-zinc-400 mt-2 max-w-xl">
                            Curso completo ensinando fundamentos do React,
                            criação de componentes, hooks e boas práticas
                            para desenvolvimento moderno.
                        </p>
                    </div>

                    <div className="
                        bg-blue-600/20
                        text-blue-400
                        text-sm
                        px-3 py-1.5
                        rounded-lg
                    ">
                        R$ 129
                    </div>

                </div>
            </div>


            {/* Conteúdo principal */}
            <div className="
                grid
                gap-8
                lg:grid-cols-2
            ">


                {/* MÓDULOS */}
                <div className="
                    bg-zinc-900
                    border border-zinc-800
                    rounded-xl
                    p-6
                ">

                    <div className="flex items-center justify-between mb-6">

                        <h2 className="font-semibold text-zinc-100">
                            Módulos do Curso
                        </h2>

                        <button className="
                            text-sm
                            bg-blue-600
                            hover:bg-blue-500
                            text-white
                            px-3 py-1.5
                            rounded-lg
                            transition
                        "
                            onClick={() => setModalOpen(true)}
                        >
                            + Novo módulo
                        </button>

                    </div>


                    {/* Lista de módulos */}
                    <div className="space-y-3">

                        <div className="
                            flex items-center justify-between
                            p-3
                            rounded-lg
                            bg-zinc-950
                            border border-zinc-800
                        ">

                            <span className="text-sm text-zinc-300">
                                1. Introdução ao React
                            </span>

                            <button className="
                                text-xs
                                text-blue-400
                                hover:text-blue-300
                            "
                                onClick={() => setModalOpenEdit(1)}
                            >
                                Editar
                            </button>

                        </div>


                        <div className="
                            flex items-center justify-between
                            p-3
                            rounded-lg
                            bg-zinc-950
                            border border-zinc-800
                        ">

                            <span className="text-sm text-zinc-300">
                                2. Componentes e Props
                            </span>

                            <button className="
                                text-xs
                                text-blue-400
                                hover:text-blue-300
                            " 
                                onClick={() => setModalOpenEdit(1)}
                             
                            >
                                Editar
                            </button>

                        </div>


                        <div className="
                            flex items-center justify-between
                            p-3
                            rounded-lg
                            bg-zinc-950
                            border border-zinc-800
                        ">

                            <span className="text-sm text-zinc-300">
                                3. Hooks Essenciais
                            </span>

                            <button className="
                                text-xs
                                text-blue-400
                                hover:text-blue-300
                            "    
                                onClick={() => setModalOpenEdit(1)}
                            >
                                Editar
                            </button>

                        </div>

                    </div>

                </div>



                {/* ALUNOS */}
                <div className="
                    bg-zinc-900
                    border border-zinc-800
                    rounded-xl
                    p-6
                ">

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

                                <tr className="border-b border-zinc-800">
                                    <td className="py-3">João Silva</td>
                                    <td>joao@email.com</td>
                                    <td>12/03/2026</td>
                                </tr>

                                <tr className="border-b border-zinc-800">
                                    <td className="py-3">Maria Souza</td>
                                    <td>maria@email.com</td>
                                    <td>15/03/2026</td>
                                </tr>

                                <tr>
                                    <td className="py-3">Carlos Lima</td>
                                    <td>carlos@email.com</td>
                                    <td>18/03/2026</td>
                                </tr>

                            </tbody>

                        </table>

                    </div>

                </div>

            </div>

            <ModalNovoModulo
                open={modalOpen}
                onClose={() => setModalOpen(false)}
            />

            <ModalEditarModulo
                onClose={() => setModalOpenEdit(null)}
                open={modalOpenEdit}
                module={module}
            />
        </div>
    )
}