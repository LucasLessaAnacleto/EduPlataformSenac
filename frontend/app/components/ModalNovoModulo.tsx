import { useRouter } from "next/navigation";
import { api } from "../utils/api";
import { Modulo } from "../types";

type Props = {
    open: boolean
    onClose: () => void,
    cursoId: number,
    onCreate: (modulo: Modulo) => void
}

export default function ModalNovoModulo({ open, onClose, cursoId, onCreate }: Props) {
    if (!open) return null;

    const router = useRouter();

    async function handleNovoCurso(formData: FormData) {
        const titulo = formData.get("titulo")?.toString();
        const ordem = Number(formData.get("ordem")?.toString() || 0);

        if(!titulo || !ordem) {
            alert("Preencha todos os campos!")
            return
        }

        const response = await api.post<Number>("/modulos", {
            titulo,
            ordem,
            cursoId
        });

        if(response.status !== 200) {
            alert("Erro ao criar modulo!")
            return
        }
        alert("Modulo criado com sucesso!");
        onCreate({
            id: Number(response.data),
            titulo,
            ordem
        })
        onClose()
    }


    return (
        <div className="
            fixed inset-0
            z-50
            flex items-center justify-center
            bg-black/60
            backdrop-blur-sm
        ">

            <div className="
                w-full max-w-md
                bg-zinc-900
                border border-zinc-800
                rounded-xl
                shadow-2xl
                p-6
            ">

                {/* Header */}
                <div className="flex items-center justify-between mb-6">

                    <h2 className="text-lg font-semibold text-zinc-100">
                        Novo módulo
                    </h2>

                    <button
                        onClick={onClose}
                        className="
                            text-zinc-400
                            hover:text-zinc-200
                            text-sm
                        "
                    >
                        ✕
                    </button>

                </div>


                {/* Form */}
                <form className="space-y-5" action={handleNovoCurso}>

                    {/* Título */}
                    <div className="flex flex-col gap-2">

                        <label className="text-sm text-zinc-400">
                            Título do módulo
                        </label>

                        <input
                            type="text"
                            placeholder="Ex: Introdução ao React"
                            className="
                                bg-zinc-950
                                border border-zinc-800
                                rounded-lg
                                px-4 py-2
                                text-sm
                                text-zinc-200
                                outline-none
                                focus:border-blue-500
                                focus:ring-2
                                focus:ring-blue-500/30
                            "
                            name="titulo"
                        />

                    </div>


                    {/* Ordem */}
                    <div className="flex flex-col gap-2">

                        <label className="text-sm text-zinc-400">
                            Ordem do módulo
                        </label>

                        <input
                            type="number"
                            placeholder="Ex: 1"
                            className="
                                bg-zinc-950
                                border border-zinc-800
                                rounded-lg
                                px-4 py-2
                                text-sm
                                text-zinc-200
                                outline-none
                                focus:border-blue-500
                                focus:ring-2
                                focus:ring-blue-500/30
                            "
                            name="ordem"
                        />

                        <span className="text-xs text-zinc-500">
                            Define a posição do módulo dentro do curso.
                        </span>

                    </div>


                    {/* Botões */}
                    <div className="flex justify-end gap-3 pt-4">

                        <button
                            type="button"
                            onClick={onClose}
                            className="
                                px-4 py-2
                                text-sm
                                text-zinc-400
                                hover:text-zinc-200
                                transition
                            "
                        >
                            Cancelar
                        </button>

                        <button
                            type="submit"
                            className="
                                bg-blue-600
                                hover:bg-blue-500
                                text-white
                                text-sm
                                font-medium
                                px-4 py-2
                                rounded-lg
                                transition
                                shadow-lg shadow-blue-600/20
                            "
                        >
                            Criar módulo
                        </button>

                    </div>

                </form>

            </div>

        </div>
    )
}