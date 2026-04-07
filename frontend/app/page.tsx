'use client'
import { useRouter } from "next/navigation";

export default function LandingPage() {
  const router = useRouter();

  return (
    <div className="bg-zinc-950 text-zinc-200">

      {/* NAVBAR */}

      <header className="border-b border-zinc-800">

        <div className="max-w-7xl mx-auto px-6 py-4 flex items-center justify-between">

          {/* LOGO */}

          <div className="flex items-center gap-3">

            <div className="
              w-9 h-9
              bg-blue-600
              rounded-lg
              flex
              items-center
              justify-center
              text-white
              font-semibold
            ">
              EP
            </div>

            <span className="font-semibold text-white text-lg">
              EduPlatform
            </span>

          </div>


          <div className="flex items-center gap-4">

            <button className="text-sm text-zinc-400 cursor-pointer hover:text-white" onClick={() => router.push("/login")}>
              Entrar
            </button>

            <button className="
              bg-blue-600
              hover:bg-blue-500
              text-white
              text-sm
              px-4
              py-2
              rounded-lg
            ">
              Criar conta
            </button>

          </div>

        </div>

      </header>



      {/* HERO */}

      <section className="py-24">

        <div className="max-w-7xl mx-auto px-6 grid lg:grid-cols-2 gap-16 items-center">

          {/* TEXTO */}

          <div className="space-y-6">

            <h1 className="text-4xl md:text-5xl font-bold text-white leading-tight">
              Crie e gerencie seus
              <span className="text-blue-500"> cursos online</span>
              <br />
              de forma simples
            </h1>

            <p className="text-zinc-400 text-lg">
              A EduPlatform permite que professores criem cursos,
              organizem módulos e acompanhem alunos em uma
              interface simples e moderna.
            </p>

            <div className="flex gap-4">

              <button className="
                bg-blue-600
                hover:bg-blue-500
                text-white
                px-6
                py-3
                rounded-lg
                font-medium
              ">
                Começar agora
              </button>

              <button className="
                border border-zinc-700
                hover:border-zinc-500
                px-6
                py-3
                rounded-lg
                text-zinc-300
              ">
                Ver demonstração
              </button>

            </div>

          </div>


          {/* MOCKUP */}

          <div className="relative">

            <img
              src="https://images.unsplash.com/photo-1551288049-bebda4e38f71"
              alt="Dashboard"
              className="
                rounded-xl
                border border-zinc-800
                shadow-2xl
              "
            />

          </div>

        </div>

      </section>



      {/* FEATURES */}

      <section className="py-20 border-t border-zinc-800">

        <div className="max-w-6xl mx-auto px-6">

          <div className="text-center mb-16">

            <h2 className="text-3xl font-semibold text-white">
              Tudo que você precisa para gerenciar cursos
            </h2>

            <p className="text-zinc-400 mt-2">
              Ferramentas simples para professores
            </p>

          </div>


          <div className="grid md:grid-cols-3 gap-8">

            {/* FEATURE */}

            <div className="
              bg-zinc-900
              border border-zinc-800
              rounded-xl
              overflow-hidden
              hover:border-blue-500/40
              transition
            ">

              <img
                src="https://images.unsplash.com/photo-1516321318423-f06f85e504b3"
                className="h-40 w-full object-cover"
              />

              <div className="p-6">

                <h3 className="text-white font-semibold mb-2">
                  Gerencie cursos
                </h3>

                <p className="text-sm text-zinc-400">
                  Crie cursos com descrição, preço e organize
                  todo seu conteúdo facilmente.
                </p>

              </div>

            </div>



            <div className="
              bg-zinc-900
              border border-zinc-800
              rounded-xl
              overflow-hidden
              hover:border-blue-500/40
              transition
            ">

              <img
                src="https://images.unsplash.com/photo-1501504905252-473c47e087f8"
                className="h-40 w-full object-cover"
              />

              <div className="p-6">

                <h3 className="text-white font-semibold mb-2">
                  Organize módulos
                </h3>

                <p className="text-sm text-zinc-400">
                  Estruture seu conteúdo em módulos organizados
                  e fáceis de acompanhar.
                </p>

              </div>

            </div>



            <div className="
              bg-zinc-900
              border border-zinc-800
              rounded-xl
              overflow-hidden
              hover:border-blue-500/40
              transition
            ">

              <img
                src="https://images.unsplash.com/photo-1522202176988-66273c2fd55f"
                className="h-40 w-full object-cover"
              />

              <div className="p-6">

                <h3 className="text-white font-semibold mb-2">
                  Controle alunos
                </h3>

                <p className="text-sm text-zinc-400">
                  Visualize todos os alunos inscritos em seus
                  cursos e acompanhe matrículas.
                </p>

              </div>

            </div>

          </div>

        </div>

      </section>



      {/* SEÇÃO VISUAL */}

      <section className="py-24 border-t border-zinc-800">

        <div className="max-w-6xl mx-auto px-6 grid md:grid-cols-2 gap-16 items-center">

          <img
            src="https://images.unsplash.com/photo-1553877522-43269d4ea984"
            className="
              rounded-xl
              border border-zinc-800
            "
          />

          <div className="space-y-6">

            <h2 className="text-3xl font-semibold text-white">
              Organize seus cursos em minutos
            </h2>

            <p className="text-zinc-400">
              Cadastre cursos, crie módulos e acompanhe
              matrículas em uma interface simples,
              pensada para produtividade.
            </p>

            <button className="
              bg-blue-600
              hover:bg-blue-500
              text-white
              px-6
              py-3
              rounded-lg
              font-medium
            ">
              Criar minha conta
            </button>

          </div>

        </div>

      </section>



      {/* CTA */}

      <section className="py-24 border-t border-zinc-800 text-center">

        <div className="max-w-3xl mx-auto px-6 space-y-6">

          <h2 className="text-3xl font-semibold text-white">
            Comece hoje mesmo
          </h2>

          <p className="text-zinc-400">
            Crie sua conta gratuita e comece a organizar
            seus cursos e alunos em minutos.
          </p>

          <button className="
            bg-blue-600
            hover:bg-blue-500
            text-white
            px-8
            py-3
            rounded-lg
            font-medium
          ">
            Criar conta gratuita
          </button>

        </div>

      </section>



      {/* FOOTER */}

      <footer className="
        border-t
        border-zinc-800
        py-6
        text-center
        text-sm
        text-zinc-500
      ">
        © {new Date().getFullYear()} EduPlatform
      </footer>

    </div>
  );
}