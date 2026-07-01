import { Curso, CursoRequest } from "../types/cursos"
import api from "./api"

export async function buscaCursos(): Promise<Curso[]> {
    const dados = await api.get<Curso[]>("/cursos")

    if (dados.status === 200) {
        return dados.data
    }

    return []
}

export async function buscarCursoPorId(id: number): Promise<Curso> {
    return (await api.get<Curso>("/cursos/" + id)).data
}

export async function salvarCurso(curso: CursoRequest): Promise<Curso> {
    return (await api.post<Curso>("/cursos", curso)).data
}

export async function atualizarCurso(id: number, curso: CursoRequest): Promise<Curso> {
    return (await api.put<Curso>("/cursos/" + id, curso)).data
}

export async function deletarCurso(id: number): Promise<void> {
    await api.delete("/cursos/" + id)
}