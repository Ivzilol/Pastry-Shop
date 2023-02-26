import React, {useEffect, useRef, useState} from 'react';
import ajax  from "../../Services/FetchService";
import {Button, Col, Container, Form, Row, Badge} from "react-bootstrap";
import {useLocalState} from "../../util/useLocalStorage";

const ModeratorShopsView = () => {
    const [jwt, setJwt] = useLocalState("", "jwt")
    const shopId = window.location.href.split("/shops/")[1];
    const [shop, setShop] = useState({
        town: "",
        address: "",
        number: null,
        status: ""
    });

    const [shopsEnums, setShopsEnums] = useState([]);
    const [shopsStatuses, setShopsStatuses] = useState([]);

    const prevShopValue = useRef(shop);

    function updateShop(prop, value) {
        const newShop = {...shop}
        newShop[prop] = value;
        setShop(newShop);
    }

    function saveShop(status) {
        if (status && shop.status !== status) {
            updateShop("status", status)
        } else {
            persist();
        }
    }

    function persist() {
        ajax(`/api/shops/${shopId}`, "PUT", jwt, shop)
            .then(shopData => {
                    setShop(shopData);
                }
            );
    }

    useEffect(() => {
        if (prevShopValue.current.status !== shop.status) {
            persist();
        }
        prevShopValue.current = shop;
    }, [shop])

    useEffect(() => {
        ajax(`/api/shops/${shopId}`, "GET", jwt)
            .then(shopResponse => {
                let shopData = shopResponse.shops;
                if (shopData.town === null) shopData.town = ""
                if (shopData.address === null) shopData.address = ""
                setShop(shopData);
                setShopsEnums(shopResponse.shopsEnums);
                setShopsStatuses(shopResponse.statusEnums);
            });
    }, []);

    return (
        <Container className="mt-4">
            <Row className="d-flex justify-content-center align-items-end">
                <Col>
                    {shop.number ? <h4>Shop: {shop.number}</h4> : <></>

                    }
                </Col>
                <Col>
                    <Badge pill bg="info" style={{fontSize: "20px"}}>
                        Status: {shop.status}
                    </Badge>
                </Col>
            </Row>
            {shop ? (
                <>
                    <Form.Group as={Row} className="mb-3" controlId="town">
                        <Form.Label column sm="2" className="">
                            Town:
                        </Form.Label>
                        <Col sm="10">
                            <Form.Control
                                onChange={(e) => updateShop("town", e.target.value)}
                                value={shop.town}
                                type="text"
                                readOnly
                                placeholder="Town"
                            />
                        </Col>
                    </Form.Group>
                    <Form.Group as={Row} className="mb-3" controlId="address">
                        <Form.Label column sm="2" className="">
                            Address:
                        </Form.Label>
                        <Col sm="10">
                            <Form.Control
                                onChange={(e) => updateShop("address", e.target.value)}
                                value={shop.address}
                                type="text"
                                readOnly
                                placeholder="address"
                            />
                        </Col>
                    </Form.Group>
                    <div className="buttons">
                        <Button
                            style={{marginRight: '10px'}}
                            onClick={() => saveShop(shopsStatuses[0].status)}>
                            Change Shop
                        </Button>
                        <Button
                            id="submit"
                            type="button"
                            onClick={() => {
                                window.location.href = "/shops";
                            }}
                        > Shops
                        </Button>
                    </div>
                </>) : (
                <></>
            )}
        </Container>
    );
};

export default ModeratorShopsView;