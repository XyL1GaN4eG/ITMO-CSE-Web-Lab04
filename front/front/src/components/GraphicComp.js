import React, {useContext, useEffect, useState} from "react";
import {useNavigate} from "react-router-dom";
import {AuthContext} from "../context/AuthContext";
import {getAllPoints as apiGetAllPoints, checkPoint as apiCheckPoint, checkPoint} from "../api/points";
/*
todo:
   Набор полей ввода для задания координат точки и радиуса
    области в соответствии с вариантом задания:
    Button {'-5','-4','-3','-2','-1','0','1','2','3'} для координаты по оси X,
    Text (-3 ... 3) для координаты по оси Y,
    и Button {'-5','-4','-3','-2','-1','0','1','2','3'} для задания радиуса области.
    Если поле ввода допускает ввод заведомо некорректных данных
    (таких, например, как буквы в координатах точки или отрицательный радиус),
     то приложение должно осуществлять их валидацию.
 */

const center = 210;
const rightEdge = 410;
const leftEdge = 10;
const bottomEdge = 410;
const topEdge = 10;
const max = 420;
const l = (bottomEdge - topEdge) / 6;
//todo: возможно стоит добавить кастомные цвета
const mainColor = '#007BFF';
let pointsByR = {}



const Graph = () => {
    useEffect(() => {
        drawGraph();
        provideInteractiveGraphics()
    }, [])

    const {token} = useContext(AuthContext);


    function drawGraph() {
        const r = 1;
        const canvas = document.getElementById("graphic");
        const context = canvas.getContext('2d');
        context.clearRect(0, 0, max, max);
        context.strokeStyle = mainColor;
        context.fillStyle = mainColor;
        context.globalAlpha = 1;
        context.beginPath();
        drawArrow(context, leftEdge, center, rightEdge, center);
        drawArrow(context, center, bottomEdge, center, topEdge);
        context.globalAlpha = 0.25;

        //rectangle
        context.fillRect(center, center, -r * l, r * l / 2);

        // triangle
        context.beginPath();
        context.moveTo(center, center);
        context.lineTo(center + r / 2 * l, center);
        context.lineTo(center, center + r * l);
        context.closePath();
        context.fill();

        //quadrant
        context.beginPath();
        context.moveTo(center - r * l, center);
        context.arc(center, center, r * l, 0, -Math.PI / 2, true);
        context.lineTo(center, center);
        context.closePath();
        context.fill();

        context.globalAlpha = 1;
        context.font = '15px monospace'

        context.fillText('-R', center - r * l, center);
        context.fillText('R', center, center - r * l);
        context.fillText('-R/2', center - r * l / 2, center);
        context.fillText('R/2', center, center - r * l / 2);
        context.fillText('R/2', center + r * l / 2, center);
        context.fillText('R', center + r * l, center);
        context.fillText('-R/2', center, center + r * l / 2);
        context.fillText('-R', center, center + r * l);
        // todo: добавить поддержку дефолтного r чтобы при первой загрузке страницы на графике сразу были все точки
        try {
            saveDots(apiGetAllPoints());
            drawAllDots(r);
        } catch (error) {
            console.error("Ошибка при прорисовке всех точек: ", error)
        }
    }

    function drawAllDots() {
        let r = 1;
        if (!pointsByR[r]) {
            pointsByR[r] = [];
        }

        pointsByR[r].forEach(point => {
            drawDot(center + (point.x / r) * r * l, center - (point.y / r) * r * l,
                point.hit ? '#0F0' : '#F00');
        })
    }


    function drawArrow(context, fromX, fromY, tox, toy) {
        const headLen = 10;
        const dx = tox - fromX;
        const dy = toy - fromY;
        const angle = Math.atan2(dy, dx);
        context.moveTo(fromX, fromY);
        context.lineTo(tox, toy);
        context.lineTo(tox - headLen * Math.cos(angle - Math.PI / 6), toy - headLen * Math.sin(angle - Math.PI / 6));
        context.moveTo(tox, toy);
        context.lineTo(tox - headLen * Math.cos(angle + Math.PI / 6), toy - headLen * Math.sin(angle + Math.PI / 6));
        context.stroke();
    }

    function drawDot(x, y, color) {
        const canvas = document.getElementById("graphic");
        const context = canvas.getContext('2d');
        context.fillStyle = color;
        context.globalAlpha = 1;
        context.beginPath();
        context.moveTo(x, y);
        context.arc(x, y, 4, 0, 2 * Math.PI);
        context.closePath();
        context.fill();
    }

    function provideInteractiveGraphics() {
        const canvas = document.getElementById("graphic");
        canvas.addEventListener("click", function (e) {
            let x = (e.offsetX - center) / l;
            let y = -(e.offsetY - center) / l;

            //todo: добавить изменение r по button
            let r = 1;

            let point = {
                x: x,
                y: y,
                r: r,
                isIn: null
            }

            if (!pointsByR[r]) {
                pointsByR[r] = [];
            }
            pointsByR[r].push({x, y});

            const canvasX = e.offsetX;
            const canvasY = e.offsetY;
            point = checkPoint(token, point)
            drawDot(point.x, point.y, point.isIn ? "red" : "green");
        });
    }

    function saveDots(x, y, r, hit) {
        for (let i = 0; i <= x.length - 1; i++) {
            let point = {
                x: x[i],
                y: y[i],
                hit: hit[i]
            };
            if (!pointsByR[r[i]]) {
                pointsByR[r[i]] = [];
            }
            pointsByR[r[i]].push(point)
        }
    }

    return (
        <>
            //todo: возможно стоит добавить какую то красоту реактовскую
            <div className="graph">
                <canvas id="graphic" width="420px" height="420px" onClick={provideInteractiveGraphics}/>
            </div>
        </>
    )
}

export default Graph;