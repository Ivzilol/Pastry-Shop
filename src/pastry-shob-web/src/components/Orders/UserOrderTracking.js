import {useUser} from "../../UserProvider/UserProvider";
import {useEffect, useState} from "react";
import NavBar from "../NavBar/NavBar";
import ajax from "../../Services/FetchService";

const UserOrderTracking = () => {

    const user = useUser();
    const [order, setOrder] = useState(null);

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
                            <h4>Вашата поръчка се приготвя</h4>
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
                                    </div>
                                    :
                                    <></>
                            ))}
                        </article>
                    ) : (
                        <></>
                    )}
                </section>
                <section className="tracking-in-send">
                    {order ? (
                        <article
                            id={order.keyOrderProduct}
                            className="tracking-in-send-order">
                            <h4>Вапата поръчка в процес на доставка</h4>
                            {order.map((currentOrder) => (
                                currentOrder.status === 'sent'
                                    ?
                                    <div className="tracking-in-send-order-items"
                                         key={currentOrder.id}
                                         id={currentOrder.keyOrderProduct}
                                    >
                                        <p>{currentOrder.productName}</p>
                                    </div>
                                    :
                                    <></>
                            ))}
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