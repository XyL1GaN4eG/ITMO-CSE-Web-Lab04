import React, {useContext, useState} from "react";
import Graph from "../components/GraphicComp";
import PointsTable from "../components/PointsTable";
import FormInput from "../components/FormInput";
import {checkPoint} from "../api/points";
import {AuthContext} from "../context/AuthContext";

const MainPage = () => {
    const [points, setPoints] = useState([]);
    const {token} = useContext(AuthContext);
    // Функция для добавления новой точки
    const handleAddPoint = (point) => {
        const responsePoint = checkPoint(token, point)
        setPoints((prevPoints) => [...prevPoints, responsePoint]);
    };

    return (
        <>
            <FormInput onSubmit={handleAddPoint} />
            <Graph points={points} setPoints={setPoints} />
            <PointsTable points={points} />
        </>
    );
};

export default MainPage;
