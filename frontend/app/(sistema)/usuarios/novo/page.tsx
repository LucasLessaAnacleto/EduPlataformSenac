import Link from "next/link";
import UsuarioForm from "../components/UsuarioForm";

export default function NovoUsuarioPage() {
    return (
        <div className="space-y-8">

            {/* HEADER */}
            <div className="flex items-center justify-between">

                <div className="space-y-2">

                    {/* VOLTAR */}
                    <Link
                        href="/usuarios"
                        className="
                            inline-flex items-center gap-2
                            text-sm
                            text-zinc-400
                            hover:text-white
                            transition
                            cursor-pointer
                        "
                    >
                        ← Voltar
                    </Link>

                    {/* TÍTULO */}
                    <div>
                        <h1 className="text-2xl font-semibold text-white">
                            Novo Usuário
                        </h1>
                        <p className="text-sm text-zinc-400 mt-1">
                            Cadastre um novo usuário na plataforma
                        </p>
                    </div>

                </div>

            </div>

            {/* FORM */}
            <UsuarioForm />

        </div>
    );
}