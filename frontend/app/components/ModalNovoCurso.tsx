import { useRouter } from "next/navigation";
import { api } from "../utils/api";

type Props = {
    open: boolean
    onClose: () => void
}

export default function ModalNovoCurso({ open, onClose }: Props) {
    if (!open) return null;
    const router = useRouter();

    async function handleNovoCurso(formData: FormData) {
        const titulo = formData.get("titulo")?.toString();
        const descricao = formData.get("descricao")?.toString();
        const preco = formData.get("preco")?.toString();

        if(!titulo || !descricao || !preco) {
            alert("Preencha todos os campos!")
            return
        }

        const response = await api.post<Number>("/cursos", {
            titulo,
            descricao,
            preco: parseFloat(preco)
        });

        if(response.status !== 200) {
            alert("Erro ao criar curso!")
            return
        }
        alert("Curso criado com sucesso!");
        router.push("/cursos");
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
                w-full max-w-lg
                bg-zinc-900
                border border-zinc-800
                rounded-xl
                shadow-2xl
                p-6
            ">

                {/* Header */}
                <div className="flex items-center justify-between mb-6">

                    <h2 className="text-lg font-semibold text-zinc-100">
                        Criar novo curso
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
                            Título do curso
                        </label>

                        <input
                            type="text"
                            placeholder="Ex: React para Iniciantes"
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

                    <div className="flex flex-col gap-2">

                        <label className="text-sm text-zinc-400">
                            Descrição
                        </label>

                        <textarea
                            rows={4}
                            placeholder="Descreva o conteúdo do curso..."
                            className="
                                bg-zinc-950
                                border border-zinc-800
                                rounded-lg
                                px-4 py-2
                                text-sm
                                text-zinc-200
                                outline-none
                                resize-none
                                focus:border-blue-500
                                focus:ring-2
                                focus:ring-blue-500/30
                            "
                            name="descricao"
                        />

                    </div>


                    {/* Preço */}
                    <div className="flex flex-col gap-2">

                        <label className="text-sm text-zinc-400">
                            Preço
                        </label>

                        <input
                            type="number"
                            placeholder="Ex: 129"
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
                            name="preco"
                        />

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
                            Criar Curso
                        </button>

                    </div>

                </form>

            </div>

        </div>
    )
}