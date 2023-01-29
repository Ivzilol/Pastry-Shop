import React, {useEffect, useState} from 'react';
import {useLocalState} from "../util/useLocalStorage";
import ajax from "../Services/FetchService";

const ShopsView = () => {
    const [jwt] = useLocalState("", "jwt")
    const shopId = window.location.href.split("/shops/")[1];
    const [shop, setShop] = useState({
        branch: "",
        town: "",
        address: ""

    });

    function updateShop(prop, value) {
        const newShop = {...shop}
        newShop[prop] = value;
        setShop(newShop);
    }

    function saveShop() {
        ajax(`/api/shops/${shopId}`, "PUT", jwt, shop)
            .then(shopData => {
                setShop(shopData);
            }
        );
    }

    useEffect(() => {
        ajax(`/api/shops/${shopId}`, "GET", jwt)
            .then(shopData => {
                setShop(shopData);
            });
    }, [])


    return (
        <div>
            <h1>Shop {shopId}</h1>
            {shop ? (
                <>
                    <h2>Name: {shop.name}</h2>
                    <h3>
                        Town: {" "}
                        <input
                            type="text"
                            id="town"
                            onChange={(e) => updateShop("town", e.target.value)}
                            value={shop.town}
                        />
                    </h3>
                    <h3>
                        Address: {" "}
                        <input
                            type="text"
                            id="address"
                            onChange={(e) =>
                                updateShop("address", e.target.value)}
                            value={shop.address}
                        />
                    </h3>
                    <button onClick={() => saveShop()}>Submit Town</button>
                </>) : (
                <></>
            )}
        </div>
    );
};

export default ShopsView;