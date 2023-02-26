import React, {useEffect, useState} from 'react';
import ajax from "../../Services/FetchService";
import {Badge, Button, Card, Col, Row} from "react-bootstrap";
import {useLocalState} from "../../util/useLocalStorage";
import StatusBadge from "../StatusBadge/StatusBadge";

const Shops = () => {

    const [jwt, setJwt] = useLocalState("", "jwt");
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
                                    <StatusBadge text={shops.status} />
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
            <Button
                style={{
                    marginTop: '10px'
                }}
                onClick={() => createShop()}>Submit New Shop</Button>
            <Row>
                <Col>
                    <button
                        style={{
                            cursor: 'pointer',
                            backgroundColor: 'blue',
                            borderRadius: '10px',
                            border: 'none',
                            marginTop: '10px',
                            width: '150px',
                            padding: '5px',
                            color: 'white'
                        }}
                        onClick={() => {
                            setJwt(null);
                            window.location.href = '/login'
                        }}>Logout
                    </button>
                </Col>
            </Row>
        </div>
    );
};

export default Shops;