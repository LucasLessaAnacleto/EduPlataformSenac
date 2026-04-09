'use client'

import Link from "next/link"
import { usePathname } from "next/navigation"
import { useAuth } from "../context/AuthContext"

import {BookOpen,User,LogOut} from "lucide-react"

export default function Sidebar() {

    const { logout } = useAuth()
    const pathname = usePathname()

    const menu = [
        {
            name: "Cursos",
            href: "/cursos",
            icon: BookOpen
        },
        {
            name: "Perfil",
            href: "/perfil",
            icon: User
        }
    ]

    return (
        <aside className="w-64 bg-zinc-900 border-r border-zinc-800 flex flex-col">

            <div className="h-16 flex items-center gap-3 px-6 border-b border-zinc-800">
                <div className="w-9 h-9 rounded-lg bg-blue-600 flex items-center justify-center text-sm font-bold text-white shadow-md shadow-blue-600/20">
                    EP
                </div>
                <span className="font-semibold text-zinc-100 text-lg">
                    EduPlatform
                </span>
            </div>

            <nav className="flex-1 px-3 py-6">
                <ul className="space-y-1">

                    {menu.map((item) => {
                        const isActive = pathname === item.href
                        const Icon = item.icon

                        return (
                            <li key={item.href}>
                                <Link href={item.href} className={`flex items-center gap-3 px-3 py-2.5 rounded-lg text-sm transition
                                    ${isActive ? "bg-blue-600/10 text-blue-400 border border-blue-600/20" : "text-zinc-400 hover:bg-zinc-800 hover:text-white"}`}
                                >
                                    <Icon className={`w-4 h-4 ${isActive ? "text-blue-400" : ""}`} />
                                    {item.name}
                                </Link>
                            </li>
                        )
                    })}
                </ul>

            </nav>

            <div className="border-t border-zinc-800 p-4">
                <button onClick={logout} className="w-full flex items-center gap-3 px-3 py-2.5 rounded-lg text-sm text-zinc-400 hover:bg-zinc-800 hover:text-red-400 transition cursor-pointer">
                    <LogOut className="w-4 h-4" />
                    Sair
                </button>
            </div>

        </aside>
    )
}