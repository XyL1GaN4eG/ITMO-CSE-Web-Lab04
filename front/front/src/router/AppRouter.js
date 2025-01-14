import React from 'react';
import {Routes, Route} from "react-router-dom";
import LoginPage from '../pages/LoginPage'
import MainPage from '../pages/MainPage'
import PrivateRoute from "./PrivateRoute";
import RegisterPage from "../pages/RegisterPage";

const AppRouter = () => (
    //todo: add navbar
    <Routes>
        <Route path="/" element={<LoginPage />} />
        <Route path="/register" element={<RegisterPage/>}/>
        //роутим таким образом чтобы пользователь не могу попасть если неавторизирован
        <Route path="/main" element={<PrivateRoute element={<MainPage />} />} />
        {/*<Route path="/main" element={<MainPage/>} />*/}
    </Routes>
);

export default AppRouter;