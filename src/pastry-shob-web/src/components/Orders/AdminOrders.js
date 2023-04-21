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
    let currentKeyOrder;


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
                                key={orders.id}>
                                {orders.map((order) => (
                                    currentKeyOrder !== order.keyOrderProduct
                                        ?
                                        <div className="admin-bord-section-details"
                                            id={order.keyOrderProduct}
                                        >{currentKeyOrder = order.keyOrderProduct}
                                            <h5>Username: {order.users.username}</h5>
                                            <p>Product: {order.productName}</p>
                                            <p>Price: {order.price}</p>
                                            <p>Address: {order.users.address}</p>
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
                    <h2>Confirmed orders</h2>
                    <ul className="confirmed-orders-list">

                    </ul>
                </article>
            </section>
        </main>


        // <main className="orders-admin">
        //     <NavBar/>
        //     {orders ? (
        //         <article className="orders-admin-container">
        //             {orders.map((order) => (
        //                 currentUser !== order.users.username
        //                     ?
        //                     <div>{currentUser = order.users.username}
        //                         <div className="orders-admin-container-items"
        //                              key={order.id}
        //                         >
        //                             <h4>User name: {order.users.username}</h4>
        //                             {orders ? (
        //                                 <div key={order.users.id}>
        //                                     {orders.map((userOrder) =>
        //                                         userOrder.users.username === order.users.username
        //                                             ?
        //                                             <div className="orders-admin-container-items-userOrder">
        //                                                 <div className="orders-admin-container-p.name">Product
        //                                                     name: {userOrder.productName}</div>
        //                                                 <div className="orders-admin-container-p.price"> Price: {userOrder.price}</div>
        //
        //                                             </div>
        //                                             :
        //                                             <></>
        //                                     )}
        //                                     <div className="orders-admin-container-address"
        //                                     >Address: {order.users.address}</div>
        //                                     <button
        //                                         onClick={() => startProcessingOrder(order.users.id)}
        //                                     >Make order in Progress</button>
        //                                 </div>
        //                             ) : (
        //                                 <></>
        //                             )}
        //                         </div>
        //                     </div>
        //                     :
        //                     <></>
        //             ))}
        //         </article>
        //     ) : (
        //         <h4>No Orders for processing</h4>
        //     )}
        // </main>
    )
}
export default AdminOrders;