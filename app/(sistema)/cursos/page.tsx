'use client'

import ModalNovoCurso from "@/app/components/ModalNovoCurso"
import { useState } from "react"

export default function CursosPage() {
    const [modalOpen, setModalOpen] = useState(false)
    return (
        <div className="space-y-8">

            {/* Cabeçalho da página */}
            <div className="flex items-center justify-between">

                <div>
                    <h1 className="text-2xl font-semibold text-zinc-100">
                        Meus Cursos
                    </h1>

                    <p className="text-sm text-zinc-400 mt-1">
                        Gerencie os cursos publicados na plataforma
                    </p>
                </div>

                <button className="
                    bg-blue-600
                    hover:bg-blue-500
                    text-white
                    text-sm
                    font-medium
                    px-4 py-2
                    rounded-lg
                    transition
                    shadow-lg shadow-blue-600/20
                "
                    onClick={() => setModalOpen(true)}>
                    + Novo Curso
                </button>

            </div>


            {/* Barra de busca */}
            <div className="
                bg-zinc-900
                border border-zinc-800
                rounded-xl
                p-4
            ">
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


            {/* Grid de cursos */}
            <div className="
                grid
                gap-6
                sm:grid-cols-2
                lg:grid-cols-3
            ">

                {/* Card curso */}
                <div className="
                    bg-zinc-900
                    border border-zinc-800
                    rounded-xl
                    p-6
                    hover:border-blue-500/40
                    hover:bg-zinc-900/80
                    transition
                ">

                    <div className="flex items-start justify-between">

                        <h2 className="font-semibold text-zinc-100">
                            React para Iniciantes
                        </h2>

                        <span className="
                            text-xs
                            bg-blue-600/20
                            text-blue-400
                            px-2 py-1
                            rounded
                        ">
                            R$ 129
                        </span>

                    </div>

                    <p className="text-sm text-zinc-400 mt-3 line-clamp-3">
                        Curso completo ensinando os fundamentos do React,
                        componentes, hooks e criação de aplicações modernas.
                    </p>

                    <div className="
                        flex items-center justify-between
                        mt-6
                        text-sm text-zinc-400
                    ">
                        <span>8 módulos</span>

                        <button className="
                            text-blue-400
                            hover:text-blue-300
                            transition
                        ">
                            Ver detalhes →
                        </button>
                    </div>

                </div>


                {/* Card curso */}
                <div className="
                    bg-zinc-900
                    border border-zinc-800
                    rounded-xl
                    p-6
                    hover:border-blue-500/40
                    hover:bg-zinc-900/80
                    transition
                ">

                    <div className="flex items-start justify-between">

                        <h2 className="font-semibold text-zinc-100">
                            JavaScript Avançado
                        </h2>

                        <span className="
                            text-xs
                            bg-blue-600/20
                            text-blue-400
                            px-2 py-1
                            rounded
                        ">
                            R$ 159
                        </span>

                    </div>

                    <p className="text-sm text-zinc-400 mt-3">
                        Aprenda closures, prototypes, async/await,
                        event loop e conceitos avançados da linguagem.
                    </p>

                    <div className="
                        flex items-center justify-between
                        mt-6
                        text-sm text-zinc-400
                    ">
                        <span>12 módulos</span>

                        <button className="
                            text-blue-400
                            hover:text-blue-300
                            transition
                        ">
                            Ver detalhes →
                        </button>
                    </div>

                </div>


                {/* Card novo curso */}
                <div className="
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
                    onClick={() => setModalOpen(true)}
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