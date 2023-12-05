import React, {useEffect, useState} from 'react';
import ajax from "../../Services/FetchService";
import {Button, Card, Row} from "react-bootstrap";
import StatusBadge from "../StatusBadge/StatusBadge";
import {useNavigate} from "react-router-dom";
import {useUser} from "../../UserProvider/UserProvider";
import NavBarAdmin from "../NavBarAdmin/NavBarAdmin";
import baseURL from "../BaseURL/BaseURL";
const ShopsViewAdmin = () => {

    const user = useUser();
    const [shops, setShops] = useState(null);
    let navigate = useNavigate();

    useEffect(() => {
        ajax(`${baseURL}api/shops`, "GET", user.jwt)
            .then(shopData => {
                setShops(shopData);
            });
        if (!user.jwt) navigate("/login");
    }, [navigate, user.jwt])

    function createShop() {
        ajax(`${baseURL}api/shops`, "POST", user.jwt)
            .then((response) => {
                if (response.custom === 'Successful create shop') {
                    refreshPage()
                }
        });
    }

    function refreshPage() {
        window.location.reload();
    }

    function deleteShop(id) {
        ajax(`${baseURL}api/shops/delete/${id}`, "DELETE", user.jwt)
            .then((response) => {
                console.log(response)
                if (response.custom === 'Successful delete shop') {
                    refreshPage()
                }
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
                            style={{width: '18rem', marginRight: '10px', marginTop: '50px'}}>
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
                                    > Редактирай
                                    </Button>
                                    <Button
                                        id="submit"
                                        type="button"
                                        onClick={() => {
                                            deleteShop(shops.id);
                                        }}
                                    > Изтрий
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
                onClick={() => createShop()}>Създай нов магазин</Button>
        </div>
    );
};

export default ShopsViewAdmin;