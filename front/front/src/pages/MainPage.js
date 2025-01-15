import React, {useEffect, useState} from "react";
import Graph from "../components/GraphicComp";
import PointsTable from "../components/PointsTable";
import PointsForm from "../components/forms/PointsForm";
import {checkPoint, getAllPoints} from "../api/points";
import {useNavigate} from "react-router-dom";
import {usePoints} from "../context/PointsContext";

const MainPage = () => {
    const [points, setPoints] = useState(null); // Начальное значение — пустой массив
    const [r, setR] = useState(1); // Начальное значение R
    const navigate = useNavigate();

    // Функция для добавления новой точки

    useEffect(() => {
        const getAllPointsWithRefresh = async () => {
            try {
                const response = await getAllPoints();
                console.log("Пришедший JSON со всеми точками: ", JSON.stringify(response));
                setPoints(response);
                console.log("Все точки: ", points)
            } catch (e) {
                console.error("Проблема при получении точек: ", e);
                navigate("/");
            }
        };

        getAllPointsWithRefresh();
    }, [navigate]);

    useEffect(() => {
        console.log("Все точки после обновления: ", JSON.stringify(points));
    }, [points]);

    return (
        <>
            <PointsForm setR={setR} currentR={r}/>
            <Graph points={points} currentR={r}/>
            <PointsTable firstPoints={points}/>
        </>
    );
};

export default MainPage;
