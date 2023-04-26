import React, {useEffect, useState} from 'react';
import NavBar from "../NavBar/NavBar";
import {useUser} from "../../UserProvider/UserProvider";
import {useNavigate} from "react-router-dom";
import ajax from "../../Services/FetchService";

const Homepage = () => {

    const user = useUser();
    const [products, setProducts] = useState(null);
    let navigate = useNavigate();
    const [product, setProduct] = useState({
            name: "",
            price: null,
            categories: "",
            description: "",
            imageUrl: ""
        }
    );

    useEffect(() => {
        ajax("api/", "GET", user.jwt)
            .then(productsData => {
                setProduct(productsData);
            })
        if (!user.jwt) navigate("/login")
    }, [user.jwt]);

    return (
        <>
            <NavBar/>
            <div className="main-title">
                <h1>Сладкарницата на Мама</h1>
            </div>
        </>
    );
};

export default Homepage;