'use client'
import { Usuario } from "@/app/context/AuthContext"
import Link from "next/link";
import { useState } from "react"


export default function UsuarioForm() {


    const [usuario, setUsuario] = useState<Usuario>(new Usuario(0, '', '', true));

    const handleChange = (campo: 'nome' | 'cpf', valor: string) => {
        setUsuario(prev =>
            new Usuario(
                prev.codigo,
                campo === 'nome' ? valor : prev.nome,
                campo === 'cpf' ? valor : prev.cpf,
                prev.ativo
            )
        )
    }

    const handleSalvar = (formData : FormData)=>{

    }


    return (
    <form action={handleSalvar}>
        <div>
            <div>
                <label>
                    Nome completo
                </label>
                <input
                    type="text"
                    required
                    value={usuario.nome}
                    onChange={(e) => handleChange('nome', e.target.value)}
                    placeholder="João da Silva Sauro"></input>

            </div>
            <div>
                <label>
                   CPF
                </label>
                <input
                    type="text"
                    required
                    maxLength={14}
                    value={usuario.cpf}
                    onChange={(e) => handleChange('nome', e.target.value)}
                    placeholder="000.000.000-00"></input>

            </div>
            <div>
                <Link href="/usuario">Cancelar</Link>
                <button type="submit" >Salvar</button>
            </div>
        </div>
    </form>)
}