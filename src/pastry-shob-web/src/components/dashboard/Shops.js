import React, {useEffect, useState} from 'react';
import ajax from "../../Services/FetchService";
import {Button, Card} from "react-bootstrap";
import {useNavigate} from "react-router-dom";
import {useUser} from "../../UserProvider/UserProvider";
import jwt_decode from "jwt-decode";
import {useLocalState} from "../../util/useLocalStorage";

const Shops = () => {

    const [jwt, setJwt] = useLocalState("", "jwt");
    const [shops, setShops] = useState(null);

    useEffect(() => {
        fetch("api/shops", {
            headers: {
                "Content-type": "application/json",
                Authorization: `Bearer ${jwt}`
            },
            method: "GET",
        }).then(response => {
            if (response.status === 200) return response.json();
        }).then(shopData => {
            setShops(shopData);
        })
    }, [])

    function createShop() {
        fetch("api/shops", {
            headers: {
                "Content-type": "application/json",
                Authorization: `Bearer ${jwt}`,
            },
            method: "POST",
        }).then(response => {
            if (response.status === 200) return response.json()
        }).then(shop => {
            window.location.href = `/shops/${shop.id}`
        })
    }

    // return (
    //     <div style={{margin: "20px"}}>
    //         <button onClick={() => createProduct()}>Submit new Shop</button>
    //     </div>
    // );


    return (
        <div style={{margin: '2em'}}>
            <Button onClick={() => createShop()}>Submit New Shop</Button>
            {shops ? (
                shops.map((shops) => (
                    <div key={shops.id} className="d-grid">
                        <Card style={{width: '18rem'}}>
                            <Card.Body>
                                <Card.Title>Name: {shops.name}</Card.Title>
                                <p><b>Town:</b> {shops.town} </p>
                                <p><b>Address:</b> {shops.address} </p>
                                <Card.Text>
                                    <Button
                                        id="submit"
                                        type="button"
                                        onClick={() => {
                                            window.location.href = `/shops/${shops.id}`;
                                        }}
                                    > Edit
                                    </Button>
                                </Card.Text>
                            </Card.Body>
                        </Card>
                    </div>
                ))
            ) : (
                <></>
            )}
        </div>
    );
};

export default Shops;