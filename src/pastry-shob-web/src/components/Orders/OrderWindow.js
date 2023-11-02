import {useUser} from "../../UserProvider/UserProvider";
import {useEffect, useState} from "react";
import ajax from "../../Services/FetchService";
import {useTranslation} from "react-i18next";
import baseURL from "../BaseURL/BaseURL";

const OrderWindow = () => {

    const user = useUser();
    const [order, setOrder] = useState(null);
    const [dialogVisible, setDialogVisible] = useState(false);
    const [dialogVisibleConfirmed, setDialogVisibleConfirmed] = useState(false);
    const {t} = useTranslation();

    useEffect(() => {
        if (user.jwt) {
            ajax(`${baseURL}api/orders/status`, "GET", user.jwt)
                .then(result => {
                    setOrder(result);
                    if (result.length > 0) {
                        setDialogVisible(true);
                    }
                });
        }
    }, []);

    useEffect(() => {
        if (user.jwt) {
            ajax(`${baseURL}api/orders/status/confirmed`, "GET", user.jwt)
                .then(result => {
                    setOrder(result);
                    if (result.length > 0) {
                        setDialogVisibleConfirmed(true);
                    }
                });
        }
    }, []);


    return (
        <main>
            {dialogVisible && (
                <div className="order-window">
                    <p>{t('order-window.finish')}<button
                        onClick={() => {
                            window.location.href = "/orders";
                        }}
                    >{t('order-window.finish-button')}</button></p>
                </div>
            )}
            {dialogVisibleConfirmed && (
                <div className="order-window">
                    <p>{t('order-window.tracking')} <button
                        onClick={() => {
                            window.location.href = "/orders/tracking";
                        }}
                    >{t('order-window.tracking-button')}</button></p>
                </div>
            )}
        </main>
    )

}

export default OrderWindow;