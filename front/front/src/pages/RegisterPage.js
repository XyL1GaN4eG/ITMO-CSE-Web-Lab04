import React, {useContext, useState} from "react";
import {useNavigate} from "react-router-dom";
import {AuthContext} from "../context/AuthContext";
import {register} from "../api/auth";

const RegisterPage = () => {
    const {login} = useContext(AuthContext)
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const navigate = useNavigate();

    const handleSubmit = async (event) => {
        event.preventDefault();
        try {
            //todo: добавить обработку ситуаций когда юзернейм занят
            const data = await register(username, password)
            if (data) {
                console.log("успешная регистрация: ", data)
                navigate("/main");
            }
        } catch (e) {
            console.error("Ошибка при регистрации: ", e);
        }
    }

    return (
        <>
            <form onSubmit={handleSubmit}>
                <input
                    type="text"
                    placeholder={"Придумайте свое имя"}
                    value={username}
                    onChange={(event) => setUsername(event.target.value)}
                />
                <input
                    type="text"
                    placeholder={"Придумайте пароль"}
                    value={password}
                    //todo: добавить требование при вводе чтобы пароль был сложный
                    onChange={(event) => setPassword(event.target.value)}
                />
                <button type="submit">Зарегестрироваться</button>
            </form>
        </>
    )
}

export default RegisterPage;