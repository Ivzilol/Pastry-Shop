import {useUser} from "../../UserProvider/UserProvider";
import {useEffect, useState} from "react";
import ajax from "../../Services/FetchService";
import NavBarAdmin from "../NavBarAdmin/NavBarAdmin";

const AdminOrdersHistory = () => {

    const user = useUser();
    const [orders, setOrders] = useState(null);
    const baseUrl = "http://localhost:8080/";

    useEffect(() => {
        ajax(`${baseUrl}api/orders/history/admin`, "GET", user.jwt)
            .then(ordersData => {
                setOrders(ordersData);
            });
    }, [user.jwt]);

    return (
        <section className="ordersUser">
            <NavBarAdmin/>
            <h3 className="admin-users-container-title">История на поръчките</h3>
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
                                    <td>{order.user.username}</td>
                                </tr>
                            ))}
                        </>
                    ) : (
                        <></>
                    )}
                </table>
            </div>
        </section>
    )
}

export default AdminOrdersHistory;