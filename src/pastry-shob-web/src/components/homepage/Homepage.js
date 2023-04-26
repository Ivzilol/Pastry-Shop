import React, {useEffect, useState} from 'react';
import NavBar from "../NavBar/NavBar";
import {useUser} from "../../UserProvider/UserProvider";
import {useNavigate} from "react-router-dom";
import ajax from "../../Services/FetchService";

const Homepage = () => {

    const user = useUser();
    const [products, setProducts] = useState(null);
    let navigate = useNavigate();


    useEffect(() => {
        ajax("api/", "GET", user.jwt)
            .then(productsData => {
                setProducts(productsData);
            })
        if (!user.jwt) navigate("/login")
    }, [user.jwt]);

    return (
        <main className="home-page">
            <NavBar/>
            <div className="main-title">
                <h1>Сладкарницата на Мама</h1>
            </div>
            <h4 className="home-page-most-ordered-title">Most ordered products!</h4>
                {products ? (
                    <article className="home-page-container">

                        {products.map((product) => (
                            <div
                                className="home-page-container-items"
                                key={product.id}
                            >
                                <img
                                    className="home-page-container-item-img" src={product.imageUrl} alt="new"
                                />
                                <p className="home-page-container-item"
                                >{product.name}</p>
                                <p className="home-page-container-item"
                                >Цена: {product.price}</p>
                            </div>
                        ))}
                    </article>
                ) : (
                    <></>
                )}
        </main>
    );
};

export default Homepage;