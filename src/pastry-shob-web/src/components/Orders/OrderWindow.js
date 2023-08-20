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
                console.log(result)
            })
    },[user.jwt])

    return(
        <main>
            {dialogVisible && (
                <div></div>
            )}
        </main>
    )

}

export default OrderWindow;