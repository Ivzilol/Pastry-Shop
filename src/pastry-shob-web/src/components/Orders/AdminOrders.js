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
                    <h2>Orders in processing</h2>
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
                                            <p>Address: {order.users.address}</p>
                                            {orders ? (
                                                    <div className="orders-details-product"
                                                         key={orders.keyOrderProduct}
                                                    >
                                                        {orders.map((orderDetails) =>
                                                            order.keyOrderProduct === orderDetails.keyOrderProduct
                                                                ?
                                                                <div>
                                                                    <p>Product: {orderDetails.productName}</p>
                                                                    <p>Price: {orderDetails.price}</p>
                                                                </div>
                                                                :
                                                                <></>
                                                        )}
                                                    </div>
                                                ) :
                                                <></>
                                            }
                                            <button
                                                onClick={() => startProcessingOrder(order.keyOrderProduct)}
                                            >Send Order
                                            </button>
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
                    <h2>Orders in process of delivery</h2>
                    <ul className="confirmed-orders-list">
                        {sentOrders ? (
                            <div className="confirmed-orders-list-container"
                                id={sentOrders.id}>
                                {sentOrders.map((sendOrders) => (
                                    <div className="confirmed-orders-list-container-details"
                                        id={sendOrders.id}
                                    >
                                        <h5>Recipient: {sendOrders.user.firstName} {sendOrders.user.lastName}</h5>
                                        <p>Address: {sendOrders.user.address}</p>
                                        <p>Total Price: {Number(sendOrders.totalPrice).toFixed(2)} lv.</p>
                                        <button
                                        onClick={() => confirmOrderDelivery(sendOrders.id)}
                                        >Confirm Delivery</button>
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