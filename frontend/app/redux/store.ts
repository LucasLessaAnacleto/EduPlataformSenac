import { configureStore } from "@reduxjs/toolkit"
import authReducer from "./slices/authSlice"
import pesquisaReducer from "./slices/pesquisaSlice"

export const store = configureStore({
    reducer: {
        auth: authReducer,
        pesquisa: pesquisaReducer
    }
})

export type RootState = ReturnType<typeof store.getState>
export type AppDispatch = typeof store.dispatch;