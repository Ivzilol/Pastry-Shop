import {useUser} from "../../UserProvider/UserProvider";
import {useEffect, useState} from "react";
import ajax from "../../Services/FetchService";
import {useNavigate} from "react-router-dom";
import NavBar from "../NavBar/NavBar";
import button from "bootstrap/js/src/button";

const AdminOrders = () => {
    const user = useUser();
    const [orders, setOrders] = useState(null);
    let navigate = useNavigate();
    let currentKeyOrder;
    const [sentOrders, setSendOrders] = useState(null);


    useEffect(() => {
        ajax('/api/orders/admin', "GET", user.jwt)
            .then(ordersData => {
                setOrders(ordersData)
            });
        if (!user.jwt) navigate('/login')
    }, [user.jwt])

    function refreshPage() {
        window.location.reload();
    }


    function startProcessingOrder(id) {
        ajax(`/api/orders/admin/${id}`, "POST", user.jwt, {
            id: id
        }).then(() =>
            confirmOrder(id)
        )
            .then(() => {
                refreshPage()
            })
    }

    useEffect(() => {
        ajax("/api/orders/admin/send", "GET", user.jwt)
            .then(ordersData => {
                setSendOrders(ordersData);
            });
        if (!user.jwt) navigate("/login")
    }, [user.jwt])


    function confirmOrder(id) {
        ajax(`/api/orders/${id}`, "PATCH", user.jwt, {
            status: "sent"
        })
            .then(() =>
                refreshPage())
    }

    function confirmOrderDelivery(id) {
        ajax(`/api/orders/admin/delivery/${id}`, "PATCH", user.jwt, {
            status: "delivery"
        })
            .then(() => {
                refreshPage()
            })
    }

    return (
        <main className="orders-admin">
            <NavBar/>
            <section className="admin-bord-section">
                <article className="unconfirmed-orders">
                    <h2>Проъчки в процес на приготвяне</h2>
                    <ul className="unconfirmed-orders-list">
                        {orders ? (
                            <div className="keyOrder"
                                 key={orders.keyOrderProduct}>
                                {orders.map((order) => (
                                    currentKeyOrder !== order.keyOrderProduct
                                        ?
                                        <div className="admin-bord-section-details"
                                             id={order.keyOrderProduct}
                                        >
                                            <div className="orderKey">
                                                {currentKeyOrder = order.keyOrderProduct}
                                            </div>
                                            <h5>Username: {order.users.username}</h5>
                                            <p>Адрес: {order.users.address}</p>
                                            {orders ? (
                                                    <div className="orders-details-product"
                                                         key={orders.keyOrderProduct}
                                                    >
                                                        {orders.map((orderDetails) =>
                                                            order.keyOrderProduct === orderDetails.keyOrderProduct
                                                                ?
                                                                <div>
                                                                    <p>Продукт: {orderDetails.productName}</p>
                                                                    <p>Цена: {orderDetails.price} лв.</p>
                                                                </div>
                                                                :
                                                                <></>
                                                        )}
                                                    </div>
                                                ) :
                                                <></>
                                            }
                                            <button className="orders-admin-button"
                                                    onClick={() => startProcessingOrder(order.keyOrderProduct)}
                                            >Изпрати поръчката
                                            </button>
                                            <hr className="orders-admin-line"/>
                                        </div>
                                        :
                                        <></>
                                ))}
                            </div>

                        ) : (
                            <>No Orders</>
                        )}
                    </ul>
                </article>
                <article className="confirmed-orders">
                    <h2>Поръчки в процес на доставка</h2>
                    <ul className="confirmed-orders-list">
                        {sentOrders ? (
                            <div className="confirmed-orders-list-container"
                                 id={sentOrders.id}>
                                {sentOrders.map((sendOrders) => (
                                    <div className="confirmed-orders-list-container-details"
                                         id={sendOrders.id}
                                    >
                                        <h5>Получател: {sendOrders.user.firstName} {sendOrders.user.lastName}</h5>
                                        <p>Адрес: {sendOrders.user.address}</p>
                                        <p>Обща цена: {Number(sendOrders.totalPrice).toFixed(2)} лв.</p>
                                        <button className="orders-admin-button"
                                                onClick={() => confirmOrderDelivery(sendOrders.id)}
                                        > Потвърди доставката
                                        </button>
                                        <hr className="orders-admin-line"/>
                                    </div>
                                ))}

                            </div>
                        ) : (
                            <></>
                        )}
                    </ul>
                </article>
            </section>
        </main>
    )
}
export default AdminOrders;