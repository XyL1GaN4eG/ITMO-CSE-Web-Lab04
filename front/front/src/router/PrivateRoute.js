import React, { useContext } from "react";
import { Navigate } from "react-router-dom";
import { AuthContext } from "../context/AuthContext";

const PrivateRoute = ({ element }) => {
    if (localStorage.getItem("token") == null) {
        return <Navigate to="/" />;
    }

    return element;
};

export default PrivateRoute;
