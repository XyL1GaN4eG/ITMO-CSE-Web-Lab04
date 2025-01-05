import React from 'react';

// Компонент для отображения данных точек в таблице
const PointsTable = ({ points }) => {
    return (
        <table>
            <thead>
            <tr>
                <th>X</th>
                <th>Y</th>
                <th>R</th>
                <th>В области</th>
                <th>Время от сервера</th>
                <th>Время выполнения</th>
            </tr>
            </thead>
            <tbody>
            {points.map((point, index) => (
                <tr key={index}>
                    <td>{point.x}</td>
                    <td>{point.y}</td>
                    <td>{point.r}</td>
                    <td>{point.isIn ? 'Да' : 'Нет'}</td>
                    <td>{point.serverTime}</td>
                    <td>{point.executionTime}</td>
                </tr>
            ))}
            </tbody>
        </table>
    );
};

export default PointsTable;
