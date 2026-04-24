import { Curso } from "../types/curso"
import { api } from "./api"

export async function buscaCursos(): Promise<Curso[]> {
    const response = await api.get<Curso[]>("/cursos");

    if (response.status === 200) {
        throw new Error((response.data as any).error || "Erro ao buscar cursos");
    }

    return response.data
}