import {useUser} from "../../UserProvider/UserProvider";
import {useEffect, useState} from "react";
import NavBar from "../NavBar/NavBar";
import ajax from "../../Services/FetchService";

const UserOrderTracking = () => {

    const user = useUser();
    const [order, setOrder] = useState(null);
    let allPriceProcessing = 0;
    let allPriceSend = 0;

    useEffect(() => {
        ajax("/api/orders/tracking", "GET", user.jwt)
            .then(orderData => {
                setOrder(orderData)
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
                            <h4>В процес на приготвяне</h4>
                            {order.map((currentOrder) => (
                                currentOrder.status === 'confirmed'
                                    ?
                                    <div
                                        className="tracking-in-processing-order-items"
                                        key={currentOrder.id}
                                        id={currentOrder.keyOrderProduct}
                                    >
                                        <p>{currentOrder.productName}</p>
                                        <p>{currentOrder.price} лв.</p>
                                        <p>Oт дата: {currentOrder.dateCreated}</p>
                                        <p className="getAllPrice">{allPriceProcessing += currentOrder.price}</p>
                                    </div>
                                    :
                                    <>-------------</>
                            ))}
                            <h4>Обща цена: {allPriceProcessing.toFixed(2)} лв.</h4>
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
                            <h4>В процес на доставка</h4>
                            {order.map((currentOrder) => (
                                currentOrder.status === 'sent'
                                    ?
                                    <div className="tracking-in-send-order-items"
                                         key={currentOrder.id}
                                         id={currentOrder.keyOrderProduct}
                                    >
                                        <p>{currentOrder.productName}</p>
                                        <p>{currentOrder.price}</p>
                                        <p>Дата на доставка: {currentOrder.dateOfDelivery}</p>
                                        <p>Очаквайте вашата доставка около: {currentOrder.timeOfDelivery} часа</p>
                                        <p className="getAllPrice">{allPriceSend += currentOrder.price}</p>
                                    </div>
                                    :
                                    <>-------------</>
                            ))}
                            <h4>Обща цена: {allPriceSend.toFixed(2)} лв.</h4>
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