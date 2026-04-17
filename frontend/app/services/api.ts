import axios from "axios"
import { useAuth } from "../context/AuthContext";

const api = axios.create({
    baseURL: "http://localhost:8080"
});

api.interceptors.request.use(config => {
    const { token } = useAuth();
    if(token)
        config.headers.Authorization = `Bearer ${token}`;

    return config;
});

export { api };