import React, {useEffect, useRef, useState} from 'react';
import ajax from "../../Services/FetchService";
import {Button, Col, Container, Form, Row} from "react-bootstrap";
import {useLocalState} from "../../util/useLocalStorage";
import StatusBadge from "../StatusBadge/StatusBadge";
import {useNavigate} from "react-router-dom";
import CommentContainer from "../CommentContainer/CommentContainer";

const ModeratorShopsView = () => {
    const [jwt] = useLocalState("", "jwt")
    const shopId = window.location.href.split("/shops/")[1];
    let navigate = useNavigate();
    const [shop, setShop] = useState({
            town: "",
            address: "",
            number: null,
            status: ""
    });

    const [, setShopsEnums] = useState([]);
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
        window.location.href = "/shops";
    }

    // eslint-disable-next-line react-hooks/exhaustive-deps
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
    }, [persist, shop])

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
    }, [jwt, shopId]);

    return (
        <Container className="mt-4">
            <Row className="d-flex justify-content-center align-items-end">
                <Col>
                    {shop.number ? <h4>Shop: {shop.number}</h4> : <></>

                    }
                </Col>
                <Col>
                    <StatusBadge text={shop.status}/>
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
                                navigate("/shops");
                            }}
                        > Shops
                        </Button>
                    </div>
                    <CommentContainer shopsId={shopId}/>
                </>
            ) : (
                <></>
            )}
        </Container>
    );
};

export default ModeratorShopsView;