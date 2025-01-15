import React, {useEffect} from 'react';
import {usePoints} from "../context/PointsContext";

const PointsTable = ({firstPoints}) => {

    try {
        return (
            <table>
                <thead>
                <tr>
                    <th>X</th>
                    <th>Y</th>
                    <th>R</th>
                    <th>В области</th>
                    <th>Время на сервере</th>
                    <th>Время выполнения</th>
                </tr>
                </thead>
                <tbody>
                {
                    Object.entries(firstPoints).map(([r, pointsArray]) =>
                        pointsArray.map((point, index) => (
                            <tr key={`${r}-${index}`}>
                                <td>{point.x}</td>
                                <td>{point.y}</td>
                                <td>{point.r}</td>
                                <td>{point.in ? "Да" : "Нет"}</td>
                                <td>{new Date(point.attemptTime).toLocaleString()}</td>
                                <td>{point.executionTime}</td>
                            </tr>
                        ))
                    )}
                </tbody>
            </table>
        );
    } catch (e) {
        return <h1>Попробуйте отправить запрос!</h1>
    }
};


export default PointsTable;
