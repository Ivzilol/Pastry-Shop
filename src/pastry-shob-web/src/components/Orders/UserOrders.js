import {useUser} from "../../UserProvider/UserProvider";
import React, {useEffect, useState} from "react";
import ajax from "../../Services/FetchService";
import {useNavigate} from "react-router-dom";
import NavBar from "../NavBar/NavBar";
import {useTranslation} from "react-i18next";
import baseURL from "../BaseURL/BaseURL";
import Loading from "../Loading/Loading";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faInfoCircle} from "@fortawesome/free-solid-svg-icons";

const UserOrders = () => {
    const user = useUser();
    let navigate = useNavigate();
    const [products, setProducts] = useState(null);
    const [dialogVisible, setDialogVisible] = useState(false);
    const [confirmOrderMessage, setConfirmOrderMessage] = useState("");
    const [promoCode, setPromoCode] = useState("");
    const [errorPromoCode, setErrorPromoCode] = useState(false);
    const now = new Date();
    const hours = now.getHours();
    let promotion = false;
    if (hours >= 14 && hours < 21) {
        promotion = true;
    }
    let allPrice = 0;
    let discount = allPrice - (allPrice * 0.20);

    const {t} = useTranslation();
    const [isLoading, setIsLoading] = useState(false);

    useEffect(() => {
        ajax(`${baseURL}api/orders`, "GET", user.jwt)
            .then(productsData => {
                setProducts(productsData);
            });
        if (!user.jwt) navigate('/login')
    }, [navigate, user.jwt]);

    function removeProductFromOrder(id) {
        ajax(`${baseURL}api/orders/${id}`, "DELETE", user.jwt)
            .then(() => {
                refreshPage()
            })
    }

    function refreshPage() {
        window.location.reload();
    }

    const statusAndPayment = {
        status: "confirmed",
        payment: "",
        promoCode: promoCode.trim()
    }

    function confirmPayment() {
        if (creditCardData.cardHolderName === '' || creditCardData.cardNumber === '') {
            alert("Please fill in Name and Card number")
            return;
        }
        statusAndPayment.payment = "payment_confirm";
        alert("Payment and order confirm")
        confirmOrder()
    }

    const [errorCode, setErrorCode] = useState({
        custom: null
    })

    function confirmOrder() {
        setIsLoading(true);
        ajax(`${baseURL}api/orders`, "PATCH", user.jwt, statusAndPayment)
            .then((response) => {
                if (response.custom === 'Confirm order') {
                    setIsLoading(false);
                    refreshPage()
                    setDialogVisible(true);
                } else {
                    setIsLoading(false);
                    setErrorCode(response.custom)
                    setErrorPromoCode(true);
                }
            })
    }

    const [paymentMethod, setPaymentMethod] = useState("delivery");
    const [creditCardData, setCreditCardData] = useState({
        cardNumber: "",
        cardHolderName: "",
    });

    const handlePaymentMethodChange = (e) => {
        setPaymentMethod(e.target.value);
    };

    const handleCreditCardChange = (e) => {
        const {name, value} = e.target;
        setCreditCardData({...creditCardData, [name]: value});
    };


    return (
        <>
            <NavBar/>
            {isLoading ?
                <Loading/>
                :
                <main className="orders-user">
                    {dialogVisible &&
                        <div className="home-page-order-dialog">
                            <h4>{confirmOrderMessage}</h4>
                        </div>
                    }
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
                                        {t('products-users.price')} {product.price.toFixed(2)} {t('products-users.currency')}
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
                                            allPrice += product.price
                                            :
                                            allPrice += product.price
                                    }
                                    </p>
                                    <p className="getAllPrice">{
                                        promotion
                                            ?
                                            discount += product.price - (product.price * 0.75)
                                            :
                                            discount += product.price
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
                                <input
                                    className="orders-user-input-promo-code-input"
                                    type="text"
                                    autoComplete="off"
                                    name="promoCode"
                                    placeholder="  Enter promo code"
                                    value={promoCode}
                                    onChange={(e) => setPromoCode(e.target.value)}
                                />
                                {errorPromoCode && errorCode.custom !== null &&
                                    <span id="validate-username"> <FontAwesomeIcon icon={faInfoCircle}/> {errorCode}</span>
                                }
                                {promotion
                                    ?
                                    <h5 className="orders-user-title"
                                    >{t('orders-user.discount')} {discount.toFixed(2)} {t('products-users.currency')}
                                    </h5>
                                    :
                                    <></>
                                }
                                <h5 className="orders-user-title"
                                >{t('orders-user.all-price')} {allPrice.toFixed(2)} {t('products-users.currency')}
                                </h5>
                                <div className="orders-user-form">
                                    <h3>{t('orders-user.method-pay')}</h3>
                                    <div>
                                        <label>
                                            <input
                                                type="radio"
                                                value="delivery"
                                                checked={paymentMethod === "delivery"}
                                                onChange={handlePaymentMethodChange}
                                            />
                                            {t('orders-user.pay-delivery')}
                                        </label>
                                    </div>
                                    <div>
                                        <label>
                                            <input
                                                type="radio"
                                                value="creditCard"
                                                checked={paymentMethod === "creditCard"}
                                                onChange={handlePaymentMethodChange}
                                            />
                                            {t('orders-user.pay-card')}
                                        </label>
                                    </div>

                                    {paymentMethod === "creditCard" && (
                                        <div className="orders-user-form-card">
                                            <h4>{t('orders-user.card-information')}</h4>
                                            <p>{t('orders-user.card-*')}</p>
                                            <input
                                                type="text"
                                                name="cardNumber"
                                                placeholder="  Credit card number"
                                                onChange={handleCreditCardChange}
                                                value={creditCardData.cardNumber}
                                            />
                                            <input
                                                type="text"
                                                name="cardHolderName"
                                                placeholder="Name of holder"
                                                onChange={handleCreditCardChange}
                                                value={creditCardData.cardHolderName}
                                            />
                                            <button
                                                type="submit"
                                                onClick={() => confirmPayment()}
                                            >{t('orders-user.pay-confirm')}</button>
                                        </div>
                                    )}
                                </div>
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
            }
        </>
    )
}

export default UserOrders;