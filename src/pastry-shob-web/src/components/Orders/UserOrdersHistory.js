import {useEffect, useState} from "react";
import ajax from "../../Services/FetchService";
import {useUser} from "../../UserProvider/UserProvider";
import NavBar from "../NavBarAdmin/NavBarAdmin";

const UserOrdersHistory = () => {

    const user = useUser();
    const [orders, setOrders] = useState(null);

    useEffect(() => {
        ajax("/orders/history/user", "GET", user.jwt)
            .then(ordersData => {
                setOrders(ordersData);
            });
    });

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