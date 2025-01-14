import axios from 'axios';

//todo: вынести куда то в статик или синглтон
const BACKEND_URL = "http://213.171.27.51:8080/back-1.0-SNAPSHOT/api/auth";

export const login = async (username, password) => {
    try {
        //todo: переписать с использованием гета
        const response = await axios.post(`${BACKEND_URL}/login`, { username, password });
        console.log("Пришел следующий ответ при попытке залогинится: ", response)
        return response.data;
    } catch (error) {
        if (error.response) {
            // Сервер вернул ответ с ошибкой (например, 401, 403, 500)
            console.error("Ошибка авторизации:", error.response.data.message || error.response.statusText);
            throw new Error(error.response.data.message || "Ошибка авторизации. Попробуйте снова.");
        } else if (error.request) {
            // Сервер не ответил, или запрос не был выполнен
            console.error("Нет ответа от сервера:", error.request);
            throw new Error("Нет ответа от сервера. Проверьте соединение.");
        } else {
            // Ошибка произошла при настройке запроса
            console.error("Ошибка настройки запроса:", error.message);
            throw new Error("Ошибка настройки запроса.");
        }
    }
};

export const register = async (username, password) => {
    try {
         await axios.post(`${BACKEND_URL}/auth/register`, {username, password});
        return true; // Возвращаем заголовки, если всё прошло успешно
    } catch (error) {
        if (error.response) {
            console.error("Ошибка регистрации:", error.response.data.message || error.response.statusText);
            throw new Error(error.response.data.message || "Ошибка регистрации. Попробуйте снова.");
        } else if (error.request) {
            console.error("Нет ответа от сервера:", error.request);
            throw new Error("Нет ответа от сервера. Проверьте соединение.");
        } else {
            console.error("Ошибка настройки запроса:", error.message);
            throw new Error("Ошибка настройки запроса.");
        }
    }
};
