import React, {useContext, useState} from "react";
import {useNavigate} from "react-router-dom";
import {AuthContext} from "../context/AuthContext";
import AuthForm from "../components/forms/AuthForm";

const LoginPage = () => {
    const {login} = useContext(AuthContext)
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const navigate = useNavigate();
    const handleSubmit = async (event) => {
        // блокируем дефолтное поведение браузера то есть предотвращаем перезагрузку страницы при отправке формы
        event.preventDefault()

        try {
            let data = await login(username, password)
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

    const handleRegisterRedirect = () => {
        navigate("/register");
    }

    return (
        <>
            <AuthForm
                username={username}
                setUsername={setUsername}
                password={password}
                setPassword={setPassword}
                handleSubmit={handleSubmit}
                buttonText="Войти"
                redirectText="Хотите зарегистрироваться?"
                onRedirect={handleRegisterRedirect}
            />

        </>
    )
}

export default LoginPage;