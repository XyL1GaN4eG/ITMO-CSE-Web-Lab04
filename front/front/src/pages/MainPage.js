import React, {useContext, useState} from "react";
import Graph from "../components/GraphicComp";
import PointsTable from "../components/PointsTable";
import PointsForm from "../components/forms/PointsForm";
import {checkPoint} from "../api/points";
import {AuthContext} from "../context/AuthContext";

const MainPage = () => {
    const [points, setPoints] = useState([]);
    const {token} = useContext(AuthContext);
    // Функция для добавления новой точки
    const handleAddPoint = async (point) => {
        let responsePoint = await checkPoint(point);
        console.log("Полученные данные: ", responsePoint);
        setPoints((prevPoints) => [...prevPoints, responsePoint]);
    };

    return (
        <>
            <PointsForm onSubmit={handleAddPoint} />
            <Graph points={points} setPoints={setPoints} />
            <PointsTable points={points} />
        </>
    );
};

export default MainPage;
