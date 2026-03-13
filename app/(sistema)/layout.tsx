'use client'

import { useRouter } from "next/navigation";
import Footer from "../components/Footer"
import Header from "../components/Header"
import Sidebar from "../components/Sidebar"
import { useAuth } from "../context/AuthContext";
import { useEffect } from "react";

export default function SistemaLayout({
  children,
}: {
  children: React.ReactNode
}) {
  const { usuario } = useAuth();
  const router = useRouter();

  // useEffect(() => {
  //   debugger;
  //   if (usuario == null) {

  //     router.push("/login")
  //   }
  // })

  if (usuario == null) return null;

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