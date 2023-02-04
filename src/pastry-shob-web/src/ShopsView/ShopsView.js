// import React, {useEffect, useState} from 'react';
// import {useLocalState} from "../util/useLocalStorage";
// import ajax from "../Services/FetchService";
// import {Badge, Button, Col, Container, Form, Row} from "react-bootstrap";
//
// const ShopsView = () => {
//     const [jwt] = useLocalState("", "jwt")
//     const shopId = window.location.href.split("/shops/")[1];
//     const [shop, setShop] = useState({
//         town: "",
//         address: ""
//
//     });
//
//     function updateShop(prop, value) {
//         const newShop = {...shop}
//         newShop[prop] = value;
//         setShop(newShop);
//     }
//
//     function saveShop() {
//         ajax(`/api/shops/${shopId}`, "PUT", jwt, shop)
//             .then(shopData => {
//                     setShop(shopData);
//                 }
//             );
//     }
//
//     useEffect(() => {
//         ajax(`/api/shops/${shopId}`, "GET", jwt)
//             .then(shopData => {
//                 if (shopData.town === null) shopData.town = ""
//                 if (shopData.address === null) shopData.address = ""
//                 setShop(shopData);
//             });
//     }, [jwt, shopId])
//
//
//     return (
//         <Container className="mt-4">
//             <Row className="d-flex justify-content-center align-items-center">
//                 <Col>
//                     <h1>Shop {shopId}</h1>
//                 </Col>
//                 <Col>
//                     <Badge pill bg="info" style={{fontSize: "20px"}}>
//                         Name: {shop.name}
//                     </Badge>
//                 </Col>
//             </Row>
//             {shop ? (
//                 <>
//                     <Form.Group as={Row} className="mb-3" controlId="shop">
//                         <Form.Label column sm="2" className="">
//                             Town:
//                         </Form.Label>
//                         <Col sm="10">
//                             <Form.Control
//                                 onChange={(e) => updateShop("town", e.target.value)}
//                                 value={shop.town}
//                                 type="text"
//                                 placeholder="Town"
//                             />
//                         </Col>
//                     </Form.Group>
//                     <Form.Group as={Row} className="mb-3" controlId="shop">
//                         <Form.Label column sm="2" className="">
//                             Address:
//                         </Form.Label>
//                         <Col sm="10">
//                             <Form.Control
//                                 onChange={(e) => updateShop("town", e.target.value)}
//                                 value={shop.address}
//                                 type="text"
//                                 placeholder="address"
//                             />
//                         </Col>
//                     </Form.Group>
//                     <Button onClick={() => saveShop()}>Submit Town</Button>
//                 </>) : (
//                 <></>
//             )}
//         </Container>
//     );
// };
//
// export default ShopsView;