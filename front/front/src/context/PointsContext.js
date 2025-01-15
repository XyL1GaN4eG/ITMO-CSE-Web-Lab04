import React, { createContext, useContext, useState } from "react";

const PointsContext = createContext();

export const PointsProvider = ({ children }) => {
    const [points, setPoints] = useState({});

    const addPoint = (r, point) => {
        setPoints(prev => ({
            ...prev,
            [r]: [...(prev[r] || []), point] // Создаём новый массив для r
        }));
    };

    const setAllPoints = (allPoints) => {
        const groupedPoints = {};
        allPoints.forEach(point => {
            if (!groupedPoints[point.r]) groupedPoints[point.r] = [];
            groupedPoints[point.r].push(point);
        });

        setPoints(groupedPoints); // Заменяем весь объект
    };

    return (
        <PointsContext.Provider value={{ points, addPoint, setAllPoints }}>
            {children}
        </PointsContext.Provider>
    );
};

export const usePoints = () => useContext(PointsContext);
