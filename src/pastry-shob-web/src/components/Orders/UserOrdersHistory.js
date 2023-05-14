import {useEffect, useState} from "react";
import ajax from "../../Services/FetchService";
import {useUser} from "../../UserProvider/UserProvider";
import NavBar from "../NavBarAdmin/NavBarAdmin";

const UserOrdersHistory = () => {

    const user = useUser();
    const [orders, setOrders] = useState(null);

    useEffect(() => {
        ajax("/api/orders/history/user", "GET", user.jwt)
            .then(ordersData => {
                setOrders(ordersData);
            });
    }, [user.jwt]);

    return (
        <section className="ordersUser">
            <NavBar/>
            {orders ? (
                <h3>История на поръчките</h3>
            ) : (
                <></>
            )}
        </section>
    )
}

export default UserOrdersHistory;