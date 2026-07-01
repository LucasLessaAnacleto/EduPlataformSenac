export class Modulo {
    constructor(
        public id: number | null,
        public titulo: string,
        public ordem: number,
        public cursoId: number
    ) { }
}

export interface ModuloRequest {
    titulo: string
    ordem: number
    cursoId: number
}

export interface ModuloResponse {
    id: number
    titulo: string
    ordem: number
    cursoId: number
}

export interface ModuloFormProps {
    moduloExistente?: Modulo
    cursoId: number
}