import React, {useEffect, useState} from 'react';
import ajax from "../../Services/FetchService";
import {Badge, Button, Card, Col, Container, Row} from "react-bootstrap";
import {useLocalState} from "../../util/useLocalStorage";

const ModeratorShops = () => {

    const [jwt, setJwt] = useLocalState("", "jwt");
    const [shops, setShops] = useState(null);

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
        </Container>
    );
};

export default ModeratorShops;