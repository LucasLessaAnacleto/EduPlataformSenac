export type Curso = {
    id: number
    titulo: string
    descricao: string
    preco: number
}

export type Modulo = {
    id: number
    titulo: string
    ordem: number
}

export type Matricula = {
    id: number
    nomeAluno: string
    emailAluno: string
    data: string
}