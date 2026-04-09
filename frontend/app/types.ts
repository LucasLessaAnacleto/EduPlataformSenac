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

export type Professor = {
    id: number
    nome: string
    email: string
    biografia: string
}
