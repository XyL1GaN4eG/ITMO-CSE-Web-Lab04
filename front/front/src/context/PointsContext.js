import React, { createContext, useState, useContext } from "react";
import {all} from "axios";

// Создаем контекст
export const PointsContext = createContext();

// Провайдер для точек
export const PointsProvider = ({ children }) => {
    let points = [];
    const getPoints = () => {
        return points;
    }
    const addPoint = (r, newPoint) => {
        // if (!points[r]) {
        //     points[r] = []
        // }
        points[r].push(newPoint);
        console.log("lol")
    };

    const setAllPoints = (allPoints) => {
        points = allPoints; // Устанавливаем массив точек
    };

    return (
        <PointsContext.Provider value={{usePoints, getPoints, addPoint, setAllPoints }}>
            {children}
        </PointsContext.Provider>
    );
};

// Хук для доступа к контексту
export const usePoints = () => useContext(PointsContext);
