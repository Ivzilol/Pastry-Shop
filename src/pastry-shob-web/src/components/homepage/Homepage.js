import React, {useEffect, useState} from 'react';
import NavBar from "../NavBar/NavBar";
import {useUser} from "../../UserProvider/UserProvider";
import {useNavigate} from "react-router-dom";
import ajax from "../../Services/FetchService";
import {Dialog} from "@mui/material";
import {FaSearch} from 'react-icons/fa';
import Footer from "../Footer/Footer";
import OrderWindow from "../Orders/OrderWindow";
import {useTranslation} from "react-i18next";


const Homepage = () => {

    const user = useUser();
    const [products, setProducts] = useState(null);
    let navigate = useNavigate();
    const [open, setOpen] = useState(false);
    const [currentProduct, setCurrentProduct] = useState(null);
    const [product, setProduct] = useState(null);
    const [recommendedProducts, setRecommendedProducts] = useState(null);
    const [showEvent, setShowEvent] = useState(null);
    const { t, i18n } = useTranslation();
    const changeLanguageHandler = (lang) => {
        i18n.changeLanguage("en")
    }
    const baseUrl = "http://localhost:8080/";

    useEffect(() => {
        ajax(`${baseUrl}api/`, "GET", user.jwt)
            .then(productsData => {
                setProducts(productsData);
            });

    }, [user.jwt]);

    useEffect(() => {
        ajax(`${baseUrl}api/home`, "GET", user.jwt)
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
        ajax(`${baseUrl}api/products/${id}`, "GET", user.jwt)
            .then(productData => {
                setCurrentProduct(productData);
            });
    }

    function orderProducts(id) {
        ajax(`${baseUrl}api/orders/${id}`, "POST", user.jwt, product)
            .then(productData => {
                setProduct(productData);
                alert("Successfully add the product to your cart")
            })

    }

    function toLogin() {
        navigate("/login");
    }

    useEffect(() => {
        const checkTimeAndShowEvent = () => {
            const now = new Date();
            const hours = now.getHours();
            if (hours >= 14 && hours < 21) {
                setShowEvent(true);
            } else {
                setShowEvent(false);
            }
        }
        const interval = setInterval(checkTimeAndShowEvent, 60000);
        checkTimeAndShowEvent();
        return () => {
            clearInterval(interval);
        }
    }, []);

    // eslint-disable-next-line react-hooks/exhaustive-deps
    const eventEndTime = new Date();
    eventEndTime.setHours(21, 0, 0);
    const getTimeRemaining = (endTime) => {
        const now = new Date();
        const timeDiff = endTime - now;
        const hours = Math.floor(timeDiff / (1000 * 60 * 60));
        const minutes = Math.floor((timeDiff % (1000 * 60 * 60)) / (1000 * 60));
        const seconds = Math.floor((timeDiff % (1000 * 60)) / 1000);

        return {hours, minutes, seconds};
    };
    const [timeRemaining, setTimeRemaining] = useState(getTimeRemaining(eventEndTime))

    useEffect(() => {
        const interval = setInterval(() => {
            setTimeRemaining(getTimeRemaining(eventEndTime));
        }, 1000);

        return () => {
            clearInterval(interval);
        };
    }, [eventEndTime]);

    const [selectOptions, setSelectOptions] = useState("");
    const [dialogVisible, setDialogVisible] = useState(false);
    const [searchResult, setSearchResult] = useState(null)


    function getSearchResult() {
        const requestBody = {
            selectOptions: selectOptions
        }
        ajax(`${baseUrl}api/products/search`, "POST", user.jwt, requestBody)
            .then(productsData => {
                setSearchResult(productsData);
                setDialogVisible(true);
            });
    }

    const closeDialog = () => {
        setDialogVisible(false);
        setSearchResult(null);
    }

    return (
        <main className="home-page">
            <NavBar/>
            <OrderWindow/>
            {dialogVisible && (
                <div className="search-result">
                    <button className="search-result-close-button" onClick={closeDialog}>Close</button>
                    <h6>Search Result</h6>
                    {searchResult ? (
                        <div className="search-result-container">
                            {searchResult.map((product) => (
                                <div id={product.id}
                                     key={product.id}
                                >
                                    <img className="search-result-container-img"
                                         src={product.imageUrl} alt="new"/>
                                    <h5>{product.name}</h5>
                                    <p className="search-result-container-price">Цена: {product.price} лв.</p>
                                    <p className="search-result-container-description" >{product.description}</p>
                                    <button
                                        className="product-details-selected-product-button"
                                        id="submit"
                                        type="button"
                                        onClick={() => orderProducts(product.id)}
                                    >
                                        Поръчай
                                    </button>
                                </div>
                            ))}
                        </div>
                    ) : (
                        <></>
                    )}
                </div>
            )}
            <section className="home-page-event-search">
                <div className="home-page-event">
                    {showEvent ?
                        <div>
                            <h5>Поръчвай всички наши продукти с 20 процента отстъпка!</h5>
                            <div>
                                <h6>Време до края на промоцията</h6>
                                <p>{timeRemaining.hours.toString().padStart(2, '0')}:
                                    {timeRemaining.minutes.toString().padStart(2, '0')}:
                                    {timeRemaining.seconds.toString().padStart(2, '0')}</p>
                            </div>
                        </div>
                        :
                        <div>
                            {t('promo')}
                        </div>
                    }
                </div>
                <div className="home-page-search">
                    <h5>Search Product</h5>
                    <select
                        id="search-product"
                        name="search-product"
                        placeholder="Select product"
                        value={selectOptions}
                        onChange={(e) => setSelectOptions(e.target.value)}

                    >
                        <option value="">Select Product</option>
                        <option value="pie">Погачи</option>
                        <option value="sweets">Сладки</option>
                        <option value="buns">Кифлички</option>
                        <option value="cake">Торти</option>
                    </select>
                    <button onClick={getSearchResult}>Search</button>
                </div>
            </section>
            <section className="home-page-first"
                     onClick={toLogin}
            >
                <div className="home-page-first-left">
                    <img className="home-page-first-left-picture"
                         src="https://i.ibb.co/vDRjrkc/bfi1677689901o.jpg" alt="img"/>
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
                                open={open} onClose={handleClickCloseProductDetails}
                            >
                                <section
                                    className="product-details"
                                    key={product.id}>
                                    <div className="product-details-container">
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