// import React, {useEffect, useState} from 'react';
// import ajax from "../../Services/FetchService";
// import {Button, Card} from "react-bootstrap";
// import {useNavigate} from "react-router-dom";
// import {useUser} from "../../UserProvider/UserProvider";
// import jwt_decode from "jwt-decode";
//
// const Shops = () => {
//     const navigate = useNavigate();
//     const user = useUser();
//     const [shops, setShops] = useState(null);
//     const [userData, setUserData] = useState(null);
//
//     useEffect(() => {
//         const decodeJwt =jwt_decode(user.jwt);
//         if (userData && shops) {
//             ajax("api/users/" + decodeJwt.sub, "GET", user.jwt)
//                 .then((data) => {
//                     setUserData(data)
//                 })
//         }
//     }, [user, userData, shops]);
//
//     useEffect(() => {
//         ajax("api/shops", "GET", user.jwt)
//             .then((shopData) => {
//                 setShops(shopData);
//             });
//         if (!user.jwt)
//             console.warn("No valid jwt found, redirecting to login page");
//             navigate("/login")
//     });
//
//     function createProduct() {
//         ajax("api/shops", "POST", user.jwt)
//             .then((shop) => {
//                 navigate("/shops");
//             })
//     }
//
//     return (
//
//         <div style={{margin: '2em'}}>
//             <Button onClick={() => createProduct()}>Submit New Shop</Button>
//             {shops ? (
//                 shops.map((shops) => (
//                     <div key={shops.id} className="d-grid">
//                         <Card style={{width: '18rem'}}>
//                             <Card.Body>
//                                 <Card.Title>Name: {shops.name}</Card.Title>
//                                 <p><b>Town:</b> {shops.town} </p>
//                                 <p><b>Address:</b> {shops.address} </p>
//                                 <Card.Text>
//                                     <Button
//                                         id="submit"
//                                         type="button"
//                                         onClick={() => {
//                                             window.location.href = `/shops/${shops.id}`;
//                                         }}
//                                     > Edit
//                                     </Button>
//                                 </Card.Text>
//                             </Card.Body>
//                         </Card>
//                     </div>
//                 ))
//             ) : (
//                 <></>
//             )}
//         </div>
//     );
// };
//
// export default Shops;