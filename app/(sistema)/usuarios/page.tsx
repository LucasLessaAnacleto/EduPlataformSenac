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

    const handlerAlerarStatus = async (usuario: Usuario) => {
        try {
            setUsuarios(usuariosAtuais =>
                usuariosAtuais.map(u =>
                    u.codigo === usuario.codigo
                        ? new Usuario(u.codigo, u.nome, u.cpf, !u.ativo)
                        : u
                ));
        } catch (error) {
            alert("Erro ao alterar status do usuário!")
        }
    }

    return (
        <div>
            <div className="flex items-center justify-between">
                <h1 className="">
                    Gestão de Usuários
                </h1>
                <Link href="/usuarios/novo">
                    <span>+</span> 
                    Novo Usuário
                </Link>
            </div>

            <div className="overflow-x-auto">
                <table className="">
                    <thead>
                        <tr className="">
                            <th className="">Código</th>
                            <th className="">Nome</th>
                            <th className="">CPF</th>
                            <th className="">Status</th>
                            <th className="">Ações</th>
                        </tr>
                    </thead>
                    <tbody className="">
                        {usuarios.map((usuario) => (
                            <tr key={usuario.codigo} className="">
                                <td className="">
                                    #{usuario.codigo}
                                </td>
                                <td className="">
                                    {usuario.nome}
                                </td>
                                <td className="">
                                    {usuario.cpf}
                                </td>
                                <td className="">
                                    {usuario.ativo ? 'Ativo' : 'Inativo'}
                                </td>
                                <td className="">
                                    <Link href={`/usuarios/${usuario.codigo}/editar`} className="">Editar</Link>
                                    <button onClick={() => handlerAlerarStatus(usuario)}>
                                        {usuario.ativo ? 'Inativar' : 'Ativar'}
                                    </button>
                                </td>
                            </tr>
                        ))}

                        {usuarios.length === 0 && (
                            <tr>
                                <td>Nenhum usuário encontrado!</td>
                            </tr>
                        )}
                    </tbody>
                </table>
            </div>
        </div>
    )
}