import {useUser} from "../../UserProvider/UserProvider";
import React, {useEffect, useState} from "react";
import {useNavigate} from "react-router-dom";
import ajax from "../../Services/FetchService";
import NavBar from "../NavBar/NavBar";
import ShopArt from "../ShopArt/ShopArt";
import Footer from "../Footer/Footer";
import OrderWindow from "../Orders/OrderWindow";
import {useTranslation} from "react-i18next";
import baseURL from "../BaseURL/BaseURL";

const ShopsViewUser = () => {

    const user = useUser();
    const [shops, setShops] = useState(null);
    let navigate = useNavigate();
    const {t} = useTranslation();

    useEffect(() => {
        ajax(`${baseURL}api/shops`, "GET", user.jwt)
            .then(shopData => {
                setShops(shopData);
            });
        // if (!user.jwt) navigate("/login");
    }, [navigate, user.jwt]);


    return (

        <main className="shops-view">
            <OrderWindow/>
            <NavBar/>
            <ShopArt/>
            {shops ? (
                <section className="shops-view-container">
                    {shops.map((shops) => (
                        <article
                            key={shops.id}
                        >
                            <h3 className="shops-view-container-comment-title">
                                {t('shops-view.h3')}</h3>
                            <button className="shops-view-button"
                                    id="submit"
                                    type="button"
                                    onClick={() => {
                                        user.jwt
                                            ?
                                            window.location.href = `/shops/${shops.id}`
                                            :
                                            alert("To comment please log in with your account");
                                        navigate("/login")
                                    }}
                            >
                                {t('shops-view.button')}
                            </button>
                        </article>
                    ))}
                </section>
            ) : (
                <></>
            )}
            <Footer/>
        </main>
    );
}

export default ShopsViewUser;