import React, {useEffect, useState} from 'react';
import {useLocalState} from "../../util/useLocalStorage";
import ajax from "../../Services/FetchService";
import {Button, Card} from "react-bootstrap";

const Dashboard = () => {

    const [jwt] = useLocalState("", "jwt")
    const [shops, setShops] = useState(null);

    useEffect(() => {
        ajax("api/shops", "GET", jwt)
            .then(shopData => {
                setShops(shopData);
            });
    }, [jwt])

    function createProduct() {
        ajax("api/shops", "POST", jwt)
            .then(shop => {
                window.location.href = `/shops/${shop.id}`;
            });
    }

    return (

        <div style={{margin: '2em'}}>
            <Button onClick={() => createProduct()}>Submit New Shop</Button>
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

export default Dashboard;