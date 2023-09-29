import {useUser} from "../../UserProvider/UserProvider";
import {useEffect, useState} from "react";
import NavBar from "../NavBar/NavBar";
import ajax from "../../Services/FetchService";
import {useTranslation} from "react-i18next";

const UserOrderTracking = () => {

    const user = useUser();
    const [order, setOrder] = useState(null);
    const [status, setStatus] = useState(null)
    const now = new Date();
    const hours = now.getHours();
    let promotion = false;
    if (hours >= 14 && hours < 21) {
        promotion = true;
    }
    let allPriceProcessing = 0;
    let allPriceSend = 0;
    const {t} = useTranslation();
    const baseUrl = "http://localhost:8080/";

    useEffect(() => {
        ajax(`${baseUrl}api/orders/tracking`, "GET", user.jwt)
            .then(orderData => {
                setOrder(orderData)
                if (orderData.length > 0) {
                    // eslint-disable-next-line array-callback-return
                    orderData.map((order) => {
                        setStatus(order.status)
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
                            <h4>{t('tracking-main-section.process-order')}</h4>
                            {order.map((currentOrder) => (
                                currentOrder.status === 'confirmed'
                                    ?
                                    <div
                                        className="tracking-in-processing-order-items"
                                        key={currentOrder.id}
                                        id={currentOrder.keyOrderProduct}
                                    >
                                        <p>{currentOrder.productName}</p>
                                        <p>{currentOrder.price} {t('products-users.currency')}</p>
                                        <p>{t('tracking-main-section.data')} {currentOrder.dateCreated}</p>
                                        <p className="getAllPrice">{
                                            promotion
                                                ?
                                                allPriceProcessing += currentOrder.price - (currentOrder.price * 0.20)
                                                :
                                                allPriceProcessing += currentOrder.price
                                        }</p>
                                    </div>
                                    :
                                    <></>
                            ))}
                            {order.length > 0 && status === 'confirmed'
                                ?
                                <h4>{t('tracking-main-section.all-price')} {allPriceProcessing.toFixed(2)} {t('products-users.currency')}</h4>
                                :
                                <h4>Вие нямате поръчки в процес на приготвяне</h4>
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
                            <h4>{t('tracking-main-section.process-delivery')}</h4>
                            {order.map((currentOrder) => (
                                currentOrder.status === 'sent'
                                    ?
                                    <div className="tracking-in-send-order-items"
                                         key={currentOrder.id}
                                         id={currentOrder.keyOrderProduct}
                                    >
                                        <p>{currentOrder.productName}</p>
                                        <p>{currentOrder.price} {t('products-users.currency')}</p>
                                        <p>{t('tracking-main-section.data')} {currentOrder.dateOfDelivery}</p>
                                        <p>{t('tracking-main-section.time')} {currentOrder.timeOfDelivery} {t('tracking-main-section.hour')}</p>
                                        <p className="getAllPrice">{
                                            promotion
                                                ?
                                                allPriceSend += currentOrder.price - (currentOrder.price * 0.20)
                                                :
                                                allPriceSend += currentOrder.price
                                        }</p>
                                    </div>
                                    :
                                    <></>
                            ))}
                            {status === 'sent'
                                ?
                                <h4>{t('tracking-main-section.all-price')} {allPriceSend.toFixed(2)} {t('products-users.currency')}</h4>
                                :
                                <h4>Вие нямате поръчка в процес на доставка</h4>
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