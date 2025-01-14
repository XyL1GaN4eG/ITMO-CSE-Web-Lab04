import React, {useEffect, useState} from "react";
import Graph from "../components/GraphicComp";
import PointsTable from "../components/PointsTable";
import PointsForm from "../components/forms/PointsForm";
import {checkPoint, getAllPoints} from "../api/points";
import {useNavigate} from "react-router-dom";
import {usePoints} from "../context/PointsContext";

const MainPage = () => {
    // const [points, setPoints] = useState([]); // Начальное значение — пустой массив
    let {points, addPoint, setAllPoints} = usePoints();
    const [r, setR] = useState(1); // Начальное значение R
    const navigate = useNavigate();

    // Функция для добавления новой точки
    const handleAddPoint = async (point) => {
        try {
            let responsePoint = await checkPoint(point);
            console.log("Полученные данные: ", responsePoint);
            addPoint(point);
        } catch (error) {
            console.error("Ошибка при добавлении точки: ", error);
        }
    };

    useEffect(() => {
        const getAllPointsWithRefresh = async () => {
            try {
                const response = await getAllPoints();
                console.log("Пришедший JSON со всеми точками: ", response);
                setTimeout(setAllPoints(response), 10)
                console.log("Все точки: ", points)
            } catch (e) {
                console.error("Проблема при получении точек: ", e);
                // navigate("/");
            }
        };

        getAllPointsWithRefresh();
    }, [navigate]);

    return (
        <>
            <PointsForm onSubmit={handleAddPoint} setR={setR} currentR={r}/>
            <Graph points={points}
                   // setPoints={setPoints}
                   currentR={r} onClick={handleAddPoint}/>
            <PointsTable points={points}/>
        </>
    );
};

export default MainPage;
