import React, {useContext} from "react";
import {Navigate} from "react-router-dom";
import {AuthContext} from "../context/AuthContext"

const PrivateRoute = ({element}) => {
    const {user} = useContext(AuthContext);

    //проверка на то, есть ли пользователь в контексте
    if (!user) {
        return <Navigate to="/"/>
    }
    return element;
}

export default PrivateRoute;