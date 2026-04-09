'use client'
import { useAuth } from "../context/AuthContext";

export default function Header() {
    const { usuario, logout } = useAuth();
    return (
        <header className="
            h-16
            border-b border-zinc-800
            bg-zinc-900/70
            backdrop-blur
        ">

            <div className="max-w-7xl mx-auto px-6 h-full flex items-center justify-between">

                <div className="flex items-center gap-4">

                    <div className="flex items-center gap-3">

                        <div className="w-8 h-8 rounded-lg bg-blue-600 flex items-center justify-center text-sm font-bold"> 
                            EP
                        </div>

                        <span className="font-semibold text-zinc-200">
                            EduPlatform
                        </span>

                    </div>

                </div>

                {/* Lado direito */}
                <div className="flex items-center gap-4">

                    <div className="flex items-center gap-3">

                        <div className="
                            w-9 h-9
                            rounded-full
                            bg-blue-600
                            flex items-center
                            justify-center
                            text-sm
                            font-semibold
                        ">
                            {usuario?.nome?.at(0) || "P"}
                        </div>

                        <div className="hidden sm:flex flex-col leading-tight">
                            <span className="text-sm text-zinc-200">
                                {usuario?.nome || "Professor"}
                            </span>
                            <span className="text-xs text-zinc-400">
                                {usuario?.email || "professor@email.com"}
                            </span>
                        </div>

                    </div>

                    <button className="
                        text-sm
                        text-zinc-400
                        hover:text-red-400
                        transition
                        cursor-pointer
                    "
                     onClick={() => logout()}
                     >
                        Sair
                    </button>

                </div>

            </div>

        </header>
    )
}