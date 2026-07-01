'use client'

import { useDispatch, useSelector } from "react-redux"
import { AppDispatch, RootState } from "../redux/store"
import { logout } from "../redux/slices/authSlice"
import { useRouter } from "next/navigation"

export default function Header() {
    const usuario = useSelector((state: RootState) => state.auth.usuario)
    const dispatch = useDispatch<AppDispatch>()
    const router = useRouter()

    function handleLogout() {
        dispatch(logout())
        router.push("/login")
    }

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
                            {usuario?.email?.at(0)?.toUpperCase() || "U"}
                        </div>

                        <div className="hidden sm:flex flex-col leading-tight">
                            <span className="text-sm text-zinc-200">
                                {usuario?.role === "ROLE_ADMIN" ? "Administrador" : "Professor"}
                            </span>
                            <span className="text-xs text-zinc-400">
                                {usuario?.email || "usuario@email.com"}
                            </span>
                        </div>
                    </div>

                    <button
                        className="
                            text-sm
                            text-zinc-400
                            hover:text-red-400
                            transition
                            cursor-pointer
                        "
                        onClick={handleLogout}
                    >
                        Sair
                    </button>
                </div>
            </div>
        </header>
    )
}