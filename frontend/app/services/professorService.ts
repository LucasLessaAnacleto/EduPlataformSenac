import { Professor, ProfessorRequest } from "../types/professor"
import api from "./api"

export async function buscarListaProfessores(): Promise<Professor[]> {
    const dados = await api.get<Professor[]>("/professores")

    if (dados.status === 200) {
        return dados.data
    }

    return []
}

export async function buscarProfessorLogado(): Promise<Professor> {
    return (await api.get<Professor>("/professores/professorlogado")).data
}

export async function buscarProfessorPorId(id: number): Promise<Professor> {
    return (await api.get<Professor>("/professores/" + id)).data
}

export async function atualizarProfessorLogado(professor: ProfessorRequest): Promise<Professor> {
    return (await api.put<Professor>("/professores/professorlogado", professor)).data
}