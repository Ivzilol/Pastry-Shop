import React, {useState} from 'react';
import {useUser} from "../UserProvider/UserProvider";
import {Navigate} from "react-router-dom";
import ajax from "../Services/FetchService";
import Loading from "../components/Loading/Loading";
import baseURL from "../components/BaseURL/BaseURL";


const PrivateRoute = (props) => {
    const user = useUser();
    const [isLoading, setIsLoading] = useState(true);
    const [isValid, setIsValid] = useState(null);
    const {children} = props;

    if (user) {
        ajax(`${baseURL}api/auth/validate?token=${user.jwt}`, "GET", user.jwt)
            .then(isValid => {
                setIsValid(isValid);
                setIsLoading(false);
            });
    } else {
        return <Navigate to="/login"/>
    }

    return isLoading ? (
        <Loading/>
    ) : isValid === true ? (
        children
    ) : (
        <Navigate to="/login"/>
    );
};

export default PrivateRoute;