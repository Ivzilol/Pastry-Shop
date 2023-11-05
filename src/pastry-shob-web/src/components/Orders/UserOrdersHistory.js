import {useEffect, useState} from "react";
import ajax from "../../Services/FetchService";
import {useUser} from "../../UserProvider/UserProvider";
import NavBar from "../NavBar/NavBar";
import {useTranslation} from "react-i18next";
import baseURL from "../BaseURL/BaseURL";

const UserOrdersHistory = () => {

    const user = useUser();
    const [orders, setOrders] = useState(null);
    const {t} = useTranslation();

    useEffect(() => {
        ajax(`${baseURL}api/orders/history/user`, "GET", user.jwt)
            .then(ordersData => {
                setOrders(ordersData);
            });
    }, [user.jwt]);

    return (
        <section className="ordersUser">
            <NavBar/>
            <h3 className="admin-users-container-title">{t('ordersUser')}</h3>
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
                                    <td>{order.dateOfReceipt}</td>
                                    <td>{order.dateOfDispatch}</td>
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
    )
}

export default UserOrdersHistory;