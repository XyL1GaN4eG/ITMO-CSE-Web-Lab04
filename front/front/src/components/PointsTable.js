import React from 'react';
import {usePoints} from "../context/PointsContext";

const PointsTable = ({points}) => {
    const {pointsByR, addPoint} = usePoints();
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

            {/*{*/}
            {/*    points.forEach((point, index) => (*/}
            {/*        <tr key={index}>*/}
            {/*            <td>{point.x}</td>*/}
            {/*            <td>{point.y}</td>*/}
            {/*            <td>{point.r}</td>*/}
            {/*            <td>{point.in ? 'Да' : 'Нет'}</td>*/}
            {/*            <td>{point.attemptTime}</td>*/}
            {/*            <td>{point.executionTime}</td>*/}
            {/*        </tr>*/}
            {/*    ))*/}
            {/*}*/}
            </tbody>
        </table>
    );
};

export default PointsTable;
