import { Usuario, UsuarioProfessorRequest, UsuarioRequest } from "../types/usuarios"
import api from "./api"

export async function buscarUsuarioLogado(): Promise<Usuario> {
    return (await api.get<Usuario>("/usuarios/usuariologado")).data
}

export async function buscarListaUsuarios(): Promise<Usuario[]> {
    const dados = await api.get<Usuario[]>("/usuarios")

    if (dados.status === 200) {
        return dados.data
    }

    return []
}

export async function salvarUsuarioProfessor(usuario: UsuarioProfessorRequest): Promise<number> {
    const dadosResult = await api.post<number>("/usuarios", usuario)

    return dadosResult.data
}

export async function buscarUsuarioPorId(id: number): Promise<Usuario> {
    return (await api.get<Usuario>("/usuarios/" + id)).data
}

export async function atualizarUsuario(id: number, usuario: UsuarioRequest): Promise<Usuario> {
    return (await api.put<Usuario>("/usuarios/" + id, usuario)).data
}

export async function alterarStatusUsuario(usuario: Usuario): Promise<void> {
    let novoStatus = {}

    if (usuario.status === "ATIVO") {
        novoStatus = { status: "INATIVO" }
    } else {
        novoStatus = { status: "ATIVO" }
    }

    const dadosResult = await api.put<void>(
        "/usuarios/" + usuario.id + "/AlterarStatus",
        novoStatus
    )

    if (dadosResult.status !== 200) {
        alert("Erro ao atualizar status!")
    }
}