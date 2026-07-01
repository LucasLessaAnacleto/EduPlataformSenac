'use client'

import { useEffect } from "react"
import Footer from "../components/Footer"
import Header from "../components/Header"
import Sidebar from "../components/Sidebar"
import { useRouter } from "next/navigation"
import { useSelector } from "react-redux"
import { RootState } from "../redux/store"

export default function SistemaLayout({
  children,
}: {
  children: React.ReactNode
}) {
  const usuario = useSelector((state: RootState) => state.auth.usuario)
  const router = useRouter()

  useEffect(() => {
    if (!usuario) {
      router.push("/login")
    }
  }, [usuario, router])

  if (!usuario) return null

  return (
    <div className="flex min-h-screen bg-zinc-950 text-zinc-100">
      <Sidebar />

      <div className="flex flex-col flex-1 min-w-0">
        <Header />

        <main className="flex-1 p-6 md:p-10 bg-zinc-950">
          <div className="max-w-7xl mx-auto w-full">
            {children}
          </div>
        </main>

        <Footer />
      </div>
    </div>
  )
}