import React, {useEffect, useState} from 'react';
import {useLocalState} from "../../util/useLocalStorage";

const Dashboard = () => {

    const [jwt] = useLocalState("", "jwt")
    const [shops, setShops] = useState(null);

    useEffect(() => {
        fetch("api/shops", {
            headers: {
                "Content-Type": "application/json",
                Authorization: `Bearer ${jwt}`
            },
            method: "GET",
        })
            .then(response => {
                if (response.status === 200) return response.json();
            })
            .then(shopData => {
                setShops(shopData);
            })
    }, [])

    function createProduct() {
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
            .then(shop => {
                window.location.href = `/shops/${shop.id}`;
            })
    }

    return (
        <div style={{margin: '2em'}}>
            {shops ? (
                shops.map((shops) => (
                    <div key={shops.id}>Shops ID: {shops.id}</div>
                ))
            ) : (
                <></>
            )}
            <button onClick={() => createProduct()}>Submit New Shop</button>
        </div>
    );
};

export default Dashboard;