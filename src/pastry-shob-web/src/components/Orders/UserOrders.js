import {useUser} from "../../UserProvider/UserProvider";
import React, {useEffect, useState} from "react";
import ajax from "../../Services/FetchService";
import {useNavigate} from "react-router-dom";
import NavBar from "../NavBar/NavBar";
import {useTranslation} from "react-i18next";

const UserOrders = () => {
    const user = useUser();
    let navigate = useNavigate();
    const [products, setProducts] = useState(null);
    const now = new Date();
    const hours = now.getHours();
    let promotion = false;
    if (hours >= 14 && hours < 21) {
        promotion = true;
    }
    let allPrice = 0;

    const {t} = useTranslation();
    const baseUrl = "http://localhost:8080/";

    useEffect(() => {
        ajax(`${baseUrl}api/orders`, "GET", user.jwt)
            .then(productsData => {
                setProducts(productsData);
            });
        if (!user.jwt) navigate('/login')
    }, [navigate, user.jwt]);

    function removeProductFromOrder(id) {
        ajax(`${baseUrl}api/orders/${id}`, "DELETE", user.jwt)
            .then(() => {
                refreshPage()
            })
    }

    function refreshPage() {
        window.location.reload();
    }

    function confirmOrder() {
        ajax(`${baseUrl}api/orders`, "PATCH", user.jwt, {
            status: "confirmed"
        })
            .then(() =>
                refreshPage())
        alert("Successful confirm your order");
    }


    return (
        <main className="orders-user">
            <NavBar/>
            <h2 className="orders-user-title">{t('orders-user.title')}</h2>
            <hr className="orders-user-line"/>
            {products ? (
                <article className="orders-container">
                    {products.map((product) => (

                        <div className="orders-container-items"
                             key={product.id}
                        >
                            <p className="orders-container-items-name">
                                {t('orders-user.name')} {product.productName}
                            </p>
                            <p className="orders-container-items-name">
                                {t('products-users.price')} {product.price} {t('products-users.currency')}
                            </p>
                            {product.status === 'newOrder'
                                ?
                                <button className="orders-container-items-button"
                                        onClick={() => removeProductFromOrder(product.id)
                                        }
                                >{t('orders-user.close-product-button')}
                                </button>
                                :
                                <></>
                            }
                            <p className="getAllPrice">{
                                promotion
                                    ?
                                    allPrice += product.price - (product.price * 0.20)
                                    :
                                    allPrice += product.price
                            }
                            </p>
                        </div>
                    ))}
                </article>
            ) : (
                <></>
            )}
            <section>
                {allPrice > 0 ? (
                    <div className="orders-user-price">
                        <h5 className="orders-user-title"
                        >{t('orders-user.all-price')} {allPrice.toFixed(2)} {t('products-users.currency')}</h5>
                        <button
                            className="orders-container-items-button-confirm"
                            onClick={() => confirmOrder()}
                        >{t('orders-user.confirm-button')}
                        </button>
                    </div>
                ) : (
                    <div className="order-user-tracker">
                        <h5 className="order-user-tracker-title">
                            {t('orders-user.h5')}
                        </h5>
                        <button
                            className="order-user-tracker-button"
                            onClick={() => window.location.href = "/orders/tracking"}
                        >{t('orders-user.tracking-button')}
                        </button>
                        <button
                            className="order-user-tracker-button"
                            onClick={() => window.location.href = "/orders/history/user"}
                        >{t('orders-user.history-button')}
                        </button>
                    </div>
                )}
            </section>
        </main>
    )
}

export default UserOrders;