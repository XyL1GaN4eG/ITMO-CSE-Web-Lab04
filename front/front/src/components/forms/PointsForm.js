import React, {useState} from 'react';
import {clearPoints} from "../../api/points";

const PointsForm = ({onSubmit, setR, currentR}) => {
    let r;
    if (currentR == null) currentR = 1;
    r = currentR;
    const [x, setX] = useState(null);
    const [y, setY] = useState("");
    // const [r, setR] = useState(null);
    const [errors, setErrors] = useState({});

    const xValues = ['-5', '-4', '-3', '-2', '-1', '0', '1', '2', '3'];
    const rValues = ['1', '2', '3', '4', '5'];

    let token = localStorage.getItem("token");

    const validate = () => {
        const newErrors = {};

        if (x === null) newErrors.x = "Выберите значение X.";
        if (y === "" || isNaN(y) || y < -3 || y > 3) newErrors.y = "Введите Y в диапазоне от -3 до 3.";
        if (r === null) newErrors.r = "Выберите значение R.";

        setErrors(newErrors);

        return Object.keys(newErrors).length === 0;
    };

    const handleSubmit = (e) => {
        e.preventDefault();

        if (validate()) {
            onSubmit({x, y: parseFloat(y), r});
            setErrors({});
        }
    };

    const handleClear = async () => {
        try {
            await clearPoints(token);
            console.log("Все точки удалены!")
        } catch (error) {
            console.error("Ошибка при попытке очистить точки: ", error)
        }
    };

    return (
        <form onSubmit={handleSubmit} className="form-input">
            <div>
                <label>Координата X:</label>
                <div>
                    {xValues.map((value) => (
                        <button
                            type="button"
                            key={value}
                            className={x === value ? "selected" : ""}
                            onClick={() => setX(value)}
                        >
                            {value}
                        </button>
                    ))}
                </div>
                {errors.x && <p className="error">{errors.x}</p>}
            </div>

            <div>
                <label>Координата Y:</label>
                <input
                    type="text"
                    value={y}
                    onChange={(e) => setY(e.target.value)}
                    placeholder="Введите Y (-3 ... 3)"
                />
                {errors.y && <p className="error">{errors.y}</p>}
            </div>

            <div>
                <label>Радиус R:</label>
                <div>
                    {rValues.map((value) => (
                        <button
                            type="button"
                            key={value}
                            className={r === value ? "selected" : ""}
                            onClick={() => setR(value)}
                        >
                            {value}
                        </button>
                    ))}
                </div>
                {errors.r && <p className="error">{errors.r}</p>}
            </div>
            <div>
                <button type="submit">Отправить</button>
                <button type="button" onClick={handleClear}>
                    Очистить результаты
                </button>
            </div>
        </form>
    );
};

export default PointsForm;
