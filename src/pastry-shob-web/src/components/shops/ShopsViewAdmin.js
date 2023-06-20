import React, {useEffect, useState} from 'react';
import ajax from "../../Services/FetchService";
import {Button, Card, Row} from "react-bootstrap";
import StatusBadge from "../StatusBadge/StatusBadge";
import {useNavigate} from "react-router-dom";
import {useUser} from "../../UserProvider/UserProvider";
import NavBarAdmin from "../NavBarAdmin/NavBarAdmin";
const ShopsViewAdmin = () => {

    const user = useUser();
    const [shops, setShops] = useState(null);
    let navigate = useNavigate();
    const baseUrl = "http://localhost:8080/";

    useEffect(() => {
        ajax(`${baseUrl}api/shops`, "GET", user.jwt)
            .then(shopData => {
                setShops(shopData);
            });
        if (!user.jwt) navigate("/login");
    }, [navigate, user.jwt])

    function createShop() {
        ajax(`${baseUrl}api/shops`, "POST", user.jwt).then((shop) => {
            window.location.href = `/shops/${shop.id}`
        });
    }

    function refreshPage() {
        window.location.reload();
    }

    function deleteShop(id) {
        ajax(`${baseUrl}api/shops/delete/${id}`, "DELETE", user.jwt)
            .then(() => {
                refreshPage()
            })
    }


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
                                <Card.Text>
                                    <Button
                                        id="submit"
                                        type="button"
                                        onClick={() => {
                                            window.location.href = `/shops/${shops.id}`;
                                        }}
                                    > Edit
                                    </Button>
                                    <Button
                                        id="submit"
                                        type="button"
                                        onClick={() => {
                                            deleteShop(shops.id);
                                        }}
                                    > Delete
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
        </div>
    );
};

export default ShopsViewAdmin;