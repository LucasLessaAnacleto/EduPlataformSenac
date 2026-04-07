'use client'

import { useAuth } from "../context/AuthContext";

export default function Sidebar() {
    const { logout } = useAuth();

    return (
        <aside className="
            w-64
            bg-zinc-900
            border-r border-zinc-800
            flex
            flex-col
        ">

            {/* Logo / Sistema */}
            <div className="
                h-16
                flex
                items-center
                gap-3
                px-6
                border-b border-zinc-800
            ">
                <div className="
                    w-8 h-8
                    rounded-lg
                    bg-blue-600
                    flex
                    items-center
                    justify-center
                    text-sm
                    font-bold
                    text-white
                ">
                    EP
                </div>

                <span className="font-semibold text-zinc-200">
                    EduPlatform
                </span>
            </div>


            {/* Navegação */}
            <nav className="flex-1 px-4 py-6">

                <ul className="space-y-2">

                    {/* Cursos */}
                    <li>
                        <a
                            href="/dashboard/courses"
                            className="
                                flex items-center gap-3
                                px-3 py-2
                                rounded-lg
                                text-sm
                                text-zinc-300
                                hover:bg-zinc-800
                                hover:text-white
                                transition
                            "
                        >
                            <span>📚</span>
                            Cursos
                        </a>
                    </li>

                    {/* Módulos */}
                    <li>
                        <a
                            href="#"
                            className="
                                flex items-center gap-3
                                px-3 py-2
                                rounded-lg
                                text-sm
                                text-zinc-300
                                hover:bg-zinc-800
                                hover:text-white
                                transition
                            "
                        >
                            <span>📦</span>
                            Módulos
                        </a>
                    </li>

                    {/* Matrículas */}
                    <li>
                        <a
                            href="#"
                            className="
                                flex items-center gap-3
                                px-3 py-2
                                rounded-lg
                                text-sm
                                text-zinc-300
                                hover:bg-zinc-800
                                hover:text-white
                                transition
                            "
                        >
                            <span>👨‍🎓</span>
                            Matrículas
                        </a>
                    </li>

                    {/* Configurações */}
                    <li>
                        <a
                            href="#"
                            className="
                                flex items-center gap-3
                                px-3 py-2
                                rounded-lg
                                text-sm
                                text-zinc-300
                                hover:bg-zinc-800
                                hover:text-white
                                transition
                            "
                        >
                            <span>⚙</span>
                            Configurações
                        </a>
                    </li>

                </ul>

            </nav>


            {/* Rodapé da Sidebar */}
            <div className="
                border-t border-zinc-800
                p-4
            ">

                <button
                    className="
                        w-full
                        text-left
                        flex items-center gap-3
                        px-3 py-2
                        rounded-lg
                        text-sm
                        text-zinc-400
                        hover:bg-zinc-800
                        hover:text-red-400
                        transition
                    "
                    onClick={() => logout()}
                >
                    🚪 Sair
                </button>

            </div>

        </aside>
    )
}