import React, {useEffect, useState} from 'react';
import NavBar from "../NavBar/NavBar";
import {useUser} from "../../UserProvider/UserProvider";
import {useNavigate} from "react-router-dom";
import ajax from "../../Services/FetchService";
import {Dialog} from "@mui/material";
import {FaSearch} from 'react-icons/fa';
import Footer from "../Footer/Footer";


const Homepage = () => {

    const user = useUser();
    const [products, setProducts] = useState(null);
    let navigate = useNavigate();
    const [open, setOpen] = useState(false);
    const [currentProduct, setCurrentProduct] = useState(null);
    const [product, setProduct] = useState(null);
    const [recommendedProducts, setRecommendedProducts] = useState(null);

    useEffect(() => {
        ajax("api/", "GET", user.jwt)
            .then(productsData => {
                setProducts(productsData);
            });

    }, [user.jwt]);

    useEffect(() => {
        ajax("api/home", "GET", user.jwt)
            .then(recommendedData => {
                setRecommendedProducts(recommendedData);
            });

    }, [user.jwt]);

    function handleClickOpenProductDetails(id) {
        setOpen(true);
        getCurrentProduct(id);
    }

    function handleClickCloseProductDetails() {
        setOpen(false);
    }

    function getCurrentProduct(id) {
        ajax(`/api/products/${id}`, "GET", user.jwt)
            .then(productData => {
                setCurrentProduct(productData);
            });
    }

    function orderProducts(id) {
        ajax(`/api/orders/${id}`, "POST", user.jwt, product)
            .then(productData => {
                setProduct(productData);
                alert("Successfully add the product to your cart")
            })

    }

    function toLogin() {
        navigate("/login");
    }

    return (
        <main className="home-page">
            <NavBar/>
            <section className="home-page-first"
                     onClick={toLogin}

            >
                <div className="home-page-first-left">
                    <img className="home-page-first-left-picture"
                         src="http://alfa.kachi-snimka.info/images-2021/bfi1677689901o.jpg" alt="img"/>
                </div>
                <div className="home-page-first-right">
                    <h6>РЪЧНО ПРИГОТВЕНИ ДОМАШНИ ВКУСОТИИ</h6>
                    <h1>ЗА ВСЕКИ ВКУС</h1>
                    <p>изберете от любимите си домашни вкусотии</p>
                    <p>поръчайте онлайн с доставка до вашия дом</p>
                </div>
            </section>
            <section className="home-page-most-ordered">
                <h4 className="home-page-most-ordered-title">НАЙ ПРОДАВАНИ ПРОДУКТИ!</h4>
                <p className="home-page-most-ordered-description">Поръчай онлайн с доставка до адрес!</p>
            </section>
            {products && user.jwt ? (
                <article className="home-page-container">
                    {products.map((product) => (
                        <div
                            className="home-page-container-items"
                            key={product.id}
                        >
                            <a className="container-img"
                               onClick={() => handleClickOpenProductDetails(product.id)}
                               id="submit"
                               type="submit"
                               target="_blank"
                               rel="noreferrer"
                            >
                                <img className="home-page-container-item-img" src={product.imageUrl} alt="new"
                                />
                                <FaSearch
                                    className="home-page-container-item-current-icon"
                                />
                            </a>

                            <Dialog
                                open={open} onClose={handleClickCloseProductDetails}>
                                <section
                                    className="product-details"
                                    key={product.id}>
                                    <div>
                                        {currentProduct ? (
                                            <div className="product-details-selected-product">
                                                <a className="close"
                                                   onClick={handleClickCloseProductDetails}
                                                   id="submit"
                                                   type="submit"
                                                >X
                                                </a>
                                                <img className="product-details-selected-product-img"
                                                     src={currentProduct.imageUrl} alt="new"/>
                                                <h4>{currentProduct.name}</h4>
                                                <p>Цена: {currentProduct.price} лв.</p>
                                                <p>{currentProduct.description}</p>
                                                <button
                                                    className="product-details-selected-product-button"
                                                    id="submit"
                                                    type="button"
                                                    onClick={() => orderProducts(currentProduct.id)}
                                                >
                                                    Поръчай
                                                </button>
                                            </div>
                                        ) : (
                                            <></>
                                        )}
                                    </div>
                                </section>
                            </Dialog>
                            <p className="home-page-container-item"
                            >{product.name}</p>
                            <p className="home-page-container-item"
                            >Цена: {product.price}</p>
                        </div>
                    ))}
                </article>

            ) : (
                <h4 className="not-login-user">За да видите нашите предложения моля влезте с Вашия профил</h4>
            )}
            <h4 className="home-page-most-ordered-title">ПРЕПОРЪЧАНИ ПРОДУКТИ!</h4>
            {recommendedProducts && user.jwt ? (
                <article className="home-page-container">
                    {recommendedProducts.map((recommendedProduct) => (
                        <div
                            className="home-page-container-items"
                            key={recommendedProduct.id}
                        >
                            <a className="container-img"
                               onClick={() => handleClickOpenProductDetails(recommendedProduct.id)}
                               id="submit"
                               type="submit"
                               target="_blank"
                               rel="noreferrer"
                            >
                                <img className="home-page-container-item-img" src={recommendedProduct.imageUrl}
                                     alt="new"
                                />
                                <FaSearch
                                    className="home-page-container-item-current-icon"
                                />
                            </a>

                            <Dialog
                                open={open} onClose={handleClickCloseProductDetails}>
                                <section
                                    className="product-details"
                                    key={recommendedProduct.id}>
                                    <div>
                                        {currentProduct ? (
                                            <div className="product-details-selected-product">
                                                <a className="close"
                                                   onClick={handleClickCloseProductDetails}
                                                   id="submit"
                                                   type="submit"
                                                >X
                                                </a>
                                                <img className="product-details-selected-product-img"
                                                     src={currentProduct.imageUrl} alt="new"/>
                                                <h4>{currentProduct.name}</h4>
                                                <p>Цена: {currentProduct.price} лв.</p>
                                                <p>{currentProduct.description}</p>
                                                <button
                                                    className="product-details-selected-product-button"
                                                    id="submit"
                                                    type="button"
                                                    onClick={() => orderProducts(currentProduct.id)}
                                                >Order
                                                </button>
                                            </div>
                                        ) : (
                                            <></>
                                        )}
                                    </div>
                                </section>
                            </Dialog>
                            <p className="home-page-container-item"
                            >{recommendedProduct.name}</p>
                            <p className="home-page-container-item"
                            >Цена: {recommendedProduct.price}</p>
                        </div>
                    ))}
                </article>
            ) : (
                <h4 className="not-login-user">За да видите нашите предложения моля влезте с Вашия профил</h4>
            )}
            <Footer/>
        </main>
    );
};

export default Homepage;