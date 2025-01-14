import React, { createContext, useState, useContext } from "react";

// Создаем контекст
export const PointsContext = createContext();

// Провайдер для точек
export const PointsProvider = ({ children }) => {
    const [points, setPoints] = useState([]); // Храним массив всех точек

    const addPoint = (r, newPoint) => {
        if (!points[r]) {
            points[r] = []
        }
        points[r].push(newPoint);
        // setPoints((prev) => [...prev, newPoint]); // Добавляем новую точку
    };

    const setAllPoints = (allPoints) => {
        setPoints(allPoints); // Устанавливаем массив точек
    };

    return (
        <PointsContext.Provider value={{ points, addPoint, setAllPoints }}>
            {children}
        </PointsContext.Provider>
    );
};

// Хук для доступа к контексту
export const usePoints = () => useContext(PointsContext);
