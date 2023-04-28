import {useUser} from "../../UserProvider/UserProvider";
import React, {useEffect, useState} from "react";
import {useNavigate} from "react-router-dom";
import ajax from "../../Services/FetchService";
import NavBarAdmin from "../NavBarAdmin/NavBarAdmin";
import {Card, Col, Row} from "react-bootstrap";
import StatusBadge from "../StatusBadge/StatusBadge";
import NavBar from "../NavBar/NavBar";

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

        <main className="shops-view">
            <NavBar/>
            {shops ? (
                <section className="shops-view-container">
                    {shops.map((shops) => (
                        <article
                            key={shops.id}
                            >
                            <div className="shops-view-container-info"
                            >
                                <h4>Name: {shops.name}</h4>
                                <p>Town: {shops.town} </p>
                                <p>Address: {shops.address} </p>
                                <p>Status: {shops.status}</p>
                            </div>
                        </article>
                    ))}
                </section>
            ) : (
                <></>
            )}
        </main>
    );
}

export default ShopsViewUser;