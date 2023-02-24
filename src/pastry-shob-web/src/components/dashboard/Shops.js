import React, {useEffect, useState} from 'react';
import ajax from "../../Services/FetchService";
import {Badge, Button, Card, Row} from "react-bootstrap";
import {useLocalState} from "../../util/useLocalStorage";

const Shops = () => {

    const [jwt] = useLocalState("", "jwt");
    const [shops, setShops] = useState(null);

    useEffect(() => {
        ajax("api/shops", "GET", jwt)
            .then(shopData => {
                setShops(shopData);
            })
    }, [jwt])

    function createShop() {
        ajax("api/shops", "POST", jwt).then((shop) => {
            window.location.href = `/shops/${shop.id}`
        });
    }


    return (
        <div style={{margin: '2em'}}>
            {shops ? (
                <Row>
                    {shops.map((shops) => (
                        // <Col>
                        <Card
                            key={shops.id}
                            style={{width: '18rem', marginRight: '10px', marginTop: '10px'}}>
                            <Card.Body className="d-flex flex-direction row justify-content-space-between"
                            >
                                <Card.Title>Number: {shops.number}</Card.Title>
                                <Card.Title>Name: {shops.name}</Card.Title>
                                <div className="badge" style={{width: '50%'}}>
                                    <Badge pill bg="info" style={{fontSize: "1em"}}>
                                        {shops.status}
                                    </Badge>
                                </div>
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
                        // </Col>
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