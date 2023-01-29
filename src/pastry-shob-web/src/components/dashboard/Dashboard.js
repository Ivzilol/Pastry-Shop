import React from 'react';
import {useLocalState} from "../../util/useLocalStorage";

const Dashboard = () => {

    const [jwt] = useLocalState("", "jwt")

    function createProduct () {
        fetch("api/shops", {
            headers: {
                "Content-Type": "application/json",
                Authorization: `Bearer ${jwt}`
            },
            method: "POST"
        })
            .then(response => {
            if (response.status === 200) return response.json()
        })
            .then(data => {
            console.log(data);
        })
    }

    return (
        <div style={{margin: '2em' }}>
           <button onClick={() => createProduct()} >Submit New Shop</button>
        </div>
    );
};

export default Dashboard;