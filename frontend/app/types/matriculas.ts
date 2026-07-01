export class Matricula {
    constructor(
        public id: number | null,
        public nomeAluno: string,
        public emailAluno: string,
        public data: string,
        public cursoId: number
    ) { }
}

export interface MatriculaRequest {
    nomeAluno: string
    emailAluno: string
    cursoId: number
}

export interface MatriculaResponse {
    id: number
    nomeAluno: string
    emailAluno: string
    data: string
    cursoId: number
}

export interface MatriculaFormProps {
    matriculaExistente?: Matricula
    cursoId: number
}