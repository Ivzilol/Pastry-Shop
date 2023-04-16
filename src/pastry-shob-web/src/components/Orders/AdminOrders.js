import {useUser} from "../../UserProvider/UserProvider";
import {useEffect, useState} from "react";
import ajax from "../../Services/FetchService";
import {useNavigate} from "react-router-dom";
import {Navbar} from "react-bootstrap";

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


    return(
        <main className="orders-admin">
            <Navbar/>
            {orders ? (
                <article className="orders-admin-container">
                    {orders.map((order) => (
                        <div className="orders-admin-container-items"
                            key={order.id}
                        >

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