import React, {useEffect, useState} from 'react';
import {useLocalState} from "../../util/useLocalStorage";
import {Link} from "react-router-dom";
import ajax from "../../Services/FetchService";

const Dashboard = () => {

    const [jwt] = useLocalState("", "jwt")
    const [shops, setShops] = useState(null);

    useEffect(() => {
        ajax("api/shops", "GET", jwt)
            .then(shopData => {
                if (shopData.town === null) shopData.town = "";
                if (shopData.address === null) shopData.address = "";
                setShops(shopData);
            })
    }, [])

    function createProduct() {
        ajax("api/shops", "POST", jwt)
            .then(shop => {
                window.location.href = `/shops/${shop.id}`;
            });
    }

    return (
        <div style={{margin: '2em'}}>
            {shops ? (
                shops.map((shops) => (
                    <div key={shops.id}>
                        <Link to={`/shops/${shops.id}`}>
                            Shops ID: {shops.id}
                        </Link>
                    </div>
                ))
            ) : (
                <></>
            )}
            <button onClick={() => createProduct()}>Submit New Shop</button>
        </div>
    );
};

export default Dashboard;