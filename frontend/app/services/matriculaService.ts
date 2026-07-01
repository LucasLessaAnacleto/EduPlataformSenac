import { Matricula, MatriculaRequest } from "../types/matriculas"
import api from "./api"

export async function buscarMatriculasPorCurso(cursoId: number): Promise<Matricula[]> {
    const dados = await api.get<Matricula[]>("/cursos/" + cursoId + "/matriculas")

    if (dados.status === 200) {
        return dados.data
    }

    return []
}

export async function salvarMatricula(matricula: MatriculaRequest): Promise<number> {
    const dadosResult = await api.post<number>("/matriculas", matricula)

    return dadosResult.data
}

export async function atualizarMatricula(id: number, matricula: MatriculaRequest): Promise<Matricula> {
    return (await api.put<Matricula>("/matriculas/" + id, matricula)).data
}

export async function deletarMatricula(id: number): Promise<void> {
    await api.delete("/matriculas/" + id)
}