import React, {useEffect} from "react";
import {PointsProvider, usePoints} from "../context/PointsContext";
import {checkPoint} from "../api/points";
import {Navigate} from "react-router-dom";

const center = 210;
const rightEdge = 410;
const leftEdge = 10;
const bottomEdge = 410;
const topEdge = 10;
const max = 420;
const l = (bottomEdge - topEdge) / 6;
const mainColor = "#007BFF";

let isInteractiveGraphSet = false;
let r;
const Graph = ({currentR}) => {
    const {getPoints, addPoint} = usePoints();

    useEffect(() => {
        console.log("Текущее значение R:", currentR);
        if (currentR === 0 || currentR === undefined || currentR == null) {
            currentR = 1;
        }
        r = currentR;
        drawGraph(r);
        if (!isInteractiveGraphSet) {
            setupInteractiveGraphics()
            isInteractiveGraphSet = !isInteractiveGraphSet
        }
    }, [currentR]);

    //todo: вынести все функции на прорисовку графика в отдельный файл/еще куда то отсюда
    function drawGraph() {
        // r = currentR; // Значение R синхронизировано с формой
        const canvas = document.getElementById("graphic");
        const context = canvas.getContext("2d");

        context.clearRect(0, 0, max, max);
        context.strokeStyle = mainColor;
        context.fillStyle = mainColor;
        context.globalAlpha = 1;

        // Рисуем оси
        context.beginPath();
        drawArrow(context, leftEdge, center, rightEdge, center);
        drawArrow(context, center, bottomEdge, center, topEdge);

        context.globalAlpha = 0.25;

        // Прямоугольник
        context.fillRect(center, center, -r * l, r * l / 2);

        // Треугольник
        context.beginPath();
        context.moveTo(center, center);
        context.lineTo(center + r / 2 * l, center);
        context.lineTo(center, center + r * l);
        context.closePath();
        context.fill();

        // Сектор
        context.beginPath();
        context.moveTo(center - r * l, center);
        context.arc(center, center, r * l, 0, -Math.PI / 2, true);
        context.lineTo(center, center);
        context.closePath();
        context.fill();

        context.globalAlpha = 1;
        context.font = "15px monospace";

        // Подписи
        context.fillText("-R", center - r * l, center);
        context.fillText("R", center, center - r * l);
        context.fillText("-R/2", center - r * l / 2, center);
        context.fillText("R/2", center, center - r * l / 2);
        context.fillText("R/2", center + r * l / 2, center);
        context.fillText("R", center + r * l, center);
        context.fillText("-R/2", center, center + r * l / 2);
        context.fillText("-R", center, center + r * l);

        drawAllDots(r);
    }

    function drawAllDots(r) {
        console.log("Попытка нарисовать все точки для указанного радиуса R: ", r,", ", getPoints()[r]);
        try {

            if (!getPoints()[r]) {
                getPoints()[r] = [];
            }

            let pointsForCurR = getPoints()[r];
            console.log("Все точки для указанного R=", r, ": ", getPoints());
            for (let i = 0; i <= pointsForCurR.length; i++) {
                let curPoint = pointsForCurR[i];
                console.log("Попытка нарисовать на графике точку: ", curPoint);
                drawDot(curPoint);
            }
        } catch (e) {
            console.error("AAAAAAA", e)
        }
    }

    function drawArrow(context, fromX, fromY, toX, toY) {
        const headLen = 10;
        const dx = toX - fromX;
        const dy = toY - fromY;
        const angle = Math.atan2(dy, dx);

        context.moveTo(fromX, fromY);
        context.lineTo(toX, toY);
        context.lineTo(toX - headLen * Math.cos(angle - Math.PI / 6), toY - headLen * Math.sin(angle - Math.PI / 6));
        context.moveTo(toX, toY);
        context.lineTo(toX - headLen * Math.cos(angle + Math.PI / 6), toY - headLen * Math.sin(angle + Math.PI / 6));
        context.stroke();
    }

    function drawDot(point) {
        // console.log("Попытка нарисовать точку со следующими характеристиками")
        let canvas = document.getElementById("graphic");
        let context = canvas.getContext("2d")

        let x = center + (point.x / r) * r * l;
        let y = center - (point.y / r) * r * l;
        context.fillStyle = point.in ? "#0F0" : "#F00";
        context.globalAlpha = 1;
        context.beginPath();
        context.arc(x, y, 4, 0, 2 * Math.PI);
        context.closePath();
        context.fill();
    }

    function setupInteractiveGraphics() {
        const canvas = document.getElementById("graphic");

        canvas.addEventListener("click", async (e) => {
            const x = (e.offsetX - center) / l;
            const y = -(e.offsetY - center) / l;
            // Используем R из формы
            console.log("Текущий R=", r)
            console.log("Произошел клик с x=", x, ", y=", y, "r=", r);

            let point;
            try {
                point = await checkPoint({x, y, r});
                console.log("" +
                    "Пришла точка после клика: ", point)
            } catch (e) {
                //todo: добавить реактовское уведомление об ошибке
                console.error("Пришла ошибка с сервера: ", e);
                //fixme: редирект не работает
                return <Navigate to="/"/>;

            }

            try {
                console.log("Коллекция точек до добавления новой точки: ", getPoints());
                console.log("Добавляю точку ", point, "в коллекцию для следующего R: ", r);
                addPoint(r, point);
                console.log("Коллекция точек после добавления новой: ", getPoints());

                drawDot(point);
            } catch (e) {
                console.error("Произошла ошибка при добавлении точки в коллекцию: ", e)
            }
        });
    }

    return (
        <div className="graph">
            <canvas id="graphic" width="420px" height="420px"/>
        </div>
    );
};

export default Graph;
