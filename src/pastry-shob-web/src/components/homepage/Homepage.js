import React, {useEffect, useState} from 'react';
import NavBar from "../NavBar/NavBar";
import {useUser} from "../../UserProvider/UserProvider";
import {useNavigate} from "react-router-dom";
import ajax from "../../Services/FetchService";
import {Dialog} from "@mui/material";
import {FaSearch} from 'react-icons/fa';
import Footer from "../Footer/Footer";
import OrderWindow from "../Orders/OrderWindow";
import jwt_decode from "jwt-decode";
import OrderWindowAdmin from "../Orders/OrderWindowAdmin";
import {useTranslation} from "react-i18next";
import ChatRoom from "../ChatRoom/ChatRoom";
import NavBarAdmin from "../NavBarAdmin/NavBarAdmin";
import baseURL from "../BaseURL/BaseURL";
import GlobalError from "../GlobalError/GlobalError";
import Maintenance from "../Maintenance/Maintenance";


const Homepage = () => {

    const user = useUser();
    const [products, setProducts] = useState(null);
    let navigate = useNavigate();
    const [open, setOpen] = useState(false);
    const [currentProduct, setCurrentProduct] = useState(null);
    const [product, setProduct] = useState(null);
    const [recommendedProducts, setRecommendedProducts] = useState(null);
    const [showEvent, setShowEvent] = useState(null);
    const [roles, setRoles] = useState(getRolesFromJWT());
    const [orderWindow, setOrderWindow] = useState(false);
    const {t} = useTranslation();
    const [globalError, setGlobalError] = useState(false);

    useEffect(() => {
        setRoles(getRolesFromJWT())
    }, [user.jwt])

    function getRolesFromJWT() {
        if (user.jwt) {
            const decodeJwt = jwt_decode(user.jwt);
            return decodeJwt.authorities;
        }
        return [];
    }


    useEffect(() => {
        ajax(`${baseURL}api/`, "GET", user.jwt)
            .then(productsData => {
                setProducts(productsData);
            }).catch(() => {
            setGlobalError(true);
        })
        ajax(`${baseURL}api/home`, "GET", user.jwt)
            .then(recommendedData => {
                setRecommendedProducts(recommendedData);
            }).catch(() => {
            setGlobalError(true);
        })
        if (user.jwt) {
            ajax(`${baseURL}api/orders/status`, "GET", user.jwt)
                .then(result => {
                    if (result.length > 0) {
                        setOrderWindow(true);
                    }
                }).catch(() => {
                setGlobalError(true);
            })
            ajax(`${baseURL}api/orders/status/confirmed`, "GET", user.jwt)
                .then(result => {
                    if (result.length > 0) {
                        setOrderWindow(true);
                    }
                }).catch(() => {
                setGlobalError(true)
            })
        }
    }, [user.jwt]);


    function handleClickOpenProductDetails(id) {
        setOpen(true);
        getCurrentProduct(id);
    }

    function handleClickCloseProductDetails() {
        setOpen(false);
    }

    function getCurrentProduct(id) {
        ajax(`${baseURL}api/products/${id}`, "GET", user.jwt)
            .then(productData => {
                setCurrentProduct(productData);
            })
            .catch(() => {
                setGlobalError(true);
            })
    }

    function orderProducts(id) {
        if (user.jwt) {
            if (roles.find((role) => role === 'admin')) {
                alert("Admin cant order products");
                return;
            }
            ajax(`${baseURL}api/orders/${id}`, "POST", user.jwt, product)
                .then(productData => {
                    setProduct(productData);
                    setOrderDialogProductName(productData.productName)
                    setOrderDialogProductDescription(productData.price.toFixed(2))
                    setOrderDialog(true);
                    timerOrderWindow();
                    setOrderWindow(true);
                })
                .catch(() => {
                    setGlobalError(true)
                })
        } else {
            window.location.href = "/login";
        }
    }

    function toLogin() {
        navigate("/login");
    }

    useEffect(() => {
        const checkTimeAndShowEvent = () => {
            const now = new Date();
            const hours = now.getHours();
            if (hours >= 8 && hours < 21) {
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
        ajax(`${baseURL}api/products/search`, "POST", user.jwt, requestBody)
            .then(productsData => {
                if (productsData.custom === "Please select category") {
                    setDialogVisible(true);
                } else {
                    setSearchResult(productsData);
                    setDialogVisible(true);
                }
            });
    }

    const closeDialog = () => {
        setDialogVisible(false);
        setSearchResult(null);
    }

    const [showScrollingElement1, setShowScrollingElement1] = useState(false);
    const [showScrollingElement2, setShowScrollingElement2] = useState(false);
    const [showScrollingElement3, setShowScrollingElement3] = useState(false);
    const [showScrollingElement4, setShowScrollingElement4] = useState(false);

    useEffect(() => {
        function handleScroll() {
            const scrollPosition = window.scrollY;
            const threshold = 140
            scrollPosition >= threshold && scrollPosition < 400
                ?
                setShowScrollingElement1(true)
                :
                setShowScrollingElement1(false)
            scrollPosition >= 400 && scrollPosition < 650
                ?
                setShowScrollingElement2(true)
                :
                setShowScrollingElement2(false)
            scrollPosition >= 650 && scrollPosition < 900
                ?
                setShowScrollingElement3(true)
                :
                setShowScrollingElement3(false)
            scrollPosition >= 900 && scrollPosition < 1200
                ?
                setShowScrollingElement4(true)
                :
                setShowScrollingElement4(false)
        }

        window.addEventListener("scroll", handleScroll);

        return () => {
            window.removeEventListener("scroll", handleScroll)
        }
    }, [])

    const [orderDialog, setOrderDialog] = useState(false);
    const [orderDialogProductName, setOrderDialogProductName] = useState("")
    const [orderDialogProductDescription, setOrderDialogProductDescription] = useState("")

    function timerOrderWindow() {
        setTimeout(() => {
            setOrderDialog(false)
        }, 3000)
    }


    return (
        <>
            {globalError ? <GlobalError/> : <main className="home-page">
                {roles.find((role) => role === 'admin')
                    ?
                    <NavBarAdmin/>
                    :
                    <NavBar/>
                }
                {roles.find((role) => role === 'user') && orderWindow ? <OrderWindow/> : <></>}
                {/*<Maintenance/>*/}
                {user.jwt !== null ? <ChatRoom/> : <></>}
                {orderDialog &&
                    <div className="home-page-order-dialog">
                        <h4>{t('products-users.choice')}</h4>
                        <h5>{t('products-users.name')} {orderDialogProductName}</h5>
                        <p>{t('products-users.price')} {orderDialogProductDescription} {t('products-users.currency')}</p>
                    </div>
                }
                {showScrollingElement1 &&
                    <div className="home-page-scrolling-element-1">
                        {products ? (
                            <div className="home-page-scrolling-element-1-container">
                                <p className="home-page-scrolling-element-1-name">{products[0].name}</p>
                                <img className="home-page-scrolling-element-1-img"
                                     src={products[0].imageUrl} alt="img"
                                />
                            </div>
                        ) : (
                            <></>
                        )}
                    </div>
                }
                {showScrollingElement2 &&
                    <div className="home-page-scrolling-element-1">
                        {products ? (
                            <div className="home-page-scrolling-element-1-container">
                                <p className="home-page-scrolling-element-1-name">{products[1].name}</p>
                                <img className="home-page-scrolling-element-1-img"
                                     src={products[1].imageUrl} alt="img"
                                />
                            </div>
                        ) : (
                            <></>
                        )}
                    </div>
                }
                {showScrollingElement3 &&
                    <div className="home-page-scrolling-element-1">
                        {products ? (
                            <div className="home-page-scrolling-element-1-container">
                                <p className="home-page-scrolling-element-1-name">{products[2].name}</p>
                                <img className="home-page-scrolling-element-1-img"
                                     src={products[2].imageUrl} alt="img"
                                />
                            </div>
                        ) : (
                            <></>
                        )}
                    </div>
                }
                {showScrollingElement4 &&
                    <div className="home-page-scrolling-element-1">
                        {products ? (
                            <div className="home-page-scrolling-element-1-container">
                                <p className="home-page-scrolling-element-1-name">{products[3].name}</p>
                                <img className="home-page-scrolling-element-1-img"
                                     src={products[3].imageUrl} alt="img"
                                />
                            </div>
                        ) : (
                            <></>
                        )}
                    </div>
                }
                {roles.find((role) => role === 'admin') ? <OrderWindowAdmin/> : <></>}
                {dialogVisible && (
                    <div className="search-result">
                        <button className="search-result-close-button"
                                onClick={closeDialog}>{t('search-result-close-button')}</button>
                        {searchResult !== null ? (
                            <div className="search-result-container">
                                {searchResult.map((product) => (
                                    <div id={product.id}
                                         key={product.id}
                                    >
                                        <img className="search-result-container-img"
                                             src={product.imageUrl} alt="new"/>
                                        <h5>{product.name}</h5>
                                        <p className="search-result-container-price">{t('homepage-price')} {product.price.toFixed(2)} {t('homepage-currency')}</p>
                                        <p className="search-result-container-description">{product.description}</p>
                                        <button
                                            className="product-details-selected-product-button"
                                            id="submit"
                                            type="button"
                                            onClick={() => orderProducts(product.id)}
                                        >
                                            {t('product-details-selected-product-button')}
                                        </button>
                                    </div>
                                ))}
                            </div>
                        ) : (
                            <div className="search-no-result">{t('home-page-search.search-no-result')}</div>
                        )}
                    </div>
                )}
                <section className="home-page-event-search">
                    <div className="home-page-event">
                        {showEvent ?
                            <div>
                                <h5>{t('search-result.home-page-event')}</h5>
                                <div>
                                    <h6>{t('search-result.h6')}</h6>
                                    <p>{timeRemaining.hours.toString().padStart(2, '0')}:
                                        {timeRemaining.minutes.toString().padStart(2, '0')}:
                                        {timeRemaining.seconds.toString().padStart(2, '0')}</p>
                                </div>
                            </div>
                            :
                            <div className="home-page-event-search-div">
                                {t('search-result.div')}
                            </div>
                        }
                    </div>
                    <div className="home-page-search">
                        <h5>{t('home-page-search.h5')}</h5>
                        <select
                            id="search-product"
                            name="search-product"
                            placeholder="Select product"
                            value={selectOptions}
                            onChange={(e) => setSelectOptions(e.target.value)}

                        >
                            <option value="">{t('home-page-search.option1')}</option>
                            <option value="pie">{t('home-page-search.option2')}</option>
                            <option value="sweets">{t('home-page-search.option3')}</option>
                            <option value="buns">{t('home-page-search.option4')}</option>
                            <option value="cake">{t('home-page-search.option5')}</option>
                        </select>
                        <button onClick={getSearchResult}>{t('home-page-search.button')}</button>
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
                        <h6>{t('homepage-first-right')}</h6>
                        <h1>{t('description.h1')}</h1>
                        <p>{t('description.p1')}</p>
                        <p>{t('description.p2')}</p>
                        <p>{t('description.p3')}</p>
                        <p>{t('description.p4')}</p>
                    </div>
                </section>
                <section className="home-page-most-ordered">
                    <h4 className="home-page-most-ordered-title">{t('home-page-most-ordered.home-page-most-ordered-title')}</h4>
                    <p className="home-page-most-ordered-description">{t('home-page-most-ordered.home-page-most-ordered-description')}</p>
                </section>
                {products ? (
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
                                                    <p>Цена: {currentProduct.price.toFixed(2)} лв.</p>
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
                                >{t('homepage-price')} {product.price.toFixed(2)} {t('homepage-currency')}</p>
                            </div>
                        ))}
                    </article>

                ) : (
                    <></>
                )}
                <h4 className="home-page-most-ordered-title">{t('home-page-most-ordered-title-2')}</h4>
                {recommendedProducts ? (
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
                                                    <p>Цена: {currentProduct.price.toFixed(2)} лв.</p>
                                                    <p>{currentProduct.description}</p>
                                                    <button
                                                        className="product-details-selected-product-button"
                                                        id="submit"
                                                        type="button"
                                                        onClick={() => orderProducts(currentProduct.id)}
                                                    >{t('product-details-selected-product-button')}
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
                                >{t('homepage-price')} {recommendedProduct.price.toFixed(2)} {t('homepage-currency')}</p>
                            </div>
                        ))}
                    </article>
                ) : (
                    <></>
                )}
                <Footer/>
            </main>}
        </>
    );
};

export default Homepage;