import {useUser} from "../../UserProvider/UserProvider";
import {useEffect, useState} from "react";
import ajax from "../../Services/FetchService";

const OrderWindow = () => {

    const user = useUser();
    const baseUrl = "http://localhost:8080/";
    const [order, setOrder] = useState([]);
    const [dialogVisible, setDialogVisible] = useState(false);

    useEffect(() => {
        ajax(`${baseUrl}api/orders/status`, "GET", user.jwt)
            .then(result => {
                setOrder(result);
                if (result.length > 0) {
                    setDialogVisible(true);
                }
            })
    },[user.jwt])

    return(
        <main>
            {dialogVisible && (
                <div className="order-window">
                    <h5>Вие имате незавършена или недоставена поръчка</h5>
                    <p>Можете да завршите вашата поръчка от тук: <button>Завърши</button></p>
                    <p>Да следите статуса на вашата доставка от тук: <button>Проследи</button></p>
                </div>
            )}
        </main>
    )

}

export default OrderWindow;