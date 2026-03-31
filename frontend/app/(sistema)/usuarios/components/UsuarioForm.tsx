'use client'

import { Usuario } from "@/app/context/AuthContext"
import { UsuarioMock } from "@/app/mock/usuario";
import Link from "next/link";
import { useRouter } from "next/navigation";
import { useState } from "react"

interface UsuarioFormProps {
    usuarioExistente?: Usuario
}

export default function UsuarioForm({ usuarioExistente }: UsuarioFormProps) {

    const [usuario, setUsuario] = useState<any>(
        usuarioExistente || {
            id: 0,
            nome: '',
            email: '',
            senha: '',
            status: 'ATIVO'
        }
    );

    const router = useRouter();

    const handleChange = (campo: string, valor: string) => {
        setUsuario((prev: any) => ({
            ...prev,
            [campo]: valor
        }))
    }

    const handleSalvar = async () => {
        await UsuarioMock.salvar(usuario);
        alert("Usuário salvo com sucesso!");
        router.push("/usuarios");
    }

    return (
        <form action={handleSalvar} className="space-y-8">

            <div className="
                bg-zinc-900
                border border-zinc-800
                rounded-xl
                p-6 md:p-8
                space-y-8
            ">

                {/* HEADER */}
                <div>
                    <h2 className="text-lg font-semibold text-white">
                        {usuario.id ? 'Editar usuário' : 'Novo usuário'}
                    </h2>
                    <p className="text-sm text-zinc-400 mt-1">
                        Informações básicas do usuário
                    </p>
                </div>

                {/* GRID */}
                <div className="grid grid-cols-1 md:grid-cols-2 gap-6">

                    {/* NOME */}
                    <div className="flex flex-col gap-2">
                        <label className="text-sm text-zinc-400">
                            Nome completo
                        </label>

                        <input
                            type="text"
                            value={usuario.nome}
                            onChange={(e) => handleChange('nome', e.target.value)}
                            placeholder="João da Silva"
                            className="bg-zinc-950 border border-zinc-800 rounded-lg px-4 py-2.5 text-sm text-zinc-200 outline-none focus:border-blue-500 focus:ring-2 focus:ring-blue-500/30 transition"
                        />
                    </div>

                    {/* EMAIL */}
                    <div className="flex flex-col gap-2">
                        <label className="text-sm text-zinc-400">
                            E-mail
                        </label>

                        <input
                            type="email"
                            value={usuario.email}
                            onChange={(e) => handleChange('email', e.target.value)}
                            placeholder="usuario@email.com"
                            className="bg-zinc-950 border border-zinc-800 rounded-lg px-4 py-2.5 text-sm text-zinc-200 outline-none focus:border-blue-500 focus:ring-2 focus:ring-blue-500/30 transition"
                        />
                    </div>

                    {/* SENHA */}
                    <div className="flex flex-col gap-2">
                        <label className="text-sm text-zinc-400">
                            Senha
                        </label>

                        <input
                            type="password"
                            value={usuario.senha}
                            onChange={(e) => handleChange('senha', e.target.value)}
                            placeholder={usuario.id ? "Deixe em branco para não alterar" : "••••••••"}
                            className="bg-zinc-950 border border-zinc-800 rounded-lg px-4 py-2.5 text-sm text-zinc-200 outline-none focus:border-blue-500 focus:ring-2 focus:ring-blue-500/30 transition"
                        />
                    </div>

                    {/* STATUS */}
                    <div className="flex flex-col gap-2">
                        <label className="text-sm text-zinc-400">
                            Status
                        </label>

                        <select
                            value={usuario.status}
                            onChange={(e) => handleChange('status', e.target.value)}
                            className="
                                bg-zinc-950
                                border border-zinc-800
                                rounded-lg
                                px-4 py-2.5
                                text-sm
                                text-zinc-200
                                outline-none
                                focus:border-blue-500
                                focus:ring-2
                                focus:ring-blue-500/30
                                transition
                            "
                        >
                            <option value="ATIVO">Ativo</option>
                            <option value="INATIVO">Inativo</option>
                        </select>
                    </div>

                </div>

                {/* FOOTER */}
                <div className="border-t border-zinc-800 pt-6 flex items-center justify-between">

                    <div className="text-sm text-zinc-500">
                        {usuario.id ? `ID #${usuario.id}` : 'Novo cadastro'}
                    </div>

                    <div className="flex items-center gap-3">

                        <Link
                            href="/usuarios"
                            className="
                                px-4 py-2
                                text-sm
                                rounded-lg
                                text-zinc-400
                                hover:text-white
                                hover:bg-zinc-800
                                transition
                                cursor-pointer
                            "
                        >
                            Cancelar
                        </Link>

                        <button
                            type="submit"
                            className="
                                px-6 py-2
                                bg-blue-600
                                hover:bg-blue-500
                                active:bg-blue-700
                                text-white
                                text-sm
                                font-medium
                                rounded-lg
                                transition
                                shadow-lg shadow-blue-600/20
                                cursor-pointer
                            "
                        >
                            {usuario.id ? 'Salvar alterações' : 'Criar usuário'}
                        </button>

                    </div>

                </div>

            </div>

        </form>
    )
}