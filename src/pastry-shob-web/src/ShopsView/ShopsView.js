import React, {useEffect, useState} from 'react';
import {useLocalState} from "../util/useLocalStorage";
import ajax from "../Services/FetchService";
import {Dropdown, Badge, Button, ButtonGroup, Col, Container, DropdownButton, Form, Row} from "react-bootstrap";

const ShopsView = () => {
    const [jwt] = useLocalState("", "jwt")
    const shopId = window.location.href.split("/shops/")[1];
    const [shop, setShop] = useState({
        town: "",
        address: ""
    });

    const [shopsEnums, setShopsEnums] = useState([]);

    function updateShop(prop, value) {
        const newShop = {...shop}
        newShop[prop] = value;
        setShop(newShop);
    }

    function saveShop() {
        ajax(`/api/shops/${shopId}`, "PUT", jwt, shop)
            .then(shopData => {
                    setShop(shopData);
                }
            );
    }

    useEffect(() => {
        ajax(`/api/shops/${shopId}`, "GET", jwt)
            .then(shopResponse => {
                let shopData = shopResponse.shops;
                if (shopData.town === null) shopData.town = ""
                if (shopData.address === null) shopData.address = ""
                setShop(shopData);
                setShopsEnums(shopResponse.shopsEnums);
            });
    }, []);

    useEffect(() => {
        console.log(shopsEnums);
    }, [shopsEnums]);


    return (
        <Container className="mt-4">
            <Row className="d-flex justify-content-center align-items-center">
                <Col>
                    <h1>Shop: {shopId}</h1>
                </Col>
                <Col>
                    <Badge pill bg="info" style={{fontSize: "20px"}}>
                        Name: {shop.name}
                    </Badge>
                </Col>
            </Row>
            {shop ? (
                <>
                    <Form.Group as={Row} className="mb-3" controlId="shop">
                        <Form.Label column sm="2" className="">
                            Shops Number:
                        </Form.Label>
                        <Col sm="10">
                            <DropdownButton
                                as={ButtonGroup}
                                title="ShopName"
                                id="shopName"
                                variant={'info'}
                            >
                                {shopsEnums.map((shopsEnums) =>
                                    <Dropdown.Item eventKey={shopsEnums.shopName}>
                                        {shopsEnums.shopName}
                                    </Dropdown.Item>)}

                            </DropdownButton>
                        </Col>
                    </Form.Group>

                    <Form.Group as={Row} className="mb-3" controlId="shop">
                        <Form.Label column sm="2" className="">
                            Town:
                        </Form.Label>
                        <Col sm="10">
                            <Form.Control
                                onChange={(e) => updateShop("town", e.target.value)}
                                value={shop.town}
                                type="text"
                                placeholder="Town"
                            />
                        </Col>
                    </Form.Group>
                    <Form.Group as={Row} className="mb-3" controlId="shop">
                        <Form.Label column sm="2" className="">
                            Address:
                        </Form.Label>
                        <Col sm="10">
                            <Form.Control
                                onChange={(e) => updateShop("address", e.target.value)}
                                value={shop.address}
                                type="text"
                                placeholder="address"
                            />
                        </Col>
                    </Form.Group>
                    <Button onClick={() => saveShop()}>Submit Shop</Button>
                    <Button
                        id="submit"
                        type="button"
                        onClick={() => {
                            window.location.href = "/shops";
                        }}
                    > Shops
                    </Button>
                </>) : (
                <></>
            )}
        </Container>
    );
};

export default ShopsView;