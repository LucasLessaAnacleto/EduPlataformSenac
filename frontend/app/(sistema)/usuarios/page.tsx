'use client'

import { Usuario } from "@/app/context/AuthContext";
import { UsuarioMock } from "@/app/mock/usuario";
import Link from "next/link";
import { useEffect, useState } from "react";

export default function UsuariosPage() {

    const [usuarios, setUsuarios] = useState<Usuario[]>([]);

    useEffect(() => {
        carregarDados();
    }, []);

    const carregarDados = async () => {
        try {
            const dados = await UsuarioMock.listarTodos();
            setUsuarios(dados);
        } catch (error) {
            console.error(error)
        }
    }

    const handlerAlterarStatus = async (usuario: Usuario) => {
        try {
            setUsuarios(usuariosAtuais =>
                usuariosAtuais.map(u =>
                    u.id === usuario.id
                        ? new Usuario(u.id, u.nome, u.email, u.senha, u.status === 'ATIVO' ? 'INATIVO' : 'ATIVO')
                        : u
                ));
        } catch (error) {
            alert("Erro ao alterar status do usuário!")
        }
    }

    return (
        <div className="space-y-8">

            {/* HEADER */}
            <div className="flex items-center justify-between">
                <div>
                    <h1 className="text-2xl font-semibold text-white">Gestão de Usuários</h1>
                    <p className="text-sm text-zinc-400 mt-1">
                        Gerencie todos os usuários da plataforma
                    </p>
                </div>

                <Link
                    href="/usuarios/novo"
                    className="flex items-center gap-2 bg-blue-600 hover:bg-blue-500 text-white text-sm px-4 py-2 rounded-lg transition shadow-lg shadow-blue-600/20"
                >
                    <span className="text-lg">+</span>
                    Novo Usuário
                </Link>
            </div>

            {/* TABELA */}
            <div className="bg-zinc-900 border border-zinc-800 rounded-xl overflow-hidden">

                <div className="overflow-x-auto">

                    <table className="w-full text-sm">

                        <thead className="bg-zinc-900/80 border-b border-zinc-800">
                            <tr className="text-left text-zinc-400">
                                <th className="px-6 py-4 font-medium">Código</th>
                                <th className="px-6 py-4 font-medium">Nome</th>
                                <th className="px-6 py-4 font-medium">E-mail</th>
                                <th className="px-6 py-4 font-medium">Status</th>
                                <th className="px-6 py-4 font-medium text-right">Ações</th>
                            </tr>
                        </thead>

                        <tbody>
                            {usuarios.map((usuario) => (
                                <tr
                                    key={usuario.id}
                                    className="border-b border-zinc-800 hover:bg-zinc-800/50 transition"
                                >
                                    <td className="px-6 py-4 text-zinc-300">#{usuario.id}</td>
                                    <td className="px-6 py-4 text-white font-medium">{usuario.nome}</td>
                                    <td className="px-6 py-4 text-zinc-400">{usuario.email}</td>
                                    <td className="px-6 py-4">
                                        <span className={`
                                            px-3 py-1 text-xs rounded-full font-medium
                                            ${usuario.status === 'ATIVO'
                                                ? 'bg-green-500/10 text-green-400 border border-green-500/20'
                                                : 'bg-red-500/10 text-red-400 border border-red-500/20'
                                            }
                                        `}>
                                            {usuario.status}
                                        </span>
                                    </td>
                                    <td className="px-6 py-4">
                                        <div className="flex justify-end gap-2">

                                            {/* EDITAR */}
                                            <Link
                                                href={`/usuarios/${usuario.id}/editar`}
                                                className="px-3 py-1.5 text-xs rounded-md bg-zinc-800 text-zinc-300 hover:bg-zinc-700 hover:text-white transition cursor-pointer"
                                            >
                                                Editar
                                            </Link>

                                            {/* ATIVAR / INATIVAR */}
                                            <button
                                                onClick={() => handlerAlterarStatus(usuario)}
                                                className={`
                                                    px-3 py-1.5 text-xs rounded-md transition cursor-pointer
                                                    ${usuario.status === 'ATIVO'
                                                        ? 'bg-red-500/10 text-red-400 hover:bg-red-500/20'
                                                        : 'bg-green-500/10 text-green-400 hover:bg-green-500/20'
                                                    }
                                                `}
                                            >
                                                {usuario.status === 'ATIVO' ? 'Inativar' : 'Ativar'}
                                            </button>

                                        </div>
                                    </td>
                                </tr>
                            ))}
                        </tbody>
                    </table>

                    {usuarios.length === 0 && (
                        <div className="py-16 text-center text-zinc-500">
                            <p className="text-lg">Nenhum usuário encontrado</p>
                            <p className="text-sm mt-2">Comece criando um novo usuário</p>
                        </div>
                    )}

                </div>

            </div>

        </div>
    )
}