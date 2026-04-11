import { ReactNode } from "react"

type Props<T> = {
    type: "Editar" | "Criar",
    url: string,
    dados?: T,
    onSubmit: (item: T) => void,
    children: ReactNode
}
export default function Modal<T>({}: Props<T>){

    return <></>
}