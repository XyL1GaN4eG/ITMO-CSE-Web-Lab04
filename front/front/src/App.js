import ReactDOM from "react-dom/client";
import {BrowserRouter, Routes, Route} from "react-router-dom";
import {AuthProvider} from "./context/AuthContext";
import AppRouter from "./router/AppRouter";

function App() {
    return (
        <AuthProvider>
            <BrowserRouter>
                <AppRouter />
            </BrowserRouter>
        </AuthProvider>
    );
}

export default App;
