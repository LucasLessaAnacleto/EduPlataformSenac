'use client'
import { createContext, ReactNode, useContext, useEffect, useState } from "react";
import Cookies from "js-cookie";

interface Pesquisa {
    termo: string;
    data: string;
}

interface PesquisaContextType {
    pesquisas: Pesquisa[],
    adicionarPesquisa: (termo: string) => void,
    limparPesquisas: () => void
}

const PesquisaContext = createContext<PesquisaContextType | undefined>(undefined);

export function PesquisaProvider({ children }: { children: ReactNode }) {
    const [pesquisas, setPesquisas] = useState<Pesquisa[]>([]);

    useEffect(() => {
        const pesquisasRecover = Cookies.get('pesquisas');

        if (pesquisasRecover) {
            try {
                setPesquisas(JSON.parse(pesquisasRecover));
            } catch (e) {
                console.error(e);
            }
        }
    }, []);

    const adicionarPesquisa = (termo: string) => {
        const novaPesquisa: Pesquisa = {
            termo,
            data: new Date().toISOString()
        };

        const novasPesquisas = [novaPesquisa, ...pesquisas].slice(0, 5); // guarda só as 5 últimas

        setPesquisas(novasPesquisas);
        Cookies.set('pesquisas', JSON.stringify(novasPesquisas), { expires: 7 });
    };

    const limparPesquisas = () => {
        setPesquisas([]);
        Cookies.remove('pesquisas');
    };

    return (
        <PesquisaContext.Provider value={{ pesquisas, adicionarPesquisa, limparPesquisas }}>
            {children}
        </PesquisaContext.Provider>
    );
}

export const usePesquisa = () => {
    const context = useContext(PesquisaContext);
    if (!context) throw new Error('usePesquisa deve ser usado dentro do provider!');
    return context;
};