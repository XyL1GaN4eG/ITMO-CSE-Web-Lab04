import React, { createContext, useState, useContext } from "react";

// Создаем контекст
export const PointsContext = createContext();

// Провайдер для точек
export const PointsProvider = ({ children }) => {
    const [pointsByR, setPointsByR] = useState([]); // Храним массив R и точек

    const addPoint = (r, point) => {
        setPointsByR((prev) => {
            const existingR = prev.find((item) => item.r === r);
            if (existingR) {
                // Если R уже существует, добавляем точку в массив
                return prev.map((item) =>
                    item.r === r
                        ? { ...item, points: [...item.points, point] }
                        : item
                );
            }
            // Если R еще нет, добавляем новый массив
            return [...prev, { r, points: [point] }];
        });
    };

    return (
        <PointsContext.Provider value={{ pointsByR, addPoint }}>
            {children}
        </PointsContext.Provider>
    );
};

// Хук для доступа к контексту
export const usePoints = () => useContext(PointsContext);
