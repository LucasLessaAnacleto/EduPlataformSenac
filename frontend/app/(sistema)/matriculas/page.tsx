export default function MatriculasPage() {
    return (
        <div className="space-y-8">

            {/* Cabeçalho */}
            <div>

                <h1 className="text-2xl font-semibold text-zinc-100">
                    Matrículas
                </h1>

                <p className="text-sm text-zinc-400 mt-1">
                    Visualize todos os alunos inscritos em seus cursos
                </p>

            </div>


            {/* Barra de busca */}
            <div className="
                bg-zinc-900
                border border-zinc-800
                rounded-xl
                p-4
                flex flex-col gap-3 md:flex-row md:items-center md:justify-between
            ">

                <input
                    type="text"
                    placeholder="Buscar por aluno ou email..."
                    className="
                        w-full md:w-80
                        bg-zinc-950
                        border border-zinc-800
                        rounded-lg
                        px-4 py-2
                        text-sm
                        text-zinc-200
                        placeholder:text-zinc-500
                        outline-none
                        focus:border-blue-500
                        focus:ring-2
                        focus:ring-blue-500/30
                        transition
                    "
                />

                <select
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
                >
                    <option>Todos os cursos</option>
                    <option>React para Iniciantes</option>
                    <option>JavaScript Avançado</option>
                </select>

            </div>


            {/* Tabela */}
            <div className="
                bg-zinc-900
                border border-zinc-800
                rounded-xl
                overflow-hidden
            ">

                <div className="overflow-x-auto">

                    <table className="w-full text-sm">

                        <thead className="
                            bg-zinc-950
                            border-b border-zinc-800
                            text-zinc-400
                        ">
                            <tr>

                                <th className="text-left px-6 py-3">
                                    Aluno
                                </th>

                                <th className="text-left px-6 py-3">
                                    Email
                                </th>

                                <th className="text-left px-6 py-3">
                                    Curso
                                </th>

                                <th className="text-left px-6 py-3">
                                    Data
                                </th>

                            </tr>
                        </thead>


                        <tbody className="text-zinc-300">

                            <tr className="border-b border-zinc-800 hover:bg-zinc-950/60 transition">

                                <td className="px-6 py-4">
                                    João Silva
                                </td>

                                <td>
                                    joao@email.com
                                </td>

                                <td>
                                    React para Iniciantes
                                </td>

                                <td>
                                    12/03/2026
                                </td>

                            </tr>


                            <tr className="border-b border-zinc-800 hover:bg-zinc-950/60 transition">

                                <td className="px-6 py-4">
                                    Maria Souza
                                </td>

                                <td>
                                    maria@email.com
                                </td>

                                <td>
                                    JavaScript Avançado
                                </td>

                                <td>
                                    15/03/2026
                                </td>

                            </tr>


                            <tr className="hover:bg-zinc-950/60 transition">

                                <td className="px-6 py-4">
                                    Carlos Lima
                                </td>

                                <td>
                                    carlos@email.com
                                </td>

                                <td>
                                    React para Iniciantes
                                </td>

                                <td>
                                    18/03/2026
                                </td>

                            </tr>

                        </tbody>

                    </table>

                </div>

            </div>

        </div>
    )
}