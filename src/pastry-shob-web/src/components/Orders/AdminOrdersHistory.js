import {useUser} from "../../UserProvider/UserProvider";
import {useEffect, useState} from "react";
import ajax from "../../Services/FetchService";
import NavBarAdmin from "../NavBarAdmin/NavBarAdmin";
import baseURL from "../BaseURL/BaseURL";
import * as PropTypes from "prop-types";

function OrdersHistory(props) {
    return <section className="ordersUser">
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
                {props.orders ? (
                    <>
                        {props.orders.map(props.prop1)}
                    </>
                ) : (
                    <></>
                )}
            </table>
        </div>
    </section>;
}

OrdersHistory.propTypes = {
    orders: PropTypes.any,
    prop1: PropTypes.func
};
const AdminOrdersHistory = () => {

    const user = useUser();
    const [orders, setOrders] = useState(null);

    useEffect(() => {
        ajax(`${baseURL}api/orders/history/admin`, "GET", user.jwt)
            .then(ordersData => {
                setOrders(ordersData);
            });
    }, [user.jwt]);

    return (
        <OrdersHistory orders={orders} prop1={order => (
            <tr id={order.id}>
                <td>{order.dateOfDispatch}</td>
                <td>{order.dateOfReceipt}</td>
                <td>{Number(order.totalPrice).toFixed(2)}</td>
                <td>{order.statusOrder}</td>
                <td>{order.username}</td>
            </tr>
        )}/>
    )
}

export default AdminOrdersHistory;