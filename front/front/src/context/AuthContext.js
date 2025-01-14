import {login as apiLogin, register as apiRegister} from "../api/auth";
import {createContext} from "react";

export const AuthContext = createContext();

export const AuthProvider = ({ children }) => {

    const login = async (username, password) => {
        try {
            //todo: возможно стоит
            const incomeToken = await apiLogin(username, password);
            localStorage.setItem("token", incomeToken);
            console.log(localStorage.getItem("token"))
            return incomeToken;
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
        localStorage.removeItem("token");
    };

    return (
        <AuthContext.Provider value={{login, logout, register }}>
            {children}
        </AuthContext.Provider>
    );
};
