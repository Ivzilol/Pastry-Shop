import {useUser} from "../../UserProvider/UserProvider";
import ajax from "../../Services/FetchService";
import baseURL from "../BaseURL/BaseURL";
import React, {useEffect, useState} from "react";
import NavBarAdmin from "../NavBarAdmin/NavBarAdmin";


const Monitoring = () => {

    const user = useUser();
    const [startDate, setStartDate] = useState("");
    const [endDate, setEndDate] = useState("");
    const [orders, setOrders] = useState(null);
    const [numberSearch, setNumberSearch] = useState(null);

    useEffect(() => {
        const interval = setInterval(() => {
            ajax(`${baseURL}api/monitoring`, "GET", user.jwt)
                .then((response) => {
                    console.log(response)
                    setNumberSearch(response)
                });
        }, 10000)
        return () => clearInterval(interval);
    },[]);

    function getOrdersByDate() {
        ajax(`${baseURL}api/orders-processing/admin/date?startDate=${startDate}&endDate=${endDate}`, "GET", user.jwt)
            .then((ordersData) => {
                setOrders(ordersData);
            })
    }



    return (
        <main className="monitoring">
            <NavBarAdmin/>
            <h3 className="admin-users-container-title">Брой на търсения на продукти: {numberSearch}</h3>
            <h3 className="admin-users-container-title">История на поръчките</h3>
            <section className="monitoring-date">
                <article className="monitoring-date-start-date">
                    <label form="date-delivery">Начална дата:</label>
                    <input
                        type="date"
                        id="date-start"
                        name="date-start"
                        value={startDate}
                        onChange={(e) => setStartDate(e.target.value)}
                    />
                </article>
                <article className="monitoring-date-end-date">
                    <label form="date-delivery">Крайна дата:</label>
                    <input
                        type="date"
                        id="date-end"
                        name="date-end"
                        value={endDate}
                        onChange={(e) => setEndDate(e.target.value)}
                    />
                </article>
                <button
                    className="monitoring-date-button"
                    type="submit"
                    id="button"
                    onClick={() => getOrdersByDate()}
                >
                    Покажи поръчките
                </button>
            </section>
            <section className="ordersUser-date">
                <NavBarAdmin/>
                <hr/>
                <div className="admin-users-container-header">
                    <table className="admin-users-table">
                        <tr>
                            <th>Data ot Dispatch</th>
                            <th>Data of Recipe</th>
                            <th>Total Price</th>
                            <th>Status</th>
                            <th>User</th>
                        </tr>
                        {orders ? (
                            <>
                                {orders.map(order => (
                                    <tr id={order.id}>
                                        <td>{order.dateOfDispatch}</td>
                                        <td>{order.dateOfReceipt}</td>
                                        <td>{Number(order.totalPrice).toFixed(2)}</td>
                                        <td>{order.statusOrder}</td>
                                        <td>{order.username}</td>
                                    </tr>
                                ))}
                            </>
                        ) : (
                            <></>
                        )}
                    </table>
                </div>
            </section>
        </main>
    )
}

export default Monitoring;