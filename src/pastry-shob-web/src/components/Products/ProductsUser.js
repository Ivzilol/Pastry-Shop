import NavBar from "../NavBar/NavBar";
import {useUser} from "../../UserProvider/UserProvider";
import React, {useEffect, useState} from "react";
import {useNavigate} from "react-router-dom";
import ajax from "../../Services/FetchService";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {
    faThumbsUp
} from "@fortawesome/free-solid-svg-icons";
import Footer from "../Footer/Footer";
import jwt_decode from "jwt-decode";
import OrderWindow from "../Orders/OrderWindow";


const ProductsUser = () => {
    const user = useUser()
    const [products, setProducts] = useState(null);
    let isEquals = false;
    const [roles, setRoles] = useState(getRolesFromJWT());
    let navigate = useNavigate();
    const baseUrl = "http://localhost:8080/";
    const [product, setProduct] = useState({
            name: "",
            price: null,
            categories: "",
            description: "",
            imageUrl: ""
        }
    );

    useEffect(() => {
        ajax(`${baseUrl}api/products`, "GET", user.jwt)
            .then(productsData => {
                setProducts(productsData);
            });

        if (!user.jwt) navigate("/login")
    }, [navigate, user.jwt]);

    function orderProduct(id) {
        ajax(`${baseUrl}api/products/${id}`, "GET", user.jwt)
            .then(productData => {
                setProduct(productData);
            });

    }

    function orderProducts(id) {
        ajax(`${baseUrl}api/orders/${id}`, "POST", user.jwt, product)
            .then(productData => {
                setProduct(productData);
                alert("Successfully add the product to your cart")
            })
    }

    function refreshPage() {
        window.location.reload();
    }

    function likeProduct(id) {
        ajax(`${baseUrl}api/products/${id}`, "PATCH", user.jwt)
            .then(() => {
                alert("You like this product")
                refreshPage();
            })
    }

    useEffect(() => {
        setRoles(getRolesFromJWT())
    }, [getRolesFromJWT, user.jwt])

    // eslint-disable-next-line react-hooks/exhaustive-deps
    function getRolesFromJWT() {
        if (user.jwt) {
            const decodeJwt = jwt_decode(user.jwt);
            return decodeJwt.sub;
        }
        return [];
    }

    function dislikeProduct(id) {
        ajax(`${baseUrl}api/products/dislike/${id}`, "Delete", user.jwt)
            .then(() => {
                alert("You dislike product")
                refreshPage();
            })
    }


    function getIsLike(product) {
        return <>
            {/* eslint-disable-next-line array-callback-return */}
            {Object.entries(product.userLikes).map(([, value]) => {
                // eslint-disable-next-line no-lone-blocks
                {
                    for (const [, v] of Object.entries(value)) {
                        if (v === roles) {
                            isEquals = true;
                            break;
                        }
                    }
                }
            })}
        </>;
    }

    return (
        <main className="products-users">
            <NavBar/>
            <OrderWindow/>
            {products ? (
                <article className="products-container">
                    {products.map((product) => (
                        <div
                            className="products-container-items"
                            key={product.id}
                            id={product.id}
                        >
                            <p className="products-container-item"
                            >Продукт: {product.name}</p>
                            <p className="products-container-item"
                            >Цена: {product.price} лв.</p>
                            <p className="products-container-item"
                            >Описание на продукта: {product.description}</p>
                            <img
                                className="product-img" src={product.imageUrl} alt="new"
                            />
                            <div className="products-container-item-likes-container">
                                <p className="products-container-item-likes">
                                    <FontAwesomeIcon icon={faThumbsUp}
                                                     className="products-container-item-likes-icon"
                                    />
                                    {Number(product.userLikes.length)}</p>
                                <div className="products-container-item-likes-container-buttons">
                                    {/* eslint-disable-next-line array-callback-return */}
                                    {getIsLike(product)}
                                    {isEquals
                                        ?
                                        <button
                                            className="products-container-item-likes-button"
                                            id="submit"
                                            type="button"
                                            onClick={() => dislikeProduct(product.id)}
                                        >Не Харесвам
                                        </button>
                                        :
                                        <button
                                            className="products-container-item-likes-button"
                                            id="submit"
                                            type="button"
                                            onClick={() => likeProduct(product.id)}
                                        >Харесва ми
                                        </button>
                                    }
                                    <button
                                        className="products-container-item-likes-container-button2"
                                        id="submit"
                                        type="button"
                                        onClick={() => {
                                            orderProduct(product.id);
                                            orderProducts(product.id);
                                        }}
                                    >
                                        Поръчай
                                    </button>
                                </div>
                                <p className="counter-likes">
                                    {isEquals = false}
                                </p>
                            </div>
                        </div>
                    ))}
                </article>
            ) : (
                <></>
            )}
            <Footer/>
        </main>
    )
}
export default ProductsUser;