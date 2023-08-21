import {useUser} from "../../UserProvider/UserProvider";
import {useEffect, useState} from "react";
import ajax from "../../Services/FetchService";

const OrderWindowAdmin = () => {
    const user = useUser();
    const baseUrl = "http://localhost:8080/";
    const [order, setOrder] = useState(null);
    const [dialogVisibleConfirmed, setDialogVisibleConfirmed] = useState(false);

    useEffect(() => {
        ajax(`${baseUrl}api/orders/status/confirmed/admin`, "GET", user.jwt)
            .then(result => {
                setOrder(result);
                if (result.length > 0) {
                    setDialogVisibleConfirmed(true);
                }
            })
    }, [user.jwt])

    return (
        <main>
            {dialogVisibleConfirmed && (
                <div className="order-window">
                    <p>Има необработена заявка <button
                        onClick={() => {
                            window.location.href = "/orders";
                        }}
                    >Обработи</button></p>
                </div>
            )}
        </main>
    )
}
export default OrderWindowAdmin;