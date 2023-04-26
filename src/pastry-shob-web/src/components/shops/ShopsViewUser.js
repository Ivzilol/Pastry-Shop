import {useUser} from "../../UserProvider/UserProvider";
import React, {useEffect, useState} from "react";
import {useNavigate} from "react-router-dom";
import ajax from "../../Services/FetchService";
import NavBarAdmin from "../NavBarAdmin/NavBarAdmin";
import {Card, Col, Row} from "react-bootstrap";
import StatusBadge from "../StatusBadge/StatusBadge";

const ShopsViewUser = () => {

    const user = useUser();
    const [shops, setShops] = useState(null);
    let navigate = useNavigate();

    useEffect(() => {
        ajax("api/shops", "GET", user.jwt)
            .then(shopData => {
                setShops(shopData);
            });
        if (!user.jwt) navigate("/login");
    }, [user.jwt]);

    return (

        <div style={{margin: '2em'}}>
            <NavBarAdmin/>
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
                            user.setJwt(null);
                            navigate('/login')
                        }}>Logout
                    </button>
                </Col>
            </Row>
        </div>
    );
}

export default ShopsViewUser;