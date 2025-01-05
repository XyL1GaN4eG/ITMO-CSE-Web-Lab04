import React, {useContext, useState} from "react";
import {useNavigate} from "react-router-dom";
import {AuthContext} from "../context/AuthContext";

const LoginPage = () => {
    const {login} = useContext(AuthContext)
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const navigate = useNavigate();
    const handleSubmit = async (event) => {
        // блокируем дефолтное поведение браузера то есть предотвращаем перезагрузку страницы при отправке формы
        event.preventDefault()

        try {
            const data = await login(username, password)
            if (data) {
                console.log("успешная авторизация, получен токен: ", data)
                navigate("/main");
            } else {
                console.error("ответ не пришел")
            }
        } catch (e) {
            console.error("ошибка авторизации: ", e);
        }
    }

    const handleRegisterRedirect = () =>{
        navigate("/register");
    }

    return (
        <>
            <form onSubmit={handleSubmit}>
                <input
                    type="text"
                    placeholder="Логин"
                    value={username}
                    onChange={(event) => setUsername(event.target.value)}
                />
                <input
                    type="password"
                    //todo: добавить скрытие и показывание пароля
                    placeholder="Пароль"
                    value={password}
                    onChange={(event) => setPassword(event.target.value)}
                />
                <button type="submit">Войти</button>
            </form>
            <button onClick={handleRegisterRedirect}>"Хотите зарегестрироваться?"</button>
        </>
    )
}

export default LoginPage;