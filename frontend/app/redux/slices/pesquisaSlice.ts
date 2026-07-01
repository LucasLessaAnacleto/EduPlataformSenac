import { createSlice, PayloadAction } from "@reduxjs/toolkit"

type Pesquisa = {
    termo: string
    data: string
}

type PesquisaState = {
    pesquisas: Pesquisa[]
}

const initialState: PesquisaState = {
    pesquisas: []
}

const pesquisaSlice = createSlice({
    name: "pesquisa",
    initialState,
    reducers: {
        adicionarPesquisa: (state, action: PayloadAction<{ termo: string }>) => {
            const termo = action.payload.termo.trim()

            if (!termo) {
                return
            }

            const jaExiste = state.pesquisas.some(
                pesquisa => pesquisa.termo.toLowerCase() === termo.toLowerCase()
            )

            if (!jaExiste) {
                state.pesquisas.unshift({
                    termo,
                    data: new Date().toISOString()
                })
            }
        },

        limparPesquisas: (state) => {
            state.pesquisas = []
        }
    }
})

export const { adicionarPesquisa, limparPesquisas } = pesquisaSlice.actions
export default pesquisaSlice.reducer