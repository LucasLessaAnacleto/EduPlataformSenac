export class Curso {
    constructor(
        public id: number | null,
        public titulo: string,
        public descricao: string,
        public preco: number,
        public professorId?: number
    ) { }
}

export interface CursoRequest {
    titulo: string
    descricao: string
    preco: number
}

export interface CursoResponse {
    id: number
    titulo: string
    descricao: string
    preco: number
    professorId: number
}

export interface CursoFormProps {
    cursoExistente?: Curso
}