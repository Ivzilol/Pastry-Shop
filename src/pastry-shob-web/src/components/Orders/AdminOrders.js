import {useUser} from "../../UserProvider/UserProvider";
import {useEffect, useState} from "react";
import ajax from "../../Services/FetchService";
import {useNavigate} from "react-router-dom";
import NavBar from "../NavBar/NavBar";

const AdminOrders = () => {
    const user = useUser();
    const [orders, setOrders] = useState(null);
    let navigate = useNavigate();
    let currentUser;
    useEffect(() => {
        ajax('/api/orders/admin', "GET", user.jwt)
            .then(ordersData => {
                setOrders(ordersData)
            });
        if (!user.jwt) navigate('/login')
    }, [user.jwt])


    function startProcessingOrder(id) {
        ajax(`/api/orders/admin/${id}`, "POST", user.jwt)
    }

    return (
        <main className="orders-admin">
            <NavBar/>
            {orders ? (
                <article className="orders-admin-container">
                    {orders.map((order) => (
                        currentUser !== order.users.username
                            ?
                            <div>{currentUser = order.users.username}
                                <div className="orders-admin-container-items"
                                     key={order.id}
                                >
                                    <h4>User name: {order.users.username}</h4>
                                    {orders ? (
                                        <div key={order.users.id}>
                                            {orders.map((userOrder) =>
                                                userOrder.users.username === order.users.username
                                                    ?
                                                    <div className="orders-admin-container-items-userOrder">
                                                        <div className="orders-admin-container-p.name">Product
                                                            name: {userOrder.productName}</div>
                                                        <div className="orders-admin-container-p.price"> Price:
                                                             {userOrder.price}</div>
                                                        <div className="orders-admin-container-address">Address:
                                                             {userOrder.users.address}</div>
                                                    </div>
                                                    :
                                                    <></>
                                            )}
                                            <button
                                            >Make order in Progress</button>
                                        </div>
                                    ) : (
                                        <></>
                                    )}
                                </div>
                            </div>
                            :
                            <></>
                    ))}
                </article>
            ) : (
                <h4>No Orders for processing</h4>
            )}
        </main>
    )
}
export default AdminOrders;