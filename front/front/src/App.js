import {BrowserRouter} from "react-router-dom";
import {AuthProvider} from "./context/AuthContext";
import AppRouter from "./router/AppRouter";
import {PointsProvider} from "./context/PointsContext";

function App() {
    return (
        <AuthProvider>
            <BrowserRouter>
                <PointsProvider>
                    <AppRouter/>
                </PointsProvider>
            </BrowserRouter>
        </AuthProvider>
    );
}

export default App;
