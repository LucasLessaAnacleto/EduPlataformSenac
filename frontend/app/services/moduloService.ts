import { Modulo, ModuloRequest } from "../types/modulos"
import api from "./api"

export async function buscarModulosPorCurso(cursoId: number): Promise<Modulo[]> {
    const dados = await api.get<Modulo[]>("/cursos/" + cursoId + "/modulos")

    if (dados.status === 200) {
        return dados.data
    }

    return []
}

export async function salvarModulo(modulo: ModuloRequest): Promise<number> {
    const dadosResult = await api.post<number>("/modulos", modulo)

    return dadosResult.data
}

export async function atualizarModulo(id: number, modulo: ModuloRequest): Promise<Modulo> {
    return (await api.put<Modulo>("/modulos/" + id, modulo)).data
}

export async function deletarModulo(id: number): Promise<void> {
    await api.delete("/modulos/" + id)
}