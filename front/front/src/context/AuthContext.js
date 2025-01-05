import {login as apiLogin, register as apiRegister} from "../api/auth";
import {createContext, useState} from "react";
import {useNavigate} from "react-router-dom";

export const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
    const [user, setUser] = useState(null);
    const [token, setToken] = useState(localStorage.getItem("token") || null);

    const login = async (username, password) => {
        try {
            //todo: возможно стоит
            const token = await apiLogin(username, password);
            // Сохраняем токен в состоянии
            setToken(token)
            // Сохраняем токен в локалсторадж чтобы при перезагрузке сессия не вылетела
            localStorage.setItem("token", token);

            return true;
        } catch (error) {
            //todo: добавить реактовское уведомление
            console.error("Ошибка авторизации:", error);
            // throw error;
        }
    };

    const register = async (username, password) => {
        try {
            return await apiRegister(username, password);
        } catch (error) {
            //todo: добавить реактовское уведомление
            console.error("Ошибка при регистрации: ", error)
            // throw error;
        }
    }

    const logout = async () => {
        setUser(null);
        setToken(null);
        localStorage.removeItem("token");
    };

    return (
        <AuthContext.Provider value={{ user, login, logout, register }}>
            {children}
        </AuthContext.Provider>
    );
};
