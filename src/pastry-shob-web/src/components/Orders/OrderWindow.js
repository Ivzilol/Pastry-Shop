import {useUser} from "../../UserProvider/UserProvider";
import {useEffect, useState} from "react";
import ajax from "../../Services/FetchService";

const OrderWindow = () => {

    const user = useUser();
    const baseUrl = "http://localhost:8080/";
    const [order, setOrder] = useState(null);
    const [dialogVisible, setDialogVisible] = useState(false);
    const [dialogVisibleConfirmed, setDialogVisibleConfirmed] = useState(false);


    useEffect(() => {
        ajax(`${baseUrl}api/orders/status`, "GET", user.jwt)
            .then(result => {
                setOrder(result);
                if (result.length > 0) {
                    setDialogVisible(true);
                }
            })
    }, [user.jwt])

    useEffect(() => {
        ajax(`${baseUrl}api/orders/status/confirmed`, "GET", user.jwt)
            .then(result => {
                setOrder(result);
                if (result.length > 0) {
                    setDialogVisibleConfirmed(true);
                }
            })
    }, [user.jwt])


    return (
        <main>
            {dialogVisible && (
                <div className="order-window">
                    <p>Можете да завършите вашата поръчка от тук: <button
                        onClick={() => {
                            window.location.href = "/orders";
                        }}
                    >Завърши</button></p>
                </div>
            )}
            {dialogVisibleConfirmed && (
                <div className="order-window">
                    <p>Можете да следите статуса на вашата доставка от тук: <button
                        onClick={() => {
                            window.location.href = "/orders/tracking";
                        }}
                    >Проследи</button></p>
                </div>
            )}
        </main>
    )

}

export default OrderWindow;