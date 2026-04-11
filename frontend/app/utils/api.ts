import axios from "axios";
import Cookies from "js-cookie";

const api = axios.create({
  baseURL: "http://localhost:8080",
});

api.interceptors.request.use(config => {
  const token = Cookies.get('token');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

api.interceptors.response.use(
  (response) => response,
  (error) => {
    if((location.pathname !== "/login") && (error.status == 401 || error.code == "ERR_NETWORK")){
        Cookies.remove('token');
        document.body.innerHTML += `<div class="fixed inset-0 z-50 flex items-center justify-center bg-black/70 backdrop-blur-sm">
            <div class="w-full max-w-md bg-zinc-900 border border-zinc-800 rounded-xl shadow-2xl p-6 animate-fadeIn">
                <div class="flex items-center gap-3 mb-4">
                <div class="w-10 h-10 flex items-center justify-center rounded-lg bg-blue-600/20 text-blue-400 text-lg">⚠</div>
                <h2 class="text-lg font-semibold text-zinc-100">Sessão expirada</h2>
                </div>
                <p class="text-sm text-zinc-400 mb-6">
                Sua sessão expirou ou houve um problema de conexão. Faça login novamente para continuar usando a plataforma.
                </p>
                <div class="flex justify-end">
                <button class="bg-blue-600 hover:bg-blue-500 text-white text-sm font-medium px-4 py-2 rounded-lg transition shadow-lg shadow-blue-600/20" onclick="window.location.href='/login'">
                    Fazer login novamente
                </button>
                </div>
            </div>
        </div>`;
    }
    return error;
  }
);

export { api };