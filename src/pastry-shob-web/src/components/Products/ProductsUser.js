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
import {useTranslation} from "react-i18next";
import baseURL from "../BaseURL/BaseURL";
import GlobalError from "../GlobalError/GlobalError";


const ProductsUser = () => {
    const user = useUser()
    const [products, setProducts] = useState(null);
    let isEquals = false;
    const [roles, setRoles] = useState(getRolesFromJWT());
    let navigate = useNavigate();
    const {t} = useTranslation();
    const [orderWindow, setOrderWindow] = useState(false);
    const [globalError, setGlobalError] = useState(false);
    const [product, setProduct] = useState({
            name: "",
            price: null,
            categories: "",
            description: "",
            imageUrl: ""
        }
    );

    useEffect(() => {
        ajax(`${baseURL}api/products`, "GET", user.jwt)
            .then(productsData => {
                setProducts(productsData);
            });
        if (user.jwt) {
            ajax(`${baseURL}api/orders/status`, "GET", user.jwt)
                .then(result => {
                    if (result.length > 0) {
                        setOrderWindow(true);
                    }
                });
            ajax(`${baseURL}api/orders/status/confirmed`, "GET", user.jwt)
                .then(result => {
                    if (result.length > 0) {
                        setOrderWindow(true);
                    }
                });
        }

        if (!user.jwt) navigate("/login")
    }, [navigate, user.jwt]);

    function orderProduct(id) {
        ajax(`${baseURL}api/products/${id}`, "GET", user.jwt)
            .then(productData => {
                setProduct(productData);
                setOrderDialogProductName(productData.name)
                setOrderDialogProductDescription(productData.price)
                setOrderDialog(true);
                timerOrderWindow();
            });
    }

    function orderProducts(id) {
        ajax(`${baseURL}api/orders/${id}`, "POST", user.jwt, product)
            .then(productData => {
                setProduct(productData);
                setOrderWindow(true);
            })
            .catch(() => {
                setGlobalError(true)
            })
    }

    function refreshPage() {
        window.location.reload();
    }

    function likeProduct(id) {
        ajax(`${baseURL}api/products/${id}`, "PATCH", user.jwt)
            .then(() => {
                refreshPage();
            })
            .catch(() => {
                setGlobalError(true)
            })
    }

    useEffect(() => {
        setRoles(getRolesFromJWT())
        scrolling()
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
        ajax(`${baseURL}api/products/dislike/${id}`, "Delete", user.jwt)
            .then(() => {
                refreshPage();
            })
            .catch(() => {
                setGlobalError(true);
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

    function handleScroll() {
        localStorage.setItem('scrollPosition', window.scrollY);
    }


    useEffect(() => {
        window.addEventListener('scroll', handleScroll);
        return () => {
            window.removeEventListener('scroll', handleScroll);
        };
    }, []);


    function scrolling() {
        const scrollPosition = localStorage.getItem('scrollPosition');
        window.scrollTo(0, parseInt(scrollPosition));
    }

    useEffect(() => {
        scrolling()
    }, []);

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
            {globalError ? <GlobalError/> : <main className="products-users">
                <NavBar/>
                {orderWindow ? <OrderWindow/> : <></>}
                {orderDialog &&
                    <div className="home-page-order-dialog">
                        <h4>{t('products-users.choice')}</h4>
                        <h5>{t('products-users.name')} {orderDialogProductName}</h5>
                        <p>{t('products-users.price')} {orderDialogProductDescription} {t('products-users.currency')}</p>
                    </div>
                }
                {products ? (
                    <article className="products-container">
                        {products.map((product) => (
                            <div
                                className="products-container-items"
                                key={product.id}
                                id={product.id}
                            >
                                <p className="products-container-item"
                                >{t('products-users.name')} {product.name}</p>
                                <p className="products-container-item"
                                >{t('products-users.price')} {product.price} {t('products-users.currency')}</p>
                                <p className="products-container-item-description"
                                >{t('products-users.description')} {product.description}</p>
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
                                            >{t('products-users.no-like-button')}
                                            </button>
                                            :
                                            <button
                                                className="products-container-item-likes-button"
                                                id="submit"
                                                type="button"
                                                onClick={() => likeProduct(product.id)}
                                            >{t('products-users.like-button')}
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
                                            {t('products-users.order-button')}
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
            }
        </>
    )
}
export default ProductsUser;