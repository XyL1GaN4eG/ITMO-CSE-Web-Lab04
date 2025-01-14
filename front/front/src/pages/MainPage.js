import React, {useEffect, useState} from "react";
import Graph from "../components/GraphicComp";
import PointsTable from "../components/PointsTable";
import PointsForm from "../components/forms/PointsForm";
import {checkPoint, getAllPoints} from "../api/points";
import {useNavigate} from "react-router-dom";

const MainPage = () => {
    const [points, setPoints] = useState([]); // Начальное значение — пустой массив
    const [r, setR] = useState(1); // Начальное значение R
    const navigate = useNavigate();

    // Функция для добавления новой точки
    const handleAddPoint = async (point) => {
        try {
            let responsePoint = await checkPoint(point);
            console.log("Полученные данные: ", responsePoint);
            setPoints((prevPoints) => [...prevPoints, responsePoint]);
        } catch (error) {
            console.error("Ошибка при добавлении точки: ", error);
        }
    };

    useEffect(() => {
        const getAllPointsWithRefresh = async () => {
            try {
                const response = await getAllPoints();
                console.log("Пришедший JSON со всеми точками: ", response);
                setPoints(response);
                console.log("Все точки: ", points)
            } catch (e) {
                console.error("Проблема при получении точек: ", e);
                navigate("/");
            }
        };

        getAllPointsWithRefresh();
    }, [navigate]);

    return (
        <>
            <PointsForm onSubmit={handleAddPoint} setR={setR} currentR={r}/>
            <Graph points={points} setPoints={setPoints} currentR={r} onClick={handleAddPoint}/>
            <PointsTable points={points}/>
        </>
    );
};

export default MainPage;
