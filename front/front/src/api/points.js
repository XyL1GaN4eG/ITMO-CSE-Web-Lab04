import axios from "axios";

const BACKEND_URL = "http://localhost:8080"; // Исправьте URL

export const getAllPoints = async (token) => {
    try {
        const response = await axios.get(`${BACKEND_URL}/points/`, {
            headers: {
                Authorization: `Bearer ${token}` // Передаём токен в заголовок
            }
        });
        return response.data; // Возвращаем данные с сервера
    } catch (error) {
        handleResponseError(error);
    }
};

export const checkPoint = async (token, point) => {
    try {
        const response = await axios.post(`${BACKEND_URL}/points/check`, point, {
            headers: {
                Authorization: `Bearer ${token}`,
                "Content-Type": "application/json"
            },
        });
        return response.data; // Возвращаем данные из ответа
    } catch (error) {
        handleResponseError(error);
    }
};

export const clearPoints = async (token) => {
    try {
        await axios.delete(`${BACKEND_URL}/points/clear`, {
            headers: {
                Authorization: `Bearer ${token}`,
            },
        });
    } catch (error) {
        handleResponseError(error);
    }
}

//todo: возможно не стоит одинаково обрабатывать все ошибки от каждого запроса
const handleResponseError = (error) => {
    // Обработка ошибок
    if (error.response) {
        console.error("Ошибка от сервера:", error.response.data);
    } else if (error.request) {
        console.error("Нет ответа от сервера:", error.request);
    } else {
        console.error("Ошибка:", error.message);
    }
    //todo: добавить нормальную обработку ошибок
    // throw error; // Пробрасываем ошибку дальше, если нужно
}
