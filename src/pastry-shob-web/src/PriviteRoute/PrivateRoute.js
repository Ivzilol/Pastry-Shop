import React from 'react';
import {useLocalState} from "../util/useLocalStorage";
import {Navigate} from "react-router-dom";

const PrivateRoute = ({children}) => {

    const [jwt, setJwt] = useLocalState("", "jwt")

    return jwt ? children : <Navigate to="/login" />
};

export default PrivateRoute;