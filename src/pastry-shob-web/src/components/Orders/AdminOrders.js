import {useUser} from "../../UserProvider/UserProvider";
import {useEffect, useState} from "react";
import ajax from "../../Services/FetchService";
import {useNavigate} from "react-router-dom";
import NavBar from "../NavBar/NavBar";

const AdminOrders = () => {
    const user = useUser();
    const [orders, setOrders] = useState(null);
    let navigate = useNavigate();
    useEffect(() => {
        ajax('/api/orders/admin', "GET", user.jwt)
            .then(ordersData => {
                setOrders(ordersData)
            });
        if (!user.jwt) navigate('/login')
    }, [user.jwt])


    return (
        <main className="orders-admin">
            <NavBar/>
            {orders ? (
                <article className="orders-admin-container">
                    {orders.map((order) => (
                        <div className="orders-admin-container-items"
                             key={order.id}
                        >
                            <h4>User name: {order.users.username}</h4>
                            {orders ? (
                                <div key={order.users.id}>
                                    {orders.map((userOrder) =>
                                    userOrder.users.username === order.users.username
                                        ?
                                        <p>Product name: {userOrder.productName}</p>
                                        :
                                        <></>
                                    )}
                                </div>

                            ) : (
                                <></>
                            )}
                        </div>
                    ))}
                </article>
            ) : (
                <h4>No Orders for processing</h4>
            )}
        </main>
    )
}
export default AdminOrders;