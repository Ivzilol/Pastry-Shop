import React, {useEffect, useState} from 'react';
import ajax from "../../Services/FetchService";
import {Badge, Button, Card, Col, Container, Row} from "react-bootstrap";
import {useLocalState} from "../../util/useLocalStorage";
import jwt_decode from "jwt-decode";
import StatusBadge from "../StatusBadge/StatusBadge";
import {useNavigate} from "react-router-dom";

const ModeratorShops = () => {

    const [jwt, setJwt] = useLocalState("", "jwt");
    const [shops, setShops] = useState(null);
    let navigate = useNavigate();
    function updateShop(shop) {
         window.location.href = `/shops/${shop.id}`;
    }

    function changeShop(shop) {
        const decodeJwt = jwt_decode(jwt);
        shop.moderator = {
            username: decodeJwt.sub,
        };
        // Todo: don't hardcode this status
        shop.status = "Working";
        ajax(`/api/shops/${shop.id}`, "PUT", jwt, shop)
            .then(updateShop => {
                const shopsCopy = [...shops];
                const s = shopsCopy.findIndex(s => s.id === updateShop.id);
                shopsCopy[s] = updateShop;
                setShops(shopsCopy);
            })
    }

    useEffect(() => {
        ajax("api/shops", "GET", jwt)
            .then(shopData => {
                setShops(shopData);
            })
    }, [jwt])
    return (
        <Container style={{margin: '2em'}}>
            <Row>
                <Col>
                    <h1>Moderator</h1>
                </Col>
            </Row>
            <div className="shop-wrapper need-update">
                <div className="shop-wrapper-title h3">Waiting Update!</div>
                {shops && shops.filter((shops) => shops.status === 'non-working').length > 0
                    ? (
                    <Row>
                        {shops.filter(shop => shop.status === 'non-working').map((shop) => (
                            // <Col>
                            <Card
                                key={shops.id}
                                style={{width: '18rem', marginRight: '10px', marginTop: '10px'}}>
                                <Card.Body className="d-flex flex-direction row justify-content-space-between"
                                >
                                    <Card.Title>Number: {shop.number}</Card.Title>
                                    <Card.Title>Name: {shop.name}</Card.Title>
                                    <div className="badge" style={{width: '50%'}}>
                                        <StatusBadge text={shop.status} />
                                    </div>
                                    <p><b>Town:</b> {shop.town} </p>
                                    <p><b>Address:</b> {shop.address} </p>
                                    <Card.Text>
                                        <Button
                                            id="submit"
                                            type="button"
                                            onClick={() => {
                                                updateShop(shop);
                                            }}
                                        > Change
                                        </Button>
                                    </Card.Text>
                                </Card.Body>
                            </Card>
                            // </Col>
                        ))}
                    </Row>

                ) : (
                    <div>No shops for Update</div>
                )}
            </div>
            <div className="shop-wrapper updated">
                <div className="shop-wrapper-title-updated h3">Updated</div>
                {shops && shops.filter((shops) => shops.status === 'Working').length > 0
                    ? (
                    <Row>
                        {shops.filter(shops => shops.status === 'Working').map((shop) => (
                            // <Col>
                            <Card
                                key={shop.id}
                                style={{width: '18rem', marginRight: '10px', marginTop: '10px'}}>
                                <Card.Body className="d-flex flex-direction row justify-content-space-between"
                                >
                                    <Card.Title>Number: {shop.number}</Card.Title>
                                    <Card.Title>Name: {shop.name}</Card.Title>
                                    <div className="badge" style={{width: '50%'}}>
                                        <StatusBadge text={shop.status} />
                                    </div>
                                    <p><b>Town:</b> {shop.town} </p>
                                    <p><b>Address:</b> {shop.address} </p>
                                    <Card.Text>
                                        <Button
                                            id="submit"
                                            type="button"
                                            onClick={() => {
                                                changeShop(shop);
                                            }}
                                        > Change
                                        </Button>
                                    </Card.Text>
                                </Card.Body>
                            </Card>
                            // </Col>
                        ))}
                    </Row>

                ) : (
                        <div>Not Have Working</div>
                )}
            </div>

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
                            navigate('/login')
                        }}>Logout
                    </button>
                </Col>
            </Row>
        </Container>
    );
};

export default ModeratorShops;