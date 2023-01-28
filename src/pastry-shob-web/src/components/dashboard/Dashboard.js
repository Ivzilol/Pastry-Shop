import React from 'react';
import {useLocalState} from "../../util/useLocalStorage";

const Dashboard = () => {

    const [jwt, setJwt] = useLocalState("", "jwt")
    return (
        <div>
            <h1>Pastry Shop</h1>
            <div>JWT Values is {jwt}</div>
        </div>
    );
};

export default Dashboard;