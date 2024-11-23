import {useUser} from "../../UserProvider/UserProvider";
import React, {useEffect, useState} from "react";
import ajax from "../../Services/FetchService";
import {useNavigate} from "react-router-dom";
import NavBarAdmin from "../NavBarAdmin/NavBarAdmin";
import baseURL from "../BaseURL/BaseURL";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faInfoCircle} from "@fortawesome/free-solid-svg-icons";

const AdminOrders = () => {
    const user = useUser();
    const [orders, setOrders] = useState(null);
    let navigate = useNavigate();
    let currentKeyOrder;
    const [sentOrders, setSendOrders] = useState(null);
    const [dateDelivery, setDateDelivery] = useState("");
    const [timeDelivery, setTimeDelivery] = useState("");
    const [errorDataTime, setErrorDataTime] = useState(false);

    useEffect(() => {
        ajax(`${baseURL}api/orders/admin`, "GET", user.jwt)
            .then(ordersData => {
                setOrders(ordersData)
            });
        if (!user.jwt) navigate('/login')
    }, [navigate, user.jwt])

    function refreshPage() {
        window.location.reload();
    }


    function startProcessingOrder(id) {
        ajax(`${baseURL}api/orders-processing/admin/${id}`, "POST", user.jwt, {
            id: id,
        })
            .then((response) => {
                if (response.custom === 'Successful start processing order') {
                    refreshPage()
                }
            })
    }

    useEffect(() => {
        ajax(`${baseURL}api/orders/admin/send`, "GET", user.jwt)
            .then(ordersData => {
                setSendOrders(ordersData);
            });
        if (!user.jwt) navigate("/login")
    }, [navigate, user.jwt])


    function confirmOrder(id) {
        if (dateDelivery === "" || timeDelivery === "") {
            alert("Put data and time on delivery")
            return;
        }
        const requestBody = {
            status: "sent",
            dateDelivery: dateDelivery,
            timeDelivery: timeDelivery
        }
        fetch(`${baseURL}api/orders/${id}`, {
            headers: {
                "Content-Type": "application/json",
            },
            method: "PATCH",
            body: JSON.stringify(requestBody)

        })
            .then((response) => response.json())
            .then(data => {
                if (data.custom === 'Order send') {
                    startProcessingOrder(id)
                } else {
                    setError(data)
                    setErrorDataTime(true);
                }
            })
    }

    const [error, setError] = useState({
        custom: ''
    })

    function confirmOrderDelivery(id) {
        ajax(`${baseURL}api/orders/admin/delivery/${id}`, "PATCH", user.jwt, {
            status: "delivery"
        })
            .then(() => {
                refreshPage()
            })
    }

    return (
        <main className="orders-admin">
            <NavBarAdmin/>
            <button
                className="order-users-tracker-button"
                onClick={() => window.location.href = "/orders/history/user"}
            >История на поръчките
            </button>
            <button
                className="order-users-tracker-button"
                onClick={() => window.location.href = "/monitoring"}
            >Мониторинг
            </button>
            <section className="admin-bord-section">
                <article className="unconfirmed-orders">
                    <h2>Поръчки в процес на приготвяне</h2>
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
                                                         id={orders.keyOrderProduct}
                                                    >
                                                        {orders.map((orderDetails) =>
                                                            order.keyOrderProduct === orderDetails.keyOrderProduct
                                                                ?
                                                                <div id={order.keyOrderProduct}>
                                                                    <p>Продукт: {orderDetails.productName}</p>
                                                                    <p>Цена: {orderDetails.price.toFixed(2)} лв.</p>
                                                                </div>
                                                                :
                                                                <></>
                                                        )}
                                                    </div>
                                                ) :
                                                <></>
                                            }
                                            <div className="order-admin-input">
                                                <label form="date-delivery">Добави дата на доставка</label>
                                                <input
                                                    type="date"
                                                    id="date-delivery"
                                                    name="date-delivery"
                                                    value={dateDelivery}
                                                    onChange={(e) => setDateDelivery(e.target.value)}
                                                />
                                                <label form="time-delivery">Добави час на доставка</label>
                                                <input
                                                    type="time"
                                                    id="time-delivery"
                                                    name="time-delivery"
                                                    value={timeDelivery}
                                                    onChange={(e) => setTimeDelivery(e.target.value)}
                                                />
                                                <button className="orders-admin-button"
                                                        onClick={() => {
                                                            confirmOrder(order.keyOrderProduct)
                                                        }}
                                                >Изпрати поръчката
                                                </button>
                                                {errorDataTime && error.custom !== null &&
                                                    <span id="validate-username"> <FontAwesomeIcon
                                                        icon={faInfoCircle}/> {error.custom}</span>
                                                }
                                            </div>
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
                                        <h5>Получател: {sendOrders.firstName} {sendOrders.lastName}</h5>
                                        <p>Адрес: {sendOrders.address}</p>
                                        <p>Обща цена: {Number(sendOrders.totalPrice).toFixed(2)} лв.</p>
                                        <div className="confirmed-orders-list-container-details-paid">
                                            <p>
                                                Платена:
                                            </p>
                                            {
                                                sendOrders.paid === false
                                                    ?
                                                    <p className="confirmed-orders-list-container-details-paid-no">NO</p>
                                                    :
                                                    <p className="confirmed-orders-list-container-details-paid-yes">YES</p>
                                            }
                                        </div>
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