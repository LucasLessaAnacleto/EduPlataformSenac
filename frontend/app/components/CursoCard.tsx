'use client'

import Link from "next/link"

type Curso = {
    id: number
    titulo: string
    descricao: string
    preco: number
}
export default function CursoCard({ curso }: { curso: Curso }) {

    return (
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
                    {curso.titulo}
                </h2>

                <span className="
                    text-xs
                    bg-blue-600/20
                    text-blue-400
                    px-2 py-1
                    rounded
                ">
                    R$ {curso.preco}
                </span>

            </div>

            <p className="text-sm text-zinc-400 mt-3 line-clamp-3">
                {curso.descricao}
            </p>

            <div className="
                flex items-center justify-end
                mt-6
                text-sm text-zinc-400
            ">

                <Link
                    href={`/cursos/${curso.id}`}
                    className="
                        text-blue-400
                        hover:text-blue-300
                        transition
                    "
                >
                    Ver detalhes →
                </Link>

            </div>

        </div>
    )
}