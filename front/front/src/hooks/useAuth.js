import {useAuth} from '../context/AuthContext';

const useAuth = () => {
    const {user, setUser} = useAuth();

    const login = (userData) => {
        setUser(userData);
        localStorage.setItem('user', JSON.stringify(userData));
    }

    const logout = () => {
        setUser(null);
        localStorage.removeItem('user')
    }

    return {user, login, logout}
}

export default useAuth;