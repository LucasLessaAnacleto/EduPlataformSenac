export class Professor {
    constructor(
        public id: number | null,
        public nome: string,
        public cpf: string,
        public biografia: string
    ) { }
}

export interface ProfessorRequest {
    nome: string
    cpf: string
    biografia: string
}

export interface ProfessorResponse {
    id: number
    nome: string
    cpf: string
    biografia: string
}