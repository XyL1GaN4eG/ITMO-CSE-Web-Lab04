import React, {useContext, useState} from "react";
import {useNavigate} from "react-router-dom";
import {AuthContext} from "../context/AuthContext";
import {register} from "../api/auth";
import AuthForm from "../components/forms/AuthForm";

const RegisterPage = () => {
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
                navigate("/");
            }
        } catch (error) {
            console.error("Ошибка при регистрации: ", error);
        }
    }

    const handleLoginRedirect = () => {
        navigate("/")
    }

    return (
        <AuthForm
            username={username}
            setUsername={setUsername}
            password={password}
            setPassword={setPassword}
            handleSubmit={handleSubmit}
            buttonText="Зарегистрироваться"
            redirectText="Хотите войти в аккаунт?"
            onRedirect={handleLoginRedirect}
        />
    )
}

export default RegisterPage;