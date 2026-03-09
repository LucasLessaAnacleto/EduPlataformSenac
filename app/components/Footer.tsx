export default function Footer() {
    return (
        <footer className="
            border-t border-zinc-800
            bg-zinc-900/70
            backdrop-blur
        ">

            <div className="
                max-w-7xl mx-auto
                px-6 py-4
                flex items-center justify-between
                text-sm text-zinc-400
            ">

                <span>
                    © {new Date().getFullYear()} EduPlatform
                </span>

                <div className="flex items-center gap-6">

                    <span className="hidden sm:block">
                        Plataforma de cursos e treinamentos
                    </span>

                    <div className="flex gap-4">
                        <a className="hover:text-zinc-200 transition cursor-pointer">
                            Privacidade
                        </a>

                        <a className="hover:text-zinc-200 transition cursor-pointer">
                            Termos
                        </a>
                    </div>

                </div>

            </div>

        </footer>
    )
}