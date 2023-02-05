import React, {useEffect, useState} from 'react';
import ajax from "../../Services/FetchService";
import {Button, Card, Col, Row} from "react-bootstrap";
import {useLocalState} from "../../util/useLocalStorage";

const Shops = () => {

    const [jwt, setJwt] = useLocalState("", "jwt");
    const [shops, setShops] = useState(null);

    useEffect(() => {
        ajax("api/shops", "GET", jwt)
        .then(shopData => {
            setShops(shopData);
        })
    }, [])

    function createShop() {
        ajax("api/shops", "POST", jwt, )
        .then(response => {
            if (response.status === 200) return response.json()
        }).then(shop => {
            window.location.href = `/shops/${shop.id}`
        })
    }


    return (
        <div style={{margin: '2em'}}>

            {shops ? (
                <Row >
                    {shops.map((shops) => (
                    <Col>
                        <Card key={shops.id} style={{width: '18rem'}}>
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
                    </Col>
                ))}
                </Row>

            ) : (
                <></>
            )}
            <Button onClick={() => createShop()}>Submit New Shop</Button>
        </div>
    );
};

export default Shops;