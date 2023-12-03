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
    const [emptyDate, setEmptyDate] = useState(false);
    const [currentUser, setCurrentUser] = useState("");
    const [emptyUsername, setEmptyUsername] = useState(false);
    const [invalidUsername, setInvalidUsername] = useState(false);

    useEffect(() => {
        const interval = setInterval(() => {
            ajax(`${baseURL}api/monitoring`, "GET", user.jwt)
                .then((response) => {
                    setNumberSearch(response)
                });
        }, 10000)
        return () => clearInterval(interval);
    }, []);


    function getOrdersByDate() {
        if (startDate === '' || endDate === '' || startDate > endDate) {
            setEmptyDate(true)
            return
        }
        setOrders(null)
        ajax(`${baseURL}api/orders-processing/admin/date?startDate=${startDate}&endDate=${endDate}`, "GET", user.jwt)
            .then((ordersData) => {
                setOrders(ordersData);
                setEmptyDate(false)
            })
    }

    function getOrdersByUser() {
        if (currentUser === '') {
            setEmptyUsername(true)
            return
        }
        setOrders(null)
        ajax(`${baseURL}api/orders-processing/admin/user?currentUser=${currentUser}`, "GET", user.jwt)
            .then((response) => {
                if (response.length === 0) {
                    setInvalidUsername(true)
                    return
                }
                setOrders(response);
            })
    }

    const sendWithEnter = (e) => {
        if (e.keyCode === 13) {
            getOrdersByUser()
        }
    }


    return (
        <main className="monitoring">
            <NavBarAdmin/>
            <h3 className="admin-users-container-title">Използвания на търсачката: {numberSearch}</h3>
            <h3 className="admin-users-container-title">История на поръчките</h3>
            <div className="monitoring-container">
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
                    {emptyDate && <p className="copied">Please put correct date!</p>}
                </section>
                <section className="monitoring-date-user">
                    <label form="username">Username</label>
                    <input
                        type="text"
                        id="username"
                        name="username"
                        placeholder="  Username"
                        value={currentUser}
                        onChange={(e) => setCurrentUser(e.target.value)}
                        onKeyDown={(e) => sendWithEnter(e)}
                    />
                    {emptyUsername && <p className="copied">Please put correct username</p>}
                    {invalidUsername && <p className="copied">User with this username does not exist</p>}
                </section>
            </div>
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
                            <tbody>
                            {orders.map(order => (
                                <tr id={order.id}>
                                    <td>{order.dateOfDispatch}</td>
                                    <td>{order.dateOfReceipt}</td>
                                    <td>{Number(order.totalPrice).toFixed(2)}</td>
                                    <td>{order.statusOrder}</td>
                                    <td>{order.username}</td>
                                </tr>
                            ))}
                            </tbody>
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