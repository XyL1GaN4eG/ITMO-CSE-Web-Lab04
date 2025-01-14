import { Navigate } from "react-router-dom";

const PrivateRoute = ({ element }) => {
    if (localStorage.getItem("token") == null) {
        return <Navigate to="/" />;
    }

    return element;
};

export default PrivateRoute;
