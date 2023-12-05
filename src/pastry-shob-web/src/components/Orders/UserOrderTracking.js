import {useUser} from "../../UserProvider/UserProvider";
import React, {useEffect, useState} from "react";
import NavBar from "../NavBar/NavBar";
import ajax from "../../Services/FetchService";
import {useTranslation} from "react-i18next";
import baseURL from "../BaseURL/BaseURL";

const UserOrderTracking = () => {

    const user = useUser();
    const [order, setOrder] = useState(null);
    const [status, setStatus] = useState(null)
    const [sendStatus, setSendStatus] = useState(null);
    let currentKeyOrder;
    const {t} = useTranslation();

    useEffect(() => {
        ajax(`${baseURL}api/orders/tracking`, "GET", user.jwt)
            .then(orderData => {
                setOrder(orderData)
                if (orderData.length > 0) {
                    // eslint-disable-next-line array-callback-return
                    orderData.map((order) => {
                        setStatus(order.status)
                        if (order.status === 'sent') {
                            setSendStatus(order.status)
                        }
                    })
                }
            });
    }, [user.jwt]);

    return (
        <main>
            <NavBar/>
            <section className="tracking-main-section">
                <section className="tracking-in-processing">
                    {order ? (
                        <article
                            id={order.keyOrderProduct}
                            className="tracking-in-processing-order">
                            <h4 className="tracking-in-processing-title">{t('tracking-main-section.process-order')}</h4>
                            {order.map((currentOrder) => (
                                currentOrder.status === 'confirmed' && currentKeyOrder !== currentOrder.keyOrderProduct
                                    ?
                                    <div
                                        className="tracking-in-processing-order-items"
                                        key={currentOrder.id}
                                        id={currentOrder.keyOrderProduct}
                                    >
                                        <div className="orderKey">
                                            {currentKeyOrder = currentOrder.keyOrderProduct}
                                        </div>
                                        {order ? (
                                            <div key={order.id}>
                                                {order.map((orderDetails) =>
                                                    currentOrder.keyOrderProduct === orderDetails.keyOrderProduct && currentOrder.id !== orderDetails.id
                                                        ?
                                                        <div key={orderDetails.keyOrderProduct}>
                                                            <p>{orderDetails.productName}</p>
                                                            <p>{orderDetails.price.toFixed(2)} {t('products-users.currency')}</p>
                                                        </div>
                                                        :
                                                        <></>
                                                )}
                                            </div>
                                        ) : <></>}
                                        <p>{currentOrder.productName}</p>
                                        <p>{currentOrder.price.toFixed(2)} {t('products-users.currency')}</p>
                                        <p>
                                            <strong>{t('tracking-main-section.all-price')} {currentOrder.totalPrice.toFixed(2)} {t('products-users.currency')}</strong>
                                        </p>
                                    </div>
                                    :
                                    <></>
                            ))}
                            {status === 'confirmed'
                                ?
                                <></>
                                :
                                <h4 className="no-orders-processing">{t('no-orders-processing')}</h4>
                            }
                        </article>
                    ) : (
                        <div></div>
                    )}
                </section>
                <section className="tracking-in-send">
                    {order ? (
                        <article
                            id={order.keyOrderProduct}
                            className="tracking-in-send-order">
                            <h4 className="tracking-in-processing-title">{t('tracking-main-section.process-delivery')}</h4>
                            {order.map((currentOrder) => (
                                currentOrder.status === 'sent' && currentKeyOrder !== currentOrder.keyOrderProduct
                                    ?
                                    <div className="tracking-in-send-order-items"
                                         key={currentOrder.id}
                                         id={currentOrder.keyOrderProduct}
                                    >
                                        <div className="orderKey">
                                            {currentKeyOrder = currentOrder.keyOrderProduct}
                                        </div>
                                        {order ? (
                                                <div key={order.id}>
                                                    {order.map((orderSend) =>
                                                        currentOrder.keyOrderProduct === orderSend.keyOrderProduct
                                                            ?
                                                            <div>
                                                                <p>{orderSend.productName}</p>
                                                                <p> {orderSend.price.toFixed(2)} {t('products-users.currency')}</p>
                                                            </div>
                                                            :
                                                            <></>
                                                    )}
                                                </div>
                                            ) :
                                            <></>
                                        }
                                        <p>{t('tracking-main-section.data')} {currentOrder.dateOfDelivery}</p>
                                        <p>{t('tracking-main-section.time')} {currentOrder.timeOfDelivery} {t('tracking-main-section.hour')}</p>
                                        <p>
                                            <strong>{t('tracking-main-section.all-price')} {currentOrder.totalPrice.toFixed(2)} {t('products-users.currency')}</strong>
                                        </p>
                                    </div>
                                    :
                                    <></>
                            ))}
                            {sendStatus === 'sent'
                                ?
                                <></>
                                :
                                <h4 className="no-orders-delivery">{t('no-orders-delivery')}</h4>
                            }
                        </article>
                    ) : (
                        <></>
                    )}
                </section>
            </section>
        </main>
    )
}

export default UserOrderTracking;