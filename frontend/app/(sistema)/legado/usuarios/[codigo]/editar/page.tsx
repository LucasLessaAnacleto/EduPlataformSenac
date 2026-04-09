'use client'

import { Usuario } from "@/app/context/legado/AuthContext";
import Link from "next/link";
import { useParams, useRouter } from "next/navigation";
import { useEffect, useState } from "react";
import UsuarioForm from "../../components/UsuarioForm";
import Loading from "@/app/components/Loading";
import { api } from "@/app/utils/api";

export default function EditarUsuario() {

    const params = useParams();
    const router = useRouter();
    const id = Number(params.codigo);

    const [usuario, setUsuario] = useState<Usuario | null>(null);
    const [carregando, setCarregando] = useState(true);

    useEffect(() => {
        buscarDados();
    }, []);

    const buscarDados = async () => {
        try {
            const response = await api.get<Usuario>(`/usuarios/${id}`);
            if(response.status !== 200 || !response.data){
                throw new Error("Usuário não encontrado!");
            }
            console.log(response.data);
            setUsuario(response.data);
        } catch (error) {
            console.error(error);
            alert("Erro ao carregar dados do usuário!");
            router.push("/usuarios");
        } finally {
            setCarregando(false);
        }
    }

    if (carregando) {
        return (
            <Loading message="Carregando dados do usuário..." fullScreen />
        );
    }

    if (!usuario) {
        return (
            <div className="p-8 text-red-400">
                Usuário não encontrado!
            </div>
        );
    }

    return (
        <div className="space-y-8 p-6">

            {/* Cabeçalho */}
            <div className="flex items-center justify-between">
                <div className="space-y-1">
                    <Link
                        href="/usuarios"
                        className="text-sm text-zinc-400 hover:text-white transition-colors"
                    >
                        &larr; Voltar
                    </Link>
                    <h1 className="text-2xl font-semibold text-white">
                        {`Editar Usuário #${usuario.id}`}
                    </h1>
                    <p className="text-sm text-zinc-400">
                        Altere os dados do usuário abaixo
                    </p>
                </div>
            </div>

            {/* Formulário */}
            <div className="bg-zinc-900 border border-zinc-800 rounded-xl p-6">
                <UsuarioForm usuarioExistente={usuario} />
            </div>
        </div>
    );
}